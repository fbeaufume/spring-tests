package com.adeliosys.sample.test;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A JUnit 5 extension that tracks the usage of Spring application contexts by test classes.
 */
public class SpringContextTrackerExtension implements BeforeAllCallback, TestExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringContextTrackerExtension.class);

    /**
     * In the usage summary, the maximum displayed tests classes for each Spring context.
     */
    public static final int MAX_DISPLAYED = 5;

    /**
     * For each Spring application context, the test classes using it.
     * Application contexts are identified by their startup date (the Long key of the map). It is usually good enough.
     */
    private static final Map<Long, List<String>> springContextDescriptors = new HashMap<>();

    @Override
    public void beforeAll(ExtensionContext context) {
        ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);
        springContextDescriptors.computeIfAbsent(applicationContext.getStartupDate(), k -> new ArrayList<>())
                // Or use context.getRequiredTestClass().getName() for FQCN instead of display name
                .add(context.getDisplayName());
    }

    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        logSpringContextUsage();
    }

    /**
     * For each Spring application context, log the test classes using it.
     */
    private void logSpringContextUsage() {
        LOGGER.info("Spring contexts usage:");
        AtomicInteger index = new AtomicInteger(0);
        springContextDescriptors.entrySet().stream()
                // Sort by decreasing number of test classes
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .forEach(entry -> {
                            // For brevity, do not display all test classes
                            List<String> testClasses = new ArrayList<>(entry.getValue().stream().limit(MAX_DISPLAYED).toList());
                            if (entry.getValue().size() > MAX_DISPLAYED) {
                                testClasses.add("...");
                            }

                            String testClassesSummary = String.join(", ", testClasses);

                            LOGGER.info("- Context #{} is used by {} test class{}: {}",
                                    index.incrementAndGet(),
                                    entry.getValue().size(),
                                    entry.getValue().size() > 1 ? "es" : "",
                                    testClassesSummary);
                        }
                );
    }
}

package com.adeliosys.sample.test;

import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This JUnit listener logs the duration of the execution of the tests.
 * The class is declared in the META-INF/services/org.junit.platform.launcher.TestExecutionListener file
 * and also requires the org.junit.platform:junit-platform-launcher Maven test dependency.
 * <p>
 * Note that Spring Test also provides a TestExecutionListener interface, but it is not the same as the one from JUnit.
 */
public class DurationListener implements TestExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DurationListener.class);

    private static long startTimestamp = 0;

    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {
        startTimestamp = System.currentTimeMillis();
    }

    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        long duration = System.currentTimeMillis() - startTimestamp;
        LOGGER.info("Tests executed in {} s", "%d.%03d".formatted(duration / 1000, duration % 1000));
    }
}

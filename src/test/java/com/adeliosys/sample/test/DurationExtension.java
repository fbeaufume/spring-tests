package com.adeliosys.sample.test;

import org.junit.jupiter.api.extension.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This simple JUnit 5 extension logs the duration of each test method and the whole test class.
 */
public class DurationExtension implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback, AfterEachCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(DurationExtension.class);

    private long classTimestamp;

    private long methodTimestamp;

    @Override
    public void beforeAll(ExtensionContext context) {
        classTimestamp = System.currentTimeMillis();
    }

    @Override
    public void afterAll(ExtensionContext context) {
        LOGGER.info("Executed class {} in {} ms",
                context.getDisplayName(),
                System.currentTimeMillis() - classTimestamp);
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        methodTimestamp = System.currentTimeMillis();
    }

    @Override
    public void afterEach(ExtensionContext context) {
        LOGGER.info("Executed method {}.{} in {} ms",
                context.getParent().map(ExtensionContext::getDisplayName).orElse(""),
                context.getDisplayName(),
                System.currentTimeMillis() - methodTimestamp);
    }
}

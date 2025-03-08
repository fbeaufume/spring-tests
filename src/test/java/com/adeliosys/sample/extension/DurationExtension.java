package com.adeliosys.sample.extension;

import org.junit.jupiter.api.extension.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DurationExtension implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback, AfterEachCallback {

    private static final Logger logger = LoggerFactory.getLogger(DurationExtension.class);

    private long classTimestamp;

    private long methodTimestamp;

    @Override
    public void beforeAll(ExtensionContext context) {
        classTimestamp = System.currentTimeMillis();
    }

    @Override
    public void afterAll(ExtensionContext context) {
        logger.info("Executed class '{}' in {} ms",
                context.getDisplayName(),
                System.currentTimeMillis() - classTimestamp);
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        methodTimestamp = System.currentTimeMillis();
    }

    @Override
    public void afterEach(ExtensionContext context) {
        logger.info("Executed method '{}.{}' in {} ms",
                context.getParent().map(ExtensionContext::getDisplayName).orElse(""),
                context.getDisplayName(),
                System.currentTimeMillis() - methodTimestamp);
    }
}

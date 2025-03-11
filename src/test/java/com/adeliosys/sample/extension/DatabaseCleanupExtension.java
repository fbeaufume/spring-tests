package com.adeliosys.sample.extension;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * This JUnit 5 extension verifies that the application tables are left empty by the test classes.
 */
public class DatabaseCleanupExtension implements AfterAllCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseCleanupExtension.class);

    @Override
    public void afterAll(ExtensionContext context) {
        ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);

        boolean success = true;

        // Lookup all application repositories from Spring
        Map<String, JpaRepository> repositories = applicationContext.getBeansOfType(JpaRepository.class);

        for (Map.Entry<String, JpaRepository> entry : repositories.entrySet()) {
            // We could implement here a mechanism that skips some selected repositories

            // Check that the table is empty
            long count = entry.getValue().count();
            if (count > 0) {
                LOGGER.error("Repository '{}' found {} row{} instead of 0", entry.getKey(), count, count > 1 ? "s" : "");
                success = false;
            }
        }

        // Log the result
        if (success) {
            LOGGER.info("Database cleanup is ok");
        }
        else {
            fail("Database cleanup verifications failed");
        }
    }
}

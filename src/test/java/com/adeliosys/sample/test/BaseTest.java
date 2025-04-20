package com.adeliosys.sample.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Base class for some integration test classes.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles(value = "test", resolver = CustomActiveProfilesResolver.class)
@ExtendWith({SpringContextTrackerExtension.class, DatabaseCleanupCheckExtension.class, DurationExtension.class})
public abstract class BaseTest {

    @Autowired
    protected MockMvc mockMvc;

    // Not used, this is only for demonstration purpose
    @Autowired
    protected ObjectMapper objectMapper;

    // Not used, this is only for demonstration purpose
    @Autowired
    protected ResourceLoader resourceLoader;
}

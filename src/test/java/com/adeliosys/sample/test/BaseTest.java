package com.adeliosys.sample.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.context.WebApplicationContext;
import tools.jackson.databind.ObjectMapper;

/**
 * Base class for integration test classes.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
@ActiveProfiles(value = "test", resolver = CustomActiveProfilesResolver.class)
@ExtendWith({SpringContextTrackerExtension.class, DatabaseCleanupCheckExtension.class, DurationExtension.class})
public abstract class BaseTest {

    @Autowired
    protected RestTestClient clientRtc;

    protected RestTestClient serverRtc;

    @BeforeEach
    void baseBeforeEach(WebApplicationContext context) {
        serverRtc = RestTestClient.bindToApplicationContext(context).build();
    }

    // Not used, this is only for demonstration purpose
    @Autowired
    protected ObjectMapper objectMapper;

    // Not used, this is only for demonstration purpose
    @Autowired
    protected ResourceLoader resourceLoader;
}

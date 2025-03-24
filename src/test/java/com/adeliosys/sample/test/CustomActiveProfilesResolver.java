package com.adeliosys.sample.test;

import org.springframework.test.context.ActiveProfilesResolver;
import org.springframework.test.context.support.DefaultActiveProfilesResolver;

import java.util.Optional;

/**
 * This custom ActiveProfilesResolver is used to override the active profiles used by integration tests.
 */
public class CustomActiveProfilesResolver implements ActiveProfilesResolver {

    private final DefaultActiveProfilesResolver defaultActiveProfilesResolver = new DefaultActiveProfilesResolver();

    @Override
    public String[] resolve(Class<?> testClass) {
        return Optional.ofNullable(System.getProperty("test.profiles"))
                .map(p -> p.split("\\s*,\\s*"))
                .orElseGet(() -> defaultActiveProfilesResolver.resolve(testClass));
    }
}

# Integration tests with Spring

> This is the Spring Boot 4 variant of the sample application.
> For the original Spring Boot 3 variant, checkout the `spring-boot-3` tag.

This repository is a sample application for my
[Getting started with Spring tests](https://beaufume.fr/articles/spring-tests/),
[JUnit extensions for Spring tests](https://beaufume.fr/articles/spring-tests-extensions/) and
[Switching between H2 and Testcontainers in Spring tests](https://beaufume.fr/articles/spring-tests-database/)
articles.

This simple Spring Boot web application shows several opinionated test practices.

The test classes are :

- `ItemServiceTest`: service integration tests
- `ItemControllerServerTest`: "server web" integration tests using `RestTestClient`
- `ItemControllerClientTest`: "client web" integration tests using `RestTestClient`
- `ItemControllerSliceTest`: "web slice" component tests using `RestTestClient`
- `ItemTest`: unit tests, does not use Spring at all

The test support classes are :

- `BaseTest`: base class for several Spring test classes
- `DurationExtension`: JUnit extension to measure test execution time
- `DurationListener`: JUnit listener that logs the total tests execution duration
- `DatabaseCleanupCheckExtension`: JUnit extension that verifies that the database is cleared after each test class
- `SpringContextTrackerExtension`: JUnit extension that tracks Spring application context usage by test classes

For extra information about Spring application context caching in tests, see
[Context Caching](https://docs.spring.io/spring-framework/reference/testing/testcontext-framework/ctx-management/caching.html).

To run the tests using H2 execute `mvn test`.

To run the tests using PostgreSQL execute `mvn test -Dtest.profiles=test,postgres`.
Note that this uses Testcontainers, and so requires Docker.

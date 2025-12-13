# Integration tests with Spring

This repository is a sample application for my
[Getting started with Spring tests](https://beaufume.fr/articles/spring-tests/) and
[Switching between H2 and Testcontainers in Spring tests](https://beaufume.fr/articles/spring-tests-database/)
articles.

This simple Spring Boot web application shows several opinionated best practices for Spring tests.

The test classes are :

- `ItemServiceTest`: an example of service tests
- `ItemControllerServerTest`: an example of "server" web integration tests for a REST controller using MockMvc
- `ItemControllerClientTest`: an example of "client" web integration tests for a REST controller using REST Assured
- `ItemControllerSliceTest`: an example of a "web slice" integration tests for a REST controller (uses a dedicated Spring application context)
- `ItemTest`: basic non-Spring tests

The support test classes are :

- `BaseTest`: a base class for several integration test classes
- `DurationExtension`: a JUnit 5 extension to measure test execution time
- `DurationListener`: a JUnit 5 listener that logs the total tests execution duration
- `DatabaseCleanupCheckExtension`: a JUnit 5 extension that verifies that the database is cleared after each test class
- `SpringContextTrackerExtension`: a JUnit 5 extension to track Spring application context usage over test classes

For extra information about Spring application context caching in tests, see [Context Caching](https://docs.spring.io/spring-framework/reference/testing/testcontext-framework/ctx-management/caching.html).

To run the tests using H2 execute `mvn test`.

To run the tests using PostgreSQL execute `mvn test -Dtest.profiles=test,postgres`.
Note that this uses Testcontainers, and so requires Docker.

This application also uses JUnit Insights to generate some test execution metrics, see HTML files in
`target/junit-insights-reports`.

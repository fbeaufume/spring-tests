# Integration tests with Spring

This simple Spring Boot web application shows several opinionated best practices for Spring tests:

- `ItemControllerServerTest`: an example of "server" web integration test for a REST controller using MockMvc
- `ItemControllerClientTest`: an example of "client" web integration test for a REST controller using REST Assured
- `BaseTest`: a base integration test class for the above test classes
- `ItemControllerSliceTest`: an example of a "web slice" integration test for a REST controller (uses a dedicated Spring application context)
- `SumTest`: a sample parameterized unit test class

For extra information about Spring application context caching in tests, see [Context Caching](https://docs.spring.io/spring-framework/reference/testing/testcontext-framework/ctx-management/caching.html).

To run the tests using H2 execute `mvn test`.

To run the tests using PostgreSQL execute `mvn test -Dtest.profiles=postgres`.
Note that this uses Testcontainers, and so requires Docker.

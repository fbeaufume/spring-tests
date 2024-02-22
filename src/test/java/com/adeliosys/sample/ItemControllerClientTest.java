package com.adeliosys.sample;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

/**
 * "Client" (i.e. with a real HTTP call) integration tests for the item controller.
 */
// No need for @Transactional since no test method modifies the database
class ItemControllerClientTest extends BaseTest {

    @LocalServerPort
    private int port;

    @BeforeAll
    static void beforeAll(@Autowired ItemRepository itemRepository) {
        itemRepository.saveAll(List.of(new Item("item1", 10), new Item("item2", 20)));
    }

    @AfterAll
    static void afterAll(@Autowired ItemRepository itemRepository) {
        itemRepository.deleteAll();
    }

    @BeforeEach
    public void beforeEach() {
        RestAssured.port = port;
    }

    @Test
    public void getItems() {
        given().get("/items")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("[0].name", is("item1"))
                .body("[0].price", is(10))
                .body("[1].name", is("item2"))
                .body("[1].price", is(20));
    }
}

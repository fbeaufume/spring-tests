package com.adeliosys.sample.item;

import com.adeliosys.sample.test.BaseTest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * "Client" (i.e. with a real HTTP call) integration tests for the item controller.
 */
// No need for @Transactional since no test method modifies the database
class ItemControllerClientTest extends BaseTest {

    @LocalServerPort
    private int port;

    @BeforeAll
    static void beforeAll(@Autowired ItemRepository itemRepository) {
        itemRepository.save(new Item("Item 1", 10));
    }

    @AfterAll
    static void afterAll(@Autowired ItemRepository itemRepository) {
        itemRepository.deleteAll();
    }

    @BeforeEach
    public void beforeEach() {
        RestAssured.port = port;
    }

    /**
     * Check the response using path-oriented validation.
     */
    @Test
    public void getItems1() {
        given().get("/items")
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].name", is("Item 1"))
                .body("[0].price", is(10));

    }

    /**
     * Check the response using DTO-oriented validation.
     */
    @Test
    public void getItems2() {
        List<ItemDto> items = given().get("/items").as(new TypeRef<>() {
        });
        assertEquals(1, items.size());
        assertEquals("Item 1", items.get(0).name());
        assertEquals(10, items.get(0).price());
    }
}

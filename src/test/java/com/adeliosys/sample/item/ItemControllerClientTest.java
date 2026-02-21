package com.adeliosys.sample.item;

import com.adeliosys.sample.test.BaseTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * "Client" (i.e. with a real HTTP call) integration tests for the item controller.
 */
// No need for @Transactional since no test method modifies the database
class ItemControllerClientTest extends BaseTest {

    @BeforeAll
    static void beforeAll(@Autowired ItemRepository itemRepository) {
        itemRepository.save(new Item("Item 1", 10));
    }

    @AfterAll
    static void afterAll(@Autowired ItemRepository itemRepository) {
        itemRepository.deleteAll();
    }

    @Test
    public void getItems() {
        clientRtc.get().uri("/items")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].name").isEqualTo("Item 1")
                .jsonPath("$[0].price").isEqualTo(10);
    }
}

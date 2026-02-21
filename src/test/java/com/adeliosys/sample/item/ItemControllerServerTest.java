package com.adeliosys.sample.item;

import com.adeliosys.sample.test.BaseTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * "Server" (i.e. with no real HTTP call) integration tests for the item controller.
 */
@Transactional
class ItemControllerServerTest extends BaseTest {

    @Autowired
    private ItemRepository itemRepository;

    @BeforeAll
    static void beforeAll(@Autowired ItemRepository itemRepository) {
        itemRepository.save(new Item("Item 1", 10));
    }

    @AfterAll
    static void afterAll(@Autowired ItemRepository itemRepository) {
        // Only used to show that @Transactional reverted the item inserted during createItem test method
        assertEquals(1, itemRepository.count());

        itemRepository.deleteAll();
    }

    /**
     * Check the response using path-oriented validation.
     */
    @Test
    public void getItems1() {
        serverRtc.get().uri("/items")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].name").isEqualTo("Item 1")
                .jsonPath("$[0].price").isEqualTo(10);
    }

    /**
     * Check the response using DTO-oriented validation.
     */
    @Test
    public void getItems2() throws Exception {
        List<ItemDto> items = serverRtc.get().uri("/items")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<ItemDto>>() {
                })
                .returnResult()
                .getResponseBody();

        assertEquals(1, items.size());
        assertEquals("Item 1", items.get(0).name());
        assertEquals(10, items.get(0).price());
        ;
    }

    @Test
    public void createItem() {
        serverRtc.post().uri("/items").body(new Item("Item 2", 20)).exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isNumber();

        List<Item> items = itemRepository.findAll();
        assertEquals(2, items.size());
        assertEquals("Item 2", items.get(1).getName());
        assertEquals(20, items.get(1).getPrice());
    }
}

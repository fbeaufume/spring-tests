package com.adeliosys.sample.item;

import com.adeliosys.sample.test.BaseTest;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void getItems1() throws Exception {
        mockMvc.perform(get("/items"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Item 1")))
                .andExpect(jsonPath("$[0].price", is(10)));
    }

    /**
     * Check the response using DTO-oriented validation.
     */
    @Test
    public void getItems2() throws Exception {
        List<ItemDto> items = objectMapper.readValue(mockMvc.perform(get("/items"))
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertEquals(1, items.size());
        assertEquals("Item 1", items.get(0).name());
        assertEquals(10, items.get(0).price());
    }

    @Test
    public void createItem() throws Exception {
        mockMvc.perform(post("/items")
                        .content("{ \"name\":\"Item 2\", \"price\":20 }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", notNullValue()));

        List<Item> items = itemRepository.findAll();
        assertEquals(2, items.size());
        assertEquals("Item 2", items.get(1).getName());
        assertEquals(20, items.get(1).getPrice());
    }
}

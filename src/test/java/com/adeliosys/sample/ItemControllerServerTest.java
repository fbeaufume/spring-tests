package com.adeliosys.sample;

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
        itemRepository.saveAll(List.of(new Item("item1", 10), new Item("item2", 20)));
    }

    @AfterAll
    static void afterAll(@Autowired ItemRepository itemRepository) {
        // Only used to show that @Transactional reverted the item inserted during createItem test method
        assertEquals(2, itemRepository.count());

        itemRepository.deleteAll();
    }

    @Test
    public void getItems() throws Exception {
        // Path-oriented testing
        mockMvc.perform(get("/items"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("item1")))
                .andExpect(jsonPath("$[0].price", is(10)))
                .andExpect(jsonPath("$[1].name", is("item2")))
                .andExpect(jsonPath("$[1].price", is(20)));

        // DTO-oriented testing
        List<ItemDto> items = objectMapper.readValue(mockMvc.perform(get("/items"))
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertEquals(2, items.size());
        assertEquals("item1", items.get(0).name());
        assertEquals(10, items.get(0).price());
        assertEquals("item2", items.get(1).name());
        assertEquals(20, items.get(1).price());
    }

    @Test
    public void createItem() throws Exception {
        mockMvc.perform(post("/items")
                        .content("{ \"name\":\"item3\", \"price\":30 }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", notNullValue()));

        List<Item> items = itemRepository.findAll();
        assertEquals(3, items.size());
        assertEquals("item3", items.get(2).getName());
        assertEquals(30, items.get(2).getPrice());
    }
}

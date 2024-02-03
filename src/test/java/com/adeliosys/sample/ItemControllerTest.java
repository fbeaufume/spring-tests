package com.adeliosys.sample;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void beforeAll(@Autowired ItemRepository itemRepository) {
        itemRepository.saveAll(List.of(new Item("item1", 10), new Item("item2", 20)));
    }

    @AfterAll
    static void afterAll(@Autowired ItemRepository itemRepository) {
        itemRepository.deleteAll();
    }

    @Test
    void getItems() throws Exception {
        mockMvc.perform(get("/items"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("item1")))
                .andExpect(jsonPath("$[0].price", is(10)))
                .andExpect(jsonPath("$[1].name", is("item2")))
                .andExpect(jsonPath("$[1].price", is(20)));
    }
}

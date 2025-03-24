package com.adeliosys.sample.item;

import com.adeliosys.sample.test.CustomActiveProfilesResolver;
import com.adeliosys.sample.test.DurationExtension;
import com.adeliosys.sample.test.SpringContextTrackerExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Web slice test for the item controller.
 */
@WebMvcTest
@ActiveProfiles(value = "test", resolver = CustomActiveProfilesResolver.class)
@ExtendWith({SpringContextTrackerExtension.class, DurationExtension.class})
class ItemControllerSliceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Test
    public void getItems() throws Exception {
        Mockito.when(itemService.getItems()).thenReturn(List.of(new Item("item1", 10)));

        mockMvc.perform(get("/items"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("item1")))
                .andExpect(jsonPath("$[0].price", is(10)));
    }
}

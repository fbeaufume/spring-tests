package com.adeliosys.sample.item;

import com.adeliosys.sample.test.CustomActiveProfilesResolver;
import com.adeliosys.sample.test.DurationExtension;
import com.adeliosys.sample.test.SpringContextTrackerExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.List;

/**
 * Web slice test for the item controller.
 */
@WebMvcTest(ItemController.class)
@AutoConfigureRestTestClient
@ActiveProfiles(value = "test", resolver = CustomActiveProfilesResolver.class)
@ExtendWith({SpringContextTrackerExtension.class, DurationExtension.class})
class ItemControllerSliceTest {

    @Autowired
    private RestTestClient client;

    @MockitoBean
    private ItemService itemService;

    @Test
    public void getItems() {
        Mockito.when(itemService.getItems()).thenReturn(List.of(new Item("Item 1", 10)));

        client.get().uri("/items")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].name").isEqualTo("Item 1")
                .jsonPath("$[0].price").isEqualTo(10);
    }
}

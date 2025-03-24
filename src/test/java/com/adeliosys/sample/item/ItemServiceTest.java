package com.adeliosys.sample.item;

import com.adeliosys.sample.test.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
public class ItemServiceTest extends BaseTest {

    @Autowired
    private ItemService itemService;

    @Test
    public void getItems() {
        assertEquals(0, itemService.getItems().size());
    }

    @Test
    public void createItem() {
        itemService.createItem(new Item("item", 1));

        List<Item> items = itemService.getItems();
        assertEquals(1, items.size());
        assertEquals("item", items.get(0).getName());
        assertEquals(1, items.get(0).getPrice());
    }
}

package com.adeliosys.sample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the item entity.
 * The tests are not very interesting, they only serve as examples
 */
public class ItemTest {

    @Test
    public void setName() {
        Item item = new Item();
        item.setName("Some name");
        assertEquals("Some name", item.getName());
    }

    @Test
    public void setPrice() {
        Item item = new Item();
        item.setPrice(12);
        assertEquals(12, item.getPrice());
    }
}

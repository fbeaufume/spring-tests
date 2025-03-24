package com.adeliosys.sample.item;

import com.adeliosys.sample.test.DurationExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the item entity.
 * The tests are not very interesting, they only serve as examples.
 */
@ExtendWith(DurationExtension.class)
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

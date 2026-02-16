package com.adeliosys.sample.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemService.class);

    @Autowired
    private ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public List<Item> getItems() {
        LOGGER.info("Loading all items");
        return itemRepository.findAll();
    }

    public Item createItem(Item item) {
        LOGGER.info("Creating an item");
        return itemRepository.save(item);
    }
}

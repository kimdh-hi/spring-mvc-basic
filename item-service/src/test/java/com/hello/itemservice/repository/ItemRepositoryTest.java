package com.hello.itemservice.repository;

import com.hello.itemservice.domain.item.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void clear() {
        itemRepository.clear();
    }

    @Test
    void save() {
        //given
        Item itemA = new Item("itemA", 1000, 10);
        //when
        Item savedItem = itemRepository.save(itemA);
        //then
        Assertions.assertThat(itemA).isEqualTo(savedItem);
    }

    @Test
    void findById() {
        //given
        Item itemA = new Item("itemA", 1000, 10);
        //when
        Item savedItem = itemRepository.save(itemA);
        Item findItem = itemRepository.findById(itemA.getId());
        //then
        Assertions.assertThat(itemA).isEqualTo(findItem);
    }

    @Test
    void findAll() {
        //given
        Item itemA = new Item("itemA", 1000, 10);
        Item itemB = new Item("itemB", 1000, 10);
        //when
        Item savedItemA = itemRepository.save(itemA);
        Item savedItemB = itemRepository.save(itemB);
        List<Item> findAllItems = itemRepository.findAll();
        //then
        Assertions.assertThat(findAllItems.size()).isEqualTo(2);
    }

    @Test
    void update() {
        //given
        Item itemA = new Item("itemA", 1000, 10);
        Item updateItem = new Item("itemUpdate", 2000, 20);
        //when
        Item savedItem = itemRepository.save(itemA);
        itemRepository.update(savedItem.getId(), updateItem);
        //then
        Assertions.assertThat(savedItem.getItemName()).isEqualTo(updateItem.getItemName());
    }
}
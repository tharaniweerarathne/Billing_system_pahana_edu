package com.Billing_system_pahana_edu.service;

import com.Billing_system_pahana_edu.dao.ItemDAO;
import com.Billing_system_pahana_edu.model.Item;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ItemServiceTest {

    static class DummyItemService {
        private final List<Item> items = new ArrayList<>();

        void addItem(Item item) {
            items.add(item);
        }

        void updateItem(Item updated) {
            for (Item item : items) {
                if (item.getItemId().equals(updated.getItemId())) {
                    item.setItemName(updated.getItemName());
                    item.setCategory(updated.getCategory());
                    item.setPrice(updated.getPrice());
                    item.setUnit(updated.getUnit());
                }
            }
        }

        void deleteItem(String itemId) {
            items.removeIf(i -> i.getItemId().equals(itemId));
        }

        List<Item> getAll() {
            return new ArrayList<>(items);
        }
    }

    private DummyItemService itemService;

    @Before
    public void setUp() {
        itemService = new DummyItemService();
    }

    @Test
    public void testAddItem() {
        Item item = new Item();
        item.setItemId("I001");
        item.setItemName("Book");
        item.setCategory("Stationery");
        item.setPrice(200);
        item.setUnit(5);

        itemService.addItem(item);

        List<Item> allItems = itemService.getAll();
        assertEquals(1, allItems.size());
        assertEquals("I001", allItems.get(0).getItemId());
        assertEquals("Book", allItems.get(0).getItemName());
    }

    @Test
    public void testUpdateItem() {
        Item item = new Item();
        item.setItemId("I002");
        item.setItemName("Pen");
        item.setCategory("Stationery");
        item.setPrice(50);
        item.setUnit(10);

        itemService.addItem(item);

        // Update
        item.setItemName("Marker");
        item.setPrice(70);
        item.setUnit(15);
        itemService.updateItem(item);

        Item updated = itemService.getAll().get(0);
        assertEquals("Marker", updated.getItemName());
        assertEquals(70, updated.getPrice());
        assertEquals(15, updated.getUnit());
    }

    @Test
    public void testDeleteItem() {
        Item item = new Item();
        item.setItemId("I003");
        item.setItemName("Notebook");
        item.setCategory("Stationery");
        item.setPrice(100);
        item.setUnit(8);

        itemService.addItem(item);
        itemService.deleteItem("I003");

        List<Item> allItems = itemService.getAll();
        assertTrue(allItems.isEmpty());
    }

    @Test
    public void testGetAllItems() {
        Item item1 = new Item();
        item1.setItemId("I004");
        item1.setItemName("Eraser");
        item1.setCategory("Stationery");
        item1.setPrice(20);
        item1.setUnit(50);

        Item item2 = new Item();
        item2.setItemId("I005");
        item2.setItemName("Ruler");
        item2.setCategory("Stationery");
        item2.setPrice(30);
        item2.setUnit(30);

        itemService.addItem(item1);
        itemService.addItem(item2);

        List<Item> allItems = itemService.getAll();
        assertEquals(2, allItems.size());
    }
}
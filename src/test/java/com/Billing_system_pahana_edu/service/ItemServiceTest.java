package com.Billing_system_pahana_edu.service;


import com.Billing_system_pahana_edu.model.Item;
import com.Billing_system_pahana_edu.util.DBUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static org.junit.Assert.*;

public class ItemServiceTest {

    private ItemService itemService;

    @Before
    public void setUp() {
        itemService = new ItemService();
        cleanupTestData();
    }

    @After
    public void tearDown() {
        cleanupTestData();
    }

    private void cleanupTestData() {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM items WHERE itemName LIKE 'TestItem%' OR itemName LIKE 'UpdatedItem%'"
            );
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("Cleanup failed: " + e.getMessage());
        }
    }

    private Item createTestItem(String suffix) {
        Item item = new Item();
        item.setItemName("TestItem" + suffix);
        item.setCategory("Category" + suffix);
        item.setPrice(100 + Integer.parseInt(suffix));
        item.setUnit(10 + Integer.parseInt(suffix));
        return item;
    }

    private String generateUniqueSuffix() {
        return String.valueOf(System.currentTimeMillis() % 100000);
    }

    @Test
    public void testAddItem() {
        String suffix = generateUniqueSuffix();
        Item item = createTestItem(suffix);

        itemService.addItem(item);

        List<Item> items = itemService.getAll();
        boolean found = items.stream().anyMatch(i -> i.getItemName().equals(item.getItemName()));
        assertTrue("Item should be added successfully", found);
    }

    @Test
    public void testUpdateItem() {
        String suffix = generateUniqueSuffix();
        Item item = createTestItem(suffix);
        itemService.addItem(item);

        List<Item> items = itemService.searchItems("TestItem" + suffix);
        Item added = items.stream().filter(i -> i.getItemName().equals(item.getItemName())).findFirst().orElse(null);
        assertNotNull("Item should exist before update", added);

        added.setItemName("UpdatedItem" + suffix);
        added.setCategory("UpdatedCategory" + suffix);
        added.setPrice(999);
        added.setUnit(99);
        itemService.updateItem(added);

        List<Item> updatedList = itemService.searchItems("UpdatedItem" + suffix);
        Item updated = updatedList.stream().filter(i -> i.getItemName().equals("UpdatedItem" + suffix)).findFirst().orElse(null);

        assertNotNull("Updated item should exist", updated);
        assertEquals("UpdatedItem" + suffix, updated.getItemName());
        assertEquals("UpdatedCategory" + suffix, updated.getCategory());
        assertEquals(999, updated.getPrice());
        assertEquals(99, updated.getUnit());
    }

    @Test
    public void testDeleteItem() {
        String suffix = generateUniqueSuffix();
        Item item = createTestItem(suffix);
        itemService.addItem(item);

        List<Item> items = itemService.searchItems("TestItem" + suffix);
        Item added = items.stream().filter(i -> i.getItemName().equals(item.getItemName())).findFirst().orElse(null);
        assertNotNull("Item should exist before deletion", added);

        itemService.deleteItem(added.getItemId());

        List<Item> afterDelete = itemService.searchItems("TestItem" + suffix);
        assertTrue("Item should be deleted", afterDelete.isEmpty());
    }

    @Test
    public void testGetAllItems() {
        String suffix1 = generateUniqueSuffix();
        String suffix2 = String.valueOf((System.currentTimeMillis() + 1) % 100000);
        Item item1 = createTestItem(suffix1);
        Item item2 = createTestItem(suffix2);

        itemService.addItem(item1);
        itemService.addItem(item2);

        List<Item> allItems = itemService.getAll();
        assertTrue(allItems.size() >= 2);
        assertTrue(allItems.stream().anyMatch(i -> i.getItemName().equals(item1.getItemName())));
        assertTrue(allItems.stream().anyMatch(i -> i.getItemName().equals(item2.getItemName())));
    }

    @Test
    public void testGetNextItemId() {
        String nextId = itemService.getNextId();
        assertNotNull("Next item ID should not be null", nextId);
        assertTrue(nextId.startsWith("IT"));
    }

    @Test
    public void testSearchItems() {
        String suffix = generateUniqueSuffix();
        Item item = createTestItem(suffix);
        itemService.addItem(item);

        List<Item> results = itemService.searchItems("TestItem" + suffix);
        assertNotNull(results);
        assertTrue(results.stream().anyMatch(i -> i.getItemName().equals(item.getItemName())));
    }
}
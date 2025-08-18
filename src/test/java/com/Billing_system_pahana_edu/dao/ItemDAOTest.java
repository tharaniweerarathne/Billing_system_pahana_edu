package com.Billing_system_pahana_edu.dao;

import com.Billing_system_pahana_edu.model.Item;
import com.Billing_system_pahana_edu.util.DBUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ItemDAOTest {
    private ItemDAO itemDAO;

    @Before
    public void setUp() {
        itemDAO = new ItemDAO();
        cleanupTestData();
    }

    @After
    public void tearDown() {
        cleanupTestData();
    }

    private void cleanupTestData() {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM items WHERE itemName LIKE 'TestItem%'"
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

    // --------------------- ADD ITEM ---------------------
    @Test
    public void testAddItem_AddsSuccessfully() {
        String suffix = generateUniqueSuffix();
        Item item = createTestItem(suffix);

        itemDAO.addItem(item);

        List<Item> items = itemDAO.getAllItems();
        boolean found = items.stream().anyMatch(i -> i.getItemName().equals(item.getItemName()));
        assertTrue("Item should be added successfully", found);
    }

    // --------------------- GET ALL ITEMS ---------------------
    @Test
    public void testGetAllItems_ReturnsList() {
        String suffix = generateUniqueSuffix();
        Item item = createTestItem(suffix);
        itemDAO.addItem(item);

        List<Item> items = itemDAO.getAllItems();
        assertNotNull("getAllItems should return a list", items);
        assertTrue("List should contain at least one item", items.size() >= 1);
    }

    // --------------------- UPDATE ITEM ---------------------
    @Test
    public void testUpdateItem_UpdatesSuccessfully() {
        String suffix = generateUniqueSuffix();
        Item item = createTestItem(suffix);
        itemDAO.addItem(item);

        List<Item> items = itemDAO.searchItems("TestItem" + suffix);
        Item added = items.stream().filter(i -> i.getItemName().equals(item.getItemName())).findFirst().orElse(null);
        assertNotNull("Item should exist before update", added);

        added.setItemName("UpdatedItem" + suffix);
        added.setCategory("UpdatedCategory" + suffix);
        added.setPrice(999);
        added.setUnit(99);
        itemDAO.updateItem(added);

        Item updated = itemDAO.getItemById(added.getItemId());
        assertEquals("Item name should be updated", "UpdatedItem" + suffix, updated.getItemName());
        assertEquals("Category should be updated", "UpdatedCategory" + suffix, updated.getCategory());
        assertEquals("Price should be updated", 999, updated.getPrice());
        assertEquals("Unit should be updated", 99, updated.getUnit());
    }

    // --------------------- DELETE ITEM ---------------------
    @Test
    public void testDeleteItem_DeletesSuccessfully() {
        String suffix = generateUniqueSuffix();
        Item item = createTestItem(suffix);
        itemDAO.addItem(item);

        List<Item> items = itemDAO.searchItems("TestItem" + suffix);
        Item added = items.stream().filter(i -> i.getItemName().equals(item.getItemName())).findFirst().orElse(null);
        assertNotNull("Item should exist before deletion", added);

        itemDAO.deleteItem(added.getItemId());

        Item deleted = itemDAO.getItemById(added.getItemId());
        assertNull("Item should be deleted", deleted);
    }

    // --------------------- GET NEXT ID ---------------------
    @Test
    public void testGetNextId_ReturnsNextItemId() {
        String nextId = itemDAO.getNextId();
        assertNotNull("getNextId should return a non-null ID", nextId);
        assertTrue("ID should start with IT", nextId.startsWith("IT"));
    }

    // --------------------- SEARCH ITEMS ---------------------
    @Test
    public void testSearchItems_ReturnsList() {
        String suffix = generateUniqueSuffix();
        Item item = createTestItem(suffix);
        itemDAO.addItem(item);

        List<Item> results = itemDAO.searchItems("TestItem" + suffix);
        assertNotNull("searchItems should return a list", results);
        assertTrue("Search results should contain the item", results.stream().anyMatch(i -> i.getItemName().equals(item.getItemName())));
    }

    // --------------------- GET ITEM BY ID ---------------------
    @Test
    public void testGetItemById_ReturnsItem() {
        String suffix = generateUniqueSuffix();
        Item item = createTestItem(suffix);
        itemDAO.addItem(item);

        List<Item> items = itemDAO.searchItems("TestItem" + suffix);
        Item added = items.stream().filter(i -> i.getItemName().equals(item.getItemName())).findFirst().orElse(null);
        assertNotNull("Item should exist", added);

        Item fetched = itemDAO.getItemById(added.getItemId());
        assertNotNull("getItemById should return the item", fetched);
        assertEquals("Fetched item name should match", added.getItemName(), fetched.getItemName());
    }
}

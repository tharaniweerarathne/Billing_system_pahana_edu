package com.Billing_system_pahana_edu.dao;

import com.Billing_system_pahana_edu.model.Item;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ItemDAOTest {
    static class DummyItemDAO {
        private List<Item> items = new ArrayList<>();

        public void addItem(Item item) {
            item.setItemId(getNextId());
            items.add(item);
        }

        public List<Item> getAll() {
            return new ArrayList<>(items);
        }

        public void updateItem(Item item) {
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getItemId().equals(item.getItemId())) {
                    items.set(i, item);
                }
            }
        }

        public void deleteItem(String id) {
            items.removeIf(i -> i.getItemId().equals(id));
        }

        public String getNextId() {
            if (items.isEmpty()) return "IT001";
            int lastNum = Integer.parseInt(items.get(items.size() - 1).getItemId().substring(2));
            return String.format("IT%03d", lastNum + 1);
        }

        public Item getItemById(String id) {
            return items.stream().filter(i -> i.getItemId().equals(id)).findFirst().orElse(null);
        }

        public List<Item> searchItems(String keyword) {
            List<Item> result = new ArrayList<>();
            for (Item i : items) {
                if (i.getItemId().contains(keyword) || i.getItemName().contains(keyword) || i.getCategory().contains(keyword)) {
                    result.add(i);
                }
            }
            return result;
        }
    }

    private DummyItemDAO dao;

    @Before
    public void setup() {
        dao = new DummyItemDAO();

        Item i1 = new Item();
        i1.setItemId("IT001");
        i1.setItemName("Pen");
        i1.setCategory("Stationery");
        i1.setPrice(10);
        i1.setUnit(100);

        Item i2 = new Item();
        i2.setItemId("IT002");
        i2.setItemName("Book");
        i2.setCategory("Stationery");
        i2.setPrice(200);
        i2.setUnit(50);

        dao.items.add(i1);
        dao.items.add(i2);
    }

    @Test
    public void testGetAll() {
        List<Item> list = dao.getAll();
        assertEquals(2, list.size());
    }

    @Test
    public void testAddItem() {
        Item i3 = new Item();
        i3.setItemName("Pencil");
        i3.setCategory("Stationery");
        i3.setPrice(5);
        i3.setUnit(300);

        dao.addItem(i3);
        assertEquals(3, dao.getAll().size());
        assertEquals("IT003", dao.getAll().get(2).getItemId());
    }

    @Test
    public void testUpdateItem() {
        Item updated = new Item();
        updated.setItemId("IT001");
        updated.setItemName("Pen Blue");
        updated.setCategory("Stationery");
        updated.setPrice(15);
        updated.setUnit(120);

        dao.updateItem(updated);

        Item fetched = dao.getItemById("IT001");
        assertEquals("Pen Blue", fetched.getItemName());
        assertEquals(15, fetched.getPrice());
    }

    @Test
    public void testDeleteItem() {
        dao.deleteItem("IT001");
        assertNull(dao.getItemById("IT001"));
        assertEquals(1, dao.getAll().size());
    }

    @Test
    public void testGetItemById() {
        Item item = dao.getItemById("IT002");
        assertNotNull(item);
        assertEquals("Book", item.getItemName());
    }

    @Test
    public void testSearchItems() {
        List<Item> result = dao.searchItems("Book");
        assertEquals(1, result.size());
        assertEquals("Book", result.get(0).getItemName());
    }

    @Test
    public void testGetNextId() {
        String next = dao.getNextId();
        assertEquals("IT003", next);
    }
}

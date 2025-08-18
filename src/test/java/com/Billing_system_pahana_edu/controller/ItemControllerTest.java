package com.Billing_system_pahana_edu.controller;

import com.Billing_system_pahana_edu.model.Item;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ItemControllerTest {
    static class DummyRequest {
        String action;
        String itemId;
        String itemName;
        String category;
        String price;
        String unit;
        String query;

        List<Item> itemsAttr;
        String searchQueryAttr;
    }

    // Simple dummy response
    static class DummyResponse {
        String redirectedUrl;
        void sendRedirect(String url) { redirectedUrl = url; }
    }

    // Dummy controller using ArrayList
    static class DummyItemController {
        List<Item> items;

        DummyItemController(List<Item> items) { this.items = items; }

        void doGet(DummyRequest req) {
            List<Item> list = new ArrayList<>();
            if (req.query != null && !req.query.trim().isEmpty()) {
                for (Item i : items) {
                    if (i.getItemId().contains(req.query) || i.getItemName().contains(req.query)
                            || i.getCategory().contains(req.query)) {
                        list.add(i);
                    }
                }
            } else {
                list.addAll(items);
            }
            req.itemsAttr = list;
            req.searchQueryAttr = req.query;
        }

        void doPost(DummyRequest req, DummyResponse res) {
            if ("add".equals(req.action)) {
                Item i = new Item();
                i.setItemId("I" + String.format("%03d", items.size() + 1));
                i.setItemName(req.itemName);
                i.setCategory(req.category);
                i.setPrice(Integer.parseInt(req.price));
                i.setUnit(Integer.parseInt(req.unit));
                items.add(i);

            } else if ("update".equals(req.action)) {
                for (Item i : items) {
                    if (i.getItemId().equals(req.itemId)) {
                        i.setItemName(req.itemName);
                        i.setCategory(req.category);
                        i.setPrice(Integer.parseInt(req.price));
                        i.setUnit(Integer.parseInt(req.unit));
                    }
                }

            } else if ("delete".equals(req.action)) {
                items.removeIf(i -> i.getItemId().equals(req.itemId));
            }

            res.sendRedirect("ItemController");
        }
    }

    private List<Item> dummyList;
    private DummyItemController controller;

    @Before
    public void setup() {
        dummyList = new ArrayList<>();
        Item i1 = new Item();
        i1.setItemId("I001");
        i1.setItemName("Book");
        i1.setCategory("Stationery");
        i1.setPrice(200);
        i1.setUnit(5);

        Item i2 = new Item();
        i2.setItemId("I002");
        i2.setItemName("Pen");
        i2.setCategory("Stationery");
        i2.setPrice(50);
        i2.setUnit(10);

        dummyList.add(i1);
        dummyList.add(i2);

        controller = new DummyItemController(dummyList);
    }

    @Test
    public void testDoGetAll() {
        DummyRequest req = new DummyRequest();
        controller.doGet(req);
        assertEquals(2, req.itemsAttr.size());
    }

    @Test
    public void testDoGetSearch() {
        DummyRequest req = new DummyRequest();
        req.query = "Pen";
        controller.doGet(req);
        assertEquals(1, req.itemsAttr.size());
        assertEquals("Pen", req.itemsAttr.get(0).getItemName());
    }

    @Test
    public void testDoPostAdd() {
        DummyRequest req = new DummyRequest();
        DummyResponse res = new DummyResponse();
        req.action = "add";
        req.itemName = "Pencil";
        req.category = "Stationery";
        req.price = "20";
        req.unit = "15";

        controller.doPost(req, res);

        assertEquals(3, dummyList.size());
        assertEquals("Pencil", dummyList.get(2).getItemName());
        assertEquals("ItemController", res.redirectedUrl);
    }

    @Test
    public void testDoPostUpdate() {
        DummyRequest req = new DummyRequest();
        DummyResponse res = new DummyResponse();
        req.action = "update";
        req.itemId = "I001";
        req.itemName = "Notebook";
        req.category = "Stationery";
        req.price = "150";
        req.unit = "7";

        controller.doPost(req, res);
        assertEquals("Notebook", dummyList.get(0).getItemName());
        assertEquals(150, dummyList.get(0).getPrice());
        assertEquals("ItemController", res.redirectedUrl);
    }

    @Test
    public void testDoPostDelete() {
        DummyRequest req = new DummyRequest();
        DummyResponse res = new DummyResponse();
        req.action = "delete";
        req.itemId = "I001";

        controller.doPost(req, res);
        assertEquals(1, dummyList.size());
        assertEquals("Pen", dummyList.get(0).getItemName());
        assertEquals("ItemController", res.redirectedUrl);
    }
}

package com.Billing_system_pahana_edu.service;

import com.Billing_system_pahana_edu.dao.ItemDAO;
import com.Billing_system_pahana_edu.model.Item;

import java.util.List;

public class ItemService {
    private final ItemDAO dao = new ItemDAO();

    public void addItem(Item item) {
        dao.addItem(item);
    }

    public void updateItem(Item item) {
        dao.updateItem(item);
    }

    public void deleteItem(String itemId) {
        dao.deleteItem(itemId);
    }

    public List<Item> getAll() {
        return dao.getAll();
    }

    public String getNextId() {
        return dao.getNextId();
    }

    public List<Item> searchItems(String keyword) {
        return dao.searchItems(keyword);
    }

}



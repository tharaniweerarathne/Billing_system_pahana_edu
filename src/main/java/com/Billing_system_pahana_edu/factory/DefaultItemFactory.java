package com.Billing_system_pahana_edu.factory;

import com.Billing_system_pahana_edu.model.Item;

public class DefaultItemFactory implements ItemFactory {

    @Override
    public Item createItem(String itemId, String itemName, String category, int price, int unit) {
        Item item = new Item();
        item.setItemId(itemId);
        item.setItemName(itemName);
        item.setCategory(category);
        item.setPrice(price);
        item.setUnit(unit);
        return item;
    }
}
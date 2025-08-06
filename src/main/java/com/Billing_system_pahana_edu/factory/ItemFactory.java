package com.Billing_system_pahana_edu.factory;

import com.Billing_system_pahana_edu.model.Item;

public interface ItemFactory {
    Item createItem(String itemId, String itemName, String category, int price, int unit);
}

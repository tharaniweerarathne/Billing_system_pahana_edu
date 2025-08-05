package com.Billing_system_pahana_edu.model;

public class Item {
    private String itemId;
    private String itemName;
    private String category;
    private int price;
    private int unit;

    public String getItemId() { return itemId; }
    public void setItemId(String itemId) { this.itemId = itemId; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public int getUnit() { return unit; }
    public void setUnit(int unit) { this.unit = unit; }
}

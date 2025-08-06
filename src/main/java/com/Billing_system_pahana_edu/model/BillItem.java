package com.Billing_system_pahana_edu.model;

public class BillItem {
    private String itemId;
    private String itemName;
    private double unitPrice;
    private int unit;
    private double totalPrice;

    public String getItemId() { return itemId; }
    public void setItemId(String itemId) { this.itemId = itemId; }
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    public int getUnit() { return unit; }
    public void setUnit(int unit) { this.unit = unit; }
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}


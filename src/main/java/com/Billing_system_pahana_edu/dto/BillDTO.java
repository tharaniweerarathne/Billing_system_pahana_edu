package com.Billing_system_pahana_edu.dto;

import com.Billing_system_pahana_edu.model.BillItem;

import java.util.List;

public class BillDTO {
    private String customerId;
    private List<BillItem> items;
    private double discount;
    private double totalAmount;
    private double finalAmount;

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public List<BillItem> getItems() { return items; }
    public void setItems(List<BillItem> items) { this.items = items; }
    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public double getFinalAmount() { return finalAmount; }
    public void setFinalAmount(double finalAmount) { this.finalAmount = finalAmount; }
}

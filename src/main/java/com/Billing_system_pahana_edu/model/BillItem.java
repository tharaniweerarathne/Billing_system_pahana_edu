package com.Billing_system_pahana_edu.model;

public final class BillItem {
    private final String itemId;
    private final String itemName;
    private final double unitPrice;
    private final int unit;
    private final double totalPrice;

    // Private constructor to enforce use of Builder
    private BillItem(Builder builder) {
        this.itemId = builder.itemId;
        this.itemName = builder.itemName;
        this.unitPrice = builder.unitPrice;
        this.unit = builder.unit;
        this.totalPrice = builder.totalPrice;
    }

    // Getters only (immutable)
    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getUnit() {
        return unit;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    // Builder class
    public static class Builder {
        private String itemId;
        private String itemName;
        private double unitPrice;
        private int unit;
        private double totalPrice;

        public Builder setItemId(String itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder setItemName(String itemName) {
            this.itemName = itemName;
            return this;
        }

        public Builder setUnitPrice(double unitPrice) {
            this.unitPrice = unitPrice;
            return this;
        }

        public Builder setUnit(int unit) {
            this.unit = unit;
            return this;
        }

        public Builder setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public BillItem build() {
            return new BillItem(this);
        }
    }

}



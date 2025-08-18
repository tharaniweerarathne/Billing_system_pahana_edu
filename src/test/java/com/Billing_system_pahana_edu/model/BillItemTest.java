package com.Billing_system_pahana_edu.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BillItemTest {
    @Test
    public void testBillItemBuilder() {

        String expectedId = "I001";
        String expectedName = "Laptop";
        double expectedUnitPrice = 1500.50;
        int expectedUnit = 2;
        double expectedTotalPrice = expectedUnitPrice * expectedUnit;

        BillItem billItem = new BillItem.Builder()
                .setItemId(expectedId)
                .setItemName(expectedName)
                .setUnitPrice(expectedUnitPrice)
                .setUnit(expectedUnit)
                .setTotalPrice(expectedTotalPrice)
                .build();

        assertEquals(expectedId, billItem.getItemId());
        assertEquals(expectedName, billItem.getItemName());
        assertEquals(expectedUnitPrice, billItem.getUnitPrice(), 0.001);
        assertEquals(expectedUnit, billItem.getUnit());
        assertEquals(expectedTotalPrice, billItem.getTotalPrice(), 0.001);
    }
}

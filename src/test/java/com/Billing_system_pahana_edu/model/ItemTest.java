package com.Billing_system_pahana_edu.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ItemTest {
    @Test
    public void testItemSettersAndGetters() {
        Item item = new Item();

        item.setItemId("I001");
        item.setItemName("Test");
        item.setCategory("TestBook");
        item.setPrice(250);
        item.setUnit(10);

        assertEquals("I001", item.getItemId());
        assertEquals("Test", item.getItemName());
        assertEquals("TestBook", item.getCategory());
        assertEquals(250, item.getPrice());
        assertEquals(10, item.getUnit());
    }
}

package com.Billing_system_pahana_edu.factory;

import com.Billing_system_pahana_edu.model.Item;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DefaultItemFactoryTest {

    @Test
    public void testCreateItem() {
        ItemFactory factory = new DefaultItemFactory();

        Item item = factory.createItem("I001", "Book", "Stationery", 200, 5);

        assertNotNull(item);
        assertEquals("I001", item.getItemId());
        assertEquals("Book", item.getItemName());
        assertEquals("Stationery", item.getCategory());
        assertEquals(200, item.getPrice());
        assertEquals(5, item.getUnit());
    }
}

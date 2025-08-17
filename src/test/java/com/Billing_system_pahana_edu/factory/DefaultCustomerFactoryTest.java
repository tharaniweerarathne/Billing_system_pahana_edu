package com.Billing_system_pahana_edu.factory;

import com.Billing_system_pahana_edu.model.Customer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DefaultCustomerFactoryTest {
    @Test
    public void testCreateCustomer() {
        CustomerFactory factory = new DefaultCustomerFactory();

        Customer customer = factory.createCustomer(
                "C001",
                "Test",
                "test@example.com",
                "123 Test Street",
                "0123456789"
        );

        assertEquals("C001", customer.getAccountNo());
        assertEquals("Test", customer.getName());
        assertEquals("test@example.com", customer.getEmail());
        assertEquals("123 Test Street", customer.getAddress());
        assertEquals("0123456789", customer.getTelephone());
    }

    @Test
    public void testCreateCustomerWithEmptyFields() {
        CustomerFactory factory = new DefaultCustomerFactory();

        Customer customer = factory.createCustomer("", "", "", "", "");

        assertEquals("", customer.getAccountNo());
        assertEquals("", customer.getName());
        assertEquals("", customer.getEmail());
        assertEquals("", customer.getAddress());
        assertEquals("", customer.getTelephone());
    }
}

package com.Billing_system_pahana_edu.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CustomerTest {
    @Test
    public void testCustomerSettersAndGetters() {
        Customer customer = new Customer();

        customer.setAccountNo("C001");
        customer.setName("Test");
        customer.setEmail("test@example.com");
        customer.setAddress("123 Test Street");
        customer.setTelephone("0123456789");

        assertEquals("C001", customer.getAccountNo());
        assertEquals("Test", customer.getName());
        assertEquals("test@example.com", customer.getEmail());
        assertEquals("123 Test Street", customer.getAddress());
        assertEquals("0123456789", customer.getTelephone());
    }

    @Test
    public void testCustomerDefaultValues() {
        Customer customer = new Customer();

        assertNull(customer.getAccountNo());
        assertNull(customer.getName());
        assertNull(customer.getEmail());
        assertNull(customer.getAddress());
        assertNull(customer.getTelephone());
    }
}

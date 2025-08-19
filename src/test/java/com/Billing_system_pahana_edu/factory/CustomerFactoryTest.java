package com.Billing_system_pahana_edu.factory;

import com.Billing_system_pahana_edu.model.Customer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class CustomerFactoryTest {

    @Test
    public void testCreateCustomer() {
        CustomerFactory factory = new DefaultCustomerFactory();

        Customer customer = factory.createCustomer("C001", "Test", "test@example.com", "123 test", "0771234567");

        assertNotNull(customer);
        assertEquals("C001", customer.getAccountNo());
        assertEquals("Test", customer.getName());
        assertEquals("test@example.com", customer.getEmail());
        assertEquals("123 test", customer.getAddress());
        assertEquals("0771234567", customer.getTelephone());
    }
}

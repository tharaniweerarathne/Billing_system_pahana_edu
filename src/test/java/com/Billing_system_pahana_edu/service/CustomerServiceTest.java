package com.Billing_system_pahana_edu.service;

import com.Billing_system_pahana_edu.dao.CustomerDAO;
import com.Billing_system_pahana_edu.model.Customer;
import com.Billing_system_pahana_edu.util.DBUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CustomerServiceTest {
    private CustomerService service;

    @Before
    public void setUp() {
        service = new CustomerService();
        cleanupTestData();
    }

    @After
    public void tearDown() {
        cleanupTestData();
    }

    private void cleanupTestData() {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM customers WHERE accountNo LIKE 'C9%' OR name LIKE 'TestCustomer%'"
            );
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("Cleanup failed: " + e.getMessage());
        }
    }

    private Customer createTestCustomer(String suffix) {
        Customer c = new Customer();
        c.setName("TestCustomer" + suffix);
        c.setEmail("testcustomer" + suffix + "@example.com");
        c.setAddress("123 Test Street");
        c.setTelephone("077123456" + suffix);
        return c;
    }

    private String generateUniqueSuffix() {
        return String.valueOf(System.currentTimeMillis() % 100000);
    }

    @Test
    public void testAddCustomer() {
        String suffix = generateUniqueSuffix();
        Customer c = createTestCustomer(suffix);

        service.addCustomer(c);

        List<Customer> all = service.getAll();
        boolean found = all.stream().anyMatch(customer -> customer.getEmail().equals(c.getEmail()));
        assertTrue("Customer should be added via service", found);
    }

    @Test
    public void testUpdateCustomer() {
        String suffix = generateUniqueSuffix();
        Customer c = createTestCustomer(suffix);
        service.addCustomer(c);

        Customer added = service.getAll().stream()
                .filter(customer -> customer.getEmail().equals(c.getEmail()))
                .findFirst()
                .orElse(null);
        assertNotNull("Customer must exist before update", added);

        added.setName("UpdatedName" + suffix);
        added.setAddress("456 Updated Street");
        added.setTelephone("07799999" + suffix);

        service.updateCustomer(added);

        Customer updated = service.getAll().stream()
                .filter(customer -> customer.getAccountNo().equals(added.getAccountNo()))
                .findFirst()
                .orElse(null);

        assertEquals("UpdatedName" + suffix, updated.getName());
        assertEquals("456 Updated Street", updated.getAddress());
        assertEquals("07799999" + suffix, updated.getTelephone());
    }

    @Test
    public void testDeleteCustomer() {
        String suffix = generateUniqueSuffix();
        Customer c = createTestCustomer(suffix);
        service.addCustomer(c);

        Customer added = service.getAll().stream()
                .filter(customer -> customer.getEmail().equals(c.getEmail()))
                .findFirst()
                .orElse(null);
        assertNotNull("Customer should exist before deletion", added);

        service.deleteCustomer(added.getAccountNo());

        Customer deleted = service.getAll().stream()
                .filter(customer -> customer.getAccountNo().equals(added.getAccountNo()))
                .findFirst()
                .orElse(null);
        assertNull("Customer should be deleted", deleted);
    }

    @Test
    public void testGetNextAccountNo() {
        String nextAccountNo = service.getNextAccountNo();
        assertNotNull("Next account number should not be null", nextAccountNo);
        assertTrue(nextAccountNo.startsWith("C"));
    }

    @Test
    public void testSearchCustomers() {
        String suffix = generateUniqueSuffix();
        Customer c = createTestCustomer(suffix);
        service.addCustomer(c);

        List<Customer> results = service.searchCustomers("TestCustomer" + suffix);
        assertNotNull("Search results should not be null", results);
        assertTrue(results.stream().anyMatch(customer -> customer.getEmail().equals(c.getEmail())));
    }
}
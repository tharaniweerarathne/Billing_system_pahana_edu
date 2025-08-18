package com.Billing_system_pahana_edu.dao;

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

public class CustomerDAOTest {
    private CustomerDAO customerDAO;

    @Before
    public void setUp() {
        customerDAO = new CustomerDAO();
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

    private Customer createTestCustomer(String uniqueSuffix) {
        Customer c = new Customer();
        c.setName("TestCustomer" + uniqueSuffix);
        c.setEmail("testcustomer" + uniqueSuffix + "@example.com");
        c.setAddress("123 Test Street");
        c.setTelephone("077123456" + uniqueSuffix);
        return c;
    }

    private void insertTestCustomerDirectly(Customer c) {
        customerDAO.addCustomer(c);
    }

    private String generateUniqueSuffix() {
        return String.valueOf(System.currentTimeMillis() % 100000);
    }

    // --------------------- ADD CUSTOMER ---------------------
    @Test
    public void testAddCustomer_AddsSuccessfully() {
        String suffix = generateUniqueSuffix();
        Customer c = createTestCustomer(suffix);

        customerDAO.addCustomer(c);

        List<Customer> customers = customerDAO.getAll();
        boolean found = customers.stream().anyMatch(customer -> customer.getEmail().equals(c.getEmail()));
        assertTrue("Customer should be added successfully", found);
    }

    // --------------------- GET ALL CUSTOMERS ---------------------
    @Test
    public void testGetAll_ReturnsList() {
        String suffix = generateUniqueSuffix();
        Customer c = createTestCustomer(suffix);
        insertTestCustomerDirectly(c);

        List<Customer> customers = customerDAO.getAll();
        assertNotNull("getAll should return a list", customers);
        assertTrue("List should contain at least one customer", customers.size() >= 1);
    }

    // --------------------- UPDATE CUSTOMER ---------------------
    @Test
    public void testUpdateCustomer_UpdatesSuccessfully() {
        String suffix = generateUniqueSuffix();
        Customer c = createTestCustomer(suffix);
        insertTestCustomerDirectly(c);

        List<Customer> customers = customerDAO.getAll();
        Customer added = customers.stream().filter(customer -> customer.getEmail().equals(c.getEmail())).findFirst().orElse(null);
        assertNotNull("Customer should exist before update", added);

        added.setName("UpdatedName" + suffix);
        added.setAddress("456 Updated Street");
        added.setTelephone("07799999" + suffix);
        customerDAO.updateCustomer(added);

        Customer updated = customerDAO.getCustomerByAccountNo(added.getAccountNo());
        assertEquals("Name should be updated", "UpdatedName" + suffix, updated.getName());
        assertEquals("Address should be updated", "456 Updated Street", updated.getAddress());
        assertEquals("Telephone should be updated", "07799999" + suffix, updated.getTelephone());
    }

    // --------------------- DELETE CUSTOMER ---------------------
    @Test
    public void testDeleteCustomer_DeletesSuccessfully() {
        String suffix = generateUniqueSuffix();
        Customer c = createTestCustomer(suffix);
        insertTestCustomerDirectly(c);

        List<Customer> customers = customerDAO.getAll();
        Customer added = customers.stream().filter(customer -> customer.getEmail().equals(c.getEmail())).findFirst().orElse(null);
        assertNotNull("Customer should exist before deletion", added);

        customerDAO.deleteCustomer(added.getAccountNo());

        Customer deleted = customerDAO.getCustomerByAccountNo(added.getAccountNo());
        assertNull("Customer should be deleted", deleted);
    }

    // --------------------- GET NEXT ACCOUNT NO ---------------------
    @Test
    public void testGetNextAccountNo_ReturnsNextAccountNumber() {
        String nextAccountNo = customerDAO.getNextAccountNo();
        assertNotNull("getNextAccountNo should return a non-null account number", nextAccountNo);
        assertTrue("Account number should start with C", nextAccountNo.startsWith("C"));
    }

    // --------------------- SEARCH CUSTOMERS ---------------------
    @Test
    public void testSearchCustomers_ReturnsList() {
        String suffix = generateUniqueSuffix();
        Customer c = createTestCustomer(suffix);
        insertTestCustomerDirectly(c);

        List<Customer> results = customerDAO.searchCustomers("TestCustomer" + suffix);
        assertNotNull("searchCustomers should return a list", results);
        assertTrue("Search results should contain the customer", results.stream().anyMatch(customer -> customer.getEmail().equals(c.getEmail())));
    }

    // --------------------- GET CUSTOMER BY ACCOUNT NO ---------------------
    @Test
    public void testGetCustomerByAccountNo_ReturnsCustomer() {
        String suffix = generateUniqueSuffix();
        Customer c = createTestCustomer(suffix);
        insertTestCustomerDirectly(c);

        List<Customer> customers = customerDAO.getAll();
        Customer added = customers.stream().filter(customer -> customer.getEmail().equals(c.getEmail())).findFirst().orElse(null);
        assertNotNull("Customer should exist", added);

        Customer fetched = customerDAO.getCustomerByAccountNo(added.getAccountNo());
        assertNotNull("getCustomerByAccountNo should return the customer", fetched);
        assertEquals("Fetched customer email should match", added.getEmail(), fetched.getEmail());
    }
}

package com.Billing_system_pahana_edu.dao;

import com.Billing_system_pahana_edu.model.Customer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CustomerDAOTest {
    static class DummyCustomerDAO extends CustomerDAO {
        private final List<Customer> dummyCustomers = new ArrayList<>();

        public DummyCustomerDAO() {
            Customer c1 = new Customer();
            c1.setAccountNo("C001");
            c1.setName("Test");
            c1.setEmail("test@example.com");
            c1.setAddress("123 Test Street");
            c1.setTelephone("0123456789");
            dummyCustomers.add(c1);

            Customer c2 = new Customer();
            c2.setAccountNo("C002");
            c2.setName("Alice");
            c2.setEmail("alice@example.com");
            c2.setAddress("456 Alice Road");
            c2.setTelephone("0987654321");
            dummyCustomers.add(c2);
        }

        @Override
        public void addCustomer(Customer c) {
            c.setAccountNo("C" + String.format("%03d", dummyCustomers.size() + 1));
            dummyCustomers.add(c);
        }

        @Override
        public List<Customer> getAll() {
            return new ArrayList<>(dummyCustomers);
        }

        @Override
        public void updateCustomer(Customer c) {
            for (Customer cust : dummyCustomers) {
                if (cust.getAccountNo().equals(c.getAccountNo())) {
                    cust.setName(c.getName());
                    cust.setEmail(c.getEmail());
                    cust.setAddress(c.getAddress());
                    cust.setTelephone(c.getTelephone());
                }
            }
        }

        @Override
        public void deleteCustomer(String accountNo) {
            dummyCustomers.removeIf(c -> c.getAccountNo().equals(accountNo));
        }

        @Override
        public Customer getCustomerByAccountNo(String accountNo) {
            return dummyCustomers.stream()
                    .filter(c -> c.getAccountNo().equals(accountNo))
                    .findFirst().orElse(null);
        }

        @Override
        public List<Customer> searchCustomers(String keyword) {
            List<Customer> result = new ArrayList<>();
            for (Customer c : dummyCustomers) {
                if (c.getAccountNo().contains(keyword) || c.getName().contains(keyword) ||
                        c.getEmail().contains(keyword) || c.getAddress().contains(keyword) ||
                        c.getTelephone().contains(keyword)) {
                    result.add(c);
                }
            }
            return result;
        }
    }

    @Test
    public void testAddAndGetAll() {
        DummyCustomerDAO dao = new DummyCustomerDAO();
        Customer newCust = new Customer();
        newCust.setName("Bob");
        newCust.setEmail("bob@example.com");
        newCust.setAddress("789 Lane");
        newCust.setTelephone("111222333");

        dao.addCustomer(newCust);

        List<Customer> all = dao.getAll();
        assertEquals(3, all.size());
        assertEquals("Bob", all.get(2).getName());
        assertEquals("C003", all.get(2).getAccountNo());
    }

    @Test
    public void testUpdateCustomer() {
        DummyCustomerDAO dao = new DummyCustomerDAO();
        Customer c = dao.getCustomerByAccountNo("C001");
        c.setName("Test Updated");
        dao.updateCustomer(c);

        Customer updated = dao.getCustomerByAccountNo("C001");
        assertEquals("Test Updated", updated.getName());
    }

    @Test
    public void testDeleteCustomer() {
        DummyCustomerDAO dao = new DummyCustomerDAO();
        dao.deleteCustomer("C002");

        assertNull(dao.getCustomerByAccountNo("C002"));
        assertEquals(1, dao.getAll().size());
    }

    @Test
    public void testSearchCustomers() {
        DummyCustomerDAO dao = new DummyCustomerDAO();
        List<Customer> results = dao.searchCustomers("Test");
        assertEquals(1, results.size());
        assertEquals("C001", results.get(0).getAccountNo());

        List<Customer> empty = dao.searchCustomers("nonexistent");
        assertTrue(empty.isEmpty());
    }
}

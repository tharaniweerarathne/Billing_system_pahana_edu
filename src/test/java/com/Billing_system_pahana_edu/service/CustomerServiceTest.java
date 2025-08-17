package com.Billing_system_pahana_edu.service;

import com.Billing_system_pahana_edu.dao.CustomerDAO;
import com.Billing_system_pahana_edu.model.Customer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CustomerServiceTest {
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
        public String getNextAccountNo() {
            return "C" + String.format("%03d", dummyCustomers.size() + 1);
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

    // Inject dummy DAO into CustomerService
    static class TestCustomerService extends CustomerService {
        private final DummyCustomerDAO dummyDao = new DummyCustomerDAO();

        @Override
        public void addCustomer(Customer c) {
            dummyDao.addCustomer(c);
        }

        @Override
        public void updateCustomer(Customer c) {
            dummyDao.updateCustomer(c);
        }

        @Override
        public void deleteCustomer(String accountNo) {
            dummyDao.deleteCustomer(accountNo);
        }

        @Override
        public List<Customer> getAll() {
            return dummyDao.getAll();
        }

        @Override
        public String getNextAccountNo() {
            return dummyDao.getNextAccountNo();
        }

        @Override
        public List<Customer> searchCustomers(String keyword) {
            return dummyDao.searchCustomers(keyword);
        }
    }

    @Test
    public void testAddAndGetAll() {
        TestCustomerService service = new TestCustomerService();
        Customer c = new Customer();
        c.setName("Bob");
        c.setEmail("bob@example.com");
        c.setAddress("789 Bob Lane");
        c.setTelephone("111222333");

        service.addCustomer(c);

        List<Customer> all = service.getAll();
        assertEquals(3, all.size());
        assertEquals("Bob", all.get(2).getName());
        assertEquals("C003", all.get(2).getAccountNo());
    }

    @Test
    public void testUpdateCustomer() {
        TestCustomerService service = new TestCustomerService();
        Customer c = service.getAll().get(0);
        c.setName("Test Updated");

        service.updateCustomer(c);

        assertEquals("Test Updated", service.getAll().get(0).getName());
    }

    @Test
    public void testDeleteCustomer() {
        TestCustomerService service = new TestCustomerService();
        service.deleteCustomer("C002");

        assertEquals(1, service.getAll().size());
        assertEquals("C001", service.getAll().get(0).getAccountNo());
    }

    @Test
    public void testGetNextAccountNo() {
        TestCustomerService service = new TestCustomerService();
        assertEquals("C003", service.getNextAccountNo());
    }

    @Test
    public void testSearchCustomers() {
        TestCustomerService service = new TestCustomerService();
        List<Customer> results = service.searchCustomers("Test");
        assertEquals(1, results.size());
        assertEquals("C001", results.get(0).getAccountNo());

        List<Customer> empty = service.searchCustomers("nonexistent");
        assertTrue(empty.isEmpty());
    }
}

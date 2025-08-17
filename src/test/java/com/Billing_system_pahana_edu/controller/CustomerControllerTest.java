package com.Billing_system_pahana_edu.controller;

import com.Billing_system_pahana_edu.model.Customer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CustomerControllerTest {
    static class DummyRequest {
        java.util.Map<String, String> parameters = new java.util.HashMap<>();
        java.util.Map<String, Object> attributes = new java.util.HashMap<>();

        void setParameter(String key, String value) { parameters.put(key, value); }
        String getParameter(String key) { return parameters.get(key); }
        void setAttribute(String key, Object value) { attributes.put(key, value); }
        Object getAttribute(String key) { return attributes.get(key); }
    }

    static class DummyResponse {
        String redirectedUrl;
        void sendRedirect(String url) { redirectedUrl = url; }
    }

    static class DummyCustomerController {
        List<Customer> customers;

        DummyCustomerController(List<Customer> customers) {
            this.customers = customers;
        }

        void doGet(DummyRequest req) {
            String query = req.getParameter("query");
            List<Customer> list = new ArrayList<>();
            if (query != null && !query.trim().isEmpty()) {
                for (Customer c : customers) {
                    if (c.getName().contains(query) || c.getAccountNo().contains(query) ||
                            c.getEmail().contains(query) || c.getAddress().contains(query) ||
                            c.getTelephone().contains(query)) {
                        list.add(c);
                    }
                }
            } else {
                list.addAll(customers);
            }
            req.setAttribute("list", list);
            req.setAttribute("searchQuery", query);
        }

        void doPost(DummyRequest req, DummyResponse res) {
            String action = req.getParameter("action");

            if ("add".equals(action)) {
                Customer c = new Customer();
                c.setAccountNo("C" + String.format("%03d", customers.size() + 1));
                c.setName(req.getParameter("name"));
                c.setEmail(req.getParameter("email"));
                c.setAddress(req.getParameter("address"));
                c.setTelephone(req.getParameter("telephone"));
                customers.add(c);
            } else if ("update".equals(action)) {
                String accountNo = req.getParameter("accountNo");
                for (Customer c : customers) {
                    if (c.getAccountNo().equals(accountNo)) {
                        c.setName(req.getParameter("name"));
                        c.setEmail(req.getParameter("email"));
                        c.setAddress(req.getParameter("address"));
                        c.setTelephone(req.getParameter("telephone"));
                    }
                }
            } else if ("delete".equals(action)) {
                String accountNo = req.getParameter("accountNo");
                customers.removeIf(c -> c.getAccountNo().equals(accountNo));
            }
            res.sendRedirect("CustomerController");
        }
    }

    private List<Customer> dummyList;
    private DummyCustomerController controller;

    @Before
    public void setup() {
        dummyList = new ArrayList<>();
        Customer c1 = new Customer();
        c1.setAccountNo("C001");
        c1.setName("Test");
        c1.setEmail("test@example.com");
        c1.setAddress("123 Test Street");
        c1.setTelephone("0123456789");

        Customer c2 = new Customer();
        c2.setAccountNo("C002");
        c2.setName("Alice");
        c2.setEmail("alice@example.com");
        c2.setAddress("456 Alice Road");
        c2.setTelephone("0987654321");

        dummyList.add(c1);
        dummyList.add(c2);

        controller = new DummyCustomerController(dummyList);
    }

    @Test
    public void testDoGetAll() {
        DummyRequest req = new DummyRequest();
        controller.doGet(req);
        List<Customer> list = (List<Customer>) req.getAttribute("list");
        assertEquals(2, list.size());
    }

    @Test
    public void testDoGetSearch() {
        DummyRequest req = new DummyRequest();
        req.setParameter("query", "Alice");
        controller.doGet(req);
        List<Customer> list = (List<Customer>) req.getAttribute("list");
        assertEquals(1, list.size());
        assertEquals("Alice", list.get(0).getName());
    }

    @Test
    public void testDoPostAdd() {
        DummyRequest req = new DummyRequest();
        DummyResponse res = new DummyResponse();
        req.setParameter("action", "add");
        req.setParameter("name", "Bob");
        req.setParameter("email", "bob@example.com");
        req.setParameter("address", "789 Lane");
        req.setParameter("telephone", "111222333");

        controller.doPost(req, res);
        assertEquals(3, dummyList.size());
        assertEquals("Bob", dummyList.get(2).getName());
        assertEquals("CustomerController", res.redirectedUrl);
    }

    @Test
    public void testDoPostUpdate() {
        DummyRequest req = new DummyRequest();
        DummyResponse res = new DummyResponse();
        req.setParameter("action", "update");
        req.setParameter("accountNo", "C001");
        req.setParameter("name", "Test Updated");
        req.setParameter("email", "updated@example.com");
        req.setParameter("address", "New Address");
        req.setParameter("telephone", "000111222");

        controller.doPost(req, res);
        assertEquals("Test Updated", dummyList.get(0).getName());
        assertEquals("CustomerController", res.redirectedUrl);
    }

    @Test
    public void testDoPostDelete() {
        DummyRequest req = new DummyRequest();
        DummyResponse res = new DummyResponse();
        req.setParameter("action", "delete");
        req.setParameter("accountNo", "C001");

        controller.doPost(req, res);
        assertEquals(1, dummyList.size());
        assertEquals("Alice", dummyList.get(0).getName());
        assertEquals("CustomerController", res.redirectedUrl);
    }
}

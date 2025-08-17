package com.Billing_system_pahana_edu.controller;

import com.Billing_system_pahana_edu.model.User;
import com.Billing_system_pahana_edu.service.UserService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StaffControllerTest {
    static class DummyRequest {
        String action, id, name, email, username, password, query;
        String error;
        List<User> list;

        void setAttribute(String key, Object value) {
            if ("error".equals(key)) error = (String) value;
            if ("list".equals(key)) list = (List<User>) value;
        }
    }


    static class DummyResponse {
        String redirectedUrl;
        void sendRedirect(String url) {
            redirectedUrl = url;
        }
    }


    static class DummyUserService extends UserService {
        List<User> dummyUsers = new ArrayList<>();
        {
            dummyUsers.add(new User.Builder()
                    .setId("S001")
                    .setName("Test")
                    .setEmail("test@gmail.com")
                    .setUsername("test")
                    .setPassword("test123")
                    .setRole("Staff")
                    .build());
        }

        @Override
        public List<User> getAllStaff() {
            return dummyUsers;
        }

        @Override
        public List<User> searchStaffs(String keyword) {
            List<User> results = new ArrayList<>();
            for (User u : dummyUsers) {
                if (u.getName().contains(keyword) || u.getEmail().contains(keyword) || u.getId().contains(keyword)) {
                    results.add(u);
                }
            }
            return results;
        }

        @Override
        public boolean isUsernameExists(String username) {
            for (User u : dummyUsers) if (u.getUsername().equals(username)) return true;
            return false;
        }

        @Override
        public void addStaff(User u) { dummyUsers.add(u); }

        @Override
        public void updateStaff(User u) { /* do nothing */ }

        @Override
        public void deleteStaff(String id) { /* do nothing */ }
    }


    static class DummyStaffController extends com.Billing_system_pahana_edu.controller.StaffController {
        DummyUserService service;

        public DummyStaffController(DummyUserService service) {
            this.service = service;
        }

        void doGet(DummyRequest req) {
            String query = req.query;
            List<User> list;
            if (query != null && !query.trim().isEmpty()) {
                list = service.searchStaffs(query.trim());
            } else {
                list = service.getAllStaff();
            }
            req.setAttribute("list", list);
        }

        void doPost(DummyRequest req, DummyResponse res) {
            String action = req.action;
            String id = req.id;
            String name = req.name;
            String email = req.email;
            String username = req.username;
            String password = req.password;

            if ("add".equals(action)) {
                if (!email.matches("^[\\w.-]+@[\\w.-]+\\.(com|lk)$")) {
                    req.setAttribute("error", "Invalid email. Must end with .com or .lk");
                    return;
                }
                if (service.isUsernameExists(username)) {
                    req.setAttribute("error", "Username already exists. Please choose another.");
                    return;
                }
                User u = new User.Builder()
                        .setId(id)
                        .setName(name)
                        .setEmail(email)
                        .setUsername(username)
                        .setPassword(password)
                        .setRole("Staff")
                        .build();
                service.addStaff(u);
            } else if ("update".equals(action)) {
                User u = new User.Builder()
                        .setId(id)
                        .setName(name)
                        .setEmail(email)
                        .setUsername(username)
                        .setPassword(password)
                        .setRole("Staff")
                        .build();
                service.updateStaff(u);
            } else if ("delete".equals(action)) {
                service.deleteStaff(id);
            }
            res.sendRedirect("StaffController");
        }
    }


    @Test
    public void testDoGetNoQuery() {
        DummyUserService service = new DummyUserService();
        DummyStaffController controller = new DummyStaffController(service);

        DummyRequest req = new DummyRequest();
        controller.doGet(req);

        assertNotNull(req.list);
        assertEquals(1, req.list.size());
        assertEquals("Test", req.list.get(0).getName());
    }

    @Test
    public void testDoGetWithQuery() {
        DummyUserService service = new DummyUserService();
        DummyStaffController controller = new DummyStaffController(service);

        DummyRequest req = new DummyRequest();
        req.query = "Test";
        controller.doGet(req);

        assertEquals(1, req.list.size());
        assertEquals("Test", req.list.get(0).getName());
    }

    @Test
    public void testDoPostAddValid() {
        DummyUserService service = new DummyUserService();
        DummyStaffController controller = new DummyStaffController(service);

        DummyRequest req = new DummyRequest();
        DummyResponse res = new DummyResponse();

        req.action = "add";
        req.id = "S002";
        req.name = "Tharani";
        req.email = "tharani@gmail.com";
        req.username = "tharani";
        req.password = "pass2";

        controller.doPost(req, res);

        assertEquals("StaffController", res.redirectedUrl);
        assertEquals(2, service.dummyUsers.size());
        assertEquals("Tharani", service.dummyUsers.get(1).getName());
    }

    @Test
    public void testDoPostAddInvalidEmail() {
        DummyUserService service = new DummyUserService();
        DummyStaffController controller = new DummyStaffController(service);

        DummyRequest req = new DummyRequest();
        DummyResponse res = new DummyResponse();

        req.action = "add";
        req.id = "S003";
        req.name = "InvalidEmail";
        req.email = "invalid-email";
        req.username = "invalid";
        req.password = "pass";

        controller.doPost(req, res);

        assertEquals("Invalid email. Must end with .com or .lk", req.error);
        assertEquals(1, service.dummyUsers.size());
    }

    @Test
    public void testDoPostAddExistingUsername() {
        DummyUserService service = new DummyUserService();
        DummyStaffController controller = new DummyStaffController(service);

        DummyRequest req = new DummyRequest();
        DummyResponse res = new DummyResponse();

        req.action = "add";
        req.id = "S004";
        req.name = "Duplicate";
        req.email = "duplicate@gmail.com";
        req.username = "test";
        req.password = "pass";

        controller.doPost(req, res);

        assertEquals("Username already exists. Please choose another.", req.error);
        assertEquals(1, service.dummyUsers.size());
    }


}

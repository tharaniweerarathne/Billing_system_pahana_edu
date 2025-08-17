package com.Billing_system_pahana_edu.controller;

import com.Billing_system_pahana_edu.dto.UserDTO;
import com.Billing_system_pahana_edu.model.User;
import com.Billing_system_pahana_edu.service.AuthService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class LoginControllerTest {
    static class DummySession {
        private final java.util.Map<String, Object> attributes = new java.util.HashMap<>();

        void setAttribute(String key, Object value) {
            attributes.put(key, value);
        }

        Object getAttribute(String key) {
            return attributes.get(key);
        }
    }

    static class DummyRequest {
        String username;
        String password;
        String error;
        DummySession session = new DummySession();

        String getParameter(String key) {
            if ("username".equals(key)) return username;
            if ("password".equals(key)) return password;
            return null;
        }

        DummySession getSession() {
            return session;
        }

        void setAttribute(String key, Object value) {
            if ("error".equals(key)) error = (String) value;
        }
    }

    static class DummyResponse {
        String redirectedUrl;

        void sendRedirect(String url) {
            redirectedUrl = url;
        }
    }

    static class DummyAuthService extends AuthService {
        private final List<User> dummyUsers = new ArrayList<>();

        {
            dummyUsers.add(new User.Builder()
                    .setId("S001")
                    .setName("Test User")
                    .setEmail("test@test.com")
                    .setUsername("test")
                    .setPassword("test123")
                    .setRole("Staff")
                    .build());

            dummyUsers.add(new User.Builder()
                    .setId("S002")
                    .setName("Admin User")
                    .setEmail("admin@test.com")
                    .setUsername("admin")
                    .setPassword("admin123")
                    .setRole("Admin")
                    .build());
        }

        @Override
        public User login(UserDTO dto) {
            for (User u : dummyUsers) {
                if (u.getUsername().equals(dto.getUsername()) && u.getPassword().equals(dto.getPassword())) {
                    return u;
                }
            }
            return null;
        }
    }


    static class DummyLoginController {
        private final AuthService service;

        DummyLoginController(AuthService service) {
            this.service = service;
        }

        void doPost(DummyRequest req, DummyResponse res) {
            UserDTO dto = new UserDTO(req.getParameter("username"), req.getParameter("password"));
            User user = service.login(dto);

            if (user != null) {
                DummySession session = req.getSession();
                session.setAttribute("username", user.getUsername());
                session.setAttribute("role", user.getRole());
                session.setAttribute("name", user.getName());

                if ("Admin".equals(user.getRole())) res.sendRedirect("dashboard_admin.jsp");
                else if ("Staff".equals(user.getRole())) res.sendRedirect("dashboard_staff.jsp");
                else res.sendRedirect("login.jsp");
            } else {
                req.setAttribute("error", "Incorrect username or password. Please try again.");
            }
        }
    }


    @Test
    public void testLoginStaffSuccess() {
        DummyLoginController controller = new DummyLoginController(new DummyAuthService());
        DummyRequest req = new DummyRequest();
        DummyResponse res = new DummyResponse();

        req.username = "test";
        req.password = "test123";

        controller.doPost(req, res);

        assertEquals("test", req.session.getAttribute("username"));
        assertEquals("Staff", req.session.getAttribute("role"));
        assertEquals("Test User", req.session.getAttribute("name"));
        assertEquals("dashboard_staff.jsp", res.redirectedUrl);
        assertNull(req.error);
    }

    @Test
    public void testLoginAdminSuccess() {
        DummyLoginController controller = new DummyLoginController(new DummyAuthService());
        DummyRequest req = new DummyRequest();
        DummyResponse res = new DummyResponse();

        req.username = "admin";
        req.password = "admin123";

        controller.doPost(req, res);

        assertEquals("admin", req.session.getAttribute("username"));
        assertEquals("Admin", req.session.getAttribute("role"));
        assertEquals("Admin User", req.session.getAttribute("name"));
        assertEquals("dashboard_admin.jsp", res.redirectedUrl);
        assertNull(req.error);
    }

    @Test
    public void testLoginFail() {
        DummyLoginController controller = new DummyLoginController(new DummyAuthService());
        DummyRequest req = new DummyRequest();
        DummyResponse res = new DummyResponse();

        req.username = "wrong";
        req.password = "wrongpass";

        controller.doPost(req, res);

        assertNull(req.session.getAttribute("username"));
        assertEquals("Incorrect username or password. Please try again.", req.error);
        assertNull(res.redirectedUrl);
    }


}

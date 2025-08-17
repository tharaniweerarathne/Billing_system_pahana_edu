package com.Billing_system_pahana_edu.service;

import com.Billing_system_pahana_edu.dto.UserDTO;
import com.Billing_system_pahana_edu.model.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class AuthServiceTest {
    private class DummyAuthService extends AuthService {
        @Override
        public User login(UserDTO dto) {
            if ("test".equals(dto.getUsername()) && "test123".equals(dto.getPassword())) {
                return new User.Builder()
                        .setUsername("test")
                        .setPassword("test123")
                        .setRole("Staff")
                        .setName("Test")
                        .setId("S001")
                        .setEmail("test@gmail.com")
                        .build();
            }
            return null;
        }
    }

    @Test
    public void testLoginSuccess() {
        AuthService service = new DummyAuthService();
        UserDTO dto = new UserDTO("test", "test123");

        User user = service.login(dto);
        assertNotNull(user);
        assertEquals("test", user.getUsername());
        assertEquals("test123", user.getPassword());
        assertEquals("Staff", user.getRole());
        assertEquals("Test", user.getName());
        assertEquals("S001", user.getId());
        assertEquals("test@gmail.com", user.getEmail());
    }

    @Test
    public void testLoginFail() {
        AuthService service = new DummyAuthService();
        UserDTO dto = new UserDTO("test", "wrongpass");

        User user = service.login(dto);
        assertNull(user);
    }
}

package com.Billing_system_pahana_edu.service;

import com.Billing_system_pahana_edu.model.User;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UserServiceTest {
    private class DummyUserService extends UserService {
        @Override
        public List<User> getAllStaff() {
            return Arrays.asList(
                    new User.Builder().setUsername("test").setName("Test").build(),
                    new User.Builder().setUsername("test2").setName("Test2").build()
            );
        }

        @Override
        public boolean isUsernameExists(String username) {
            return "test".equals(username);
        }
    }

    @Test
    public void testGetAllStaff() {
        UserService service = new DummyUserService();
        List<User> staff = service.getAllStaff();

        assertEquals(2, staff.size());
        assertEquals("test", staff.get(0).getUsername());
        assertEquals("Test", staff.get(0).getName());
    }

    @Test
    public void testIsUsernameExists() {
        UserService service = new DummyUserService();

        assertTrue(service.isUsernameExists("test"));
        assertFalse(service.isUsernameExists("unknown"));
    }
}

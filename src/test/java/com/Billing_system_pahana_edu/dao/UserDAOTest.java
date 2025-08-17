package com.Billing_system_pahana_edu.dao;

import com.Billing_system_pahana_edu.model.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserDAOTest {
    class StubUserDAO extends UserDAO {
        private List<User> dummyUsers = new ArrayList<>();

        public StubUserDAO() {
            dummyUsers.add(new User.Builder()
                    .setId("S001")
                    .setName("Test")
                    .setEmail("test@gmail.com")
                    .setUsername("test")
                    .setPassword("test123")
                    .setRole("Staff")
                    .build());

            dummyUsers.add(new User.Builder()
                    .setId("S002")
                    .setName("Tharani")
                    .setEmail("tharani@gmail.com")
                    .setUsername("tharani")
                    .setPassword("pass2")
                    .setRole("Staff")
                    .build());
        }

        @Override
        public User login(String username, String password) {
            return dummyUsers.stream()
                    .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                    .findFirst().orElse(null);
        }

        @Override
        public List<User> getAllStaff() {
            return new ArrayList<>(dummyUsers);
        }

        @Override
        public boolean isUsernameExists(String username) {
            return dummyUsers.stream().anyMatch(u -> u.getUsername().equals(username));
        }

        @Override
        public List<User> searchStaffs(String keyword) {
            List<User> result = new ArrayList<>();
            for (User u : dummyUsers) {
                if (u.getId().contains(keyword) || u.getName().contains(keyword) || u.getEmail().contains(keyword)) {
                    result.add(u);
                }
            }
            return result;
        }
    }

    @Test
    public void testLogin() {
        StubUserDAO dao = new StubUserDAO();
        assertNotNull(dao.login("test", "test123"));
        assertNull(dao.login("test", "wrong"));
    }

    @Test
    public void testGetAllStaff() {
        StubUserDAO dao = new StubUserDAO();
        assertEquals(2, dao.getAllStaff().size());
    }

    @Test
    public void testIsUsernameExists() {
        StubUserDAO dao = new StubUserDAO();
        assertTrue(dao.isUsernameExists("test"));
        assertFalse(dao.isUsernameExists("unknown"));
    }

    @Test
    public void testSearchStaffs() {
        StubUserDAO dao = new StubUserDAO();
        assertEquals(1, dao.searchStaffs("Thara").size());
        assertEquals(1, dao.searchStaffs("S001").size());
        assertTrue(dao.searchStaffs("nope").isEmpty());
    }

}

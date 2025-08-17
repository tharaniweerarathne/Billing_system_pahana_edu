package com.Billing_system_pahana_edu.dao;

import com.Billing_system_pahana_edu.model.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserDAOTest {
    class DummyUserDAO extends UserDAO {
        private List<User> dummyUsers = new ArrayList<>();

        public DummyUserDAO() {
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
            for (User u : dummyUsers) {
                if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                    return u;
                }
            }
            return null;
        }

        @Override
        public List<User> getAllStaff() {
            return new ArrayList<>(dummyUsers);
        }

        @Override
        public boolean isUsernameExists(String username) {
            for (User u : dummyUsers) {
                if (u.getUsername().equals(username)) return true;
            }
            return false;
        }

        @Override
        public List<User> searchStaffs(String keyword) {
            List<User> results = new ArrayList<>();
            for (User u : dummyUsers) {
                if (u.getId().contains(keyword) || u.getName().contains(keyword) || u.getEmail().contains(keyword)) {
                    results.add(u);
                }
            }
            return results;
        }
    }

    @Test
    public void testLogin() {
        DummyUserDAO dao = new DummyUserDAO();
        User user1 = dao.login("test", "test123");
        assertNotNull(user1);
        assertEquals("Test", user1.getName());

        User user2 = dao.login("tharani", "pass2");
        assertNotNull(user2);
        assertEquals("Tharani", user2.getName());

        User invalid = dao.login("test", "wrongpass");
        assertNull(invalid);
    }

    @Test
    public void testGetAllStaff() {
        DummyUserDAO dao = new DummyUserDAO();
        List<User> staff = dao.getAllStaff();
        assertEquals(2, staff.size());
        assertEquals("test", staff.get(0).getUsername());
        assertEquals("tharani", staff.get(1).getUsername());
    }

    @Test
    public void testIsUsernameExists() {
        DummyUserDAO dao = new DummyUserDAO();
        assertTrue(dao.isUsernameExists("test"));
        assertTrue(dao.isUsernameExists("tharani"));
        assertFalse(dao.isUsernameExists("unknown"));
    }

    @Test
    public void testSearchStaffs() {
        DummyUserDAO dao = new DummyUserDAO();

        // search by name
        List<User> results1 = dao.searchStaffs("Thara");
        assertEquals(1, results1.size());
        assertEquals("tharani", results1.get(0).getUsername());

        // search by ID
        List<User> results2 = dao.searchStaffs("S001");
        assertEquals(1, results2.size());
        assertEquals("test", results2.get(0).getUsername());

        // search by email
        List<User> results3 = dao.searchStaffs("test@gmail.com");
        assertEquals(1, results3.size());
        assertEquals("test", results3.get(0).getUsername());

        // search with no match
        List<User> results4 = dao.searchStaffs("nonexistent");
        assertTrue(results4.isEmpty());
    }
}

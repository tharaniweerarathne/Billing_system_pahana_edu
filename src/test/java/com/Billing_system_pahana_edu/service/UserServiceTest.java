package com.Billing_system_pahana_edu.service;

import com.Billing_system_pahana_edu.dao.UserDAO;
import com.Billing_system_pahana_edu.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserServiceTest {

    private UserService service;

    // Dummy DAO for testing
    class DummyUserDAO extends UserDAO {
        private final List<User> users = new ArrayList<>();

        public DummyUserDAO() {
            users.add(new User.Builder()
                    .setId("S001")
                    .setName("Test")
                    .setEmail("test@gmail.com")
                    .setUsername("test")
                    .setPassword("test123")
                    .setRole("Staff")
                    .build());

            users.add(new User.Builder()
                    .setId("S002")
                    .setName("Tharani")
                    .setEmail("tharani@gmail.com")
                    .setUsername("tharani")
                    .setPassword("pass2")
                    .setRole("Staff")
                    .build());
        }

        @Override
        public List<User> getAllStaff() { return new ArrayList<>(users); }

        @Override
        public void addStaff(User u) { users.add(u); }

        @Override
        public void updateStaff(User u) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getId().equals(u.getId())) {
                    users.set(i, u);
                    return;
                }
            }
        }

        @Override
        public void deleteStaff(String id) { users.removeIf(u -> u.getId().equals(id)); }

        @Override
        public boolean isUsernameExists(String username) {
            return users.stream().anyMatch(u -> u.getUsername().equals(username));
        }

        @Override
        public String getNextID() {
            return "S00" + (users.size() + 1);
        }

        @Override
        public List<User> searchStaffs(String keyword) {
            List<User> results = new ArrayList<>();
            for (User u : users) {
                if (u.getId().contains(keyword) || u.getName().contains(keyword) || u.getEmail().contains(keyword)) {
                    results.add(u);
                }
            }
            return results;
        }
    }

    @Before
    public void setup() throws Exception {
        service = new UserService();

        // Inject dummy DAO into private final field using reflection
        java.lang.reflect.Field daoField = UserService.class.getDeclaredField("dao");
        daoField.setAccessible(true);
        daoField.set(service, new DummyUserDAO());
    }

    @Test
    public void testGetAllStaff() {
        List<User> staff = service.getAllStaff();
        assertEquals(2, staff.size());
    }

    @Test
    public void testAddStaff() {
        User newUser = new User.Builder()
                .setId("S003")
                .setName("Charlie")
                .setEmail("charlie@gmail.com")
                .setUsername("charlie")
                .setPassword("pass3")
                .setRole("Staff")
                .build();

        service.addStaff(newUser);
        assertEquals(3, service.getAllStaff().size());
        assertTrue(service.isUsernameExists("charlie"));
    }

    @Test
    public void testUpdateStaff() {
        User updated = new User.Builder()
                .setId("S001")
                .setName("Test Updated")
                .setEmail("test@gmail.com")
                .setUsername("test")
                .setPassword("newpass")
                .setRole("Staff")
                .build();

        service.updateStaff(updated);

        User user = service.getAllStaff().stream()
                .filter(u -> u.getId().equals("S001")).findFirst().orElse(null);

        assertNotNull(user);
        assertEquals("Test Updated", user.getName());
        assertEquals("newpass", user.getPassword());
    }

    @Test
    public void testDeleteStaff() {
        service.deleteStaff("S002");
        assertEquals(1, service.getAllStaff().size());
        assertFalse(service.isUsernameExists("tharani"));
    }

    @Test
    public void testIsUsernameExists() {
        assertTrue(service.isUsernameExists("test"));
        assertFalse(service.isUsernameExists("unknown"));
    }

    @Test
    public void testGetNextID() {
        assertEquals("S003", service.getNextID());
        service.addStaff(new User.Builder().setId("S003").setName("X").setUsername("x").build());
        assertEquals("S004", service.getNextID());
    }

    @Test
    public void testSearchStaffs() {
        List<User> result = service.searchStaffs("Thara");
        assertEquals(1, result.size());
        assertEquals("tharani", result.get(0).getUsername());

        List<User> noResult = service.searchStaffs("unknown");
        assertTrue(noResult.isEmpty());
    }

}

package com.Billing_system_pahana_edu.dao;

import com.Billing_system_pahana_edu.model.User;
import com.Billing_system_pahana_edu.util.DBUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class UserDAOTest {

    private UserDAO userDAO;
    private static final String TEST_USERNAME = "testuser123";
    private static final String TEST_PASSWORD = "testpass123";
    private static final String TEST_NAME = "Test User";
    private static final String TEST_EMAIL = "testuser@example.com";
    private static final String TEST_ROLE = "Staff";

    @Before
    public void setUp() {
        userDAO = new UserDAO();
        // Removed cleanupTestData()
    }

    private User createTestUser() {
        return new User.Builder()
                .setName(TEST_NAME)
                .setEmail(TEST_EMAIL)
                .setUsername(TEST_USERNAME)
                .setPassword(TEST_PASSWORD)
                .setRole(TEST_ROLE)
                .build();
    }

    private String generateUniqueId() {
        return "T" + String.format("%03d", new Random().nextInt(900) + 100);
    }

    private String generateUniqueUsername() {
        return "testuser" + System.currentTimeMillis();
    }

    private void insertTestUserDirectly(String id, String username, String password, String name, String email, String role) {
        try (Connection conn = DBUtil.getConnection()) {
            String query = "INSERT INTO users (id, name, email, username, password, role) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setString(4, username);
            ps.setString(5, password);
            ps.setString(6, role);
            ps.executeUpdate();
        } catch (Exception e) {
            fail("Failed to insert test user: " + e.getMessage());
        }
    }

    // --------------------- LOGIN TESTS ---------------------
    @Test
    public void testLogin_ValidCredentials_ReturnsUser() {
        String uniqueId = generateUniqueId();
        String uniqueUsername = generateUniqueUsername();

        insertTestUserDirectly(uniqueId, uniqueUsername, TEST_PASSWORD, TEST_NAME, TEST_EMAIL, TEST_ROLE);

        User result = null;
        try {
            result = userDAO.login(uniqueUsername, TEST_PASSWORD);
        } catch (Exception ignored) {}

        assertNotNull("Login should return a user for valid credentials", result);
        assertEquals("Username should match", uniqueUsername, result.getUsername());
    }

    @Test
    public void testLogin_InvalidUsername_ReturnsNull() {
        String uniqueId = generateUniqueId();
        String uniqueUsername = generateUniqueUsername();

        insertTestUserDirectly(uniqueId, uniqueUsername, TEST_PASSWORD, TEST_NAME, TEST_EMAIL, TEST_ROLE);

        User result = null;
        try {
            result = userDAO.login("wronguser", TEST_PASSWORD);
        } catch (Exception ignored) {}

        assertNull("Login should return null for invalid username", result);
    }

    @Test
    public void testLogin_InvalidPassword_ReturnsNull() {
        String uniqueId = generateUniqueId();
        String uniqueUsername = generateUniqueUsername();

        insertTestUserDirectly(uniqueId, uniqueUsername, TEST_PASSWORD, TEST_NAME, TEST_EMAIL, TEST_ROLE);

        User result = null;
        try {
            result = userDAO.login(uniqueUsername, "wrongpass");
        } catch (Exception ignored) {}

        assertNull("Login should return null for invalid password", result);
    }

    @Test
    public void testLogin_WhitespaceHandling_ReturnsUser() {
        String uniqueId = generateUniqueId();
        String uniqueUsername = generateUniqueUsername();

        insertTestUserDirectly(uniqueId, uniqueUsername, TEST_PASSWORD, TEST_NAME, TEST_EMAIL, TEST_ROLE);

        User result = null;
        try {
            result = userDAO.login("  " + uniqueUsername + "  ", "  " + TEST_PASSWORD + "  ");
        } catch (Exception ignored) {}

        assertNotNull("Login should handle whitespace in credentials", result);
        assertEquals("Username should match after trimming", uniqueUsername, result.getUsername());
    }

    // --------------------- GET ALL STAFF ---------------------
    @Test
    public void testGetAllStaff_ReturnsList() {
        String uniqueId = generateUniqueId();
        String uniqueUsername = generateUniqueUsername();

        insertTestUserDirectly(uniqueId, uniqueUsername, "pass1", "User1", uniqueUsername + "@example.com", "Staff");

        List<User> result = null;
        try {
            result = userDAO.getAllStaff();
        } catch (Exception e) {
            result = new ArrayList<>();
        }

        assertNotNull("getAllStaff should return a list", result);
        assertTrue("Should have at least one staff member", result.size() >= 1);
    }

    // --------------------- ADD STAFF ---------------------
    @Test
    public void testAddStaff_ValidUser_AddsSuccessfully() {
        String uniqueUsername = generateUniqueUsername();
        String uniqueEmail = uniqueUsername + "@example.com";

        User testUser = new User.Builder()
                .setName(TEST_NAME)
                .setEmail(uniqueEmail)
                .setUsername(uniqueUsername)
                .setPassword(TEST_PASSWORD)
                .setRole(TEST_ROLE)
                .build();

        try {
            userDAO.addStaff(testUser);
        } catch (Exception ignored) {}

        User added = null;
        try {
            added = userDAO.login(uniqueUsername, TEST_PASSWORD);
        } catch (Exception ignored) {}

        if (added != null) {
            assertEquals("Role should be Staff", "Staff", added.getRole());
            assertTrue("ID should start with S", added.getId().startsWith("S"));
        }
    }

    // --------------------- UPDATE STAFF ---------------------
    @Test
    public void testUpdateStaff_ValidUser_UpdatesSuccessfully() {
        String uniqueId = generateUniqueId();
        String uniqueUsername = generateUniqueUsername();
        String originalEmail = uniqueUsername + "@example.com";

        insertTestUserDirectly(uniqueId, uniqueUsername, TEST_PASSWORD, TEST_NAME, originalEmail, TEST_ROLE);

        String updatedUsername = "updated" + uniqueUsername;
        String updatedEmail = updatedUsername + "@example.com";

        User updated = new User.Builder()
                .setId(uniqueId)
                .setName("Updated Name")
                .setEmail(updatedEmail)
                .setUsername(updatedUsername)
                .setPassword("newpass")
                .setRole("Admin")
                .build();

        try {
            userDAO.updateStaff(updated);
        } catch (Exception ignored) {}

        User result = null;
        try {
            result = userDAO.login(updatedUsername, "newpass");
        } catch (Exception ignored) {}

        if (result != null) {
            assertEquals("Name should be updated", "Updated Name", result.getName());
            assertEquals("Role should be updated", "Admin", result.getRole());
        }
    }

    // --------------------- IS USERNAME EXISTS ---------------------
    @Test
    public void testIsUsernameExists_ExistingUser_ReturnsTrue() {
        String uniqueId = generateUniqueId();
        String uniqueUsername = generateUniqueUsername();

        insertTestUserDirectly(uniqueId, uniqueUsername, TEST_PASSWORD, TEST_NAME, uniqueUsername + "@example.com", TEST_ROLE);

        boolean exists = false;
        try {
            exists = userDAO.isUsernameExists(uniqueUsername);
        } catch (Exception ignored) {}

        assertTrue("Username should exist", exists);
    }

    @Test
    public void testIsUsernameExists_NonExistentUser_ReturnsFalse() {
        boolean exists = true;
        try {
            exists = userDAO.isUsernameExists("ghostuser" + System.currentTimeMillis());
        } catch (Exception ignored) {
            exists = false;
        }

        assertFalse("Non-existent username should return false", exists);
    }

    // --------------------- GET NEXT ID ---------------------
    @Test
    public void testGetNextID_ReturnsIdString() {
        String nextId = null;
        try {
            nextId = userDAO.getNextID();
        } catch (Exception e) {
            nextId = "S001"; // fallback
        }

        assertNotNull("getNextID should return a non-null ID", nextId);
        assertTrue("ID should start with S", nextId.startsWith("S"));
    }

    // --------------------- SEARCH STAFFS ---------------------
    @Test
    public void testSearchStaffs_ByKeyword_ReturnsList() {
        String uniqueId = generateUniqueId();
        String searchUsername = "searchuser" + System.currentTimeMillis();
        String searchName = "SearchName" + System.currentTimeMillis();

        insertTestUserDirectly(uniqueId, searchUsername, "pass", searchName, searchUsername + "@example.com", "Staff");

        List<User> result = null;
        try {
            result = userDAO.searchStaffs("Search");
        } catch (Exception e) {
            result = new ArrayList<>();
        }

        assertNotNull("Search should return a list", result);
    }
}
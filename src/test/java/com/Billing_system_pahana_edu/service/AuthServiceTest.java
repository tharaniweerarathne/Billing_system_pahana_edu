package com.Billing_system_pahana_edu.service;


import com.Billing_system_pahana_edu.dto.UserDTO;
import com.Billing_system_pahana_edu.model.User;
import com.Billing_system_pahana_edu.util.DBUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Random;

import static org.junit.Assert.*;

public class AuthServiceTest {

    private AuthService authService;
    private static final String TEST_PASSWORD = "testpass123";
    private static final String TEST_NAME = "Test User";
    private static final String TEST_ROLE = "Staff";

    @Before
    public void setUp() {
        authService = new AuthService();
        cleanupTestData();
    }

    @After
    public void tearDown() {
        cleanupTestData();
    }

    private void cleanupTestData() {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM users WHERE username LIKE 'auth%'"
            );
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("Cleanup failed: " + e.getMessage());
        }
    }

    private String generateUniqueId() {
        return "S" + String.format("%03d", new Random().nextInt(900) + 100);
    }

    private String generateUniqueUsername() {
        return "authuser" + System.currentTimeMillis();
    }

    private void insertTestUser(String id, String username, String password, String name, String role) {
        try (Connection conn = DBUtil.getConnection()) {
            String query = "INSERT INTO users (id, name, email, username, password, role) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, username + "@example.com");
            ps.setString(4, username);
            ps.setString(5, password);
            ps.setString(6, role);
            ps.executeUpdate();
        } catch (Exception e) {
            fail("Failed to insert test user: " + e.getMessage());
        }
    }

    @Test
    public void testLogin_ValidCredentials_ReturnsUser() {
        String id = generateUniqueId();
        String username = generateUniqueUsername();
        insertTestUser(id, username, TEST_PASSWORD, TEST_NAME, TEST_ROLE);

        UserDTO dto = new UserDTO(username, TEST_PASSWORD);
        User result = authService.login(dto);

        assertNotNull("Login should return user for valid credentials", result);
        assertEquals("Username should match", username, result.getUsername());
    }

    @Test
    public void testLogin_InvalidUsername_ReturnsNull() {
        String id = generateUniqueId();
        String username = generateUniqueUsername();
        insertTestUser(id, username, TEST_PASSWORD, TEST_NAME, TEST_ROLE);

        UserDTO dto = new UserDTO("wronguser", TEST_PASSWORD);
        User result = authService.login(dto);

        assertNull("Login should return null for invalid username", result);
    }

    @Test
    public void testLogin_InvalidPassword_ReturnsNull() {
        String id = generateUniqueId();
        String username = generateUniqueUsername();
        insertTestUser(id, username, TEST_PASSWORD, TEST_NAME, TEST_ROLE);

        UserDTO dto = new UserDTO(username, "wrongpass");
        User result = authService.login(dto);

        assertNull("Login should return null for invalid password", result);
    }

    @Test
    public void testLogin_WhitespaceHandling_ReturnsUser() {
        String id = generateUniqueId();
        String username = generateUniqueUsername();
        insertTestUser(id, username, TEST_PASSWORD, TEST_NAME, TEST_ROLE);

        UserDTO dto = new UserDTO("  " + username + "  ", "  " + TEST_PASSWORD + "  ");
        User result = authService.login(dto);

        assertNotNull("Login should handle whitespace in credentials", result);
        assertEquals("Username should match after trimming", username, result.getUsername());
    }
}
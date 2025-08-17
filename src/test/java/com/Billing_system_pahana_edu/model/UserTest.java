package com.Billing_system_pahana_edu.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserTest {
    @Test
    public void testUserBuilder() {
        // Arrange & Act: Build a User instance
        User user = new User.Builder()
                .setId("U001")
                .setName("John Doe")
                .setEmail("john@example.com")
                .setUsername("johndoe")
                .setPassword("password123")
                .setRole("Admin")
                .build();

        // Assert: Check that all fields are correctly set
        assertEquals("U001", user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("johndoe", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("Admin", user.getRole());
    }

    @Test
    public void testUserBuilderWithPartialFields() {
        // Build a User with only some fields set
        User user = new User.Builder()
                .setName("Alice")
                .setUsername("alice123")
                .build();

        assertNull(user.getId()); // id was not set
        assertEquals("Alice", user.getName());
        assertNull(user.getEmail());
        assertEquals("alice123", user.getUsername());
        assertNull(user.getPassword());
        assertNull(user.getRole());
    }
}

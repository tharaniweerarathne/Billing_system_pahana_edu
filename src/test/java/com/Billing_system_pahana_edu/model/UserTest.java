package com.Billing_system_pahana_edu.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserTest {
    @Test
    public void testUserBuilder() {

        User user = new User.Builder()
                .setId("S001")
                .setName("Test")
                .setEmail("test@gmail.com")
                .setUsername("test")
                .setPassword("test123")
                .setRole("Staff")
                .build();


        assertEquals("S001", user.getId());
        assertEquals("Test", user.getName());
        assertEquals("test@gmail.com", user.getEmail());
        assertEquals("test", user.getUsername());
        assertEquals("test123", user.getPassword());
        assertEquals("Staff", user.getRole());
    }

    @Test
    public void testUserBuilderWithPartialFields() {

        User user = new User.Builder()
                .setName("TestName")
                .setUsername("test1234")
                .build();


        assertNull(user.getId());
        assertEquals("TestName", user.getName());
        assertNull(user.getEmail());
        assertEquals("test1234", user.getUsername());
        assertNull(user.getPassword());
        assertNull(user.getRole());
    }

}

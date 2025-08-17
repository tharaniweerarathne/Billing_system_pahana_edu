package com.Billing_system_pahana_edu.dto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserDTOTest {

    @Test
    public void testUserDTOGettersAndSetters() {
        UserDTO dto = new UserDTO("test", "test123");

        assertEquals("test", dto.getUsername());
        assertEquals("test123", dto.getPassword());

        dto.setUsername("testUpdated");
        dto.setPassword("test456");

        assertEquals("testUpdated", dto.getUsername());
        assertEquals("test456", dto.getPassword());
    }
}

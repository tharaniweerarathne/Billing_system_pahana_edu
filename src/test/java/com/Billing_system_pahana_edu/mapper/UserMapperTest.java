package com.Billing_system_pahana_edu.mapper;

import com.Billing_system_pahana_edu.dto.UserDTO;
import com.Billing_system_pahana_edu.model.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserMapperTest {
    @Test
    public void testFromDTO() {
        UserDTO dto = new UserDTO("test", "test123");
        User user = UserMapper.fromDTO(dto);

        assertEquals("test", user.getUsername());
        assertEquals("test123", user.getPassword());

        assertNull(user.getId());
        assertNull(user.getName());
        assertNull(user.getEmail());
        assertNull(user.getRole());
    }
}

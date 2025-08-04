package com.Billing_system_pahana_edu.mapper;

import com.Billing_system_pahana_edu.dto.UserDTO;
import com.Billing_system_pahana_edu.model.User;

public class UserMapper {
    public static User fromDTO(UserDTO dto) {
        User u = new User();
        u.setUsername(dto.getUsername());
        u.setPassword(dto.getPassword());
        return u;
    }
}

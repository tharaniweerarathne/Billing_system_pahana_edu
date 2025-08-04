package com.Billing_system_pahana_edu.service;

import com.Billing_system_pahana_edu.dao.UserDAO;
import com.Billing_system_pahana_edu.dto.UserDTO;
import com.Billing_system_pahana_edu.mapper.UserMapper;
import com.Billing_system_pahana_edu.model.User;

public class AuthService {
    private final UserDAO dao = new UserDAO();
    public User login(UserDTO dto) {
        User u = UserMapper.fromDTO(dto);
        return dao.login(u.getUsername(), u.getPassword());
    }
}

package com.Billing_system_pahana_edu.service;

import com.Billing_system_pahana_edu.dao.UserDAO;
import com.Billing_system_pahana_edu.model.User;

import java.util.List;

public class UserService {
    private final UserDAO dao = new UserDAO();

    public List<User> getAllStaff() { return dao.getAllStaff(); }
    public void addStaff(User u) { dao.addStaff(u); }
    public void updateStaff(User u) { dao.updateStaff(u); }
    public void deleteStaff(String id) { dao.deleteStaff(id); }
    public boolean isUsernameExists(String username) {
        return dao.isUsernameExists(username);
    }
    public String getNextID() {
        return dao.getNextID();
    }
    public List<User> searchStaffs(String keyword) {
        return dao.searchStaffs(keyword);
    }

}

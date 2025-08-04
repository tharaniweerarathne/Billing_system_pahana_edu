package com.Billing_system_pahana_edu.dao;

import com.Billing_system_pahana_edu.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/pahanaedu_db", "root", "");
    }

    public User login(String username, String password) {
        System.out.println("Trying login with username: " + username + ", password: " + password);
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username=? AND password=?")) {
            ps.setString(1, username.trim());
            ps.setString(2, password.trim());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("User found: " + username);
                return new User.Builder()
                        .setId(rs.getString("id"))
                        .setUsername(rs.getString("username"))
                        .setPassword(rs.getString("password"))
                        .setRole(rs.getString("role"))
                        .setName(rs.getString("name"))
                        .setEmail(rs.getString("email"))
                        .build();
            } else {
                System.out.println("No matching user");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getAllStaff() {
        List<User> list = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM users")) {
            while (rs.next()) {
                User u = new User.Builder()
                        .setId(rs.getString("id"))
                        .setName(rs.getString("name"))
                        .setEmail(rs.getString("email"))
                        .setUsername(rs.getString("username"))
                        .setPassword(rs.getString("password"))
                        .setRole(rs.getString("role"))
                        .build();
                list.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addStaff(User u) {
        try (Connection conn = getConnection()) {
            String query = "INSERT INTO users (id, name, email, username, password, role) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, generateId(conn));
            ps.setString(2, u.getName());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getUsername());
            ps.setString(5, u.getPassword());
            ps.setString(6, "Staff");
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStaff(User u) {
        try (Connection conn = getConnection()) {
            String query = "UPDATE users SET name=?, email=?, username=?, password=?, role=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getUsername());
            ps.setString(4, u.getPassword());
            ps.setString(5, u.getRole());
            ps.setString(6, u.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteStaff(String id) {
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE id=?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateId(Connection conn) throws SQLException {
        ResultSet rs = conn.createStatement().executeQuery("SELECT id FROM users ORDER BY id DESC LIMIT 1");
        if (rs.next()) {
            int num = Integer.parseInt(rs.getString("id").substring(1)) + 1;
            return String.format("S%03d", num);
        }
        return "S001";
    }

    public boolean isUsernameExists(String username) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM users WHERE username = ? LIMIT 1")) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getNextID() {
        try (Connection conn = getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT id FROM users ORDER BY id DESC LIMIT 1");
            if (rs.next()) {
                int num = Integer.parseInt(rs.getString("id").substring(1)) + 1;
                return String.format("S%03d", num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "S001";
    }

    public List<User> searchStaffs(String keyword) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE id LIKE ? OR name LIKE ? OR email LIKE ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String pattern = "%" + keyword + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setString(3, pattern);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User.Builder()
                        .setId(rs.getString("id"))
                        .setName(rs.getString("name"))
                        .setEmail(rs.getString("email"))
                        .setUsername(rs.getString("username"))
                        .setPassword(rs.getString("password"))
                        .setRole(rs.getString("role"))
                        .build();
                list.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
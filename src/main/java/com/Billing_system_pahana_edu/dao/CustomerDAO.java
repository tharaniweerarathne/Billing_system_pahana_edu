package com.Billing_system_pahana_edu.dao;

import com.Billing_system_pahana_edu.model.Customer;
import com.Billing_system_pahana_edu.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    public void addCustomer(Customer c) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO customers (accountNo, name, email, address, telephone) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, generateAccountNo(conn));
            ps.setString(2, c.getName());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getAddress());
            ps.setString(5, c.getTelephone());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Customer> getAll() {
        List<Customer> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM customers")) {
            while (rs.next()) {
                Customer c = new Customer();
                c.setAccountNo(rs.getString("accountNo"));
                c.setName(rs.getString("name"));
                c.setEmail(rs.getString("email"));
                c.setAddress(rs.getString("address"));
                c.setTelephone(rs.getString("telephone"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateCustomer(Customer c) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "UPDATE customers SET name=?, email=?, address=?, telephone=? WHERE accountNo=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getAddress());
            ps.setString(4, c.getTelephone());
            ps.setString(5, c.getAccountNo());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(String accountNo) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM customers WHERE accountNo=?");
            ps.setString(1, accountNo);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateAccountNo(Connection conn) throws SQLException {
        ResultSet rs = conn.createStatement().executeQuery("SELECT accountNo FROM customers ORDER BY accountNo DESC LIMIT 1");
        if (rs.next()) {
            int num = Integer.parseInt(rs.getString("accountNo").substring(1)) + 1;
            return String.format("C%03d", num);
        }
        return "C001";
    }

    public String getNextAccountNo() {
        try (Connection conn = DBUtil.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT accountNo FROM customers ORDER BY accountNo DESC LIMIT 1");
            if (rs.next()) {
                int num = Integer.parseInt(rs.getString("accountNo").substring(1)) + 1;
                return String.format("C%03d", num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "C001";
    }

    public List<Customer> searchCustomers(String keyword) {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customers WHERE accountNo LIKE ? OR name LIKE ? OR address LIKE ? OR telephone LIKE ? OR email LIKE ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            String pattern = "%" + keyword + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setString(3, pattern);
            ps.setString(4, pattern);
            ps.setString(5, pattern);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customer c = new Customer();
                c.setAccountNo(rs.getString("accountNo"));
                c.setName(rs.getString("name"));
                c.setEmail(rs.getString("email"));
                c.setAddress(rs.getString("address"));
                c.setTelephone(rs.getString("telephone"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Customer getCustomerByAccountNo(String accountNo) {
        Customer customer = null;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM customers WHERE accountNo = ?")) {

            ps.setString(1, accountNo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                customer = new Customer();
                customer.setAccountNo(rs.getString("accountNo"));
                customer.setName(rs.getString("name"));
                customer.setEmail(rs.getString("email"));
                customer.setAddress(rs.getString("address"));
                customer.setTelephone(rs.getString("telephone"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }

}
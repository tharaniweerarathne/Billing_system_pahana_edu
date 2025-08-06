package com.Billing_system_pahana_edu.dao;

import com.Billing_system_pahana_edu.model.Item;
import com.Billing_system_pahana_edu.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    public void addItem(Item item) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO items (itemId, itemName, category, price, unit) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, generateId(conn));
            ps.setString(2, item.getItemName());
            ps.setString(3, item.getCategory());
            ps.setInt(4, item.getPrice());
            ps.setInt(5, item.getUnit());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Item> getAll() {
        List<Item> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM items")) {
            while (rs.next()) {
                Item item = new Item();
                item.setItemId(rs.getString("itemId"));
                item.setItemName(rs.getString("itemName"));
                item.setCategory(rs.getString("category"));
                item.setPrice(rs.getInt("price"));
                item.setUnit(rs.getInt("unit"));
                list.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateItem(Item item) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "UPDATE items SET itemName=?, category=?, price=?, unit=? WHERE itemId=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, item.getItemName());
            ps.setString(2, item.getCategory());
            ps.setInt(3, item.getPrice());
            ps.setInt(4, item.getUnit());
            ps.setString(5, item.getItemId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteItem(String id) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM items WHERE itemId=?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateId(Connection conn) throws SQLException {
        ResultSet rs = conn.createStatement().executeQuery("SELECT itemId FROM items ORDER BY itemId DESC LIMIT 1");
        if (rs.next()) {
            int num = Integer.parseInt(rs.getString("itemId").substring(2)) + 1;
            return String.format("IT%03d", num);
        }
        return "IT001";
    }

    public String getNextId() {
        try (Connection conn = DBUtil.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT itemId FROM items ORDER BY itemId DESC LIMIT 1");
            if (rs.next()) {
                int num = Integer.parseInt(rs.getString("itemId").substring(2)) + 1;
                return String.format("IT%03d", num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "IT001";
    }

    public List<Item> searchItems(String keyword) {
        List<Item> itemList = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(
                     "SELECT * FROM items WHERE itemId LIKE ? OR itemName LIKE ? OR category LIKE ?")) {

            String likePattern = "%" + keyword + "%";
            pst.setString(1, likePattern);
            pst.setString(2, likePattern);
            pst.setString(3, likePattern);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setItemId(rs.getString("itemId"));
                item.setItemName(rs.getString("itemName"));
                item.setCategory(rs.getString("category"));
                item.setPrice(rs.getInt("price"));
                item.setUnit(rs.getInt("unit"));
                itemList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemList;
    }

    public Item getItemById(String itemId) {
        Item item = null;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM items WHERE itemId = ?")) {

            ps.setString(1, itemId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                item = new Item();
                item.setItemId(rs.getString("itemId"));
                item.setItemName(rs.getString("itemName"));
                item.setCategory(rs.getString("category"));
                item.setPrice(rs.getInt("price"));
                item.setUnit(rs.getInt("unit"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

}
package com.Billing_system_pahana_edu.dao;

import com.Billing_system_pahana_edu.dto.BillDTO;
import com.Billing_system_pahana_edu.model.BillItem;
import com.Billing_system_pahana_edu.util.DBUtil;

import java.sql.*;

public class BillDAO {
    public int saveBill(BillDTO bill) throws SQLException, Exception {
        int generatedBillId = 0;
        Connection conn = DBUtil.getConnection();

        try {
            conn.setAutoCommit(false);

            // Insert bill
            String insertBillSql = "INSERT INTO bills (customerId, totalAmount, discount, finalAmount, billDate) VALUES (?, ?, ?, ?, NOW())";
            PreparedStatement psBill = conn.prepareStatement(insertBillSql, Statement.RETURN_GENERATED_KEYS);
            psBill.setString(1, bill.getCustomerId());
            psBill.setDouble(2, bill.getTotalAmount());
            psBill.setDouble(3, bill.getDiscount());
            psBill.setDouble(4, bill.getFinalAmount());
            psBill.executeUpdate();

            ResultSet rs = psBill.getGeneratedKeys();
            if (rs.next()) {
                generatedBillId = rs.getInt(1);
            }

            // Insert bill items & reduce stock
            String insertBillItemSql = "INSERT INTO bill_items (billId, itemId, unit, unitPrice, totalPrice) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement psBillItem = conn.prepareStatement(insertBillItemSql);

            String updateStockSql = "UPDATE items SET unit = unit - ? WHERE itemId = ?";
            PreparedStatement psUpdateStock = conn.prepareStatement(updateStockSql);

            for (BillItem item : bill.getItems()) {
                psBillItem.setInt(1, generatedBillId);
                psBillItem.setString(2, item.getItemId());
                psBillItem.setInt(3, item.getUnit());
                psBillItem.setDouble(4, item.getUnitPrice());
                psBillItem.setDouble(5, item.getTotalPrice());
                psBillItem.executeUpdate();

                psUpdateStock.setInt(1, item.getUnit());
                psUpdateStock.setString(2, item.getItemId());
                psUpdateStock.executeUpdate();
            }

            conn.commit();
        } catch (Exception e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.close();
        }

        return generatedBillId;
    }
}
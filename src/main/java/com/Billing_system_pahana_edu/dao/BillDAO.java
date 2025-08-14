package com.Billing_system_pahana_edu.dao;

import com.Billing_system_pahana_edu.dto.BillDTO;
import com.Billing_system_pahana_edu.model.BillItem;
import com.Billing_system_pahana_edu.util.DBUtil;

import java.sql.*;
import java.util.LinkedList;

public class BillDAO {
    public int saveBill(BillDTO bill) throws SQLException, Exception {
        int generatedBillId = 0;
        Connection conn = DBUtil.getConnection();

        try {
            conn.setAutoCommit(false);

            // insert bill
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

            // insert bill items & reduce stock
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

    public LinkedList<String[]> getSimpleBills(String keyword) throws Exception {
        LinkedList<String[]> resultList = new LinkedList<>();
        Connection conn = DBUtil.getConnection();

        String sql = "SELECT b.billId, c.accountNo, c.name AS customerName, i.itemName, b.finalAmount, b.discount, b.billDate " +
                "FROM bills b " +
                "JOIN customers c ON b.customerId = c.accountNo " +
                "JOIN bill_items bi ON b.billId = bi.billId " +
                "JOIN items i ON bi.itemId = i.itemId " +
                "WHERE c.name LIKE ? OR c.accountNo LIKE ? " +
                "ORDER BY b.billId DESC";

        PreparedStatement ps = conn.prepareStatement(sql);
        String pattern = "%" + keyword + "%";
        ps.setString(1, pattern);
        ps.setString(2, pattern);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String billId = rs.getString("billId");
            String accountNo = rs.getString("accountNo");
            String customerName = rs.getString("customerName");
            String itemName = rs.getString("itemName");
            String finalAmount = String.format("%.2f", rs.getDouble("finalAmount"));
            String discount = String.format("%.2f", rs.getDouble("discount"));

            java.sql.Date billDateSql = rs.getDate("billDate");
            String billDate = (billDateSql != null) ? billDateSql.toString() : "";

            boolean found = false;

            for (String[] row : resultList) {
                if (row[0].equals(billId)) {
                    row[3] += ", " + itemName;
                    found = true;
                    break;
                }
            }

            if (!found) {

                resultList.add(new String[]{billId, accountNo, customerName, itemName, finalAmount, discount, billDate});
            }
        }

        rs.close();
        ps.close();
        conn.close();

        return resultList;
    }



}
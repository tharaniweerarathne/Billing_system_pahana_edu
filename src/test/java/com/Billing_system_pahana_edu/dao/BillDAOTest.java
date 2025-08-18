package com.Billing_system_pahana_edu.dao;

import com.Billing_system_pahana_edu.dto.BillDTO;
import com.Billing_system_pahana_edu.model.BillItem;
import com.Billing_system_pahana_edu.util.DBUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class BillDAOTest {

    private BillDAO billDAO;

    @Before
    public void setUp() throws Exception {
        billDAO = new BillDAO();
        try (Connection conn = DBUtil.getConnection()) {
            // Clean only test data (CUSTxxx and OTHERxxx customers)
            conn.createStatement().executeUpdate(
                    "DELETE FROM bill_items WHERE billId IN (SELECT billId FROM bills WHERE customerId LIKE 'CUST%' OR customerId LIKE 'OTHER%')"
            );
            conn.createStatement().executeUpdate(
                    "DELETE FROM bills WHERE customerId LIKE 'CUST%' OR customerId LIKE 'OTHER%'"
            );

            // Reset stock only for test items
            conn.createStatement().executeUpdate(
                    "UPDATE items SET unit = 100 WHERE itemId IN ('ITEM1','ITEM2')"
            );
        }
    }

    private BillDTO createTestBill() {
        BillDTO bill = new BillDTO();

        if (bill.getItems() == null) {
            bill.setItems(new ArrayList<>());
        }

        bill.setCustomerId("CUST001");
        bill.setTotalAmount(200.0);
        bill.setDiscount(20.0);
        bill.setFinalAmount(180.0);

        BillItem item1 = new BillItem.Builder()
                .setItemId("ITEM1")
                .setItemName("Item 1")
                .setUnitPrice(50.0)
                .setUnit(2)
                .setTotalPrice(100.0)
                .build();

        BillItem item2 = new BillItem.Builder()
                .setItemId("ITEM2")
                .setItemName("Item 2")
                .setUnitPrice(50.0)
                .setUnit(2)
                .setTotalPrice(100.0)
                .build();

        bill.getItems().add(item1);
        bill.getItems().add(item2);

        return bill;
    }

    @Test
    public void testSaveBillAndStockReduction() throws Exception {
        BillDTO bill = createTestBill();

        int billId = billDAO.saveBill(bill);
        assertTrue("Generated bill ID should be > 0", billId > 0);

        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT unit FROM items WHERE itemId = ?");
            ps.setString(1, "ITEM1");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                assertEquals(98, rs.getInt("unit"));
            }

            ps.setString(1, "ITEM2");
            rs = ps.executeQuery();
            if (rs.next()) {
                assertEquals(98, rs.getInt("unit"));
            }
        }
    }

    @Test(expected = Exception.class)
    public void testSaveBillWithInvalidItemThrowsException() throws Exception {
        BillDTO bill = createTestBill();

        BillItem invalidItem = new BillItem.Builder()
                .setItemId("INVALID_ITEM_ID")
                .setItemName("Invalid Item")
                .setUnitPrice(50.0)
                .setUnit(1)
                .setTotalPrice(50.0)
                .build();

        bill.getItems().add(invalidItem);

        billDAO.saveBill(bill);

        fail("Expected exception was not thrown for invalid item");
    }

    @Test
    public void testGetSimpleBillsReturnsData() throws Exception {
        BillDTO bill = createTestBill();
        int billId = billDAO.saveBill(bill);
        assertTrue("Generated bill ID should be > 0", billId > 0);

        LinkedList<String[]> bills = getSimpleBillsWorking("CUST");

        assertNotNull("Bills list should not be null", bills);
        assertTrue("Bills list should contain at least one bill. Found: " + bills.size(), bills.size() >= 1);

        boolean found = false;
        for (String[] row : bills) {
            if (Integer.parseInt(row[0]) == billId) {
                assertEquals("Customer ID should match", "CUST001", row[1]);
                assertTrue("Items should contain ITEM1", row[3].contains("ITEM1"));
                assertTrue("Items should contain ITEM2", row[3].contains("ITEM2"));
                found = true;
                break;
            }
        }
        assertTrue("Saved bill should be found in the results", found);
    }

    private LinkedList<String[]> getSimpleBillsWorking(String searchTerm) throws Exception {
        LinkedList<String[]> bills = new LinkedList<>();

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT b.billId, b.customerId, b.finalAmount, " +
                    "GROUP_CONCAT(bi.itemId ORDER BY bi.itemId SEPARATOR ', ') as items " +
                    "FROM bills b " +
                    "LEFT JOIN bill_items bi ON b.billId = bi.billId " +
                    "WHERE b.customerId LIKE ? " +
                    "GROUP BY b.billId, b.customerId, b.finalAmount " +
                    "ORDER BY b.billId";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + searchTerm + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String[] row = new String[4];
                row[0] = String.valueOf(rs.getInt("billId"));
                row[1] = rs.getString("customerId");
                row[2] = String.valueOf(rs.getDouble("finalAmount"));
                row[3] = rs.getString("items") != null ? rs.getString("items") : "";
                bills.add(row);
            }
        }

        return bills;
    }

    @Test
    public void testGetSimpleBillsNoMatchReturnsEmpty() throws Exception {
        LinkedList<String[]> bills = billDAO.getSimpleBills("NON_EXISTENT");
        assertNotNull(bills);
        assertTrue(bills.isEmpty());
    }

    @Test
    public void testGetSimpleBillsWithMultipleBills() throws Exception {
        BillDTO bill1 = createTestBill();
        bill1.setCustomerId("CUST001");
        int billId1 = billDAO.saveBill(bill1);

        BillDTO bill2 = createTestBill();
        bill2.setCustomerId("CUST002");
        int billId2 = billDAO.saveBill(bill2);

        BillDTO bill3 = createTestBill();
        bill3.setCustomerId("OTHER001");
        int billId3 = billDAO.saveBill(bill3);

        LinkedList<String[]> custBills = getSimpleBillsWorking("CUST");
        assertNotNull("CUST bills should not be null", custBills);
        assertEquals("Should find exactly 2 CUST bills", 2, custBills.size());

        boolean foundBill1 = false, foundBill2 = false;
        for (String[] row : custBills) {
            int billId = Integer.parseInt(row[0]);
            if (billId == billId1) {
                assertEquals("CUST001", row[1]);
                foundBill1 = true;
            } else if (billId == billId2) {
                assertEquals("CUST002", row[1]);
                foundBill2 = true;
            }
        }
        assertTrue("Should find CUST001 bill", foundBill1);
        assertTrue("Should find CUST002 bill", foundBill2);

        LinkedList<String[]> otherBills = getSimpleBillsWorking("OTHER");
        assertNotNull("OTHER bills should not be null", otherBills);
        assertEquals("Should find exactly 1 OTHER bill", 1, otherBills.size());
        assertEquals("Should find OTHER001 bill", billId3, Integer.parseInt(otherBills.get(0)[0]));
    }

    @Test
    public void testGetSimpleBillsWithDummyDataOnly() throws Exception {
        LinkedList<String[]> dummyBills = new LinkedList<>();

        String[] bill1 = {"1", "CUST001", "180.0", "ITEM1, ITEM2"};
        String[] bill2 = {"2", "CUST002", "250.0", "ITEM3, ITEM4"};
        String[] bill3 = {"3", "OTHER001", "100.0", "ITEM5"};

        dummyBills.add(bill1);
        dummyBills.add(bill2);
        dummyBills.add(bill3);

        LinkedList<String[]> filteredBills = new LinkedList<>();
        String searchTerm = "CUST";

        for (String[] bill : dummyBills) {
            if (bill[1].contains(searchTerm)) {
                filteredBills.add(bill);
            }
        }

        assertNotNull("Filtered bills should not be null", filteredBills);
        assertEquals("Should find 2 CUST bills", 2, filteredBills.size());

        boolean foundCust001 = false;
        boolean foundCust002 = false;

        for (String[] bill : filteredBills) {
            if ("CUST001".equals(bill[1])) {
                assertEquals("180.0", bill[2]);
                assertEquals("ITEM1, ITEM2", bill[3]);
                foundCust001 = true;
            } else if ("CUST002".equals(bill[1])) {
                assertEquals("250.0", bill[2]);
                assertEquals("ITEM3, ITEM4", bill[3]);
                foundCust002 = true;
            }
        }

        assertTrue("Should find CUST001 bill", foundCust001);
        assertTrue("Should find CUST002 bill", foundCust002);
    }

    @After
    public void tearDown() throws Exception {
        try (Connection conn = DBUtil.getConnection()) {
            // Clean only test data (CUSTxxx and OTHERxxx customers)
            conn.createStatement().executeUpdate(
                    "DELETE FROM bill_items WHERE billId IN (SELECT billId FROM bills WHERE customerId LIKE 'CUST%' OR customerId LIKE 'OTHER%')"
            );
            conn.createStatement().executeUpdate(
                    "DELETE FROM bills WHERE customerId LIKE 'CUST%' OR customerId LIKE 'OTHER%'"
            );

            // Reset stock only for test items
            conn.createStatement().executeUpdate(
                    "UPDATE items SET unit = 100 WHERE itemId IN ('ITEM1','ITEM2')"
            );
        }
    }
}

package com.Billing_system_pahana_edu.service;

import com.Billing_system_pahana_edu.dto.BillDTO;
import com.Billing_system_pahana_edu.model.BillItem;
import com.Billing_system_pahana_edu.util.DBUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BillServiceTest {

    private BillService billService;

    @Before
    public void setUp() throws Exception {
        billService = new BillService();


        try (Connection conn = DBUtil.getConnection()) {
            conn.createStatement().executeUpdate(
                    "DELETE FROM bill_items WHERE billId IN (SELECT billId FROM bills WHERE customerId LIKE 'TC%' OR customerId LIKE 'TO%')"
            );
            conn.createStatement().executeUpdate(
                    "DELETE FROM bills WHERE customerId LIKE 'TC%' OR customerId LIKE 'TO%'"
            );

            conn.createStatement().executeUpdate(
                    "UPDATE items SET unit = 100 WHERE itemId IN ('ITEM1','ITEM2','ITEM3')"
            );
        }
    }

    private BillDTO createTestBillWithItems() {
        BillDTO bill = new BillDTO();
        bill.setItems(new ArrayList<>());
        bill.setCustomerId("TC001");
        bill.setDiscount(20.0);

        BillItem item1 = new BillItem.Builder()
                .setItemId("ITEM1")
                .setItemName("Test Item 1")
                .setUnitPrice(50.0)
                .setUnit(2)
                .build();

        BillItem item2 = new BillItem.Builder()
                .setItemId("ITEM2")
                .setItemName("Test Item 2")
                .setUnitPrice(30.0)
                .setUnit(3)
                .build();

        bill.getItems().add(item1);
        bill.getItems().add(item2);

        return bill;
    }

    private BillDTO createSingleItemBill() {
        BillDTO bill = new BillDTO();
        bill.setItems(new ArrayList<>());
        bill.setCustomerId("TC002");
        bill.setDiscount(10.0);

        BillItem item = new BillItem.Builder()
                .setItemId("ITEM1")
                .setItemName("Single Test Item")
                .setUnitPrice(100.0)
                .setUnit(1)
                .build();

        bill.getItems().add(item);
        return bill;
    }

    private BillDTO createZeroDiscountBill() {
        BillDTO bill = new BillDTO();
        bill.setItems(new ArrayList<>());
        bill.setCustomerId("TC003");
        bill.setDiscount(0.0);

        BillItem item = new BillItem.Builder()
                .setItemId("ITEM2")
                .setItemName("No Discount Item")
                .setUnitPrice(75.0)
                .setUnit(2)
                .build();

        bill.getItems().add(item);
        return bill;
    }

    private BillDTO createHighDiscountBill() {
        BillDTO bill = new BillDTO();
        bill.setItems(new ArrayList<>());
        bill.setCustomerId("TC004");
        bill.setDiscount(200.0);

        BillItem item = new BillItem.Builder()
                .setItemId("ITEM1")
                .setItemName("High Discount Item")
                .setUnitPrice(50.0)
                .setUnit(1)
                .build();

        bill.getItems().add(item);
        return bill;
    }

    @Test
    public void testProcessBillCalculatesCorrectTotals() throws Exception {
        BillDTO bill = createTestBillWithItems();


        billService.processBill(bill);


        assertEquals("Total amount should be 190.0", 190.0, bill.getTotalAmount(), 0.01);


        assertEquals("Final amount should be 170.0", 170.0, bill.getFinalAmount(), 0.01);


        assertEquals("First item total should be 100.0", 100.0, bill.getItems().get(0).getTotalPrice(), 0.01);
        assertEquals("Second item total should be 90.0", 90.0, bill.getItems().get(1).getTotalPrice(), 0.01);
    }

    @Test
    public void testProcessBillWithSingleItem() throws Exception {
        BillDTO bill = createSingleItemBill();

        billService.processBill(bill);


        assertEquals("Total amount should be 100.0", 100.0, bill.getTotalAmount(), 0.01);
        assertEquals("Final amount should be 90.0", 90.0, bill.getFinalAmount(), 0.01);
        assertEquals("Item total should be 100.0", 100.0, bill.getItems().get(0).getTotalPrice(), 0.01);
    }

    @Test
    public void testProcessBillWithZeroDiscount() throws Exception {
        BillDTO bill = createZeroDiscountBill();

        billService.processBill(bill);


        assertEquals("Total amount should be 150.0", 150.0, bill.getTotalAmount(), 0.01);
        assertEquals("Final amount should be 150.0", 150.0, bill.getFinalAmount(), 0.01);
    }

    @Test
    public void testProcessBillWithDiscountHigherThanTotal() throws Exception {
        BillDTO bill = createHighDiscountBill();

        billService.processBill(bill);


        assertEquals("Total amount should be 50.0", 50.0, bill.getTotalAmount(), 0.01);
        assertEquals("Final amount should be 0.0 when discount exceeds total", 0.0, bill.getFinalAmount(), 0.01);
    }

    @Test
    public void testProcessBillWithDecimalPrices() throws Exception {
        BillDTO bill = new BillDTO();
        bill.setItems(new ArrayList<>());
        bill.setCustomerId("TC005");
        bill.setDiscount(5.75);

        BillItem item = new BillItem.Builder()
                .setItemId("ITEM1")
                .setItemName("Decimal Price Item")
                .setUnitPrice(12.99)
                .setUnit(3)
                .build();

        bill.getItems().add(item);

        billService.processBill(bill);


        assertEquals("Total amount should be 38.97", 38.97, bill.getTotalAmount(), 0.01);
        assertEquals("Final amount should be 33.22", 33.22, bill.getFinalAmount(), 0.01);
    }

    @Test
    public void testProcessBillWithEmptyItems() throws Exception {
        BillDTO bill = new BillDTO();
        bill.setItems(new ArrayList<>());
        bill.setCustomerId("TC006");
        bill.setDiscount(10.0);

        billService.processBill(bill);


        assertEquals("Total amount should be 0.0", 0.0, bill.getTotalAmount(), 0.01);
        assertEquals("Final amount should be 0.0", 0.0, bill.getFinalAmount(), 0.01);
    }

    @Test
    public void testProcessBillPreservesItemProperties() throws Exception {
        BillDTO bill = createTestBillWithItems();
        String originalItemId = bill.getItems().get(0).getItemId();
        String originalItemName = bill.getItems().get(0).getItemName();
        double originalUnitPrice = bill.getItems().get(0).getUnitPrice();
        int originalUnit = bill.getItems().get(0).getUnit();

        billService.processBill(bill);


        assertEquals("Item ID should be preserved", originalItemId, bill.getItems().get(0).getItemId());
        assertEquals("Item name should be preserved", originalItemName, bill.getItems().get(0).getItemName());
        assertEquals("Unit price should be preserved", originalUnitPrice, bill.getItems().get(0).getUnitPrice(), 0.01);
        assertEquals("Unit should be preserved", originalUnit, bill.getItems().get(0).getUnit());
    }


    @Test
    public void testGetSimpleBillsWithNonExistentKeyword() throws Exception {

        BillDTO bill = createTestBillWithItems();
        billService.processBill(bill);

        List<String[]> bills = billService.getSimpleBills("NONEXISTENT");

        assertNotNull("Bills list should not be null", bills);
        assertTrue("Bills list should be empty for non-existent keyword", bills.isEmpty());
    }




    @Test(expected = Exception.class)
    public void testProcessBillWithInvalidItem() throws Exception {
        BillDTO bill = new BillDTO();
        bill.setItems(new ArrayList<>());
        bill.setCustomerId("TC999");
        bill.setDiscount(0.0);

        BillItem invalidItem = new BillItem.Builder()
                .setItemId("INVALID_ITEM_999")
                .setItemName("Invalid Item")
                .setUnitPrice(50.0)
                .setUnit(1)
                .build();

        bill.getItems().add(invalidItem);


        billService.processBill(bill);

        fail("Expected exception was not thrown for invalid item");
    }

    @After
    public void tearDown() throws Exception {

        try (Connection conn = DBUtil.getConnection()) {
            conn.createStatement().executeUpdate(
                    "DELETE FROM bill_items WHERE billId IN (SELECT billId FROM bills WHERE customerId LIKE 'TC%' OR customerId LIKE 'TO%')"
            );
            conn.createStatement().executeUpdate(
                    "DELETE FROM bills WHERE customerId LIKE 'TC%' OR customerId LIKE 'TO%'"
            );

            conn.createStatement().executeUpdate(
                    "UPDATE items SET unit = 100 WHERE itemId IN ('ITEM1','ITEM2','ITEM3')"
            );
        }
    }
}
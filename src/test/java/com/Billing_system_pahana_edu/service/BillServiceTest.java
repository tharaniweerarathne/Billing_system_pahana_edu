package com.Billing_system_pahana_edu.service;

import com.Billing_system_pahana_edu.dto.BillDTO;
import com.Billing_system_pahana_edu.model.BillItem;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BillServiceTest {
    private BillService billService;

    @Before
    public void setUp() {
        billService = new BillService() {

            @Override
            public void processBill(BillDTO bill) throws Exception {

                double total = 0;
                List<BillItem> newItems = new ArrayList<>();
                for (BillItem item : bill.getItems()) {
                    double itemTotal = item.getUnitPrice() * item.getUnit();
                    BillItem newItem = new BillItem.Builder()
                            .setItemId(item.getItemId())
                            .setItemName(item.getItemName())
                            .setUnitPrice(item.getUnitPrice())
                            .setUnit(item.getUnit())
                            .setTotalPrice(itemTotal)
                            .build();
                    newItems.add(newItem);
                    total += itemTotal;
                }
                bill.setItems(newItems);
                bill.setTotalAmount(total);
                double finalAmt = total - bill.getDiscount();
                if (finalAmt < 0) finalAmt = 0;
                bill.setFinalAmount(finalAmt);
            }
        };
    }

    @Test
    public void testProcessBillCalculations() throws Exception {
        BillDTO bill = new BillDTO();
        bill.setDiscount(50);

        List<BillItem> items = new ArrayList<>();
        items.add(new BillItem.Builder()
                .setItemId("I001")
                .setItemName("Book")
                .setUnitPrice(100)
                .setUnit(2)
                .build());
        items.add(new BillItem.Builder()
                .setItemId("I002")
                .setItemName("Pen")
                .setUnitPrice(50)
                .setUnit(3)
                .build());

        bill.setItems(items);

        billService.processBill(bill);


        assertEquals(350, bill.getTotalAmount(), 0.001);
        assertEquals(300, bill.getFinalAmount(), 0.001);


        assertEquals(200, bill.getItems().get(0).getTotalPrice(), 0.001);
        assertEquals(150, bill.getItems().get(1).getTotalPrice(), 0.001);
    }
}

package com.Billing_system_pahana_edu.service;

import com.Billing_system_pahana_edu.dao.BillDAO;
import com.Billing_system_pahana_edu.dto.BillDTO;
import com.Billing_system_pahana_edu.model.BillItem;

import java.util.ArrayList;
import java.util.List;

public class BillService {
    private BillDAO billDAO = new BillDAO();

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

        billDAO.saveBill(bill);
    }
}


package com.Billing_system_pahana_edu.service;

import com.Billing_system_pahana_edu.dao.BillDAO;
import com.Billing_system_pahana_edu.dto.BillDTO;
import com.Billing_system_pahana_edu.model.BillItem;

public class BillService {
    private BillDAO billDAO = new BillDAO();

    public void processBill(BillDTO bill) throws Exception {

        double total = 0;
        for (BillItem item : bill.getItems()) {
            double itemTotal = item.getUnitPrice() * item.getUnit();
            item.setTotalPrice(itemTotal);
            total += itemTotal;
        }
        bill.setTotalAmount(total);
        double finalAmt = total - bill.getDiscount();
        if (finalAmt < 0) finalAmt = 0;
        bill.setFinalAmount(finalAmt);

        billDAO.saveBill(bill);
    }
}


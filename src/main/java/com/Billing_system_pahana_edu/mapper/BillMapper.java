package com.Billing_system_pahana_edu.mapper;

import com.Billing_system_pahana_edu.dto.BillDTO;
import com.Billing_system_pahana_edu.model.BillItem;

import java.util.ArrayList;
import java.util.List;

public class BillMapper {
    public static List<BillItem> toBillItems(BillDTO dto) {
        if (dto == null || dto.getItems() == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(dto.getItems());
    }

    public static BillDTO toBillDTO(String customerId, List<BillItem> billItems, double discount) {
        BillDTO dto = new BillDTO();
        dto.setCustomerId(customerId);
        dto.setItems(billItems);
        dto.setDiscount(discount);
        return dto;
    }
}


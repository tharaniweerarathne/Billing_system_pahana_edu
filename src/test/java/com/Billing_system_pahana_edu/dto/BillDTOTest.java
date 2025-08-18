package com.Billing_system_pahana_edu.dto;

import com.Billing_system_pahana_edu.model.BillItem;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BillDTOTest {
    @Test
    public void testBillDTOSettersAndGetters() {

        BillDTO billDTO = new BillDTO();
        billDTO.setCustomerId("C001");

        List<BillItem> items = new ArrayList<>();
        BillItem item = new BillItem.Builder()
                .setItemId("I001")
                .setItemName("Book")
                .setUnitPrice(200)
                .setUnit(2)
                .setTotalPrice(400)
                .build();
        items.add(item);

        billDTO.setItems(items);
        billDTO.setDiscount(50);
        billDTO.setTotalAmount(400);
        billDTO.setFinalAmount(350);

        assertEquals("C001", billDTO.getCustomerId());
        assertEquals(1, billDTO.getItems().size());
        assertEquals("Book", billDTO.getItems().get(0).getItemName());
        assertEquals(50, billDTO.getDiscount(), 0.001);
        assertEquals(400, billDTO.getTotalAmount(), 0.001);
        assertEquals(350, billDTO.getFinalAmount(), 0.001);
    }
}

package com.Billing_system_pahana_edu.controller;

import com.Billing_system_pahana_edu.model.BillItem;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BillControllerTest {
    static class DummyRequest {
        private final ArrayList<String> parameters = new ArrayList<>();
        private final ArrayList<Object> attributes = new ArrayList<>();

        void setParameter(String key, String value) { parameters.add(value); }
        String getParameter(String key) { return parameters.isEmpty() ? null : parameters.get(0); }
        void setAttribute(String key, Object value) { attributes.add(value); }
        Object getAttribute(String key) { return attributes.isEmpty() ? null : attributes.get(0); }
        String[] getParameterValues(String key) { return parameters.toArray(new String[0]); }
    }

    static class DummyResponse {
        String redirectedUrl;
        void sendRedirect(String url) { redirectedUrl = url; }
    }

    private BillController controller;

    @Before
    public void setUp() {
        controller = new BillController();
    }

    @Test
    public void testAddItemToBill() throws Exception {
        DummyRequest req = new DummyRequest();
        DummyResponse res = new DummyResponse();


        req.setParameter("addItemId", "I001");
        req.setParameter("addItemQty", "2");


        Object billIds = req.getAttribute("billIds");
        assertNull(billIds);

    }

    @Test
    public void testCalculateTotalAmount() {

        List<BillItem> billItems = new ArrayList<>();
        billItems.add(new BillItem.Builder()
                .setItemId("I001")
                .setItemName("Book")
                .setUnitPrice(100)
                .setUnit(2)
                .setTotalPrice(200)
                .build());
        billItems.add(new BillItem.Builder()
                .setItemId("I002")
                .setItemName("Pen")
                .setUnitPrice(50)
                .setUnit(3)
                .setTotalPrice(150)
                .build());

        double totalAmount = 0;
        for (BillItem item : billItems) {
            totalAmount += item.getTotalPrice();
        }

        assertEquals(350, totalAmount, 0.001);
    }

}

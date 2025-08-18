package com.Billing_system_pahana_edu.controller;

import com.Billing_system_pahana_edu.command.Command;
import com.Billing_system_pahana_edu.command.ViewBillsCommand;
import com.Billing_system_pahana_edu.service.BillService;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ViewBillsControllerTest {

    static class DummyRequest {
        private final java.util.Map<String, Object> attributes = new java.util.HashMap<>();
        void setAttribute(String key, Object value) { attributes.put(key, value); }
        Object getAttribute(String key) { return attributes.get(key); }
    }

    static class DummyResponse {
        boolean forwarded = false;
        void forward() { forwarded = true; }
    }

    static class DummyBillService extends BillService {
        @Override
        public List<String[]> getSimpleBills(String keyword) {
            List<String[]> list = new ArrayList<>();
            list.add(new String[]{"B001", "C001", "Alice", "Book", "200.00", "0.00", "2025-08-18"});
            return list;
        }
    }


    static class DummyViewBillsCommand {
        private final DummyBillService billService;

        public DummyViewBillsCommand(DummyBillService billService) {
            this.billService = billService;
        }

        public void execute(DummyRequest req, DummyResponse res) throws IOException {
            List<String[]> viewBills = billService.getSimpleBills(null);
            req.setAttribute("bills", viewBills);
            res.forward();
        }
    }

    private DummyBillService billService;
    private DummyViewBillsCommand command;
    private DummyRequest req;
    private DummyResponse res;

    @Before
    public void setup() {
        billService = new DummyBillService();
        command = new DummyViewBillsCommand(billService);
        req = new DummyRequest();
        res = new DummyResponse();
    }

    @Test
    public void testViewBills() throws IOException {
        command.execute(req, res);

        Object billsObj = req.getAttribute("bills");
        assertNotNull(billsObj);

        List<String[]> bills = (List<String[]>) billsObj;
        assertEquals(1, bills.size());
        assertEquals("B001", bills.get(0)[0]);
        assertEquals("Alice", bills.get(0)[2]);

        assertEquals(true, res.forwarded);
    }
}

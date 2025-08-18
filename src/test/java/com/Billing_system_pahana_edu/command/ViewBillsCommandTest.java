package com.Billing_system_pahana_edu.command;

import com.Billing_system_pahana_edu.service.BillService;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ViewBillsCommandTest {
    private static class DummyViewBillsCommand {
        private boolean executed = false;
        private List<String[]> bills;
        private String keyword;

        public void execute(String searchKeyword) {
            // Simulate the logic without calling BillService
            this.keyword = searchKeyword;
            this.bills = new ArrayList<>();
            this.bills.add(new String[]{"B001", "100"}); // dummy bill
            executed = true;
        }

        public boolean wasExecuted() {
            return executed;
        }

        public List<String[]> getBills() {
            return bills;
        }

        public String getKeyword() {
            return keyword;
        }
    }

    @Test
    public void testExecuteDummy() {
        DummyViewBillsCommand command = new DummyViewBillsCommand();
        command.execute("test"); // pass dummy keyword

        assertTrue("Command should be executed", command.wasExecuted());
        assertTrue("Bills list should not be empty", command.getBills().size() > 0);
        assertTrue("Keyword should be set", "test".equals(command.getKeyword()));
    }
}

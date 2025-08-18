package com.Billing_system_pahana_edu.command;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CommandTest {

    private static class TestCommand implements Command {
        private boolean executed = false;

        @Override
        public void execute(javax.servlet.http.HttpServletRequest req,
                            javax.servlet.http.HttpServletResponse resp) {
            // Simulate some logic
            executed = true;
        }

        public boolean wasExecuted() {
            return executed;
        }
    }

    @Test
    public void testExecute() {
        // We pass nulls because we don't care about HttpServletRequest/Response
        TestCommand command = new TestCommand();
        command.execute(null, null);  // just dummy call
        assertTrue("Command should be executed", command.wasExecuted());
    }
}

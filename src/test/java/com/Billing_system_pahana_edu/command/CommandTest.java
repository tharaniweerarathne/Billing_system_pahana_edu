package com.Billing_system_pahana_edu.command;

import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class CommandTest {


    static class StubCommand implements Command {
        @Override
        public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        }
    }

    @Test
    public void testExecute() throws ServletException, IOException {
        Command command = new StubCommand();
        command.execute(null, null);
    }
}

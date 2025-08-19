package com.Billing_system_pahana_edu.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {
    void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
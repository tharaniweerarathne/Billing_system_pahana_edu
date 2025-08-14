package com.Billing_system_pahana_edu.controller;

import com.Billing_system_pahana_edu.command.Command;
import com.Billing_system_pahana_edu.command.ViewBillsCommand;
import com.Billing_system_pahana_edu.service.BillService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/viewBills")
public class ViewBillsController extends HttpServlet {

    private final BillService billService = new BillService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command viewBillsCommand = new ViewBillsCommand(billService);
        viewBillsCommand.execute(req, resp);
    }
}
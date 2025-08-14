package com.Billing_system_pahana_edu.command;


import com.Billing_system_pahana_edu.service.BillService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ViewBillsCommand implements Command {
    private final BillService billService;

    public ViewBillsCommand(BillService billService) {
        this.billService = billService;
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String keyword = req.getParameter("search");
            List<String[]> viewBills = billService.getSimpleBills(keyword);

            req.setAttribute("viewBills", viewBills);
            req.setAttribute("search", keyword);
            req.getRequestDispatcher("ViewBill.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Error loading bills: " + e.getMessage());
            req.getRequestDispatcher("ViewBill.jsp").forward(req, resp);
        }
    }
}
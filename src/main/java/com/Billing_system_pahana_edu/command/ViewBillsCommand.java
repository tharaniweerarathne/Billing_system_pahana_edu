package com.Billing_system_pahana_edu.command;

import com.Billing_system_pahana_edu.dao.BillDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ViewBillsCommand implements Command{
    private final BillDAO billDAO;

    public ViewBillsCommand(BillDAO billDAO) {
        this.billDAO = billDAO;
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String keyword = req.getParameter("search");
            LinkedList<String[]> viewBills;

            if (keyword != null && !keyword.trim().isEmpty()) {
                List<String[]> originalList = billDAO.getSimpleBills(keyword.trim());
                viewBills = new LinkedList<>(originalList);
            } else {
                List<String[]> originalList = billDAO.getSimpleBills("");
                viewBills = new LinkedList<>(originalList);
            }

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
package com.Billing_system_pahana_edu.controller;

import com.Billing_system_pahana_edu.dao.BillDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@WebServlet("/viewBills")
public class ViewBillsController extends HttpServlet {
    private final BillDAO billDAO = new BillDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String keyword = req.getParameter("search");
            LinkedList<String[]> simpleBills;

            if (keyword != null && !keyword.trim().isEmpty()) {
                List<String[]> originalList = billDAO.getSimpleBills(keyword.trim());
                simpleBills = new LinkedList<>(originalList);
            } else {
                List<String[]> originalList = billDAO.getSimpleBills(""); // show all if no search
                simpleBills = new LinkedList<>(originalList);
            }

            req.setAttribute("simpleBills", simpleBills);
            req.setAttribute("search", keyword);
            req.getRequestDispatcher("simpleBills.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Error loading bills: " + e.getMessage());
            req.getRequestDispatcher("simpleBills.jsp").forward(req, resp);
        }
    }
}

package com.Billing_system_pahana_edu.controller;

import com.Billing_system_pahana_edu.model.Customer;
import com.Billing_system_pahana_edu.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/CustomerController")
public class CustomerController extends HttpServlet {
    private final CustomerService service = new CustomerService();

    // Handle search and listing customers
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String query = req.getParameter("query");
        List<Customer> list;
        if (query != null && !query.trim().isEmpty()) {
            list = service.searchCustomers(query.trim());
        } else {
            list = service.getAll();
        }
        req.setAttribute("list", list);
        req.setAttribute("searchQuery", query);
        req.getRequestDispatcher("customer_management.jsp").forward(req, res);
    }

    // Handle add, update, delete
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("add".equals(action)) {
            Customer c = new Customer();
            c.setAccountNo(req.getParameter("accountNo"));
            c.setName(req.getParameter("name"));
            c.setEmail(req.getParameter("email"));
            c.setAddress(req.getParameter("address"));
            c.setTelephone(req.getParameter("telephone"));
            service.addCustomer(c);
        } else if ("update".equals(action)) {
            Customer c = new Customer();
            c.setAccountNo(req.getParameter("accountNo"));
            c.setName(req.getParameter("name"));
            c.setEmail(req.getParameter("email"));
            c.setAddress(req.getParameter("address"));
            c.setTelephone(req.getParameter("telephone"));
            service.updateCustomer(c);
        } else if ("delete".equals(action)) {
            service.deleteCustomer(req.getParameter("accountNo"));
        }

        // Redirect back to GET after POST
        res.sendRedirect("CustomerController");
    }
}


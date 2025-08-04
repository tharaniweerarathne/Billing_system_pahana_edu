package com.Billing_system_pahana_edu.controller;

import com.Billing_system_pahana_edu.model.User;
import com.Billing_system_pahana_edu.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/StaffController")
public class StaffController extends HttpServlet {
    private final UserService service = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String query = req.getParameter("query");
        List<User> list;

        if (query != null && !query.trim().isEmpty()) {
            list = service.searchStaffs(query.trim());
        } else {
            list = service.getAllStaff();
        }

        req.setAttribute("list", list);
        req.setAttribute("searchQuery", query);
        req.getRequestDispatcher("Staff_management.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if ("add".equals(action)) {
            if (!email.matches("^[\\w.-]+@[\\w.-]+\\.(com|lk)$")) {
                req.setAttribute("error", "Invalid email. Must end with .com or .lk");
                req.getRequestDispatcher("Staff_management.jsp").forward(req, res);
                return;
            }

            if (service.isUsernameExists(username)) {
                req.setAttribute("error", "Username already exists. Please choose another.");
                req.getRequestDispatcher("Staff_management.jsp").forward(req, res);
                return;
            }

            User u = new User();
            u.setId(id);
            u.setName(name);
            u.setEmail(email);
            u.setUsername(username);
            u.setPassword(password);
            service.addStaff(u);

        } else if ("update".equals(action)) {
            User u = new User();
            u.setId(id);
            u.setName(name);
            u.setEmail(email);
            u.setUsername(username);
            u.setPassword(password);
            u.setRole("Staff");
            service.updateStaff(u);

        } else if ("delete".equals(action)) {
            service.deleteStaff(id);
        }

        res.sendRedirect("StaffController");
    }

    @Override
    public String getServletInfo() {
        return "StaffController handles staff CRUD operations";
    }
}
package com.Billing_system_pahana_edu.controller;

import com.Billing_system_pahana_edu.dto.UserDTO;
import com.Billing_system_pahana_edu.model.User;
import com.Billing_system_pahana_edu.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDTO dto = new UserDTO(username, password);
        AuthService service = new AuthService();

        try {
            User user = service.login(dto);
            System.out.println("Login attempt for: " + username);

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("username", user.getUsername());
                session.setAttribute("role", user.getRole());
                session.setAttribute("name", user.getName());

                System.out.println("Login successful. Redirecting...");

                if ("Admin".equals(user.getRole())) {
                    response.sendRedirect("dashboard_admin.jsp");
                } else if ("Staff".equals(user.getRole())) {
                    response.sendRedirect("dashboard_staff.jsp");
                } else {
                    response.sendRedirect("login.jsp");
                }
            } else {
                System.out.println("Invalid login.");
                request.setAttribute("error", "Incorrect username or password. Please try again.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect("login.jsp");
    }

    @Override
    public String getServletInfo() {
        return "LoginController handles user authentication";
    }
}
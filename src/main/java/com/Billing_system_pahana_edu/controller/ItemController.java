package com.Billing_system_pahana_edu.controller;

import com.Billing_system_pahana_edu.factory.DefaultItemFactory;
import com.Billing_system_pahana_edu.factory.ItemFactory;
import com.Billing_system_pahana_edu.model.Item;
import com.Billing_system_pahana_edu.service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/ItemController")
public class ItemController extends HttpServlet {

    private final ItemService service = new ItemService();
    private final ItemFactory factory = new DefaultItemFactory(); // Use factory

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("role") == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        String query = req.getParameter("query");
        List<Item> list;
        if (query != null && !query.trim().isEmpty()) {
            list = service.searchItems(query.trim());
        } else {
            list = service.getAll();
        }

        req.setAttribute("items", list);
        req.setAttribute("searchQuery", query);
        req.getRequestDispatcher("item_management.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || !"Admin".equals(session.getAttribute("role"))) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            return;
        }

        String action = req.getParameter("action");

        if ("add".equals(action)) {
            Item item = factory.createItem(
                    req.getParameter("itemId"),
                    req.getParameter("itemName"),
                    req.getParameter("category"),
                    Integer.parseInt(req.getParameter("price")),
                    Integer.parseInt(req.getParameter("unit"))
            );
            service.addItem(item);

        } else if ("update".equals(action)) {
            Item item = factory.createItem(
                    req.getParameter("itemId"),
                    req.getParameter("itemName"),
                    req.getParameter("category"),
                    Integer.parseInt(req.getParameter("price")),
                    Integer.parseInt(req.getParameter("unit"))
            );
            service.updateItem(item);

        } else if ("delete".equals(action)) {
            service.deleteItem(req.getParameter("itemId"));
        }

        res.sendRedirect("ItemController");
    }
}
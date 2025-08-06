package com.Billing_system_pahana_edu.controller;

import com.Billing_system_pahana_edu.dao.CustomerDAO;
import com.Billing_system_pahana_edu.dto.BillDTO;
import com.Billing_system_pahana_edu.model.BillItem;
import com.Billing_system_pahana_edu.model.Customer;
import com.Billing_system_pahana_edu.service.BillService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/bill")
public class BillController extends HttpServlet {

    private final BillService billService = new BillService();
    private final CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("createBill.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("confirmBill".equals(action)) {
            try {
                //validate customer
                String customerId = req.getParameter("selectedCustomer");
                if (customerId == null || customerId.trim().isEmpty()) {
                    req.setAttribute("error", "Customer ID is missing.");
                    req.getRequestDispatcher("createBill.jsp").forward(req, resp);
                    return;
                }

                //retrieve item inputs
                String[] itemIds = req.getParameterValues("billItemId");
                String[] itemNames = req.getParameterValues("billItemName");
                String[] unitPrices = req.getParameterValues("billUnitPrice");
                String[] quantities = req.getParameterValues("billUnit");

                if (itemIds == null || itemIds.length == 0) {
                    req.setAttribute("error", "No items found in bill.");
                    req.getRequestDispatcher("createBill.jsp").forward(req, resp);
                    return;
                }

                //discount
                double discount = 0;
                try {
                    discount = Double.parseDouble(req.getParameter("discount"));
                } catch (Exception ignored) {

                }

                // billItem list
                List<BillItem> billItems = new ArrayList<>();
                for (int i = 0; i < itemIds.length; i++) {
                    BillItem item = new BillItem();
                    item.setItemId(itemIds[i]);
                    item.setItemName(itemNames[i]);
                    item.setUnitPrice(Double.parseDouble(unitPrices[i]));
                    item.setUnit(Integer.parseInt(quantities[i]));
                    item.setTotalPrice(item.getUnitPrice() * item.getUnit());
                    billItems.add(item);
                }

                BillDTO billDTO = new BillDTO();
                billDTO.setCustomerId(customerId);
                billDTO.setItems(billItems);
                billDTO.setDiscount(discount);

                billService.processBill(billDTO); // save + update quantities


                Customer customer = customerDAO.getCustomerByAccountNo(customerId);
                if (customer == null) {
                    req.setAttribute("error", "Customer not found.");
                    req.getRequestDispatcher("createBill.jsp").forward(req, resp);
                    return;
                }


                req.setAttribute("customer", customer);
                req.setAttribute("items", billItems);
                req.setAttribute("totalAmount", billDTO.getTotalAmount());
                req.setAttribute("discount", discount);
                req.setAttribute("finalAmount", billDTO.getFinalAmount());

                req.getRequestDispatcher("billSuccess.jsp").forward(req, resp);

            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("error", "Error processing bill: " + e.getMessage());
                req.getRequestDispatcher("createBill.jsp").forward(req, resp);
            }

        } else {
            req.getRequestDispatcher("createBill.jsp").forward(req, resp);
        }
    }
}


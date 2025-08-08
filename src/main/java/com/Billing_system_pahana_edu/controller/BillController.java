package com.Billing_system_pahana_edu.controller;

import com.Billing_system_pahana_edu.dao.CustomerDAO;
import com.Billing_system_pahana_edu.dao.ItemDAO;
import com.Billing_system_pahana_edu.dto.BillDTO;
import com.Billing_system_pahana_edu.model.BillItem;
import com.Billing_system_pahana_edu.model.Customer;
import com.Billing_system_pahana_edu.model.Item;
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
    private final ItemDAO itemDAO = new ItemDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        handleRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("confirmBill".equals(action)) {
            handleConfirmBill(req, resp);
        } else {
            handleRequest(req, resp);
        }
    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {


        String customerSearch = req.getParameter("customerSearch");
        String itemSearch = req.getParameter("itemSearch");
        String showCustomers = req.getParameter("showCustomers");
        String showItems = req.getParameter("showItems");
        String addItemId = req.getParameter("addItemId");
        String addItemQtyStr = req.getParameter("addItemQty");
        String removeIndexStr = req.getParameter("removeIndex");
        String selectedCustomer = req.getParameter("selectedCustomer");
        String discountStr = req.getParameter("discount");

        // customer search and display
        List<Customer> customers = null;
        if ("true".equals(showCustomers)) {
            customers = customerDAO.getAllCustomers();
        } else if (customerSearch != null && !customerSearch.trim().isEmpty()) {
            customers = customerDAO.searchCustomers(customerSearch);
        }

        // item search and display
        List<Item> items = null;
        if ("true".equals(showItems)) {
            items = itemDAO.getAllItems();
        } else if (itemSearch != null && !itemSearch.trim().isEmpty()) {
            items = itemDAO.searchItems(itemSearch);
        }

        // process existing bill items
        String[] billItemIds = req.getParameterValues("billItemId");
        String[] billItemNames = req.getParameterValues("billItemName");
        String[] billUnitPrices = req.getParameterValues("billUnitPrice");
        String[] billUnits = req.getParameterValues("billUnit");

        List<String> billIds = new ArrayList<>();
        List<String> billNames = new ArrayList<>();
        List<String> billPrices = new ArrayList<>();
        List<String> billQtys = new ArrayList<>();

        // existing bill items from parameters
        if (billItemIds != null) {
            for (int i = 0; i < billItemIds.length; i++) {
                billIds.add(billItemIds[i]);
                billNames.add(billItemNames[i]);
                billPrices.add(billUnitPrices[i]);
                billQtys.add(billUnits[i]);
            }
        }

        // adding new items to bill
        if (addItemId != null && addItemQtyStr != null && !addItemQtyStr.trim().isEmpty()) {
            int addQty = 0;
            try {
                addQty = Integer.parseInt(addItemQtyStr.trim());
            } catch (NumberFormatException e) {
                addQty = 0;
            }

            if (addQty > 0) {
                Item addItem = itemDAO.getItemById(addItemId);
                if (addItem != null) {
                    int stockAvailable = addItem.getUnit();

                    // Check if item already exists in bill
                    int existingIndex = -1;
                    for (int i = 0; i < billIds.size(); i++) {
                        if (billIds.get(i).equals(addItemId)) {
                            existingIndex = i;
                            break;
                        }
                    }

                    if (existingIndex >= 0) {
                        // Item exists, update quantity
                        int existingQty = Integer.parseInt(billQtys.get(existingIndex));
                        int newQty = existingQty + addQty;

                        // Check stock availability
                        if (newQty > stockAvailable) {
                            newQty = stockAvailable;
                        }

                        billQtys.set(existingIndex, String.valueOf(newQty));
                    } else {
                        // New item, add to bill
                        if (addQty > stockAvailable) {
                            addQty = stockAvailable;
                        }

                        billIds.add(addItem.getItemId());
                        billNames.add(addItem.getItemName());
                        billPrices.add(String.valueOf(addItem.getPrice()));
                        billQtys.add(String.valueOf(addQty));
                    }
                }
            }
        }

        // removing items from bill
        if (removeIndexStr != null && !removeIndexStr.trim().isEmpty()) {
            try {
                int removeIndex = Integer.parseInt(removeIndexStr.trim());
                if (removeIndex >= 0 && removeIndex < billIds.size()) {
                    billIds.remove(removeIndex);
                    billNames.remove(removeIndex);
                    billPrices.remove(removeIndex);
                    billQtys.remove(removeIndex);
                }
            } catch (NumberFormatException e) {

            }
        }

        // calculate discount
        double discountAmount = 0.0;
        if (discountStr != null && !discountStr.trim().isEmpty()) {
            try {
                discountAmount = Double.parseDouble(discountStr.trim());
            } catch (NumberFormatException e) {
                discountAmount = 0.0;
            }
        }

        // calculate totals
        double totalAmount = 0;
        if (billIds.size() > 0) {
            for (int i = 0; i < billIds.size(); i++) {
                double price = Double.parseDouble(billPrices.get(i));
                int qty = Integer.parseInt(billQtys.get(i));
                double subtotal = price * qty;
                totalAmount += subtotal;
            }
        }

        double finalAmount = totalAmount - discountAmount;
        if (finalAmount < 0) finalAmount = 0;

        // calculating current units in bill for each item (for display purposes)
        List<Integer> itemCurrentQtys = new ArrayList<>();
        if (items != null) {
            for (Item item : items) {
                int currentQtyInBill = 0;
                for (int j = 0; j < billIds.size(); j++) {
                    if (billIds.get(j).equals(item.getItemId())) {
                        currentQtyInBill = Integer.parseInt(billQtys.get(j));
                        break;
                    }
                }
                itemCurrentQtys.add(currentQtyInBill);
            }
        }


        req.setAttribute("customers", customers);
        req.setAttribute("items", items);
        req.setAttribute("itemCurrentQtys", itemCurrentQtys);
        req.setAttribute("billIds", billIds);
        req.setAttribute("billNames", billNames);
        req.setAttribute("billPrices", billPrices);
        req.setAttribute("billQtys", billQtys);
        req.setAttribute("selectedCustomer", selectedCustomer);
        req.setAttribute("discountAmount", discountAmount);
        req.setAttribute("totalAmount", totalAmount);
        req.setAttribute("finalAmount", finalAmount);
        req.setAttribute("customerSearch", customerSearch);
        req.setAttribute("itemSearch", itemSearch);
        req.setAttribute("showCustomers", showCustomers);
        req.setAttribute("showItems", showItems);

        req.getRequestDispatcher("createBill.jsp").forward(req, resp);
    }

    private void handleConfirmBill(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            // validate customer
            String customerId = req.getParameter("selectedCustomer");
            if (customerId == null || customerId.trim().isEmpty()) {
                req.setAttribute("error", "Customer ID is missing.");
                handleRequest(req, resp);
                return;
            }

            // retrieve item inputs
            String[] itemIds = req.getParameterValues("billItemId");
            String[] itemNames = req.getParameterValues("billItemName");
            String[] unitPrices = req.getParameterValues("billUnitPrice");
            String[] quantities = req.getParameterValues("billUnit");

            if (itemIds == null || itemIds.length == 0) {
                req.setAttribute("error", "No items found in bill.");
                handleRequest(req, resp);
                return;
            }

            // discount
            double discount = 0;
            try {
                discount = Double.parseDouble(req.getParameter("discount"));
            } catch (Exception ignored) {
            }


            List<BillItem> billItems = new ArrayList<>();
            for (int i = 0; i < itemIds.length; i++) {
                double price = Double.parseDouble(unitPrices[i]);
                int qty = Integer.parseInt(quantities[i]);
                double totalPrice = price * qty;

                BillItem item = new BillItem.Builder()
                        .setItemId(itemIds[i])
                        .setItemName(itemNames[i])
                        .setUnitPrice(price)
                        .setUnit(qty)
                        .setTotalPrice(totalPrice)
                        .build();

                billItems.add(item);
            }

            BillDTO billDTO = new BillDTO();
            billDTO.setCustomerId(customerId);
            billDTO.setItems(billItems);
            billDTO.setDiscount(discount);

            billService.processBill(billDTO);

            Customer customer = customerDAO.getCustomerByAccountNo(customerId);
            if (customer == null) {
                req.setAttribute("error", "Customer not found.");
                handleRequest(req, resp);
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
            handleRequest(req, resp);
        }
    }
}
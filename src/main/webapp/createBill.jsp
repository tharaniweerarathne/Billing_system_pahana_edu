<%@ page import="com.Billing_system_pahana_edu.dao.CustomerDAO" %>
<%@ page import="com.Billing_system_pahana_edu.dao.ItemDAO" %>
<%@ page import="com.Billing_system_pahana_edu.model.Customer" %>
<%@ page import="java.util.List" %>
<%@ page import="com.Billing_system_pahana_edu.model.Item" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Sasindi Weerarathne
  Date: 8/6/2025
  Time: 12:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  CustomerDAO customerDAO = new CustomerDAO();
  ItemDAO itemDAO = new ItemDAO();

  String customerSearch = request.getParameter("customerSearch");
  String itemSearch = request.getParameter("itemSearch");
  String showCustomers = request.getParameter("showCustomers");
  String showItems = request.getParameter("showItems");

  List<Customer> customers = null;
  // Show all customers when showCustomers button is clicked
  if ("true".equals(showCustomers)) {
    customers = customerDAO.getAllCustomers();
  } else if (customerSearch != null && !customerSearch.trim().isEmpty()) {
    customers = customerDAO.searchCustomers(customerSearch);
  }

  List<Item> items = null;
  // Show all items when showItems button is clicked
  if ("true".equals(showItems)) {
    items = itemDAO.getAllItems();
  } else if (itemSearch != null && !itemSearch.trim().isEmpty()) {
    items = itemDAO.searchItems(itemSearch);
  }

  String[] billItemIds = request.getParameterValues("billItemId");
  String[] billItemNames = request.getParameterValues("billItemName");
  String[] billUnitPrices = request.getParameterValues("billUnitPrice");
  String[] billUnits = request.getParameterValues("billUnit");
  String selectedCustomer = request.getParameter("selectedCustomer");

  List<String> billIds = new ArrayList<>();
  List<String> billNames = new ArrayList<>();
  List<String> billPrices = new ArrayList<>();
  List<String> billQtys = new ArrayList<>();

  // Load existing bill items from parameters
  if (billItemIds != null) {
    for (int i = 0; i < billItemIds.length; i++) {
      billIds.add(billItemIds[i]);
      billNames.add(billItemNames[i]);
      billPrices.add(billUnitPrices[i]);
      billQtys.add(billUnits[i]);
    }
  }

  // Handle adding new items to bill
  String addItemId = request.getParameter("addItemId");
  String addItemQtyStr = request.getParameter("addItemQty");
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

  // Handle removing items from bill
  String removeIndexStr = request.getParameter("removeIndex");
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
      // Invalid index, ignore
    }
  }

  // Calculate discount
  double discountAmount = 0.0;
  String discountStr = request.getParameter("discount");
  if (discountStr != null && !discountStr.trim().isEmpty()) {
    try {
      discountAmount = Double.parseDouble(discountStr.trim());
    } catch (NumberFormatException e) {
      discountAmount = 0.0;
    }
  }
%>
<html>
  <head>
    <title>Title</title>
  </head>
  <body>

  <h1>Create Bill</h1>

  <div class="section">
    <h2>Search Customer</h2>

    <!-- Search form for specific customers -->
    <form method="get" action="createBill.jsp">
      <input type="text" name="customerSearch" placeholder="Search by AccountNo, Name, Address"
             value="<%= customerSearch != null ? customerSearch : "" %>" />

      <!-- Preserve selected customer -->
      <% if (selectedCustomer != null && !selectedCustomer.trim().isEmpty()) { %>
      <input type="hidden" name="selectedCustomer" value="<%= selectedCustomer %>" />
      <% } %>

      <!-- Preserve discount -->
      <input type="hidden" name="discount" value="<%= discountAmount %>" />

      <!-- Preserve bill items -->
      <% for (int i = 0; i < billIds.size(); i++) { %>
      <input type="hidden" name="billItemId" value="<%= billIds.get(i) %>" />
      <input type="hidden" name="billItemName" value="<%= billNames.get(i) %>" />
      <input type="hidden" name="billUnitPrice" value="<%= billPrices.get(i) %>" />
      <input type="hidden" name="billUnit" value="<%= billQtys.get(i) %>" />
      <% } %>

      <button type="submit">Search Customers</button>
    </form>

    <!-- Button to show all customers -->
    <form method="get" action="createBill.jsp" style="display: inline; margin-left: 10px;">
      <input type="hidden" name="showCustomers" value="true" />

      <!-- Preserve selected customer -->
      <% if (selectedCustomer != null && !selectedCustomer.trim().isEmpty()) { %>
      <input type="hidden" name="selectedCustomer" value="<%= selectedCustomer %>" />
      <% } %>

      <!-- Preserve discount -->
      <input type="hidden" name="discount" value="<%= discountAmount %>" />

      <!-- Preserve bill items -->
      <% for (int i = 0; i < billIds.size(); i++) { %>
      <input type="hidden" name="billItemId" value="<%= billIds.get(i) %>" />
      <input type="hidden" name="billItemName" value="<%= billNames.get(i) %>" />
      <input type="hidden" name="billUnitPrice" value="<%= billPrices.get(i) %>" />
      <input type="hidden" name="billUnit" value="<%= billQtys.get(i) %>" />
      <% } %>

      <button type="submit">Show All Customers</button>
    </form>

    <% if (customers != null && customers.size() > 0) { %>
    <table border="1" style="margin-top: 10px;">
      <tr><th>AccountNo</th><th>Name</th><th>Address</th><th>Telephone</th><th>Select</th></tr>
      <% for (Customer c : customers) { %>
      <tr>
        <td><%= c.getAccountNo() %></td>
        <td><%= c.getName() %></td>
        <td><%= c.getAddress() %></td>
        <td><%= c.getTelephone() %></td>
        <td>
          <form method="get" action="createBill.jsp" style="display:inline;">
            <input type="hidden" name="selectedCustomer" value="<%= c.getAccountNo() %>" />
            <input type="hidden" name="discount" value="<%= discountAmount %>" />

            <!-- Preserve bill items -->
            <% for (int i = 0; i < billIds.size(); i++) { %>
            <input type="hidden" name="billItemId" value="<%= billIds.get(i) %>" />
            <input type="hidden" name="billItemName" value="<%= billNames.get(i) %>" />
            <input type="hidden" name="billUnitPrice" value="<%= billPrices.get(i) %>" />
            <input type="hidden" name="billUnit" value="<%= billQtys.get(i) %>" />
            <% } %>

            <button type="submit">Select</button>
          </form>
        </td>
      </tr>
      <% } %>
    </table>
    <% } %>

    <% if (selectedCustomer != null && !selectedCustomer.trim().isEmpty()) { %>
    <p><strong>Selected Customer:</strong> <%= selectedCustomer %></p>
    <% } %>
  </div>

  <div class="section">
    <h2>Search Items</h2>

    <!-- Search form for specific items -->
    <form method="get" action="createBill.jsp">
      <input type="text" name="itemSearch" placeholder="Search by ItemId, Name, Category"
             value="<%= itemSearch != null ? itemSearch : "" %>" />

      <!-- Preserve selected customer -->
      <% if (selectedCustomer != null && !selectedCustomer.trim().isEmpty()) { %>
      <input type="hidden" name="selectedCustomer" value="<%= selectedCustomer %>" />
      <% } %>

      <!-- Preserve discount -->
      <input type="hidden" name="discount" value="<%= discountAmount %>" />

      <!-- Preserve bill items -->
      <% for (int i = 0; i < billIds.size(); i++) { %>
      <input type="hidden" name="billItemId" value="<%= billIds.get(i) %>" />
      <input type="hidden" name="billItemName" value="<%= billNames.get(i) %>" />
      <input type="hidden" name="billUnitPrice" value="<%= billPrices.get(i) %>" />
      <input type="hidden" name="billUnit" value="<%= billQtys.get(i) %>" />
      <% } %>

      <button type="submit">Search Items</button>
    </form>

    <!-- Button to show all items -->
    <form method="get" action="createBill.jsp" style="display: inline; margin-left: 10px;">
      <input type="hidden" name="showItems" value="true" />

      <!-- Preserve selected customer -->
      <% if (selectedCustomer != null && !selectedCustomer.trim().isEmpty()) { %>
      <input type="hidden" name="selectedCustomer" value="<%= selectedCustomer %>" />
      <% } %>

      <!-- Preserve discount -->
      <input type="hidden" name="discount" value="<%= discountAmount %>" />

      <!-- Preserve bill items -->
      <% for (int i = 0; i < billIds.size(); i++) { %>
      <input type="hidden" name="billItemId" value="<%= billIds.get(i) %>" />
      <input type="hidden" name="billItemName" value="<%= billNames.get(i) %>" />
      <input type="hidden" name="billUnitPrice" value="<%= billPrices.get(i) %>" />
      <input type="hidden" name="billUnit" value="<%= billQtys.get(i) %>" />
      <% } %>

      <button type="submit">Show All Items</button>
    </form>

    <% if (items != null && items.size() > 0) { %>
    <table border="1" style="margin-top: 10px;">
      <tr><th>Item ID</th><th>Name</th><th>Category</th><th>Price</th><th>Available</th><th>Qty to Add</th><th>Action</th></tr>
      <% for (Item i : items) {
        // Check current quantity in bill
        int currentQtyInBill = 0;
        for (int j = 0; j < billIds.size(); j++) {
          if (billIds.get(j).equals(i.getItemId())) {
            currentQtyInBill = Integer.parseInt(billQtys.get(j));
            break;
          }
        }
        int maxAddable = i.getUnit() - currentQtyInBill;
      %>
      <tr>
        <td><%= i.getItemId() %></td>
        <td><%= i.getItemName() %></td>
        <td><%= i.getCategory() %></td>
        <td>Rs. <%= String.format("%.2f", (double) i.getPrice()) %></td>
        <td><%= i.getUnit() %> (In Bill: <%= currentQtyInBill %>)</td>
        <td>
          <% if (maxAddable > 0) { %>
          <form method="post" action="createBill.jsp" style="display:inline;">
            <input type="number" name="addItemQty" min="1" max="<%= maxAddable %>" value="1" required style="width: 60px;" />
            <input type="hidden" name="addItemId" value="<%= i.getItemId() %>" />

            <!-- Preserve all current state -->
            <% for (int j = 0; j < billIds.size(); j++) { %>
            <input type="hidden" name="billItemId" value="<%= billIds.get(j) %>" />
            <input type="hidden" name="billItemName" value="<%= billNames.get(j) %>" />
            <input type="hidden" name="billUnitPrice" value="<%= billPrices.get(j) %>" />
            <input type="hidden" name="billUnit" value="<%= billQtys.get(j) %>" />
            <% } %>

            <% if (selectedCustomer != null && !selectedCustomer.trim().isEmpty()) { %>
            <input type="hidden" name="selectedCustomer" value="<%= selectedCustomer %>" />
            <% } %>
            <input type="hidden" name="showItems" value="true" />
            <input type="hidden" name="discount" value="<%= discountAmount %>" />

            <button type="submit">Add</button>
          </form>
          <% } else { %>
          <span style="color: red;">Max reached</span>
          <% } %>
        </td>
      </tr>
      <% } %>
    </table>
    <% } %>
  </div>

  <div class="section total-section">
    <h2>Current Bill</h2>
    <% if (billIds.size() > 0) {
      double totalAmount = 0;

      // Calculate subtotal
      for (int i = 0; i < billIds.size(); i++) {
        double price = Double.parseDouble(billPrices.get(i));
        int qty = Integer.parseInt(billQtys.get(i));
        double subtotal = price * qty;
        totalAmount += subtotal;
      }

      // Calculate final amount
      double finalAmount = totalAmount - discountAmount;
      if (finalAmount < 0) finalAmount = 0;
    %>

    <table border="1">
      <tr><th>Item ID</th><th>Name</th><th>Unit Price</th><th>Qty</th><th>Subtotal</th><th>Action</th></tr>
      <% for (int i = 0; i < billIds.size(); i++) {
        double price = Double.parseDouble(billPrices.get(i));
        int qty = Integer.parseInt(billQtys.get(i));
        double subtotal = price * qty;
      %>
      <tr>
        <td><%= billIds.get(i) %></td>
        <td><%= billNames.get(i) %></td>
        <td>Rs. <%= String.format("%.2f", price) %></td>
        <td><%= qty %></td>
        <td>Rs. <%= String.format("%.2f", subtotal) %></td>
        <td>
          <form method="post" action="createBill.jsp" style="display:inline;">
            <input type="hidden" name="removeIndex" value="<%= i %>" />

            <!-- Preserve all other items -->
            <% for (int j = 0; j < billIds.size(); j++) { %>
            <input type="hidden" name="billItemId" value="<%= billIds.get(j) %>" />
            <input type="hidden" name="billItemName" value="<%= billNames.get(j) %>" />
            <input type="hidden" name="billUnitPrice" value="<%= billPrices.get(j) %>" />
            <input type="hidden" name="billUnit" value="<%= billQtys.get(j) %>" />
            <% } %>

            <% if (selectedCustomer != null && !selectedCustomer.trim().isEmpty()) { %>
            <input type="hidden" name="selectedCustomer" value="<%= selectedCustomer %>" />
            <% } %>
            <input type="hidden" name="discount" value="<%= discountAmount %>" />

            <button type="submit" onclick="return confirm('Remove this item?')">Remove</button>
          </form>
        </td>
      </tr>
      <% } %>

      <!-- Totals section -->
      <tr style="background-color: #e9ecef;">
        <td colspan="4"><strong>Subtotal:</strong></td>
        <td colspan="2"><strong>Rs. <%= String.format("%.2f", totalAmount) %></strong></td>
      </tr>
      <tr>
        <td colspan="4"><strong>Discount:</strong></td>
        <td colspan="2">
          <strong>- Rs. <%= String.format("%.2f", discountAmount) %></strong>
          <form method="post" action="createBill.jsp" style="display:inline; margin-left: 10px;">
            <input type="number" name="discount" step="0.01" min="0" max="<%= totalAmount %>"
                   value="<%= String.format("%.2f", discountAmount) %>"
                   style="width: 100px;" placeholder="Enter discount" />
            <button type="submit" style="margin-left: 5px;">Update</button>

            <!-- Preserve all bill items -->
            <% for (int j = 0; j < billIds.size(); j++) { %>
            <input type="hidden" name="billItemId" value="<%= billIds.get(j) %>" />
            <input type="hidden" name="billItemName" value="<%= billNames.get(j) %>" />
            <input type="hidden" name="billUnitPrice" value="<%= billPrices.get(j) %>" />
            <input type="hidden" name="billUnit" value="<%= billQtys.get(j) %>" />
            <% } %>

            <% if (selectedCustomer != null && !selectedCustomer.trim().isEmpty()) { %>
            <input type="hidden" name="selectedCustomer" value="<%= selectedCustomer %>" />
            <% } %>
          </form>
        </td>
      </tr>
      <tr style="background-color: #d4edda;">
        <td colspan="4"><strong>Final Total:</strong></td>
        <td colspan="2">
          <strong>Rs. <%= String.format("%.2f", finalAmount) %></strong>
        </td>
      </tr>
    </table>

    <form method="post" action="bill">
      <input type="hidden" name="selectedCustomer" value="<%= selectedCustomer != null ? selectedCustomer : "" %>" />
      <input type="hidden" name="discount" value="<%= discountAmount %>" />

      <!-- Hidden fields to preserve bill data -->
      <% for (int i = 0; i < billIds.size(); i++) { %>
      <input type="hidden" name="billItemId" value="<%= billIds.get(i) %>" />
      <input type="hidden" name="billItemName" value="<%= billNames.get(i) %>" />
      <input type="hidden" name="billUnitPrice" value="<%= billPrices.get(i) %>" />
      <input type="hidden" name="billUnit" value="<%= billQtys.get(i) %>" />
      <% } %>

      <p>
        <button type="submit" name="action" value="confirmBill"
                style="background-color: #28a745; color: white; padding: 10px 20px; font-size: 16px;">
          Confirm Bill
        </button>
      </p>
    </form>

    <% } else { %>
    <p>No items added to the bill yet.</p>
    <% } %>
  </div>

  </body>

</html>

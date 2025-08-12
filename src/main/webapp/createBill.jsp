<%@ page import="com.Billing_system_pahana_edu.model.Customer" %>
<%@ page import="java.util.List" %>
<%@ page import="com.Billing_system_pahana_edu.model.Item" %><%--
  Created by IntelliJ IDEA.
  User: Sasindi Weerarathne
  Date: 8/6/2025
  Time: 12:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  List<Customer> customers = (List<Customer>) request.getAttribute("customers");
  List<Item> items = (List<Item>) request.getAttribute("items");
  List<Integer> itemCurrentQtys = (List<Integer>) request.getAttribute("itemCurrentQtys");
  List<String> billIds = (List<String>) request.getAttribute("billIds");
  List<String> billNames = (List<String>) request.getAttribute("billNames");
  List<String> billPrices = (List<String>) request.getAttribute("billPrices");
  List<String> billQtys = (List<String>) request.getAttribute("billQtys");
  String selectedCustomer = (String) request.getAttribute("selectedCustomer");
  Double discountAmount = (Double) request.getAttribute("discountAmount");
  Double totalAmount = (Double) request.getAttribute("totalAmount");
  Double finalAmount = (Double) request.getAttribute("finalAmount");
  String customerSearch = (String) request.getAttribute("customerSearch");
  String itemSearch = (String) request.getAttribute("itemSearch");
  String showCustomers = (String) request.getAttribute("showCustomers");
  String showItems = (String) request.getAttribute("showItems");
  String error = (String) request.getAttribute("error");

  // Handle null values
  if (billIds == null) billIds = new java.util.ArrayList<>();
  if (billNames == null) billNames = new java.util.ArrayList<>();
  if (billPrices == null) billPrices = new java.util.ArrayList<>();
  if (billQtys == null) billQtys = new java.util.ArrayList<>();
  if (discountAmount == null) discountAmount = 0.0;
  if (totalAmount == null) totalAmount = 0.0;
  if (finalAmount == null) finalAmount = 0.0;
%>
<html>
  <head>
    <title>Title</title>
    <link rel="stylesheet" href="css/Billing.css">
    <link href="https://cdn.jsdelivr.net/npm/remixicon/fonts/remixicon.css" rel="stylesheet">

  </head>
  <body>

  <div class="logo-container">
    <div class="logo">
      <img src="images/logo1.png" alt="Pahana Edu Logo" class="logo">
    </div>
  </div>

  <h1>Create Bill</h1>

  <% if (error != null && !error.trim().isEmpty()) { %>
  <div style="color: red; background-color: #ffebee; padding: 10px; margin: 10px 0; border: 1px solid red;">
    <strong>Error:</strong> <%= error %>
  </div>
  <% } %>

  <div class="section">
    <h2>Search Customer</h2>

    <!-- Search form for specific customers -->
    <form method="get" action="bill">
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

      <button type="submit"><i class="ri-search-line"></i> Search Customers</button>
    </form>

    <!-- Button to show all customers -->
    <form method="get" action="bill" style="display: inline; margin-left: 10px;">
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

      <button type="submit" class="btn1"><i class="ri-list-unordered"></i> Show All Customers</button>
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
          <form method="get" action="bill" style="display:inline;">
            <input type="hidden" name="selectedCustomer" value="<%= c.getAccountNo() %>" />
            <input type="hidden" name="discount" value="<%= discountAmount %>" />

            <!-- Preserve bill items -->
            <% for (int i = 0; i < billIds.size(); i++) { %>
            <input type="hidden" name="billItemId" value="<%= billIds.get(i) %>" />
            <input type="hidden" name="billItemName" value="<%= billNames.get(i) %>" />
            <input type="hidden" name="billUnitPrice" value="<%= billPrices.get(i) %>" />
            <input type="hidden" name="billUnit" value="<%= billQtys.get(i) %>" />
            <% } %>

            <button type="submit"><i class="ri-check-line"></i> Select</button>
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
    <form method="get" action="bill">
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

      <button type="submit"><i class="ri-search-line"></i> Search Items</button>
    </form>

    <!-- Button to show all items -->
    <form method="get" action="bill" style="display: inline; margin-left: 10px;">
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

      <button type="submit" class="btn1"><i class="ri-list-unordered"></i> Show All Items</button>
    </form>

    <% if (items != null && items.size() > 0) { %>
    <table border="1" style="margin-top: 10px;">
      <tr><th>Item ID</th><th>Name</th><th>Category</th><th>Price</th><th>Available</th><th>Qty to Add</th></tr>
      <% for (int idx = 0; idx < items.size(); idx++) {
        Item i = items.get(idx);
        int currentQtyInBill = itemCurrentQtys != null && idx < itemCurrentQtys.size() ? itemCurrentQtys.get(idx) : 0;
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
          <form method="post" action="bill" style="display:inline;">
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

            <button type="submit"><i class="ri-add-line"></i> Add</button>
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
    <% if (billIds.size() > 0) { %>

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
          <form method="post" action="bill" style="display:inline;">
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

            <button type="submit" onclick="return confirm('Remove this item?')"><i class="ri-delete-bin-line"></i> Remove</button>
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
          <form method="post" action="bill" style="display:inline; margin-left: 10px;">
            <input type="number" name="discount" step="0.01" min="0" max="<%= totalAmount %>"
                   value="<%= String.format("%.2f", discountAmount) %>"
                   style="width: 100px;" placeholder="Enter discount" />
            <button type="submit" class="btn1"><i class="ri-edit-line"></i> Update</button>

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
          <i class="ri-printer-line" ></i>
          Print Bill
        </button>

        <% String role = (String) session.getAttribute("role"); %>
        <button type="button"
                onclick="window.location.href='<%= "admin".equals(role) ? "dashboard_admin.jsp" : "dashboard_staff.jsp" %>'"
                style="background-color: #6c757d; color: white; padding: 10px 20px; font-size: 16px; display: flex; align-items: center; gap: 5px;">
          <i class="ri-arrow-left-line"></i>
          Back
        </button>

      </p>
    </form>

    <% } else { %>
    <p>No items added to the bill yet.</p>
    <% } %>
  </div>

  </body>
</html>

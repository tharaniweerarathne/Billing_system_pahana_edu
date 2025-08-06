<%@ page import="com.Billing_system_pahana_edu.dao.CustomerDAO" %>
<%@ page import="com.Billing_system_pahana_edu.dao.ItemDAO" %>
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
  CustomerDAO customerDAO = new CustomerDAO();
  ItemDAO itemDAO = new ItemDAO();

  String customerSearch = request.getParameter("customerSearch");
  String itemSearch = request.getParameter("itemSearch");

  List<Customer> customers = null;
  if (customerSearch != null) {
    customers = customerSearch.isEmpty() ? customerDAO.getAllCustomers() : customerDAO.searchCustomers(customerSearch);
  }

  List<Item> items = null;
  if (itemSearch != null) {
    items = itemSearch.isEmpty() ? itemDAO.getAllItems() : itemDAO.searchItems(itemSearch);
  }

  String[] billItemIds = request.getParameterValues("billItemId");
  String[] billItemNames = request.getParameterValues("billItemName");
  String[] billUnitPrices = request.getParameterValues("billUnitPrice");
  String[] billUnits = request.getParameterValues("billUnit");
  String selectedCustomer = request.getParameter("selectedCustomer");

  java.util.List<String> billIds = new java.util.ArrayList<>();
  java.util.List<String> billNames = new java.util.ArrayList<>();
  java.util.List<String> billPrices = new java.util.ArrayList<>();
  java.util.List<String> billQtys = new java.util.ArrayList<>();

  if (billItemIds != null) {
    for (int i = 0; i < billItemIds.length; i++) {
      billIds.add(billItemIds[i]);
      billNames.add(billItemNames[i]);
      billPrices.add(billUnitPrices[i]);
      billQtys.add(billUnits[i]);
    }
  }

  String addItemId = request.getParameter("addItemId");
  String addItemQtyStr = request.getParameter("addItemQty");
  if (addItemId != null && addItemQtyStr != null) {
    int addQty = 0;
    try {
      addQty = Integer.parseInt(addItemQtyStr);
    } catch (Exception e) {}

    if (addQty > 0) {
      Item addItem = itemDAO.getItemById(addItemId);
      if (addItem != null) {
        int stockAvailable = addItem.getUnit();

        int index = -1;
        for (int i = 0; i < billIds.size(); i++) {
          if (billIds.get(i).equals(addItemId)) {
            index = i;
            break;
          }
        }

        if (index >= 0) {
          int existingQty = Integer.parseInt(billQtys.get(index));
          int newQty = existingQty + addQty;
          if (newQty > stockAvailable) {
            newQty = stockAvailable;
          }
          billQtys.set(index, String.valueOf(newQty));
        } else {
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

  String removeIndexStr = request.getParameter("removeIndex");
  int removeIndex = -1;
  if (removeIndexStr != null) {
    try {
      removeIndex = Integer.parseInt(removeIndexStr);
    } catch (Exception e) {}
  }

  if (removeIndex >= 0 && removeIndex < billIds.size()) {
    billIds.remove(removeIndex);
    billNames.remove(removeIndex);
    billPrices.remove(removeIndex);
    billQtys.remove(removeIndex);
  }
%>
<html>
  <head>
    <title>Title</title>
  </head>
  <body>

  <h2>Search Customer</h2>
  <form method="get" action="createBill.jsp">
    <input type="text" name="customerSearch" placeholder="Search by AccountNo, Name, Address"
           value="<%= customerSearch != null ? customerSearch : "" %>" />
    <input type="hidden" name="itemSearch" value="" />
    <% if (selectedCustomer != null) { %>
    <input type="hidden" name="selectedCustomer" value="<%= selectedCustomer %>" />
    <% } %>
    <% for (int i = 0; i < billIds.size(); i++) { %>
    <input type="hidden" name="billItemId" value="<%= billIds.get(i) %>" />
    <input type="hidden" name="billItemName" value="<%= billNames.get(i) %>" />
    <input type="hidden" name="billUnitPrice" value="<%= billPrices.get(i) %>" />
    <input type="hidden" name="billUnit" value="<%= billQtys.get(i) %>" />
    <% } %>
    <button type="submit">Search Customers</button>
  </form>

  <% if (customers != null && customerSearch != null) { %>
  <table border="1">
    <tr><th>AccountNo</th><th>Name</th><th>Address</th><th>Telephone</th><th>Select</th></tr>
    <% for (Customer c : customers) { %>
    <tr>
      <td><%= c.getAccountNo() %></td>
      <td><%= c.getName() %></td>
      <td><%= c.getAddress() %></td>
      <td><%= c.getTelephone() %></td>
      <td>
        <form method="get" action="createBill.jsp">
          <input type="hidden" name="selectedCustomer" value="<%= c.getAccountNo() %>" />
          <input type="hidden" name="customerSearch" value="<%= customerSearch %>" />
          <input type="hidden" name="itemSearch" value="" />
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

  <h2>Search Item</h2>
  <form method="get" action="createBill.jsp">
    <input type="text" name="itemSearch" placeholder="Search by ItemId, Name, Category"
           value="<%= itemSearch != null ? itemSearch : "" %>" />
    <input type="hidden" name="customerSearch" value="" />
    <input type="hidden" name="selectedCustomer" value="<%= selectedCustomer != null ? selectedCustomer : "" %>" />
    <% for (int i = 0; i < billIds.size(); i++) { %>
    <input type="hidden" name="billItemId" value="<%= billIds.get(i) %>" />
    <input type="hidden" name="billItemName" value="<%= billNames.get(i) %>" />
    <input type="hidden" name="billUnitPrice" value="<%= billPrices.get(i) %>" />
    <input type="hidden" name="billUnit" value="<%= billQtys.get(i) %>" />
    <% } %>
    <button type="submit">Search Items</button>
  </form>

  <% if (items != null && itemSearch != null) { %>
  <form method="post" action="createBill.jsp">
    <table border="1">
      <tr><th>Item ID</th><th>Name</th><th>Category</th><th>Price</th><th>Available</th><th>Qty</th><th>Add</th></tr>
      <% for (Item i : items) { %>
      <tr>
        <td><%= i.getItemId() %></td>
        <td><%= i.getItemName() %></td>
        <td><%= i.getCategory() %></td>
        <td><%= String.format("%.2f", (double) i.getPrice()) %></td>
        <td><%= i.getUnit() %></td>
        <td><input type="number" name="addItemQty" min="1" max="<%= i.getUnit() %>" value="1" required /></td>
        <td><button type="submit" name="addItemId" value="<%= i.getItemId() %>">Add</button></td>
      </tr>
      <% } %>
    </table>
    <% for (int i = 0; i < billIds.size(); i++) { %>
    <input type="hidden" name="billItemId" value="<%= billIds.get(i) %>" />
    <input type="hidden" name="billItemName" value="<%= billNames.get(i) %>" />
    <input type="hidden" name="billUnitPrice" value="<%= billPrices.get(i) %>" />
    <input type="hidden" name="billUnit" value="<%= billQtys.get(i) %>" />
    <% } %>
    <input type="hidden" name="selectedCustomer" value="<%= selectedCustomer != null ? selectedCustomer : "" %>" />
    <input type="hidden" name="customerSearch" value="" />
    <input type="hidden" name="itemSearch" value="<%= itemSearch %>" />
  </form>
  <% } %>

  <h2>Current Bill</h2>
  <form method="post" action="bill">
    <input type="hidden" name="selectedCustomer" value="<%= selectedCustomer != null ? selectedCustomer : "" %>" />
    <% if (billIds.size() > 0) {
      double totalAmount = 0;
    %>
    <table border="1">
      <tr><th>Item ID</th><th>Name</th><th>Unit Price</th><th>Qty</th><th>Total</th><th>Remove</th></tr>
      <% for (int i = 0; i < billIds.size(); i++) {
        double price = Double.parseDouble(billPrices.get(i));
        int qty = Integer.parseInt(billQtys.get(i));
        double total = price * qty;
        totalAmount += total;
      %>
      <tr>
        <td><%= billIds.get(i) %></td>
        <td><%= billNames.get(i) %></td>
        <td><%= String.format("%.2f", price) %></td>
        <td><%= qty %></td>
        <td><%= String.format("%.2f", total) %></td>
        <td><button type="submit" name="removeIndex" value="<%= i %>">Remove</button></td>
      </tr>
      <input type="hidden" name="billItemId" value="<%= billIds.get(i) %>" />
      <input type="hidden" name="billItemName" value="<%= billNames.get(i) %>" />
      <input type="hidden" name="billUnitPrice" value="<%= billPrices.get(i) %>" />
      <input type="hidden" name="billUnit" value="<%= billQtys.get(i) %>" />
      <% } %>
      <tr>
        <td colspan="4">Total:</td>
        <td colspan="2"><%= String.format("%.2f", totalAmount) %></td>
      </tr>
      <tr>
        <td colspan="4">Discount:</td>
        <td colspan="2">
          <input type="number" name="discount" step="0.01" min="0" value="0" />
        </td>
      </tr>
    </table>
    <button type="submit" name="action" value="confirmBill">Confirm Bill</button>
    <% } else { %>
    <p>No items added to the bill yet.</p>
    <% } %>
  </form>

  </body>

</html>

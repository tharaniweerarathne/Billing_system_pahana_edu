<%@ page import="com.Billing_system_pahana_edu.model.Customer" %>
<%@ page import="com.Billing_system_pahana_edu.model.BillItem" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Sasindi Weerarathne
  Date: 8/6/2025
  Time: 12:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Customer customer = (Customer) request.getAttribute("customer");
  List<BillItem> items = (List<BillItem>) request.getAttribute("items");
  Double totalAmount = (Double) request.getAttribute("totalAmount");
  Double discount = (Double) request.getAttribute("discount");
  Double finalAmount = (Double) request.getAttribute("finalAmount");
%>

<html>
<head>
    <title>Print Bill</title>
  <link rel="stylesheet" href="css/printBill.css">

</head>

<body>
<div class="receipt-container">

  <div class="receipt-header">
    <div class="logo"><img src="images/logo1.png" alt="Pahana Edu Logo" class="logo"></div>
    <div class="company-name">PAHANA EDU BOOKSHOP</div>
  </div>


  <div class="receipt-body">
    <h2>Bill Confirmed Successfully!</h2>

    <h3>Customer Info</h3>
    <% if (customer != null) { %>
    Account No: <%= customer.getAccountNo() %><br>
    Name: <%= customer.getName() %><br>
    <% } else { %>
    <p>No customer info available.</p>
    <% } %>

    <div class="divider">- - - - - - - - - - - - - - - -</div>

    <h3>Purchased Items</h3>
    <% if (items != null) { %>
    <table>
      <tr><th>Item</th><th>Qty</th><th>Price</th></tr>
      <% for (BillItem item : items) { %>
      <tr>
        <td><%= item.getItemName() %></td>
        <td><%= item.getUnit() %></td>
        <td><%= item.getTotalPrice() %></td>
      </tr>
      <% } %>
    </table>

    <div class="totals-section">
      <div class="total-line">
        <span>Total:</span>
        <span><%= totalAmount %></span>
      </div>
      <div class="total-line">
        <span>Discount:</span>
        <span><%= discount %></span>
      </div>
      <div class="total-line final-total">
        <span>Final Amount:</span>
        <span><%= finalAmount %></span>
      </div>
    </div>
    <% } else { %>
    <p>No items found in bill.</p>
    <% } %>
  </div>


  <div class="receipt-footer">
    <div class="thank-you">THANK YOU FOR YOUR PURCHASE!</div>
    <div>Please keep this receipt for your records</div>
    <div style="margin-top: 5px;">Visit us again soon!</div>
  </div>
</div>
</body>

</html>

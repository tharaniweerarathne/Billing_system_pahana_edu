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
    <title>Title</title>
  <style>
    @media print {
      body {
        background: none !important;
        padding: 0 !important;
        margin: 0 !important;
      }
      .receipt-container {
        box-shadow: none !important;
        border: 1px solid #ddd !important;
        margin: 0 !important;
      }
    }

    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }

    body {
      font-family: 'Courier New', monospace;
      background: #f5f5f5;
      padding: 20px;
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 100vh;
    }

    .receipt-container {
      background: #ffffff;
      width: 320px;
      border-radius: 0;
      box-shadow:
              0 0 10px rgba(0,0,0,0.1),
              inset 0 0 20px rgba(0,0,0,0.02);
      border: 1px solid #ddd;
      position: relative;
    }

    /* Paper texture effect */
    .receipt-container::before {
      content: '';
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      background-image:
              radial-gradient(circle at 1px 1px, rgba(0,0,0,0.05) 1px, transparent 0);
      background-size: 20px 20px;
      pointer-events: none;
    }



    .receipt-header {
      background: linear-gradient(135deg, #e3f2fd 0%, #bbdefb 100%);
      padding: 20px 15px;
      text-align: center;
      border-bottom: 2px dashed #2196f3;
      position: relative;
    }

    .logo {
      width: 250px;
      height: 120px;
      margin: 0 auto 10px;
      background: #2196f3;
      border-radius: 10px;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 1.8rem;
      color: white;
      font-weight: bold;
    }

    .company-name {
      font-size: 1.3rem;
      font-weight: bold;
      color: #1565c0;
      margin-bottom: 5px;
      letter-spacing: 1px;
    }

    .receipt-info {
      font-size: 0.8rem;
      color: #666;
      margin-top: 8px;
    }

    .receipt-body {
      padding: 15px;
      background: #ffffff;
      position: relative;
      z-index: 1;
    }

    /* Hide original heading */
    h2 {
      display: none;
    }

    h3 {
      color: #1565c0;
      font-size: 0.9rem;
      font-weight: bold;
      margin: 15px 0 8px 0;
      text-transform: uppercase;
      letter-spacing: 0.5px;
      border-bottom: 1px solid #e3f2fd;
      padding-bottom: 3px;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      margin: 10px 0;
      font-size: 0.8rem;
    }

    table th {
      background: #f5f5f5;
      padding: 8px 5px;
      text-align: left;
      font-weight: bold;
      border-bottom: 1px solid #ddd;
      font-size: 0.75rem;
      text-transform: uppercase;
    }

    table td {
      padding: 6px 5px;
      border-bottom: 1px dotted #ccc;
      vertical-align: top;
    }

    table tr:last-child td {
      border-bottom: none;
    }

    /* Customer info styling */
    .receipt-body > *:not(h3):not(table):not(p) {
      line-height: 1.4;
      margin: 3px 0;
      font-size: 0.85rem;
    }

    /* Total amounts styling */
    .totals-section {
      margin-top: 15px;
      padding-top: 10px;
      border-top: 1px dashed #2196f3;
    }

    .total-line {
      display: flex;
      justify-content: space-between;
      margin: 3px 0;
      font-size: 0.85rem;
    }

    .final-total {
      font-weight: bold;
      font-size: 0.95rem;
      border-top: 1px solid #333;
      padding-top: 5px;
      margin-top: 5px;
    }

    .receipt-footer {
      text-align: center;
      padding: 15px;
      background: #f9f9f9;
      border-top: 1px dashed #2196f3;
      font-size: 0.75rem;
      color: #666;
    }

    .thank-you {
      font-weight: bold;
      color: #1565c0;
      margin-bottom: 5px;
      font-size: 0.85rem;
    }

    .divider {
      text-align: center;
      margin: 10px 0;
      color: #999;
      font-size: 0.8rem;
    }

    /* Print optimizations */
    @media print {
      .receipt-container {
        width: 100% !important;
        max-width: 300px !important;
      }
    }
  </style>
</head>

<body>
<div class="receipt-container">
  <!-- Header Section -->
  <div class="receipt-header">
    <div class="logo"><img src="images/logo1.png" alt="Pahana Edu Logo" class="logo"></div>
    <div class="company-name">PAHANA EDU BOOKSHOP</div>
  </div>

  <!-- Your Original JSP Code (Unchanged) -->
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

  <!-- Footer -->
  <div class="receipt-footer">
    <div class="thank-you">THANK YOU FOR YOUR PURCHASE!</div>
    <div>Please keep this receipt for your records</div>
    <div style="margin-top: 5px;">Visit us again soon!</div>
  </div>
</div>
</body>

</html>

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
    <link href="https://cdn.jsdelivr.net/npm/remixicon/fonts/remixicon.css" rel="stylesheet">

    <style>
      body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        margin: 0;
        padding: 10px;
        background: linear-gradient(135deg, rgba(101, 67, 33, 0.95), rgba(139, 92, 46, 0.9));
        min-height: 100vh;
        font-size: clamp(12px, 2.5vw, 14px);
      }

      /* Logo styling */
      .logo-container {
        text-align: center;
        margin: 10px 0 15px 0;
        padding: 10px;
      }

      .logo {
        display: inline-block;
      }

      .logo img {
        max-height: clamp(40px, 10vw, 60px);
        max-width: clamp(150px, 40vw, 200px);
        height: auto;
        width: auto;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0,0,0,0.2);
        background: rgba(255,255,255,0.1);
        padding: 5px;
      }

      h1 {
        text-align: center;
        color: #fff;
        font-size: clamp(1.5rem, 6vw, 3.2rem);
        font-weight: 600;
        letter-spacing: clamp(2px, 1vw, 4px);
        margin: clamp(15px, 5vw, 30px) 0 clamp(25px, 8vw, 50px) 0;
        position: relative;
        text-transform: uppercase;
        background: #fff;
        -webkit-background-clip: text;
        background-clip: text;
        -webkit-text-fill-color: transparent;
        text-shadow:
                0 1px 3px rgba(0, 0, 0, 0.25),
                0 0 10px rgba(255, 255, 255, 0.3);
        padding: 0 10px;
        word-break: break-word;
      }

      h2 {
        color: rgb(59, 43, 3);
        margin: 15px 0 10px 0;
        font-size: clamp(14px, 3vw, 16px);
        padding-bottom: 5px;
      }

      .section {
        background: rgba(196, 183, 165, 0.596);
        margin: 10px 0;
        padding: clamp(6px, 2vw, 8px) clamp(8px, 3vw, 12px);
        border-radius: 8px;
        box-shadow: 0 4px 6px rgba(0,0,0,0.1);
      }

      .total-section {
        background: rgba(255, 255, 255, 0.98);
        border: 2px solid rgba(139, 92, 46, 0.5);
      }

      /* Forms and inputs - Mobile optimized */
      input[type="text"], input[type="number"] {
        padding: clamp(6px, 2vw, 8px) clamp(8px, 2.5vw, 10px);
        border: 1px solid #ddd;
        border-radius: 4px;
        margin-right: clamp(4px, 2vw, 8px);
        margin-bottom: 8px;
        font-size: clamp(12px, 2.5vw, 13px);
        box-sizing: border-box;
      }

      input[type="text"] {
        width: clamp(120px, 40vw, 200px);
        max-width: 100%;
      }

      input[type="number"] {
        width: clamp(50px, 15vw, 60px);
        min-width: 50px;
      }

      button {
        background: linear-gradient(135deg, rgba(129, 21, 21, 0.9), rgba(162, 84, 77, 0.8));
        color: white;
        border: none;
        padding: clamp(6px, 2vw, 8px) clamp(10px, 3vw, 12px);
        border-radius: 4px;
        cursor: pointer;
        font-size: clamp(11px, 2.5vw, 12px);
        margin: 2px;
        transition: all 0.2s;
        white-space: nowrap;
        min-height: 32px;
      }

      button:hover {
        background: linear-gradient(135deg, rgba(182, 27, 25, 0.9), rgba(213, 107, 97, 0.8));
        transform: translateY(-1px);
      }

      button[name="action"][value="confirmBill"] {
        background: linear-gradient(135deg, #28a745, #20c997);
        padding: clamp(8px, 3vw, 10px) clamp(15px, 5vw, 20px);
        font-size: clamp(12px, 3vw, 14px);
        font-weight: bold;
        margin-top: 15px;
        width: 100%;
        max-width: 200px;
      }

      .btn1{
        background: linear-gradient(135deg, #0b3d0b, #145214);;
        color: white;
        border: none;
        padding: clamp(6px, 2vw, 8px) clamp(10px, 3vw, 12px);
        border-radius: 4px;
        cursor: pointer;
        font-size: clamp(11px, 2.5vw, 12px);
        margin: 2px;
        transition: all 0.2s;
        white-space: nowrap;
        min-height: 32px;
      }

      .btn1:hover {
        background: linear-gradient(135deg, #28a745, #20c997);
        transform: translateY(-1px);
      }

      /* Tables - Mobile responsive */
      .table-container {
        overflow-x: auto;
        margin-top: 10px;
        -webkit-overflow-scrolling: touch;
      }

      table {
        width: 100%;
        min-width: 400px;
        border-collapse: collapse;
        margin-top: 10px;
        font-size: clamp(10px, 2.2vw, 12px);
        background: white;
      }

      th, td {
        border: 1px solid #ddd;
        padding: clamp(4px, 1.5vw, 6px) clamp(6px, 2vw, 8px);
        text-align: left;
        white-space: nowrap;
      }

      th {
        background: linear-gradient(135deg, rgba(101, 67, 33, 0.8), rgba(139, 92, 46, 0.7));
        color: white;
        font-weight: bold;
        font-size: clamp(9px, 2vw, 11px);
        position: sticky;
        top: 0;
        z-index: 10;
      }

      tr:nth-child(even) {
        background-color: #f8f9fa;
      }

      tr:hover {
        background-color: #e8f4f8;
      }

      /* Error styling */
      div[style*="color: red"] {
        background: #ffebee !important;
        border: 1px solid #f44336 !important;
        color: #c62828 !important;
        padding: clamp(6px, 2vw, 8px) !important;
        margin: 10px 0 !important;
        border-radius: 4px;
        font-size: clamp(11px, 2.5vw, 13px);
        word-wrap: break-word;
      }

      /* Selected customer highlight */
      p strong {
        color: #2e7d32;
        background: rgba(76, 175, 80, 0.1);
        padding: 4px 8px;
        border-radius: 3px;
        word-break: break-word;
      }

      /* Compact form layouts */
      form {
        display: inline-block;
        margin: 2px;
      }

      /* Total rows styling */
      tr[style*="background-color: #e9ecef"] {
        background-color: rgba(139, 92, 46, 0.1) !important;
        font-weight: bold;
      }

      tr[style*="background-color: #d4edda"] {
        background-color: rgba(76, 175, 80, 0.2) !important;
        font-weight: bold;
      }

      /* Responsive form containers */
      .form-group {
        display: flex;
        flex-wrap: wrap;
        gap: clamp(4px, 2vw, 8px);
        align-items: center;
        margin-bottom: 10px;
      }

      .form-group input,
      .form-group button {
        margin-right: 0;
      }

      /* Mobile-specific responsive adjustments */
      @media (max-width: 480px) {
        body {
          padding: 5px;
          font-size: 12px;
        }

        .section {
          padding: 8px;
          margin: 8px 0;
        }

        h1 {
          font-size: 1.8rem;
          letter-spacing: 1px;
          margin: 10px 0 20px 0;
        }

        input[type="text"] {
          width: 100%;
          max-width: 200px;
          margin-bottom: 8px;
        }

        input[type="number"] {
          width: 80px;
        }

        button {
          padding: 8px 10px;
          font-size: 11px;
          margin: 4px 2px;
        }

        button[name="action"][value="confirmBill"] {
          width: 100%;
          margin-top: 10px;
          padding: 12px;
          font-size: 13px;
        }

        table {
          min-width: 350px;
          font-size: 10px;
        }

        th, td {
          padding: 3px 4px;
          font-size: 9px;
        }

        /* Stack form elements on very small screens */
        .form-group {
          flex-direction: column;
          align-items: stretch;
        }

        .form-group input,
        .form-group button {
          width: 100%;
          margin-bottom: 5px;
        }
      }

      @media (max-width: 768px) {
        body {
          padding: 8px;
        }

        .section {
          padding: 10px;
        }

        input[type="text"] {
          width: clamp(140px, 50vw, 180px);
        }

        table {
          font-size: 11px;
        }

        th, td {
          padding: 4px 6px;
        }

        /* Better button spacing on tablets */
        button {
          margin: 3px;
          padding: 7px 12px;
        }
      }

      @media (min-width: 1200px) {
        body {
          max-width: 1200px;
          margin: 0 auto;
          padding: 20px;
        }

        .section {
          padding: 15px 20px;
        }
      }
    </style>
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
      </p>
    </form>

    <% } else { %>
    <p>No items added to the bill yet.</p>
    <% } %>
  </div>

  </body>
</html>

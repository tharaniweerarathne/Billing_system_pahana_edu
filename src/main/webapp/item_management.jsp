<%@ page import="com.Billing_system_pahana_edu.model.Item" %>
<%@ page import="java.util.List" %>
<%@ page import="com.Billing_system_pahana_edu.service.ItemService" %><%--
  Created by IntelliJ IDEA.
  User: Sasindi Weerarathne
  Date: 8/5/2025
  Time: 8:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Item> list = (List<Item>) request.getAttribute("items");
    if (list == null) {
        list = new ItemService().getAll();
    }
    String nextId = new ItemService().getNextId();
    String query = request.getParameter("query") != null ? request.getParameter("query") : "";
    String role = (String) session.getAttribute("role");
%>
<html>
<head>
    <title>Item management</title>
    <link rel="stylesheet" href="dashboard.css">
    <link href="https://cdn.jsdelivr.net/npm/remixicon@4.3.0/fonts/remixicon.css" rel="stylesheet">

</head>
<body>
<div class="sidebar">
    <div class="logo-container">
        <div class="logo">
            <img src="images/logo1.png" alt="Pahana Edu Logo" class="logo">
        </div>
    </div>

    <ul class="nav-menu">
        <li><a href="#"><i class="ri-dashboard-line"></i> Dashboard</a></li>

        <% if ("Admin".equals(role)) { %>
        <li><a href="Staff_management.jsp"><i class="ri-user-settings-line"></i> Staff Management</a></li>
        <% } %>

        <li><a href="customer_management.jsp"><i class="ri-user-line"></i> Customer Management</a></li>
        <li><a href="item_management.jsp"><i class="ri-box-3-line"></i> View Items</a></li>
        <li><a href="createBill.jsp"><i class="ri-file-list-3-line"></i>Generate Bill</a></li>
        <li><a href="ViewBill.jsp"><i class="ri-history-line"></i> Purchase History Management</a></li>
    </ul>
</div>

<div class="main-content">
    <div class="top-bar">
        <div class="top-bar-user">
            <span class="user-name">
              Welcome, <%= session.getAttribute("name") != null ? session.getAttribute("name") : "Staff" %>
            </span>
            <a href="login.jsp" class="logout-btn">Logout</a>
        </div>
    </div>
    <div class="page-header">
        <h2>Manage Items</h2>
    </div>

    
    <% if ("Admin".equals(role)) { %>
    <form method="post" action="ItemController" class="add-form">
        <input type="hidden" name="action" value="add">
        <h3>Add New Item</h3>

        <div class="form-group">
            <label>Item ID</label>
            <input type="text" name="itemId" value="<%= nextId %>" readonly>
        </div>
        <div class="form-group">
            <label>Item Name</label>
            <input type="text" name="itemName" required>
        </div>
        <div class="form-group">
            <label>Category</label>
            <input type="text" name="category" required>
        </div>
        <div class="form-group">
            <label>Price</label>
            <input type="number" name="price" required>
        </div>
        <div class="form-group">
            <label>unit</label>
            <input type="number" name="unit" required>
        </div>
        <button type="submit" class="btn btn-primary">Add Item</button>
    </form>
    <% } %>

    <form method="get" action="ItemController" class="search-form">
        <input type="text" name="query" placeholder="Search by Name or Category" value="<%= query %>">
        <button type="submit" class="btn btn-primary btn-sm"><i class="ri-search-line"></i> Search</button>
        <a href="ItemController"><button type="button" class="btn btn-sm btn-danger"><i class="ri-refresh-line"></i> Reset</button></a>
    </form>


    <table border="1">
        <thead>
        <tr>
            <th>Item ID</th>
            <th>Name</th>
            <th>Category</th>
            <th>Price</th>
            <th>unit</th>
            <% if ("Admin".equals(role)) { %>
            <th>Actions</th>
            <% } %>
        </tr>
        </thead>
        <tbody>
        <% for (Item item : list) { %>
        <tr>
            <form method="post" action="ItemController">
                <td>
                    <%= item.getItemId() %>
                    <input type="hidden" name="itemId" value="<%= item.getItemId() %>">
                </td>
                <td><input type="text" name="itemName" value="<%= item.getItemName() %>" required></td>
                <td><input type="text" name="category" value="<%= item.getCategory() %>" required></td>
                <td>
                    <% if ("Admin".equals(role)) { %>
                    <input type="number" name="price" value="<%= item.getPrice() %>" required>
                    <% } else { %>
                    <span><%= item.getPrice() %></span>
                    <input type="hidden" name="price" value="<%= item.getPrice() %>">
                    <% } %>
                </td>
                <td>
                    <% if ("Admin".equals(role)) { %>
                    <input type="number" name="unit" value="<%= item.getUnit() %>" required>
                    <% } else { %>
                    <span><%= item.getUnit() %></span>
                    <input type="hidden" name="unit" value="<%= item.getUnit() %>">
                    <% } %>
                </td>
                <% if ("Admin".equals(role)) { %>
                <td class="actions">
                    <button type="submit" name="action" value="update" class="btn btn-primary btn-sm"><i class="ri-edit-line"></i> Update</button>
                    <button type="submit" name="action" value="delete" onclick="return confirm('Are you sure?')" class="btn btn-sm btn-danger"><i class="ri-delete-bin-line"></i> Delete</button>
                </td>
                <% } %>

            </form>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
</body>

</html>

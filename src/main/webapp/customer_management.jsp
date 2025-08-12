<%@ page import="com.Billing_system_pahana_edu.service.CustomerService" %>
<%@ page import="com.Billing_system_pahana_edu.model.Customer" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Sasindi Weerarathne
  Date: 8/5/2025
  Time: 12:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    CustomerService service = new CustomerService();
    String searchQuery = request.getParameter("query") != null ? request.getParameter("query").trim() : "";

    List<Customer> list;
    if (!searchQuery.isEmpty()) {
        list = service.searchCustomers(searchQuery); // You need to add this method in CustomerService & DAO
    } else {
        list = service.getAll();
    }

    String nextAccountNO = service.getNextAccountNo();
    String role = (String) session.getAttribute("role");
%>
<html>
<head>
    <title>Customer Management</title>
    <link rel="stylesheet" href="css/dashboard.css">
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
        <% if ("Admin".equals(role)) { %>
        <li><a href="dashboard_admin.jsp"><i class="ri-dashboard-line"></i> Dashboard</a></li>
        <% } else { %>
        <li><a href="dashboard_staff.jsp"><i class="ri-dashboard-line"></i> Dashboard</a></li>
        <% } %>

        <% if ("Admin".equals(role)) { %>
        <li><a href="Staff_management.jsp"><i class="ri-user-settings-line"></i> Staff Management</a></li>
        <% } %>

        <li><a href="customer_management.jsp"><i class="ri-user-line"></i> Customer Management</a></li>
        <li><a href="item_management.jsp"><i class="ri-box-3-line"></i> View Items</a></li>
        <li><a href="createBill.jsp"><i class="ri-file-list-3-line"></i>Generate Bill</a></li>
        <li><a href="ViewBill.jsp"><i class="ri-history-line"></i> Purchase History Management</a></li>
        <li><a href="help_section.jsp"><i class="ri-question-line"></i> Help</a></li>
    </ul>
</div>

<div class="main-content">
    <div class="top-bar">
        <div class="top-bar-user">
            <span class="user-name">
              Welcome, <%= session.getAttribute("name") != null ? session.getAttribute("name") : "Admin" %>
            </span>
            <a href="login.jsp" class="logout-btn" onclick="return confirm('Are you sure you want to log out?')">Logout</a>
        </div>
    </div>
    <h2 class="page-header">Manage Customers</h2>



    <form method="post" action="CustomerController" class="add-form">
        <input type="hidden" name="action" value="add">
        <h3>Add New Customer</h3>

        <div class="form-group">
            <label>Account Number</label>
            <input type="text" name="accountNo" value="<%= nextAccountNO %>" readonly>
        </div>
        <div class="form-group">
            <label>Name</label>
            <input type="text" name="name" required>
        </div>

        <div class="form-group">
            <label>Email</label>
            <input type="email" name="email" required>
        </div>

        <div class="form-group">
            <label>Address</label>
            <input type="text" name="address" required>
        </div>
        <div class="form-group">
            <label>Telephone</label>
            <input type="tel" name="telephone" required>
        </div>
        <button type="submit" class="btn btn-primary">
            Add Customer
        </button>
    </form>


    <form method="get" action="CustomerController" style="margin-bottom: 20px;">
        <input type="text" name="query" placeholder="Search by name, address or telephone" value="<%= searchQuery %>" style="width: 250px; padding: 5px;">
        <button type="submit" class="btn btn-primary btn-sm"><i class="ri-search-line"></i> Search</button>
        <a href="CustomerController"><button type="button" class="btn btn-sm btn-danger"> <i class="ri-refresh-line"></i> Reset</button></a>
    </form>


    <table>
        <thead>
        <tr>
            <th>Account Number</th>
            <th>Name</th>
            <th>Email</th>
            <th>Address</th>
            <th>Telephone</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% for (Customer c : list) { %>
        <tr>
            <form method="post" action="CustomerController">
                <td>
                    <%= c.getAccountNo() %>
                    <input type="hidden" name="accountNo" value="<%= c.getAccountNo() %>">
                </td>
                <td><input type="text" name="name" value="<%= c.getName() %>" required></td>
                <td><input type="email" name="email" value="<%= c.getEmail() %>" required></td>
                <td><input type="text" name="address" value="<%= c.getAddress() %>" required></td>
                <td><input type="tel" name="telephone" value="<%= c.getTelephone() %>" required></td>
                <td class="actions">
                    <button type="submit" name="action" value="update" class="btn btn-primary btn-sm" onclick="alert('Customer information updated successfully')"><i class="ri-edit-line"></i> Update</button>
                    <button type="submit" name="action" value="delete" onclick="return confirm('Are you sure?')" class="btn btn-sm btn-danger"><i class="ri-delete-bin-line"></i> Delete</button>
                </td>
            </form>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

</body>

</html>

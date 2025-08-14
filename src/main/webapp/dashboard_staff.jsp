<%--
  Created by IntelliJ IDEA.
  User: Sasindi Weerarathne
  Date: 8/4/2025
  Time: 1:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String role = (String) session.getAttribute("role");
%>
<html>
<head>
    <title>Staff Dashboard</title>
    <link rel="stylesheet" href="css/main.css">
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
        <li><a href="dashboard_staff.jsp"><i class="ri-dashboard-line"></i> Dashboard</a></li>

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
    <div class="content-wrapper">
        <div class="top-bar">
            <div class="top-bar-user">
        <span class="user-name">
          Welcome, <%= session.getAttribute("name") != null ? session.getAttribute("name") : "Staff" %>
        </span>
                <a href="login.jsp" class="logout-btn" onclick="return confirm('Are you sure you want to log out?')">Logout</a>
            </div>
        </div>

        <div class="welcome-section">
            <h1 class="welcome-title">Welcome to Pahana Edu Dashboard</h1>
            <p class="welcome-subtitle">Manage your educational resources efficiently</p>
            <span class="role-badge">
        <%= role != null ? role : "Staff" %> Panel
      </span>
        </div>

        <div class="dashboard-buttons">
            <% if ("Admin".equals(role)) { %>
            <a href="Staff_management.jsp" class="feature-btn">
                <i class="ri-user-settings-line"></i>
                <h3>Staff Management</h3>
                <p>Add, edit, and manage staff members and their permissions</p>
            </a>
            <% } %>

            <a href="customer_management.jsp" class="feature-btn">
                <i class="ri-user-line"></i>
                <h3>Customer Management</h3>
                <p>Manage customer information and maintain relationships</p>
            </a>

            <a href="item_management.jsp" class="feature-btn">
                <i class="ri-box-3-line"></i>
                <h3>Inventory Management</h3>
                <p>View and manage your inventory and stock levels</p>
            </a>

            <a href="createBill.jsp" class="feature-btn">
                <i class="ri-file-list-3-line"></i>
                <h3>Generate Bill</h3>
                <p>Create new bills </p>
            </a>

            <a href="ViewBill.jsp" class="feature-btn">
                <i class="ri-history-line"></i>
                <h3>Purchase History</h3>
                <p>View all transaction records and history</p>
            </a>


        <div class="help-section">
            <h2 class="help-title">Need Assistance?</h2>
            <p class="help-subtitle">This guide will help you understand and use the system effectively</p>
            <a href="help_section.jsp" class="help-btn">
                <i class="ri-customer-service-2-line"></i>
                Open Help Guide
            </a>
        </div>
    </div>
</div>
</div>

</body>
</html>

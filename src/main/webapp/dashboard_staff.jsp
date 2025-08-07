<%--
  Created by IntelliJ IDEA.
  User: Sasindi Weerarathne
  Date: 8/4/2025
  Time: 1:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="sidebar">
    <h2>Admin Panel</h2>
    <p>Welcome, <%= session.getAttribute("name") != null ? session.getAttribute("name") : "Staff" %></p>
    <a href="addStaff.jsp">➕ Add Staff</a>
    <a href="manageStaff.jsp">🛠 Manage Staff</a>
    <a href="customer_management.jsp">👥 View Users</a>
    <a href="item_management.jsp">🛠 Manage Item</a>
    <a href="createBill.jsp">Calculate Bills</a>
    <a href="ViewBill.jsp">Calculate Bills</a>
    <a href="logout.jsp">🚪 Logout</a>
</div>

<div class="main-content">
    <h1>Dashboard</h1>
    <p class="welcome">You're logged in as <strong>Admin</strong>.</p>
    <p>Use the sidebar to navigate through admin options.</p>
</div>

</body>
</html>

<%@ page import="com.Billing_system_pahana_edu.service.UserService" %>
<%@ page import="com.Billing_system_pahana_edu.model.User" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Sasindi Weerarathne
  Date: 8/4/2025
  Time: 3:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String role = (String) session.getAttribute("role");
    if (role == null || !"Admin".equals(role)) {
        response.sendRedirect("login.jsp");
        return;
    }

    UserService service = new UserService();
    String searchQuery = request.getParameter("query") != null ? request.getParameter("query").trim() : "";

    List<User> list;
    if (!searchQuery.isEmpty()) {
        list = service.searchStaffs(searchQuery);
    } else {
        list = service.getAllStaff();
    }

    String nextID = service.getNextID();
%>

<html>
<head>
    <title>Staff management</title>
    <link rel="stylesheet" href="css/dashboard.css">
    <link href="https://cdn.jsdelivr.net/npm/remixicon@4.3.0/fonts/remixicon.css" rel="stylesheet">

</head>
<body>


<div class="sidebar">
    <div class="logo-container">
        <div class="logo"> <img src="images/logo1.png" alt="Pahana Edu Logo" class="logo"></div>
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
        <li><a href="ViewBill.jsp"><i class="ri-history-line"></i> Purchase History </a></li>
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
    <h2 class="page-header">Manage Staff</h2>



    <form method="post" action="StaffController" class="add-form">
        <input type="hidden" name="action" value="add">
        <h3>Add New Staff</h3>
        <div class="form-group">
            <label>Staff ID</label>
            <input type="text" name="id" value="<%= nextID %>" readonly>

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
            <label>Username</label>
            <input type="text" name="username" required>
        </div>
        <div class="form-group">
            <label>Password</label>
            <input type="password" name="password" required>
        </div>
        <button type="submit" class="btn btn-primary">Add Staff</button>
    </form>

    <% if (request.getAttribute("error") != null) { %>
    <div style="color: red; font-weight: bold; margin-bottom: 10px;">
        <%= request.getAttribute("error") %>
    </div>
    <% } %>


    <form method="get" action="StaffController" style="margin-bottom: 20px;">
        <input type="text" name="query" placeholder="Search by ID , name or email"
               value="<%= request.getAttribute("searchQuery") != null ? request.getAttribute("searchQuery") : "" %>" style="width: 250px; padding: 5px;">
        <button type="submit" class="btn btn-primary btn-sm"><i class="ri-search-line"></i> Search</button>
        <a href="StaffController" class="btn btn-sm btn-danger"><i class="ri-refresh-line"></i> Reset</a>
    </form>




    <table>
        <thead>
        <tr>
            <th>Staff ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Username</th>
            <th>Password</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% for (User user : list) { %>
        <tr>
            <form method="post" action="StaffController">
                <td>
                    <%= user.getId() %>
                    <input type="hidden" name="id" value="<%= user.getId() %>">
                </td>
                <td><input type="text" name="name" value="<%= user.getName() %>" required></td>
                <td><input type="email" name="email" value="<%= user.getEmail() %>" required></td>
                <td><input type="text" name="username" value="<%= user.getUsername() %>" required></td>
                <td><input type="text" name="password" value="<%= user.getPassword() %>" required></td>
                <td class="actions">
                    <button type="submit" name="action" value="update" class="btn btn-primary btn-sm" onclick="alert('Staff information updated successfully')"><i class="ri-edit-line"></i> Update</button>
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

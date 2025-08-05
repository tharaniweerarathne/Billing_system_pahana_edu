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
    <title>Title</title>

    <style>

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            background:rgba(101, 67, 33, 0.95);
            min-height: 100vh;
            display: flex;
            font-family: 'Poppins', sans-serif;
        }


        .sidebar {
            width: 280px;
            background: rgba(101, 67, 33, 0.95);
            box-shadow: 4px 0 20px rgba(0,0,0,0.15);
            position: fixed;
            height: 100vh;
            overflow-y: auto;
            z-index: 1000;
        }


        .logo-container {
            padding: 24px;
            background: rgba(101, 67, 33, 0.95);
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 16px;
            border-bottom: 1px solid rgba(255,255,255,0.1);
        }

        .logo {
            width: 200px;
            height: 80px;
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-weight: 700;
            font-size: 18px;
            background: rgba(255,255,255,0.1);
            border: 1px solid rgba(255,255,255,0.2);
            transition: 0.3s ease;
            overflow: hidden;
        }


        .logo:hover {
            background: rgba(255,255,255,0.2);
            transform: translateY(-2px);
        }

        .logo img {
            width: 100%;
            height: 100%;
            object-fit: contain;
            padding: 8px;
        }




        .nav-menu {
            list-style: none;
            padding: 20px 0;
        }

        .nav-menu li {
            margin: 8px 0;
        }

        .nav-menu a {
            display: block;
            color: #bcaaa4;
            text-decoration: none;
            padding: 15px 25px;
            font-size: 15px;
            font-weight: 500;
            transition: all 0.3s ease;
            border-left: 4px solid transparent;
        }

        .nav-menu a:hover {
            background: rgba(255,255,255,0.1);
            color: #fff;
            border-left-color: #a1887f;
            transform: translateX(5px);
        }

        .nav-menu a:active {
            background: rgba(255,255,255,0.15);
            border-left-color: #8d6e63;
        }


        .top-bar {
            background: linear-gradient(145deg, rgba(255,255,255,0.95) 0%, rgba(248,245,240,0.9) 100%);
            backdrop-filter: blur(10px);
            border: 1px solid rgba(215, 204, 200, 0.6);
            border-radius: 20px;
            box-shadow:
                    0 8px 32px rgba(93, 64, 55, 0.12),
                    0 2px 8px rgba(0, 0, 0, 0.05),
                    inset 0 1px 0 rgba(255, 255, 255, 0.8);
            display: flex;
            justify-content: flex-end;
            align-items: center;
            padding: 18px 30px;
            margin-bottom: 25px;
            position: relative;
            overflow: hidden;
            width: fit-content;
            margin-left: auto;
        }
        .top-bar::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 1px;
            background: linear-gradient(90deg, transparent 0%, rgba(141, 110, 99, 0.3) 50%, transparent 100%);
        }

        .top-bar-user {
            display: flex;
            align-items: center;
            gap: 20px;
            padding: 5px 10px;
            border-radius: 15px;
            background: rgba(255, 255, 255, 0.4);
            backdrop-filter: blur(5px);
            transition: all 0.3s ease;
        }

        .top-bar-user:hover {
            background: rgba(255, 255, 255, 0.6);
            transform: translateY(-1px);
            box-shadow: 0 4px 15px rgba(93, 64, 55, 0.15);
        }

        .user-name {
            color: #2d1b13;
            font-weight: 700;
            font-size: 16px;
            text-shadow: 0 1px 2px rgba(255, 255, 255, 0.8);
            letter-spacing: 0.3px;
        }

        .logout-btn {
            background: linear-gradient(180deg, rgba(101, 67, 33, 0.95) 0%, rgba(140, 102, 70, 0.95) 100%);
            color: white;
            padding: 10px 24px;
            border: none;
            border-radius: 12px;
            text-decoration: none;
            font-size: 13px;
            font-weight: 700;
            transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
            text-transform: uppercase;
            letter-spacing: 0.8px;
            box-shadow:
                    0 4px 15px rgba(109, 76, 65, 0.25),
                    inset 0 1px 0 rgba(255, 255, 255, 0.2);
            position: relative;
            overflow: hidden;
        }

        .logout-btn::before {
            content: '';
            position: absolute;
            top: 0;
            left: -100%;
            width: 100%;
            height: 100%;
            background: linear-gradient(180deg, rgba(101, 67, 33, 0.95) 0%, rgba(140, 102, 70, 0.95) 100%);
            transition: left 0.6s;
        }

        .logout-btn:hover::before {
            left: 100%;
        }

        .logout-btn:hover {
            background: linear-gradient(145deg, #8d6e63 0%, #5d4037 100%);
            transform: translateY(-3px);
            box-shadow:
                    0 8px 25px rgba(93, 64, 55, 0.4),
                    inset 0 1px 0 rgba(255, 255, 255, 0.3);
        }

        .logout-btn:active {
            transform: translateY(-1px);
            box-shadow: 0 4px 15px rgba(93, 64, 55, 0.3);
        }

        .main-content {
            padding: 35px 45px;
            background: linear-gradient(135deg, #f4f1eb 0%, #e8e0d1 100%);
            border: 1px solid rgba(204, 204, 204, 0.5);
            border-radius: 25px;
            margin: 25px;
            margin-left: 280px; /* Only if your sidebar width is 280px */
            box-shadow:
                    0 10px 40px rgba(0, 0, 0, 0.08),
                    0 2px 8px rgba(0, 0, 0, 0.04);
            backdrop-filter: blur(10px);
            -webkit-backdrop-filter: blur(10px);

            /* Add this to avoid overflow */
            max-width: calc(100% - 300px); /* Adjust if sidebar is 280px */
            overflow-x: auto;
            box-sizing: border-box;
        }

        .page-header {
            margin-bottom: 35px;
            border-bottom: 5px solid rgba(101, 67, 33, 0.95);;
            border-radius: 12px 12px 0 0;

        }


        .page-header h2 {
            color: #3e2723;
            font-size: 28px;
            font-weight: 600;
            text-shadow: 1px 1px 2px rgba(0,0,0,0.1);
        }


        .add-form {
            background: linear-gradient(145deg, #fff 0%, #f8f5f0 100%);
            padding: 30px;
            border-radius: 15px;
            margin-bottom: 35px;
            box-shadow: 0 8px 25px rgba(93, 64, 55, 0.15);
            border: 2px solid #d7ccc8;
        }

        .add-form h3 {
            color: #3e2723;
            margin-bottom: 25px;
            font-size: 22px;
            font-weight: 600;
            border-bottom: 2px solid #a1887f;
            padding-bottom: 10px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #4a2c20;
            font-weight: 600;
            font-size: 14px;
        }

        .form-group input {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #d7ccc8;
            border-radius: 8px;
            font-size: 15px;
            background: #fff;
            transition: all 0.3s ease;
            font-family: inherit;
        }

        .form-group input:focus {
            outline: none;
            border-color: #8d6e63;
            box-shadow: 0 0 0 3px rgba(161, 136, 127, 0.2);
            background: #fefefe;
        }


        .btn {
            padding: 12px 24px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 14px;
            font-weight: 600;
            text-decoration: none;
            display: inline-block;
            transition: all 0.3s ease;
            font-family: inherit;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .btn-primary {
            background: linear-gradient(180deg, rgba(101, 67, 33, 0.95) 0%, rgba(140, 102, 70, 0.95) 100%);
            color: white;
            box-shadow: 0 4px 15px rgba(93, 64, 55, 0.3);
        }

        .btn-primary:hover {
            background: linear-gradient(145deg, #7d5e52 0%, #4a2c20 100%);
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(93, 64, 55, 0.4);
        }

        .btn-danger {
            background: linear-gradient(145deg, #d32f2f 0%, #b71c1c 100%);
            color: white;
            box-shadow: 0 4px 15px rgba(211, 47, 47, 0.3);
        }

        .btn-danger:hover {
            background: linear-gradient(145deg, #c62828 0%, #a01b1b 100%);
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(211, 47, 47, 0.4);
        }

        .btn-sm {
            padding: 8px 16px;
            font-size: 12px;
            margin: 0 3px;
        }


        table {
            width: 100%;
            background: #fff;
            border-radius: 15px;
            overflow: hidden;
            box-shadow: 0 10px 30px rgba(93, 64, 55, 0.15);
            border: 2px solid #d7ccc8;
        }

        thead {
            background: linear-gradient(180deg, rgba(101, 67, 33, 0.95) 0%, rgba(140, 102, 70, 0.95) 100%);
        }

        thead th {
            color: #fff;
            padding: 18px 15px;
            text-align: left;
            font-weight: 600;
            font-size: 14px;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            border-bottom: 3px solid #8d6e63;
        }

        tbody tr {
            border-bottom: 1px solid #efebe9;
            transition: all 0.3s ease;
        }

        tbody tr:nth-child(even) {
            background: rgba(245, 238, 228, 0.3);
        }

        tbody tr:hover {
            background: rgba(161, 136, 127, 0.1);
            transform: scale(1.01);
        }

        tbody td {
            padding: 15px;
            vertical-align: middle;
            border-right: 1px solid #efebe9;
        }

        tbody td:last-child {
            border-right: none;
        }

        tbody input {
            width: 100%;
            padding: 8px 12px;
            border: 1px solid #d7ccc8;
            border-radius: 6px;
            font-size: 14px;
            background: #fff;
            transition: all 0.3s ease;
        }

        tbody input:focus {
            outline: none;
            border-color: #8d6e63;
            box-shadow: 0 0 0 2px rgba(161, 136, 127, 0.2);
        }

        .actions {
            text-align: center;
            white-space: nowrap;
        }

        input[name="query"] {
            width: 250px;
            padding: 12px 16px;
            border: 2px solid rgba(101, 67, 33, 0.95);
            border-radius: 12px;
            font-size: 16px;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            font-weight: 400;
            color: #2c3e50;
            background: linear-gradient(145deg, #ffffff, #f8f9fa);
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
            transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
            outline: none;
            position: relative;
        }

        /* Hover State */
        input[name="query"]:hover {
            border-color: #a0aec0;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.12);
            transform: translateY(-1px);
        }

        /* Focus State */
        input[name="query"]:focus {
            border-color: #667eea;
            box-shadow:
                    0 0 0 3px rgba(102, 126, 234, 0.1),
                    0 8px 24px rgba(102, 126, 234, 0.15);
            transform: translateY(-2px);
            background: #ffffff;
        }

        /* Active State */
        input[name="query"]:active {
            transform: translateY(0);
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
        }

        /* Placeholder Styling */
        input[name="query"]::placeholder {
            color: #a0aec0;
            font-weight: 300;
            opacity: 1;
            transition: opacity 0.3s ease;
        }

        input[name="query"]:focus::placeholder {
            opacity: 0.6;
            transform: translateX(4px);
        }



        div[style*="color: red"] {
            background: linear-gradient(145deg, #ffebee 0%, #ffcdd2 100%) !important;
            color: #c62828 !important;
            padding: 15px 20px !important;
            border-radius: 10px !important;
            border: 2px solid #ef5350 !important;
            font-weight: 600 !important;
            margin-bottom: 20px !important;
            box-shadow: 0 4px 15px rgba(198, 40, 40, 0.2) !important;
        }



        @media (max-width: 768px) {
            .sidebar {
                width: 100%;
                height: auto;
                position: relative;
            }

            .main-content {
                margin-left: 0;
                margin-right: 0;
                max-width: 100%;
                padding: 20px;
                box-sizing: border-box;
            }

            .add-form {
                padding: 20px;
            }

            table {
                font-size: 12px;
            }

            .btn-sm {
                padding: 6px 10px;
                font-size: 10px;
            }
        }


        ::-webkit-scrollbar {
            width: 8px;
        }

        ::-webkit-scrollbar-track {
            background: #f5f5f5;
        }

        ::-webkit-scrollbar-thumb {
            background: #8d6e63;
            border-radius: 4px;
        }

        ::-webkit-scrollbar-thumb:hover {
            background: #6d4c41;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .main-content > * {
            animation: fadeIn 0.6s ease-out;
        }


        button:focus, input:focus, a:focus {
            outline: 2px solid #8d6e63;
            outline-offset: 2px;
        }


        @media print {
            .sidebar {
                display: none;
            }

            .main-content {
                margin-left: 0;
            }

            .btn {
                display: none;
            }
        }
    </style>
</head>
<body>
<div class="sidebar">
    <div class="logo-container">
        <div class="logo">
            <img src="images/logo1.png" alt="Pahana Edu Logo" class="logo">
        </div>
    </div>

    <ul class="nav-menu">
        <li><a href="#">Dashboard</a></li>
        <li><a href="manage_item.jsp">Manage Items</a></li>
        <% if ("Admin".equals(role)) { %>
        <li><a href="Staff_management.jsp">Staff Management</a></li>
        <% } %>
        <li><a href="customer_management.jsp">Customer Management</a></li>
        <li><a href="#">Settings</a></li>
        <li><a href="logout.jsp">Logout</a></li>
    </ul>
</div>

<div class="main-content">
    <div class="top-bar">
        <div class="top-bar-user">
            <span class="user-name">
              Welcome, <%= session.getAttribute("name") != null ? session.getAttribute("name") : "Admin" %>
            </span>
            <a href="login.jsp" class="logout-btn">Logout</a>
        </div>
    </div>
    <h2 class="page-header">Manage Customers</h2>


    <!-- Add New Customer Form -->
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
        <button type="submit" class="btn btn-primary">Add Customer</button>
    </form>


    <form method="get" action="CustomerController" style="margin-bottom: 20px;">
        <input type="text" name="query" placeholder="Search by name, address or telephone" value="<%= searchQuery %>" style="width: 250px; padding: 5px;">
        <button type="submit" class="btn btn-primary btn-sm">Search</button>
        <a href="CustomerController"><button type="button" class="btn btn-sm btn-danger">Reset</button></a>
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
                    <button type="submit" name="action" value="update" class="btn btn-primary btn-sm">Update</button>
                    <button type="submit" name="action" value="delete" onclick="return confirm('Are you sure?')" class="btn btn-sm btn-danger">Delete</button>
                </td>
            </form>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

</body>

</html>

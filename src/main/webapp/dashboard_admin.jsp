<%--
  Created by IntelliJ IDEA.
  User: Sasindi Weerarathne
  Date: 8/4/2025
  Time: 1:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String role = (String) session.getAttribute("role");
%>
<html>
<head>
    <title>Admin Dashboard</title>

  <link href="https://cdn.jsdelivr.net/npm/remixicon@4.3.0/fonts/remixicon.css" rel="stylesheet">
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
      overflow-x: hidden;
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
      font-size: 18px;
      font-weight: bold;
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
      z-index: 10;
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
      position: relative;
      padding: 35px 45px;
      background: linear-gradient(135deg, #f4f1eb 0%, #e8e0d1 100%);
      border: 1px solid rgba(204, 204, 204, 0.5);
      border-radius: 25px;
      margin: 25px;
      margin-left: 305px;
      width: calc(100% - 330px);
      box-shadow:
              0 10px 40px rgba(0, 0, 0, 0.08),
              0 2px 8px rgba(0, 0, 0, 0.04);
      backdrop-filter: blur(10px);
      -webkit-backdrop-filter: blur(10px);
      box-sizing: border-box;
      min-height: calc(100vh - 50px);
      height: auto;
      overflow-y: auto;
      overflow-x: hidden;
      z-index: 0;
    }

    /* Use a separate element inside for the background image */
    .main-content::before {
      content: '';
      position: absolute;
      top: 0; left: 0; right: 0; bottom: 0;
      background: url('images/background1.jpg') no-repeat center center;
      background-size: cover;
      background-attachment: scroll;  /* this makes it scroll normally */
      filter: blur(12px);
      opacity: 0.25;
      z-index: -1;
      border-radius: 25px;
      pointer-events: none;
      transition: background 0.3s ease;
    }



    .content-wrapper {
      position: relative;
      z-index: 2;
      min-height: 100%;
      padding-bottom: 50px;
    }

    .welcome-section {
      text-align: center;
      margin-bottom: 50px;
      padding: 40px 20px;
      background: linear-gradient(135deg, rgba(255,255,255,0.95) 0%, rgba(248,245,240,0.9) 100%);
      border-radius: 20px;
      box-shadow: 0 10px 30px rgba(0,0,0,0.1);
      border: 1px solid rgba(215, 204, 200, 0.3);
    }

    .welcome-title {
      font-size: 36px;
      font-weight: 700;
      color: #3e2723;
      margin-bottom: 10px;
      text-shadow: 2px 2px 4px rgba(0,0,0,0.1);
    }

    .welcome-subtitle {
      font-size: 18px;
      color: #5d4037;
      font-weight: 500;
      margin-bottom: 20px;
    }

    .role-badge {
      display: inline-block;
      padding: 8px 20px;
      background: linear-gradient(135deg, rgba(101, 67, 33, 0.95) 0%, rgba(140, 102, 70, 0.95) 100%);
      color: white;
      border-radius: 25px;
      font-weight: 600;
      font-size: 14px;
      text-transform: uppercase;
      letter-spacing: 1px;
      box-shadow: 0 4px 15px rgba(101, 67, 33, 0.3);
    }

    .dashboard-buttons {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
      gap: 25px;
      margin-bottom: 40px;
      width: 100%;
    }

    .feature-btn {
      background: linear-gradient(135deg, rgba(255,255,255,0.98) 0%, rgba(248,245,240,0.95) 100%);
      border: 2px solid rgba(101, 67, 33, 0.15);
      border-radius: 18px;
      padding: 30px 25px;
      text-align: center;
      text-decoration: none;
      color: #3e2723;
      transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
      box-shadow: 0 8px 25px rgba(0,0,0,0.08);
      position: relative;
      overflow: hidden;
    }

    .feature-btn::before {
      content: '';
      position: absolute;
      top: 0;
      left: -100%;
      width: 100%;
      height: 100%;
      background: linear-gradient(135deg, rgba(101, 67, 33, 0.1) 0%, rgba(140, 102, 70, 0.1) 100%);
      transition: left 0.5s ease;
    }

    .feature-btn:hover::before {
      left: 0;
    }

    .feature-btn:hover {
      transform: translateY(-8px);
      border-color: rgba(101, 67, 33, 0.4);
      box-shadow: 0 15px 40px rgba(0,0,0,0.15);
      background: linear-gradient(135deg, rgba(255,255,255,1) 0%, rgba(248,245,240,0.95) 100%);
    }

    .feature-btn i {
      font-size: 32px;
      color: rgba(101, 67, 33, 0.8);
      margin-bottom: 15px;
      display: block;
      transition: all 0.3s ease;
    }

    .feature-btn:hover i {
      color: rgba(101, 67, 33, 1);
      transform: scale(1.1);
    }

    .feature-btn h3 {
      font-size: 20px;
      font-weight: 700;
      margin-bottom: 10px;
      color: #3e2723;
    }

    .feature-btn p {
      font-size: 14px;
      color: #5d4037;
      line-height: 1.5;
      opacity: 0.8;
    }

    .help-section {
      background: linear-gradient(135deg, #ff6b6b 0%, #ee5a24 100%);
      border-radius: 20px;
      padding: 30px;
      text-align: center;
      box-shadow: 0 15px 35px rgba(255, 107, 107, 0.3);
      position: relative;
      overflow: hidden;
      margin-top: 30px;
    }

    .help-section::before {
      content: '';
      position: absolute;
      top: -50%;
      left: -50%;
      width: 200%;
      height: 200%;
      background: radial-gradient(circle, rgba(255,255,255,0.1) 0%, transparent 70%);
      animation: helpGlow 4s ease-in-out infinite;
    }

    @keyframes helpGlow {
      0%, 100% { opacity: 0.5; transform: scale(1); }
      50% { opacity: 0.8; transform: scale(1.1); }
    }

    .help-btn {
      background: rgba(255,255,255,0.25);
      border: 2px solid rgba(255,255,255,0.4);
      border-radius: 15px;
      padding: 20px 30px;
      color: white;
      text-decoration: none;
      font-weight: 700;
      font-size: 18px;
      display: inline-flex;
      align-items: center;
      gap: 15px;
      transition: all 0.3s ease;
      position: relative;
      z-index: 2;
    }

    .help-btn:hover {
      background: rgba(255,255,255,0.3);
      transform: translateY(-3px);
      box-shadow: 0 8px 25px rgba(0,0,0,0.2);
    }

    .help-btn i {
      font-size: 24px;
      animation: helpBounce 2s ease-in-out infinite;
    }

    @keyframes helpBounce {
      0%, 20%, 50%, 80%, 100% { transform: translateY(0); }
      40% { transform: translateY(-5px); }
      60% { transform: translateY(-3px); }
    }

    .help-title {
      color: white;
      font-size: 24px;
      font-weight: 700;
      margin-bottom: 10px;
      position: relative;
      z-index: 2;
    }

    .help-subtitle {
      color: rgba(255,255,255,0.9);
      font-size: 16px;
      margin-bottom: 20px;
      position: relative;
      z-index: 2;
    }

    @media print {
      .sidebar {
        display: none;
      }

      .main-content {
        margin-left: 0;
        max-width: 100%;
      }

      .feature-btn, .help-section {
        display: none;
      }
    }

    @media (max-width: 768px) {
      .dashboard-buttons {
        grid-template-columns: 1fr;
        gap: 20px;
      }

      .main-content {
        margin-left: 0;
        width: 100%;
        padding: 20px;
      }

      .sidebar {
        transform: translateX(-100%);
      }
    }

    @media (min-width: 769px) and (max-width: 1200px) {
      .main-content {
        margin-left: 305px;
        width: calc(100% - 330px);
      }

      .dashboard-buttons {
        grid-template-columns: repeat(2, 1fr);
      }
    }

    @media (min-width: 1201px) {
      .dashboard-buttons {
        grid-template-columns: repeat(3, 1fr);
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
        <p>View and manage your educational items and stock levels</p>
      </a>

      <a href="createBill.jsp" class="feature-btn">
        <i class="ri-file-list-3-line"></i>
        <h3>Generate Bill</h3>
        <p>Create new bills and process customer transactions</p>
      </a>

      <a href="ViewBill.jsp" class="feature-btn">
        <i class="ri-history-line"></i>
        <h3>Purchase History</h3>
        <p>View and manage all transaction records and history</p>
      </a>

      <a href="#" class="feature-btn">
        <i class="ri-bar-chart-line"></i>
        <h3>Reports & Analytics</h3>
        <p>Generate reports and view business analytics</p>
      </a>
    </div>

    <div class="help-section">
      <h2 class="help-title">Need Assistance?</h2>
      <p class="help-subtitle">Our support team is here to help you 24/7</p>
      <a href="#" class="help-btn" onclick="alert('Help system will be implemented soon!')">
        <i class="ri-customer-service-2-line"></i>
        Get Help & Support
      </a>
    </div>
  </div>
</div>


</body>
</html>

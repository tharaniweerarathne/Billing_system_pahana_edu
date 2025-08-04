<%--
  Created by IntelliJ IDEA.
  User: Sasindi Weerarathne
  Date: 8/4/2025
  Time: 1:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="login">
    <img src="images/logo.png" alt="Pahana Edu Logo" class="logo">
    <h2>Login</h2>
    <form method="post" action="login">
        <label>Username</label>
        <input type="text" name="username" required>

        <label>Password</label>
        <input type="password" name="password" required>

        <input type="submit" value="Login">

        <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null) { %>
        <div style="margin: 20px auto; padding: 10px 20px; width: fit-content; max-width: 80%; background-color: #fdecea; color: #d32f2f; border: 1px solid #f5c6cb; border-radius: 8px; text-align: center;">
            <%= error %>
        </div>
        <% } %>
    </form>
</div>


</body>
</html>

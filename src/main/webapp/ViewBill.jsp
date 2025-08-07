<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Sasindi Weerarathne
  Date: 8/7/2025
  Time: 4:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h2>Customer Purchase Summary</h2>

<form method="get" action="viewBills">
    <input type="text" name="search" placeholder="Search by customer name or ID"
           value="<%= request.getAttribute("search") != null ? request.getAttribute("search") : "" %>">
    <button type="submit">Search</button>
</form>

<hr>

<table border="1">
    <tr>
        <th>Customer Name</th>
        <th>Item Bought</th>
        <th>Final Amount</th>
        <th>Discount</th>
        <th>Bill Date</th>
    </tr>
    <%
        List<String[]> simpleBills = (List<String[]>) request.getAttribute("viewBills");
        if (simpleBills != null) {
            for (String[] row : simpleBills) {
    %>
    <tr>
        <td><%= row[0] %></td> <!-- customerName -->
        <td><%= row[1] %></td> <!-- items -->
        <td><%= row[2] %></td> <!-- finalAmount -->
        <td><%= row[3] %></td> <!-- discount -->
        <td><%= row[4] %></td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="5">Click the search Button to view the bills .</td>
    </tr>
    <%
        }
    %>
</table>

</body>
</html>

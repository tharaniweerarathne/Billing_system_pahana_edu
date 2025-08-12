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
    <title>Purchase History</title>
    <link rel="stylesheet" href="css/view_bill.css">

</head>
<body>
<div class="container">
    <div class="header">
        <div class="logo"><img src="images/logo1.png" alt="Pahana Edu Logo" class="logo"></div>
        <h2>Customer Purchase Summary</h2>
    </div>

    <div class="search-section">
        <form method="get" action="viewBills">
            <input type="text" name="search" placeholder="Search by customer name or ID"
                   value="<%= request.getAttribute("search") != null ? request.getAttribute("search") : "" %>">
            <button type="submit">Search</button>
        </form>
    </div>

    <hr>

    <div class="table-container">
        <table>
            <tr>
                <th>Bill ID</th>
                <th>Account No</th>
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
                <td><%= row[0] %></td> <!-- billId -->
                <td><%= row[1] %></td> <!-- accountNo -->
                <td><%= row[2] %></td> <!-- customerName -->
                <td><%= row[3] %></td> <!-- items -->
                <td><%= row[4] %></td> <!-- finalAmount -->
                <td><%= row[5] %></td> <!-- discount -->
                <td><%= row[6] %></td> <!-- billDate -->
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="6" class="no-data">Click the search button to view all the purchase history.</td>
            </tr>
            <%
                }
            %>
        </table>
    </div>
</div>

</body>
</html>

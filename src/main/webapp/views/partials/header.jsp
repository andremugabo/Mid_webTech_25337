<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.io.IOException" %>
<%@ page import="models.RoleType" %>  

<%
    RoleType userRole = null;  
	
    
    if (session != null) {
        userRole = (RoleType) session.getAttribute("role"); 
    }else{
    	 response.sendRedirect(request.getContextPath() + "/login.html");
         return;
    }
    
   
    if (userRole == null) {
        response.sendRedirect(request.getContextPath() + "/login.html");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Dashboard</title>
    <link rel="stylesheet" type="text/css" href="../assets/css/admin.css">
    <link rel="stylesheet" href="../assets/css/dashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="icon" type="image/x-icon" href="../assets/images/flav.png">
</head>
<body>
    <header>
        <nav class="navbar">
            <a class="logo" href="./">
                <img alt="AUCA Logo" src="../assets/images/flav.png">
                <h1>Auca Library</h1>
            </a>
        </nav>
    </header>

    <div class="container">
        <aside class="sidebar">
            <nav>
                <ul>
                    <li><a href="dashboard.jsp" class="active">Dashboard</a></li>
                    
                    <%-- Conditional menu items based on role --%>
                    <% if (userRole == RoleType.ADMIN || userRole == RoleType.LIBRARIAN) { %>
                        <li><a href="manageUsers.jsp">Manage Users</a></li>
                        <li><a href="manageBooks.jsp">Manage Books</a></li>
                        <li><a href="locationManagement.jsp">Manage Locations</a></li>
                    <% } %>

                    <% if (userRole == RoleType.LIBRARIAN || userRole == RoleType.STUDENT) { %>
                        <li><a href="borrowingRecords.jsp">Borrowing Records</a></li>
                    <% } %>

                    <% if (userRole == RoleType.ADMIN || userRole == RoleType.MANAGER) { %>
                        <li><a href="reports.jsp">Reports</a></li>
                    <% } %>

                    <% if (userRole != RoleType.STUDENT) { %>
                        <li><a href="membershipManagement.jsp">Memberships</a></li>
                        <li><a href="settings.jsp">Settings</a></li>
                    <% } %>

                    <li><a href="logout.jsp" class="logout">Logout</a></li>
                </ul>
            </nav>
        </aside>
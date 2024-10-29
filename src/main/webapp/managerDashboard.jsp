<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%
    
    String userRole = (String) session.getAttribute("userRole");
    if (userRole == null || !userRole.equals("MANAGER")) {
        response.sendRedirect("login.html");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manager Dashboard | AUCA Library Management System</title>
    <link rel="stylesheet" href="assets/css/dashboard.css">
    <link rel="icon" type="image/x-icon" href="assets/images/flav.png">
</head>
<body>
    <!-- Navbar -->
    <header>
        <nav class="navbar">
            <a class="logo" href="./">
                <img src="assets/images/flav.png" alt="AUCA Library Logo">
                <h1>Auca Library</h1>
            </a>
            <ul class="nav-links">
                <li><a href="managerDashboard.jsp">Dashboard</a></li>
                <li><a href="manageBooks.jsp">Manage Books</a></li>
                <li><a href="viewReports.jsp">View Reports</a></li>
                <li><a href="settings.jsp">Settings</a></li>
            </ul>
            <div class="user-actions">
                <span>Welcome, <%= session.getAttribute("userName") %></span>
                <a href="logout.jsp" class="btn">Logout</a>
            </div>
        </nav>
    </header>

    <!-- Main Content -->
    <main>
        <section class="dashboard">
            <div class="dashboard-container">
                <h2>Manager Dashboard</h2>
                <div class="dashboard-cards">
                    <div class="card">
                        <h3>Total Books</h3>
                        <p>350</p>
                    </div>
                    <div class="card">
                        <h3>Books Borrowed Today</h3>
                        <p>45</p>
                    </div>
                    <div class="card">
                        <h3>Pending Returns</h3>
                        <p>20</p>
                    </div>
                    <div class="card">
                        <h3>Overdue Fines Collected</h3>
                        <p>$150</p>
                    </div>
                </div>

                <h3>Quick Actions</h3>
                <div class="quick-actions">
                    <a href="addBook.jsp" class="btn">Add New Book</a>
                    <a href="manageMembers.jsp" class="btn">Manage Members</a>
                    <a href="generateReport.jsp" class="btn">Generate Report</a>
                </div>
            </div>
        </section>
    </main>

    <!-- Footer -->
    <footer>
        <p>&copy; 2024 AUCA Library Management System | <a href="privacy.html">Privacy Policy</a></p>
    </footer>
</body>
</html>

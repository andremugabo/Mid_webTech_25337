<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" type="text/css" href="../assets/css/admin.css">
    <link rel="stylesheet" href="../assets/css/styles.css">
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
                    <li><a href="manageUsers.jsp">Manage Users</a></li>
                    <li><a href="manageBooks.jsp">Manage Books</a></li>
                    <li><a href="borrowingRecords.jsp">Borrowing Records</a></li>
                    <li><a href="membershipManagement.jsp">Memberships</a></li>
                    <li><a href="reports.jsp">Reports</a></li>
                    <li><a href="settings.jsp">Settings</a></li>
                    <li><a href="logout.jsp" class="logout">Logout</a></li>
                </ul>
            </nav>
        </aside>

        <main>
            <h2>Dashboard Overview</h2>
            <div class="metrics">
                <div class="metric">
                    <h3>Total Books</h3>
                    <p>${totalBooks}</p>
                    <!-- This would be set by your servlet -->
                </div>
                <div class="metric">
                    <h3>Total Users</h3>
                    <p>${totalUsers}</p>
                </div>
                <div class="metric">
                    <h3>Total Borrowings</h3>
                    <p>${totalBorrowings}</p>
                </div>
                <div class="metric">
                    <h3>Overdue Books</h3>
                    <p>${overdueBooks}</p>
                </div>
            </div>

            <h2>Recent Activity</h2>
            <ul class="activity-log">
                <c:forEach var="activity" items="${recentActivities}">
                    <li>${activity.description} on ${activity.date}</li>
                </c:forEach>
            </ul>
        </main>
    </div>

    <footer>
        <p>&copy; 2024 Library Management System. All rights reserved.</p>
    </footer>
</body>
</html>

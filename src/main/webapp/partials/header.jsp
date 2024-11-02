<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="models.RoleType"%>

<%
RoleType userRole = null;
int unreadNotifications = 3;

if (session != null) {
	userRole = (RoleType) session.getAttribute("role");
} else {
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
<link rel="stylesheet" type="text/css" href="assets/css/admin.css">
<link rel="stylesheet" href="assets/css/dashboard.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<link rel="icon" type="image/x-icon" href="assets/images/flav.png">
</head>
<body>
	<header>
		<nav class="navbar">
			<a class="logo" href="./"> <img alt="AUCA Logo"
				src="assets/images/flav.png">
				<h1>Auca Library</h1>
			</a>
		</nav>
	</header>

	<div class="container">
		<aside class="sidebar">
			<nav>
				<ul>
					<li><a href="dashboard.jsp" class="nav-link"><i
							class="fas fa-tachometer-alt"></i> Dashboard</a></li>
					<li><a href="profile.jsp" class="nav-link"><i
							class="fas fa-user"></i> Profile</a></li>

					<%
					if (userRole == RoleType.ADMIN || userRole == RoleType.LIBRARIAN) {
					%>
					<li><a href="user?action=manageUsers" class="nav-link"><i
							class="fas fa-user-cog"></i> Manage Users</a></li>

					<li><a href="manageBooks.jsp" class="nav-link"><i
							class="fas fa-book"></i> Manage Books</a></li>
					<li><a href="locationManagement.jsp" class="nav-link"><i
							class="fas fa-map-marker-alt"></i> Manage Locations</a></li>
					<li><a href="rooms.jsp" class="nav-link"><i
							class="fas fa-door-open"></i> Manage Rooms</a></li>
					<li><a href="shelves.jsp" class="nav-link"><i
							class="fas fa-box"></i> Manage Shelves</a></li>
					<%
					}
					%>

					<%
					if (userRole == RoleType.LIBRARIAN || userRole == RoleType.STUDENT) {
					%>
					<li><a href="borrowingRecords.jsp" class="nav-link"><i
							class="fas fa-book-reader"></i> Borrowing Records</a></li>
					<%
					}
					%>

					<%
					if (userRole == RoleType.ADMIN || userRole == RoleType.MANAGER) {
					%>
					<li><a href="reports.jsp" class="nav-link"><i
							class="fas fa-chart-line"></i> Reports</a></li>
					<%
					}
					%>

					<%
					if (userRole != RoleType.STUDENT) {
					%>
					<li><a href="membershipManagement.jsp" class="nav-link"><i
							class="fas fa-id-card"></i> Memberships</a></li>
					<li><a href="settings.jsp" class="nav-link"><i
							class="fas fa-cog"></i> Settings</a></li>
					<%
					}
					%>

					<li><a href="logout" class="logout"><i
							class="fas fa-sign-out-alt"></i> Logout</a></li>
				</ul>
			</nav>
		</aside>
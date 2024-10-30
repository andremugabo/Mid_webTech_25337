<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="partials/header.jsp"%>

<main>
	<h2>
		Dashboard Overview -
		<%=userRole%></h2>

	<%-- Conditionally displayed content for different roles --%>
	<div class="metrics">
		<%
		if ("ADMIN".equals(userRole) || "MANAGER".equals(userRole)) {
		%>
		<div class="metric">
			<h3>Total Books</h3>
			<p>${totalBooks}</p>
		</div>
		<div class="metric">
			<h3>Total Users</h3>
			<p>${totalUsers}</p>
		</div>
		<%
		}
		%>

		<%
		if ("LIBRARIAN".equals(userRole) || "STUDENT".equals(userRole)) {
		%>
		<div class="metric">
			<h3>Total Borrowings</h3>
			<p>${totalBorrowings}</p>
		</div>
		<%
		}
		%>

		<%
		if ("ADMIN".equals(userRole)) {
		%>
		<div class="metric">
			<h3>Overdue Books</h3>
			<p>${overdueBooks}</p>
		</div>
		<%
		}
		%>
	</div>

	<%
	if ("ADMIN".equals(userRole) || "MANAGER".equals(userRole)) {
	%>
	<h2>Recent Activity</h2>
	<ul class="activity-log">
		<c:forEach var="activity" items="${recentActivities}">
			<li>${activity.description}on ${activity.date}</li>
		</c:forEach>
	</ul>
	<%
	}
	%>
</main>



<%@ include file="partials/footer.jsp"%>

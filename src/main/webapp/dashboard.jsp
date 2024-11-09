<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="partials/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<main>
	<h2>
		Dashboard Overview -
		<%=userRole%></h2>

	<!-- Province Finder Section -->
	<div class="province-finder">
		<h3>Find Province by Phone Number</h3>
		<form action="findProvince" method="get">
			<label for="phoneNumber">Phone Number:</label> <input type="text"
				id="phoneNumber" name="phoneNumber" required>
			<button type="submit">Find Province</button>
		</form>

		<c:if test="${not empty provinceName}">
			<div class="province-result">
				<h4>Province Result:</h4>
				<p>${provinceName}</p>
			</div>
		</c:if>

	</div>

	<!-- Dashboard Content -->
	<div class="dashboard-container">
		<!-- Metrics Section -->
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

		<!-- Chart Section -->
		<div class="chart-section">
			<h3>Library Statistics</h3>
			<div class="charts">
				<%
				if ("ADMIN".equals(userRole) || "MANAGER".equals(userRole)) {
				%>
				<div class="chart-card">
					<h4>Books Distribution</h4>
					<canvas id="booksChart"></canvas>
				</div>
				<div class="chart-card">
					<h4>User Registration Trends</h4>
					<canvas id="usersChart"></canvas>
				</div>
				<%
				}
				%>
				<%
				if ("LIBRARIAN".equals(userRole)) {
				%>
				<div class="chart-card">
					<h4>Borrowing Activities</h4>
					<canvas id="borrowingChart"></canvas>
				</div>
				<%
				}
				%>
			</div>
		</div>
	</div>
</main>

<%@ include file="partials/footer.jsp"%>

<script>
	// Books Chart Configuration
	const ctxBooks = document.getElementById('booksChart').getContext('2d');
	const booksChart = new Chart(ctxBooks, {
		type : 'bar',
		data : {
			labels : [ 'Available', 'Borrowed', 'Overdue' ],
			datasets : [ {
				label : 'Books',
				data : [ 120, 45, 10 ],
				backgroundColor : [ '#1b3058', '#007bff', '#dc3545' ]
			} ]
		},
		options : {
			responsive : true,
			scales : {
				y : {
					beginAtZero : true
				}
			}
		}
	});

	// Users Chart Configuration
	const ctxUsers = document.getElementById('usersChart').getContext('2d');
	const usersChart = new Chart(ctxUsers, {
		type : 'line',
		data : {
			labels : [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul' ],
			datasets : [ {
				label : 'User Registrations',
				data : [ 10, 20, 15, 30, 25, 35, 40 ],
				fill : false,
				borderColor : '#ff9800',
				tension : 0.1
			} ]
		},
		options : {
			responsive : true,
			scales : {
				y : {
					beginAtZero : true
				}
			}
		}
	});
</script>

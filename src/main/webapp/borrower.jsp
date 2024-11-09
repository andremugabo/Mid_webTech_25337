<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="partials/header.jsp"%>

<main>
	<h2>Borrower History</h2>

	<!-- Table for Borrowing History -->
	<table>
		<thead>
			<tr>
				<th>Borrower ID</th>
				<th>Book ID</th>
				<th>Book Title</th>
				<th>Borrow Date</th>
				<th>Due Date</th>
				<th>Return Date</th>
				<th>Fine</th>
				<th>Late Charge Fees</th>
				<th>Status</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="history" items="${borrowHistory}">
				<tr>
					<td>${history.borrowerId}</td>
					<td>${history.book.bookId}</td>
					<td>${history.book.title}</td>
					<td>${history.pickupDate}</td>
					<td>${history.dueDate}</td>
					<td>${history.returnDate != null ? history.returnDate : "Not Returned"}</td>
					<td>${history.fine}</td>
					<td>${history.lateChargeFees}</td>
					<td><c:choose>
							<c:when test="${history.returnDate != null}">
                                Returned
                            </c:when>
							<c:otherwise>
                                Borrowed
                            </c:otherwise>
						</c:choose></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<!-- Summary -->
	<div class="summary">
		<h3>Summary</h3>
		<p>
			<strong>Total Books Borrowed:</strong> ${totalBooksBorrowed}
		</p>
		<p>
			<strong>Total Fines:</strong> ${totalFines}
		</p>
		<p>
			<strong>Total Late Charge Fees:</strong> ${totalLateFees}
		</p>
	</div>

</main>

<%@ include file="partials/footer.jsp"%>

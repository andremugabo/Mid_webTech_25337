<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="partials/header.jsp"%>
<main>
	<h2>Borrower Management</h2>
	<h2>
		Manage Users -
		<%=userRole%>
	</h2>
	<h3>Existing Borrowers</h3>
	<table>
		<thead>
			<tr>
				<th>Book Title</th>
				<th>Reader Username</th>
				<th>Due Date</th>
				<th>Pickup Date</th>
				<th>Fine</th>
				<th>Late Charge Fees</th>
				<th>Return Date</th>
				<c:choose>
					<c:when
						test="${userRole != 'HOD' && userRole != 'DEAN' && userRole != 'REGISTER' && userRole != 'MANAGER'}">
						<th>Update Return Date</th>
					</c:when>
				</c:choose>



			</tr>
		</thead>
		<tbody>
			<c:forEach var="borrower" items="${listBorrowers}">
				<tr>
					<td>${borrower.book.title}</td>
					<td>${borrower.reader.userName}</td>
					<td>${borrower.dueDate}</td>
					<td>${borrower.pickupDate}</td>
					<td>${borrower.fine}</td>
					<td>${borrower.lateChargeFees}</td>
					<td>${borrower.return_date != null ? borrower.return_date : "Not Returned"}</td>
					<c:if
						test="${!(userRole == 'HOD' || userRole == 'DEAN' || userRole == 'REGISTER' || userRole == 'MANAGER')}">
						<td><c:if test="${borrower.return_date == null}">
								<button onclick="openReturnDateModal('${borrower.borrowerId}')"
									class="action-link">Set Return Date</button>
							</c:if></td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div id="returnDateModal" class="modal">
		<div class="modal-content">
			<span class="close-button" onclick="closeModal('returnDateModal')">&times;</span>
			<h3>Update Return Date</h3>
			<form action="borrower?action=updateReturnDate" method="POST">
				<input type="hidden" name="borrowerId" id="borrowerId"> <label
					for="returnDate">Return Date:</label> <input type="date"
					name="returnDate" required>
				<button type="submit" class="btn">Update Return Date</button>
			</form>
		</div>
	</div>
</main>

<%@ include file="partials/footer.jsp"%>

<script>
	// Modal and interaction handling
	function openReturnDateModal(borrowerId) {
		document.getElementById('borrowerId').value = borrowerId;
		document.getElementById('returnDateModal').style.display = "block";
	}

	function closeModal(modalId) {
		document.getElementById(modalId).style.display = "none";
	}

	window.onclick = function(event) {
		const modal = document.getElementById('returnDateModal');
		if (event.target == modal) {
			closeModal('returnDateModal');
		}
	}
</script>

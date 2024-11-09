<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="partials/header.jsp"%>

<main>
	<div class="main_title">
		<h2>Manage Memberships - Librarian</h2>
		<!--   <button onclick="document.getElementById('membershipModal').style.display='block'">
            Add New Membership
        </button> -->
	</div>

	<!-- Modal for Adding New Membership -->
	<div id="membershipModal" class="modal">
		<div class="modal-content">
			<span class="close-button"
				onclick="document.getElementById('membershipModal').style.display='none'">
				&times; </span>
			<h3>Add New Membership</h3>
			<form action="membership" method="POST">
				<input type="hidden" name="action" value="add">

				<!-- Membership Code -->
				<label for="membershipCode">Membership Code:</label> <input
					type="text" id="membershipCode" name="membershipCode" required>

				<!-- Expiring Time with DateTime Picker -->
				<label for="expiringTime">Expiring Time:</label> <input
					type="datetime-local" id="expiringTime" name="expiringTime"
					required>

				<!-- Registration Date with Default Value -->
				<label for="registrationDate">Registration Date:</label> <input
					type="date" id="registrationDate" name="registrationDate"
					value="${currentDate}" required>

				<!-- Membership Status Dropdown -->
				<label for="membershipStatus">Membership Status:</label> <select
					id="membershipStatus" name="membershipStatus" required>
					<option value="APPROVED">Approved</option>
					<option value="PENDING">Pending</option>
					<option value="REJECTED">Rejected</option>
				</select>

				<button type="submit">Add Membership</button>
			</form>
		</div>
	</div>

	<h3>Existing Memberships</h3>
	<table>
		<thead>
			<tr>
				<th>Membership ID</th>
				<th>Membership Code</th>
				<th>Expiring Time</th>
				<th>Registration Date</th>
				<th>Status</th>
				<th>Reader</th>
				<c:if
					test="${!(userRole == 'HOD' || userRole == 'DEAN' || userRole == 'REGISTER' || userRole == 'MANAGER')}">
					<th>Actions</th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="membership" items="${membershipsList}">
				<tr>
					<td>${membership.membershipId}</td>
					<td>${membership.membershipCode}</td>
					<td>${membership.expiringTime}</td>
					<td>${membership.registrationDate}</td>
					<td>${membership.membershipStatus}</td>
					<td><c:out value="${membership.reader.firstName}" /> <c:out
							value="${membership.reader.lastName}" /></td>
					<c:if
						test="${!(userRole == 'HOD' || userRole == 'DEAN' || userRole == 'REGISTER' || userRole == 'MANAGER')}">
						<td><a
							href="librarianMembership?action=edit&membershipId=${membership.membershipId}"
							class="action-link">Approve</a>| <a
							href="librarianMembership?action=delete&membershipId=${membership.membershipId}"
							class="action-link-delete">Reject</a></td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</main>

<%@ include file="partials/footer.jsp"%>

<script>
	// Script to handle modal display
	var modal = document.getElementById('membershipModal');
	window.onclick = function(event) {
		if (event.target == modal) {
			modal.style.display = "none";
		}
	}
</script>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.User"%>
<%@ include file="partials/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<main>
	<h2>
		Manage Users -
		<%=userRole%></h2>

	<h3>
		<button id="openModal">Add New User</button>
	</h3>

	<h3>Existing Users</h3>

	<!-- Display success message if exists -->
	<c:if test="${not empty param.message}">
		<div style="color: green;">
			<strong>${param.message}</strong>
		</div>
	</c:if>
	<c:if test="${not empty sessionScope.message}">
		<p style="color: green;">${sessionScope.message}</p>
		
		<c:remove var="message" scope="session" />
	</c:if>
	<table>
		<thead>
			<tr>
				<th>User ID</th>
				<th>Username</th>
				<th>Email</th>
				<th>Role</th>
				<th>Gender</th>
				<th>Actions</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="user" items="${userList}">
				<tr>
					<td>${user.personId}</td>
					<td>${user.userName}</td>
					<td>${user.phoneNumber}</td>
					<td>${user.role}</td>
					<td>${user.gender}</td>
					<td><a href="#" class="action-link"
						onclick="openUpdateModal('${user.personId}', '${user.userName}', '${user.phoneNumber}', '${user.role}', '${user.gender}')">Update</a>
						| <a href="user?action=deleteUser&userId=${user.personId}"
						class="action-link-delete">Delete</a></td>
				</tr>
			</c:forEach>
			<c:if test="${empty userList}">
				<tr>
					<td colspan="6" style="text-align: center;">No users found.</td>
				</tr>
			</c:if>
		</tbody>
	</table>
</main>

<!-- Modal Structure for Adding New User -->
<div id="userModal" style="display: none;">
	<div class="modal-content">
		<span class="close-button">&times;</span>
		<h2>Add New User</h2>
		<form action="addUser" method="POST">
			<label for="username">Username:</label> <input type="text"
				id="username" name="username" required> <label for="email">Email:</label>
			<input type="email" id="email" name="email" required> <label
				for="role">Role:</label> <select id="role" name="role" required>
				<option value="STUDENT">Student</option>
				<option value="TEACHER">Teacher</option>
				<option value="ADMIN">Admin</option>
			</select> <label for="gender">Gender:</label> <select id="gender"
				name="gender" required>
				<option value="MALE">Male</option>
				<option value="FEMALE">Female</option>
				<option value="OTHER">Other</option>
			</select>
			<button type="submit">Add User</button>
		</form>
	</div>
</div>

<!-- Modal Structure for Updating User -->
<div id="updateModal" style="display: none;">
	<div class="modal-content">
		<span class="close-button" onclick="closeUpdateModal()">&times;</span>
		<h2>Update User</h2>
		<form id="updateForm" action="user?action=updateUser" method="POST">
			<input type="hidden" id="updateUserId" name="userId"> <label
				for="updateUsername">Username:</label> <input type="text"
				id="updateUsername" name="username" required> <label
				for="updatePhone">Phone:</label> <input type="text" id="updatePhone"
				name="phoneNumber" required> <label for="updateRole">Role:</label>
			<select id="updateRole" name="role" required>
				<option value="STUDENT">Student</option>
				<option value="MANAGER">Manager</option>
				<option value="TEACHER">Teacher</option>
				<option value="LIBRARIAN">Librarian</option>
				<option value="DEAN">Dean</option>
				<option value="HOD">Head of Department</option>
				<option value="REGISTRAR">Registrar</option>
				<option value="ADMIN">Admin</option>
			</select> <label for="updateGender">Gender:</label> <select id="updateGender"
				name="gender" required>
				<option value="MALE">Male</option>
				<option value="FEMALE">Female</option>

			</select>
			<button type="submit">Update User</button>
		</form>
	</div>
</div>

<%@ include file="partials/footer.jsp"%>
<script>
	// JavaScript to handle modal display for Add New User
	document.getElementById("openModal").onclick = function() {
		document.getElementById("userModal").style.display = "block";
	};

	document.querySelector(".close-button").onclick = function() {
		document.getElementById("userModal").style.display = "none";
	};

	// Close the modal if the user clicks outside of it
	window.onclick = function(event) {
		if (event.target == document.getElementById("userModal")) {
			document.getElementById("userModal").style.display = "none";
		}
	};

	// Function to open update modal and populate user data
	function openUpdateModal(userId, username, phoneNumber, role, gender) {
		document.getElementById("updateUserId").value = userId;
		document.getElementById("updateUsername").value = username;
		document.getElementById("updatePhone").value = phoneNumber;
		document.getElementById("updateRole").value = role;
		document.getElementById("updateGender").value = gender;

		document.getElementById("updateModal").style.display = "block";
	}

	// Function to close update modal
	function closeUpdateModal() {
		document.getElementById("updateModal").style.display = "none";
	}
</script>

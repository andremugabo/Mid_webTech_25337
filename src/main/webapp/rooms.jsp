<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.Room"%>
<%@ include file="partials/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<main>
	<div class="main_title">
		<h2>Manage Rooms</h2>

		<c:if
			test="${!(userRole == 'HOD' || userRole == 'DEAN' || userRole == 'REGISTER' || userRole == 'MANAGER')}">
			<button id="openModal">Add New Room</button>
		</c:if>


	</div>

	<div class="room-finder">
        <h3>Find Total Books in a Room</h3>
        <form action="getBooksInRoom" method="get">
            <label for="roomId">Room  ID:</label>
            <input type="text" id="roomId" name="roomId" required>
            <button type="submit">Find Total Books</button>
        </form>

        <c:if test="${not empty totalBooks}">
            <div class="book-result">
                <h4>Total Books in Room:</h4>
                <p>${totalBooks}</p>
            </div>
        </c:if>
    </div>
	
	<h3>Existing Rooms</h3>
	<table>
		<thead>
			<tr>
				<th>Room ID</th>
				<th>Room Code</th>
				<c:if
					test="${!(userRole == 'HOD' || userRole == 'DEAN' || userRole == 'REGISTER' || userRole == 'MANAGER')}">
					<th>Actions</th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="room" items="${rooms}">
				<tr>
					<td>${room.roomId}</td>
					<td>${room.roomCode}</td>
					<c:if
						test="${!(userRole == 'HOD' || userRole == 'DEAN' || userRole == 'REGISTER' || userRole == 'MANAGER')}">
						<td><a href="editRoom?roomId=${room.roomId}"
							class="action-link">Edit</a> | <a
							href="deleteRoom?roomId=${room.roomId}"
							class="action-link-delete"
							onclick="return confirm('Are you sure?')">Delete</a></td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<!-- Modal for adding a new room -->
	<div id="modal" style="display: none;">
		<div class="modal-content">
			<span class="close"
				onclick="document.getElementById('modal').style.display='none'">&times;</span>
			<h3>Add New Room</h3>
			<form action="rooms" method="post">
				<input type="text" name="roomCode" placeholder="Room Code" required>
				<button type="submit">Save Room</button>
			</form>
		</div>
	</div>
</main>

<%@ include file="partials/footer.jsp"%>

<script>
	// JavaScript to open the modal
	document.getElementById("openModal").onclick = function() {
		document.getElementById("modal").style.display = "block";
	};
</script>



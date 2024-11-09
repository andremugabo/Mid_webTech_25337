<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.Shelf"%>
<%@ page import="models.Room"%>
<%@ page import="java.util.List"%>
<%@ include file="partials/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<main>
	<div class="main_title">
		<h2>
			Manage Users -
			<%=userRole%>
		</h2>
		<c:if
			test="${!(userRole == 'HOD' || userRole == 'DEAN' || userRole == 'REGISTER' || userRole == 'MANAGER')}">
			<button id="createShelfBtn">Create Shelf</button>
		</c:if>

	</div>
	<h2>All Shelves</h2>
	<table>
		<thead>
			<tr>
				<th>Book Category</th>
				<th>Initial Stock</th>
				<th>Available Stock</th>
				<th>Borrowed Book</th>
				<c:if
					test="${!(userRole == 'HOD' || userRole == 'DEAN' || userRole == 'REGISTER' || userRole == 'MANAGER')}">
					<th>Actions</th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="shelf" items="${shelfList}">
				<tr>
					<td>${shelf.bookCategory}</td>
					<td>${shelf.initialStock}</td>
					<td>${shelf.availableStock}</td>
					<td>${shelf.borrowedNumber}</td>
					<c:if
						test="${!(userRole == 'HOD' || userRole == 'DEAN' || userRole == 'REGISTER' || userRole == 'MANAGER')}">
						<td><a href="shelves?action=edit&shelfId=${shelf.shelfId}"
							class="action-link">Edit</a> <a class="action-link-delete"
							data-shelf-id="${shelf.shelfId}">Delete</a></td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<!-- Create Shelf Modal -->
	<div id="createShelfModal" class="modal">
		<div class="modal-content">
			<span class="close">&times;</span>
			<h3>Create New Shelf</h3>
			<form id="createShelfForm" method="post" action="shelves">
				<label for="shelfCode">Shelf Code:</label> <input type="text"
					id="shelfCode" name="shelfCode" required> <label
					for="bookCategory">Book Category:</label> <input type="text"
					id="bookCategory" name="bookCategory" required> <label
					for="initialStock">Initial Stock:</label> <input type="number"
					id="initialStock" name="initialStock" required min="1">

				<!-- Room Selection -->
				<label for="roomId">Room:</label> <select id="roomId" name="roomId"
					required>
					<option value="" selected disabled>Choose a Room</option>
					<c:forEach var="room" items="${roomList}">

						<option value="${room.roomId}">${room.roomCode}</option>
					</c:forEach>
				</select>

				<button type="submit">Create Shelf</button>
				<button type="button" id="cancelCreate">Cancel</button>
			</form>
		</div>
	</div>

	<!-- Delete Confirmation Modal -->
	<div id="deleteModal" class="modal">
		<div class="modal-content">
			<span class="close">&times;</span>
			<h3>Are you sure you want to delete this shelf?</h3>
			<form id="deleteForm" method="post" action="shelves">
				<input type="hidden" name="shelfId" id="shelfId" value="">
				<button type="submit">Yes, Delete</button>
				<button type="button" id="cancelDelete">Cancel</button>
			</form>
		</div>
	</div>
</main>

<%@ include file="partials/footer.jsp"%>

<script>
    // Create Shelf Modal
    const createShelfBtn = document.getElementById('createShelfBtn');
    const createShelfModal = document.getElementById('createShelfModal');
    const closeCreateShelfModal = createShelfModal.querySelector('.close');
    const cancelCreate = document.getElementById('cancelCreate');

    createShelfBtn.onclick = () => {
        createShelfModal.style.display = 'block';
    };

    closeCreateShelfModal.onclick = () => {
        createShelfModal.style.display = 'none';
    };

    cancelCreate.onclick = () => {
        createShelfModal.style.display = 'none';
    };

    // Delete Confirmation Modal
    const deleteButtons = document.querySelectorAll('.delete-btn');
    const deleteModal = document.getElementById('deleteModal');
    const closeDeleteModal = deleteModal.querySelector('.close');
    const cancelDelete = document.getElementById('cancelDelete');
    const shelfIdInput = document.getElementById('shelfId');

    deleteButtons.forEach(button => {
        button.addEventListener('click', () => {
            shelfIdInput.value = button.getAttribute('data-shelf-id');
            deleteModal.style.display = 'block';
        });
    });

    closeDeleteModal.onclick = () => {
        deleteModal.style.display = 'none';
    };

    cancelDelete.onclick = () => {
        deleteModal.style.display = 'none';
    };

    window.onclick = (event) => {
        if (event.target == createShelfModal) {
            createShelfModal.style.display = 'none';
        }
        if (event.target == deleteModal) {
            deleteModal.style.display = 'none';
        }
    };
</script>

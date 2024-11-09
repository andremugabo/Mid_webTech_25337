<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="partials/header.jsp"%>


<main>
	<div class="main_title">
		<h2>Borrow Books</h2>
		<p class="instruction-text">Select a book from the list below to
			borrow it.</p>

	</div>

	<!-- Genre Filter Section -->
	<div class="filter-section">
		<label for="genreFilter">Filter by Genre:</label> <select
			id="genreFilter" name="genreFilter" onchange="filterByGenre()">
			<option value="">-- All Genres --</option>
			<c:forEach var="genre" items="${shelfList}">
				<option value="${genre.shelfId}">${genre.bookCategory}</option>
			</c:forEach>
		</select>
	</div>

	<!-- Table for Available Books -->
	<h3>Available Books</h3>
	<table id="booksTable">
		<thead>
			<tr>
				<th>Book ID</th>
				<th>Title</th>
				<th>Author</th>
				<th>Status</th>
				<th>Genre</th>
				<c:if
					test="${!(userRole == 'HOD' || userRole == 'DEAN' || userRole == 'REGISTER' || userRole == 'MANAGER')}">
					<th>Actions</th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="book" items="${bookList}">
				<tr data-genre="${book.shelf.shelfId}">
					<td>${book.bookId}</td>
					<td>${book.title}</td>
					<td>${book.publisherName}</td>
					<td><c:choose>
							<c:when test="${book.bookStatus == 'BORROWED'}">
								<span class="status-borrowed">${book.bookStatus}</span>
							</c:when>
							<c:when test="${book.bookStatus == 'RESERVED'}">
								<span class="status-reserved">${book.bookStatus}</span>
							</c:when>
							<c:when test="${book.bookStatus == 'AVAILABLE'}">
								<span class="status-available">${book.bookStatus}</span>
							</c:when>
							<c:otherwise>
								<span>${book.bookStatus}</span>
							</c:otherwise>
						</c:choose></td>

					<td><c:forEach var="shelf" items="${shelfList}">
							<c:if test="${shelf.shelfId == book.shelf.shelfId}">
                                ${shelf.bookCategory} 
                            </c:if>
						</c:forEach></td>
					<c:if
						test="${!(userRole == 'HOD' || userRole == 'DEAN' || userRole == 'REGISTER' || userRole == 'MANAGER')}">
						<td><c:choose>
								<c:when test="${book.bookStatus == 'AVAILABLE'}">
									<button onclick="openBorrowModal('${book.bookId}')"
										class="action-link">Borrow</button>
								</c:when>
								<c:otherwise>
									<span
										style="
            padding: 3px;
            color: white;
            border-radius: 5px;
            font-weight: bold;
            font-size:8px;
            background-color: 
                <c:choose>
                    <c:when test="${book.bookStatus == 'BORROWED'}">red</c:when>
                    <c:when test="${book.bookStatus == 'RESERVED'}">orange</c:when>
                    <c:otherwise>grey</c:otherwise>
                </c:choose>;
        ">
										${book.bookStatus} </span>
								</c:otherwise>
							</c:choose></td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<!-- Modal for Borrowing Book -->
	<div id="borrowModal" class="modal">
		<div class="modal-content">
			<span class="close-button" onclick="closeBorrowModal()">&times;</span>
			<h3>Borrow Book</h3>
			<form action="borrower" method="POST">
				<input type="hidden" name="action" value="borrow"> <input
					type="hidden" id="selectedBookId" name="bookId"
					value="${book.bookId}"> <input type="hidden"
					name="readerId" value="<%=userIdString%>"><label
					for="pickupDate">Pick-Up Date:</label> <input type="date"
					id="pickupDate" name="pickupDate" required> <label
					for="returnDate">Return Date:</label> <input type="date"
					id="returnDate" name="returnDate" required>

				<button type="submit">Borrow Book</button>
			</form>
		</div>
	</div>

</main>

<%@ include file="partials/footer.jsp"%>

<script>
	// Function to filter books by selected genre
	function filterByGenre() {
		var genreFilter = document.getElementById('genreFilter').value;
		var rows = document.querySelectorAll('#booksTable tbody tr');

		rows.forEach(function(row) {
			var bookGenre = row.getAttribute('data-genre');

			if (genreFilter === '' || bookGenre === genreFilter) {
				row.style.display = '';
			} else {
				row.style.display = 'none';
			}
		});
	}

	// Script to handle modal display
	var modal = document.getElementById('borrowModal');

	// Function to open the borrow modal
	function openBorrowModal(bookId) {
		document.getElementById('selectedBookId').value = bookId;
		modal.style.display = "block";
	}

	// Function to close the borrow modal
	function closeBorrowModal() {
		modal.style.display = "none";
	}

	// Close modal if clicked outside the modal content
	window.onclick = function(event) {
		if (event.target == modal) {
			closeBorrowModal();
		}
	}
</script>

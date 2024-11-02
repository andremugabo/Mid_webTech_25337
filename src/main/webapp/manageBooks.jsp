<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="models.Book"%>
<%@ page import="java.util.List"%>
<%@ include file="partials/header.jsp"%>

<main>
    <h2>Manage Books - <%= userRole %></h2>

    <h3>
        <button id="openModal">Add New Book</button>
    </h3>

    <!-- Modal Structure -->
    <div id="bookModal" class="modal">
        <div class="modal-content">
            <span class="close-button">&times;</span>
            <h3>Add New Book</h3>
            <form action="book" method="POST" id="bookForm">
                <label for="title">Title:</label>
                <input type="text" id="title" name="title" required>

                <label for="isbnCode">ISBN Code:</label>
                <input type="text" id="isbnCode" name="isbnCode" required>

                <label for="publisherName">Publisher Name:</label>
                <input type="text" id="publisherName" name="publisherName" required>

                <label for="publicationYear">Publication Year:</label>
                <input type="date" id="publicationYear" name="publicationYear" required>

                <label for="edition">Edition:</label>
                <input type="number" id="edition" name="edition" min="1" required>

                <label for="bookStatus">Book Status:</label>
                <select id="bookStatus" name="bookStatus" required>
                    <option value="">Select Status</option>
                    <option value="AVAILABLE">Available</option>
                    <option value="CHECKED_OUT">Checked Out</option>
                    <option value="RESERVED">Reserved</option>
                </select>

                <label for="shelfId">Shelf:</label>
                <select id="shelfId" name="shelfId" required>
                    <option value="">Select Shelf</option>
                    <!-- Populate options from database or server-side code -->
                </select>

                <label for="image">Image URL:</label>
                <input type="text" id="image" name="image">

                <button type="submit">Add Book</button>
            </form>
        </div>
    </div>

    <p>Number of Books: ${listBooks.size()}</p>

    <h3>Existing Books</h3>
    <table>
        <thead>
            <tr>
                <th>Book ID</th>
                <th>Title</th>
                <th>ISBN Code</th>
                <th>Publisher</th>
                <th>Publication Year</th>
                <th>Edition</th>
                <th>Status</th>
                <th>Shelf ID</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="book" items="${listBooks}">
                <tr>
                    <td>${book.bookId}</td>
                    <td>${book.title}</td>
                    <td>${book.isbnCode}</td>
                    <td>${book.publisherName}</td>
                    <td>${book.publicationYear}</td>
                    <td>${book.edition}</td>
                    <td>${book.bookStatus}</td>
                    <td><c:if test="${book.shelf != null}">${book.shelf.shelfId}</c:if></td>
                    <td>
                        <a href="updateBook?bookId=${book.bookId}" class="action-link">Update</a> |
                        <a href="deleteBook?bookId=${book.bookId}" class="action-link-delete">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</main>

<%@ include file="partials/footer.jsp"%>

<!-- JavaScript for Modal and Form Logic -->
<script>
    const modal = document.getElementById("bookModal");
    const openModalButton = document.getElementById("openModal");
    const closeModalButton = document.querySelector(".close-button");

    openModalButton.addEventListener("click", function() {
        modal.style.display = "block";
    });

    closeModalButton.addEventListener("click", function() {
        modal.style.display = "none";
    });

    window.addEventListener("click", function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    });
</script>

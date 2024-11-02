<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="partials/header.jsp" %>

<main>
    <h2>Borrower Management</h2>
    <button onclick="document.getElementById('borrowerModal').style.display='block'">Add New Borrower</button>

    <!-- Modal for Adding New Borrower -->
    <div id="borrowerModal" class="modal">
        <div class="modal-content">
            <span class="close-button" onclick="document.getElementById('borrowerModal').style.display='none'">&times;</span>
            <h3>Add New Borrower</h3>
            <form action="borrower" method="POST">
                <input type="hidden" name="action" value="add">
                <label>Book ID:</label>
                <input type="text" name="bookId" required>
                <label>Reader ID:</label>
                <input type="text" name="readerId" required>
                <label>Due Date:</label>
                <input type="date" name="dueDate" required>
                <label>Pickup Date:</label>
                <input type="date" name="pickupDate" required>
                <label>Fine:</label>
                <input type="number" name="fine">
                <label>Late Charge Fees:</label>
                <input type="number" name="lateChargeFees">
                <button type="submit">Add Borrower</button>
            </form>
        </div>
    </div>

    <h3>Existing Borrowers</h3>
    <table>
        <thead>
            <tr>
                <th>Borrower ID</th>
                <th>Book ID</th>
                <th>Reader ID</th>
                <th>Due Date</th>
                <th>Pickup Date</th>
                <th>Fine</th>
                <th>Late Charge Fees</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="borrower" items="${listBorrowers}">
                <tr>
                    <td>${borrower.borrowerId}</td>
                    <td>${borrower.book.bookId}</td>
                    <td>${borrower.reader.userId}</td>
                    <td>${borrower.dueDate}</td>
                    <td>${borrower.pickupDate}</td>
                    <td>${borrower.fine}</td>
                    <td>${borrower.lateChargeFees}</td>
                    <td>
                        <a href="borrower?action=edit&borrowerId=${borrower.borrowerId}">Edit</a>
                        <a href="borrower?action=delete&borrowerId=${borrower.borrowerId}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</main>

<%@ include file="partials/footer.jsp" %>
<script>
// Script to handle modal display
var modal = document.getElementById('borrowerModal');
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
</script>

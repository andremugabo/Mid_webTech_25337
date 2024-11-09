<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="partials/header.jsp"%>

<main>
    <div class="main_title">
        <h2>Manage Membership Types</h2>
        <button onclick="document.getElementById('membershipTypeModal').style.display='block'">Add New Membership Type</button>
    </div>

    <!-- Modal for Adding New Membership Type -->
    <div id="membershipTypeModal" class="modal">
        <div class="modal-content">
            <span class="close-button" onclick="document.getElementById('membershipTypeModal').style.display='none'">&times;</span>
            <h3>Add New Membership Type</h3>
            <form action="membershipType" method="POST">
                <input type="hidden" name="action" value="add">
                
                <label>Membership Name:</label>
                <select name="membershipName" required>
                    <option value="" disabled selected>Select Membership Type</option>
                    <option value="Gold">Gold</option>
                    <option value="Silver">Silver</option>
                    <option value="Striver">Striver</option>
                </select>
                
                <label>Maximum Books:</label>
                <input type="number" name="maxBooks" min="1" required>
                
                <label>Price:</label>
                <input type="number" name="price" min="0" required>
                
                <button type="submit">Add Membership Type</button>
            </form>
        </div>
    </div>

    <!-- Table for Existing Membership Types -->
    <h3>Existing Membership Types</h3>
    <table>
        <thead>
            <tr>
                <th>Membership Type ID</th>
                <th>Membership Name</th>
                <th>Max Books</th>
                <th>Price</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="membershipType" items="${listMembershipTypes}">
                <tr>
                    <td>${membershipType.membershipTypeId}</td>
                    <td>${membershipType.membershipName}</td>
                    <td>${membershipType.maxBooks}</td>
                    <td>${membershipType.price}</td>
                    <td>
                        <a href="membershipType?action=edit&membershipTypeId=${membershipType.membershipTypeId}" class="action-link">Edit</a> |
                        <a href="membershipType?action=delete&membershipTypeId=${membershipType.membershipTypeId}" class="action-link-delete">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</main>

<%@ include file="partials/footer.jsp"%>

<script>
    // Script to handle modal display
    var modal = document.getElementById('membershipTypeModal');
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
</script>

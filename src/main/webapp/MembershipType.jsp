<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="partials/header.jsp" %>

<main>
    <h2>Membership Type Management</h2>
    <button onclick="document.getElementById('membershipTypeModal').style.display='block'">Add New Membership Type</button>

    <div id="membershipTypeModal" class="modal">
        <div class="modal-content">
            <span class="close-button" onclick="document.getElementById('membershipTypeModal').style.display='none'">&times;</span>
            <h3>Add New Membership Type</h3>
            <form action="membershipType" method="POST">
                <input type="hidden" name="action" value="add">
                <label>Membership Name:</label><input type="text" name="membershipName" required>
                <label>Max Books:</label><input type="number" name="maxBooks" required>
                <label>Price:</label><input type="number" name="price" required>
                <button type="submit">Add Membership Type</button>
            </form>
        </div>
    </div>

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
                        <form action="membershipType" method="POST" style="display:inline;">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="membershipTypeId" value="${membershipType.membershipTypeId}">
                            <button type="submit">Delete</button>
                        </form>
                        <button onclick="document.getElementById('membershipTypeModal').style.display='block'; populateEditForm('${membershipType.membershipTypeId}', '${membershipType.membershipName}', ${membershipType.maxBooks}, ${membershipType.price})">Edit</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</main>

<script>
function populateEditForm(id, name, maxBooks, price) {
    document.querySelector('input[name="membershipTypeId"]').value = id;
    document.querySelector('input[name="membershipName"]').value = name;
    document.querySelector('input[name="maxBooks"]').value = maxBooks;
    document.querySelector('input[name="price"]').value = price;
    document.querySelector('input[name="action"]').value = 'update';
}
</script>

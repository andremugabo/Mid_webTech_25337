<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="partials/header.jsp"%>

<main>
    <div class="main_title">
        <h2>Manage Users - <%=userRole%></h2>
        <button id="openMembershipModal">Add New Membership Type</button>
    </div>

    <h2>Membership Type Management</h2>

    <!-- Modal Structure for Adding/Editing Membership Type -->
    <div id="membershipTypeModal" class="modal">
        <div class="modal-content">
            <span class="close-button">&times;</span>
            <h3 id="modalTitle">Add New Membership Type</h3>
            <form action="membershipType" method="POST" id="membershipForm">
                <input type="hidden" name="action" value="add">
                <input type="hidden" name="membershipTypeId" id="membershipTypeId">
                
                <label for="membershipName">Membership Name:</label>
                <input type="text" id="membershipName" name="membershipName" required>

                <label for="maxBooks">Max Books:</label>
                <input type="number" id="maxBooks" name="maxBooks" required>

                <label for="price">Price:</label>
                <input type="number" id="price" name="price" required>
                
                <button type="submit">Save Membership Type</button>
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
                        <a href="javascript:void(0);" onclick="populateEditForm('${membershipType.membershipTypeId}', '${membershipType.membershipName}', ${membershipType.maxBooks}, ${membershipType.price});" class="action-link">Edit</a> |
                        <a href="deleteMembershipType?membershipTypeId=${membershipType.membershipTypeId}" class="action-link-delete">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</main>

<!-- JavaScript for Modal and Form Logic -->
<script>
    const modal = document.getElementById("membershipTypeModal");
    const openModalButton = document.getElementById("openMembershipModal");
    const closeModalButton = document.querySelector(".close-button");
    const modalTitle = document.getElementById("modalTitle");
    const membershipForm = document.getElementById("membershipForm");

    openModalButton.addEventListener("click", function() {
        resetForm();
        modal.style.display = "block";
        modalTitle.innerText = "Add New Membership Type";
    });

    closeModalButton.addEventListener("click", function() {
        modal.style.display = "none";
    });

    window.addEventListener("click", function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    });

    function populateEditForm(id, name, maxBooks, price) {
        document.getElementById("membershipTypeId").value = id;
        document.getElementById("membershipName").value = name;
        document.getElementById("maxBooks").value = maxBooks;
        document.getElementById("price").value = price;
        document.querySelector('input[name="action"]').value = 'update';
        modalTitle.innerText = "Edit Membership Type";
        modal.style.display = "block";
    }

    function resetForm() {
        membershipForm.reset();
        document.getElementById("membershipTypeId").value = '';
        document.querySelector('input[name="action"]').value = 'add';
    }
</script>
<%@ include file="partials/footer.jsp"%>

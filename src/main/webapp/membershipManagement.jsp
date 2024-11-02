<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="partials/header.jsp" %>

<main>
    <h2>Membership Management</h2>
    <button onclick="document.getElementById('membershipModal').style.display='block'">Add New Membership</button>

    <div id="membershipModal" class="modal">
        <div class="modal-content">
            <span class="close-button" onclick="document.getElementById('membershipModal').style.display='none'">&times;</span>
            <h3>Add New Membership</h3>
            <form action="membership" method="POST">
                <input type="hidden" name="action" value="add">
                <label>Membership Code:</label>
                <input type="text" name="membershipCode" required>
                <label>Expiring Time:</label>
                <input type="date" name="expiringTime" required>
                <label>Reader ID:</label>
                <input type="text" name="readerId" required>
                <label>Membership Status:</label>
                <select name="membershipStatus" required>
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
                <th>Reader ID</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="membership" items="${listMemberships}">
                <tr>
                    <td>${membership.membershipId}</td>
                    <td>${membership.membershipCode}</td>
                    <td>${membership.expiringTime}</td>
                    <td>${membership.registrationDate}</td>
                    <td>${membership.membershipStatus}</td>
                    <td>${membership.reader.userId}</td>
                    <td>
                        <a href="membership?action=edit&membershipId=${membership.membershipId}">Edit</a>
                        <a href="membership?action=delete&membershipId=${membership.membershipId}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</main>

<%@ include file="partials/footer.jsp" %>
<script>
// Script to handle modal display
var modal = document.getElementById('membershipModal');
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
</script>

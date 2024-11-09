<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="partials/header.jsp" %>

<main>
    <div class="main_title">
        <h2>Your Membership Details</h2>
    </div>

    <!-- Membership Form Section -->
    <div class="membership-form-container">
        <form action="memberMembership" method="POST" class="membership-form">
            <input type="hidden" name="action" value="${empty myMembership ? 'apply' : 'update'}">
            <input type="hidden" name="userId" value="<%= userIdString %>">

            <!-- Membership Type Selection -->
            <label for="membershipType">Select Membership Type:</label>
            <select id="membershipType" name="membershipTypeId" required>
                <option value="" selected="selected" disabled="disabled">Choose Membership Type</option>
                <c:forEach var="type" items="${memberTypeList}">
                    <option value="${type.membershipTypeId}">
                        ${type.membershipName}
                    </option>
                </c:forEach>
            </select>

            <!-- Expiring Time Input -->
            <label for="expiringTime">Expiring Time:</label>
            <input type="datetime-local" id="expiringTime" name="expiringTime" required>

            <!-- Submit Button -->
            <button type="submit" class="submit-button">
                ${empty myMembership ? 'Apply for Membership' : 'Update Membership Type'}
            </button>
        </form>
    </div>

    <!-- Membership Status Section (Displayed if user has an existing membership) -->
    <c:if test="${!empty myMembership}">
        <h3>Your Current Membership Status</h3>
        <table class="membership-table">
            <thead>
                <tr>
                    <th>Membership ID</th>
                    <th>Membership Code</th>
                    <th>Expiring Time</th>
                    <th>Registration Date</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>${myMembership.membershipId}</td>
                    <td>${myMembership.membershipCode}</td>
                    <td>${myMembership.expiringTime}</td>
                    <td>${myMembership.registrationDate}</td>
                    <td>${myMembership.membershipStatus}</td>
                </tr>
            </tbody>
        </table>
    </c:if>
</main>

<%@ include file="partials/footer.jsp" %>

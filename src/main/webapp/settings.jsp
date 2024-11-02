<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="partials/header.jsp" %>

<main>
    <h2>Settings</h2>

    <form action="settings" method="POST">
        <input type="hidden" name="action" value="updateProfile">
        <h3>User Profile Settings</h3>
        <label>Name:</label><input type="text" name="name" required>
        <label>Email:</label><input type="email" name="email" required>
        <label>Phone Number:</label><input type="tel" name="phone">
        <label>Profile Picture:</label><input type="file" name="profilePic">
        <button type="submit">Update Profile</button>
    </form>

    <form action="settings" method="POST">
        <input type="hidden" name="action" value="updateNotifications">
        <h3>Notification Settings</h3>
        <label>Email Notifications:</label>
        <input type="checkbox" name="emailNotifications" checked>
        <label>SMS Notifications:</label>
        <input type="checkbox" name="smsNotifications">
        <button type="submit">Update Notifications</button>
    </form>

    <form action="settings" method="POST">
        <input type="hidden" name="action" value="updateMembership">
        <h3>Membership Settings</h3>
        <label>Default Membership Type:</label>
        <select name="defaultMembershipType">
            <option value="basic">Basic</option>
            <option value="premium">Premium</option>
            <!-- Add more options based on your membership types -->
        </select>
        <button type="submit">Update Membership Settings</button>
    </form>

    <!-- Add more forms for additional settings as needed -->

    <c:if test="${param.success != null}">
        <div class="success-message">Settings updated successfully!</div>
    </c:if>
</main>

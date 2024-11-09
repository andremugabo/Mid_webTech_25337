<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="partials/header.jsp" %>

<main>
<div class="main_title">


</div>
    <h2>Membership Report</h2>

    <table>
        <thead>
            <tr>
                <th>Membership Type ID</th>
                <th>Membership Name</th>
                <th>Max Books</th>
                <th>Price</th>
                <th>Membership Count</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="membershipType" items="${membershipTypes}">
                <tr>
                    <td>${membershipType.membershipTypeId}</td>
                    <td>${membershipType.membershipName}</td>
                    <td>${membershipType.maxBooks}</td>
                    <td>${membershipType.price}</td>
                    <td>${membershipType.memberships.size()}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <form action="report" method="POST">
        <input type="hidden" name="action" value="generatePDF">
        <button type="submit">Generate PDF Report</button>
    </form>
</main>
<%@ include file="partials/footer.jsp"%>
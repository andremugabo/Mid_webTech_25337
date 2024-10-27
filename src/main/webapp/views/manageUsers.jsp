<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.models.User" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Users</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>

    <h1>Manage Users</h1>
    
    <table class="table">
        <thead>
            <tr>
                <th scope="col">User ID</th>
                <th scope="col">Username</th>
                <th scope="col">Email</th>
                <th scope="col">Role</th>
                <th scope="col">Actions</th>
            </tr>
        </thead>
        <tbody>
            <%
                /* Sample data; replace with your actual data source
                List<User> userList = new ArrayList<>(); // Replace with actual data retrieval logic
                // Populate userList with User objects from your data source
                
                for (User user : userList) {
            %>
                <tr>
                    <td data-label="User ID"><%= user.getUserId() %></td>
                    <td data-label="Username"><%= user.getUsername() %></td>
                    <td data-label="Email"><%= user.getEmail() %></td>
                    <td data-label="Role"><%= user.getRole() %></td>
                    <td data-label="Actions">
                        <a href="editUser?id=<%= user.getUserId() %>" class="btn-edit">Edit</a>
                        <a href="deleteUser?id=<%= user.getUserId() %>" class="btn-delete">Delete</a>
                    </td>
                </tr>
            <%
                }
                */
            %>
        </tbody>
    </table>

</body>
</html>

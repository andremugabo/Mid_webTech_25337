<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="models.Room"%>
<%@ include file="partials/header.jsp" %>

<main>
    <h2>Manage Rooms</h2>

    <h3><button id="openModal">Add New Room</button></h3>

    <h3>Existing Rooms</h3>
    <table>
        <thead>
            <tr>
                <th>Room ID</th>
                <th>Room Code</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="room" items="${rooms}">
                <tr>
                    <td>${room.roomId}</td>
                    <td>${room.roomCode}</td>
                    <td>
                        <a href="editRoom?roomId=${room.roomId}" class="action-link">Edit</a>
                        |
                        <a href="deleteRoom?roomId=${room.roomId}" class="action-link-delete" onclick="return confirm('Are you sure?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- Modal for adding a new room -->
    <div id="modal" style="display: none;">
        <div class="modal-content">
            <span class="close" onclick="document.getElementById('modal').style.display='none'">&times;</span>
            <h3>Add New Room</h3>
            <form action="rooms" method="post">
                <input type="text" name="roomCode" placeholder="Room Code" required>
                <button type="submit">Save Room</button>
            </form>
        </div>
    </div>
</main>

<%@ include file="partials/footer.jsp" %>

<script>
    // JavaScript to open the modal
    document.getElementById("openModal").onclick = function() {
        document.getElementById("modal").style.display = "block";
    };
</script>



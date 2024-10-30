<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="models.Location"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.UUID"%>
<%@ include file="partials/header.jsp"%>

<main>
    <h2>Manage Locations - <%= userRole %></h2>

    <h3><button id="openModal">Add New Location</button></h3>

    <!-- Modal Structure -->
    <div id="locationModal" class="modal">
        <div class="modal-content">
            <span class="close-button">&times;</span>
            <h3>Add New Location</h3>
            <form action="location" method="post">
                <label for="locationCode">Location Code:</label>
                <input type="text" id="locationCode" name="locationCode" required>

                <label for="locationName">Location Name:</label>
                <input type="text" id="locationName" name="locationName" required>

                <label for="locationType">Location Type:</label>
                <select id="locationType" name="locationType" required>
                    <option value="">Select Type</option>
                    <option value="PROVINCE">Province</option>
                    <option value="DISTRICT">District</option>
                    <option value="SECTOR">Sector</option>
                    <option value="CELL">Cell</option>
                    <option value="VILLAGE">Village</option>
                </select>

                <label for="parentId">Parent Location ID (Optional):</label>
                <input type="text" id="parentId" name="parentId" placeholder="Enter UUID if applicable">

                <button type="submit">Add Location</button>
            </form>
        </div>
    </div>

    <h3>Existing Locations</h3>
    <table>
        <thead>
            <tr>
                <th>Location ID</th>
                <th>Location Code</th>
                <th>Location Name</th>
                <th>Location Type</th>
                <th>Parent Location ID</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="location" items="${locationList}">
                <tr>
                    <td>${location.locationId}</td>
                    <td>${location.locationCode}</td>
                    <td>${location.locationName}</td>
                    <td>${location.locationType}</td>
                    <td>
                        <c:if test="${location.parentLocation != null}">
                            ${location.parentLocation.locationId}
                        </c:if>
                    </td>
                    <td>
                        <!-- Links for Update and Delete actions -->
                        <a href="updateLocation?locationId=${location.locationId}" class="action-link">Update</a>
                        |
                        <a href="deleteLocation?locationId=${location.locationId}" class="action-link">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
</main>

<%@ include file="partials/footer.jsp"%>

<!-- Include JavaScript for Modal -->
<script>
   
    const modal = document.getElementById("locationModal");
    
    const openModalButton = document.getElementById("openModal");
    
    const closeModalButton = document.querySelector(".close-button");

    
    openModalButton.addEventListener("click", function() {
        modal.style.display = "block";
    });

    
    closeModalButton.addEventListener("click", function() {
        modal.style.display = "none";
    });

    
    window.addEventListener("click", function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    });
</script>

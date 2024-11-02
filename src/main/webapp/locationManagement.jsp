<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.Location"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.UUID"%>
<%@ page import="models.Location"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="partials/header.jsp"%>


<main>
	<h2>
		Manage Locations -
		<%=userRole%></h2>

	<h3>
		<button id="openModal">Add New Location</button>
	</h3>

	<!-- Modal Structure -->
	<div id="locationModal" class="modal">
		<div class="modal-content">
			<span class="close-button">&times;</span>
			<h3>Add New Location</h3>
			<form action="location" method="POST" id="locationForm">
				<label for="locationCode">Location Code:</label> <input type="text"
					id="locationCode" name="locationCode" required> <label
					for="locationName">Location Name:</label> <input type="text"
					id="locationName" name="locationName" required> <label
					for="locationType">Location Type:</label> <select id="locationType"
					name="locationType" required onchange="updateParentOptions()">
					<option value="">Select Type</option>
					<option value="PROVINCE">Province</option>
					<option value="DISTRICT">District</option>
					<option value="SECTOR">Sector</option>
					<option value="CELL">Cell</option>
					<option value="VILLAGE">Village</option>
				</select> <label for="parentId">Parent Location:</label> <select
					id="parentId" name="parentId" disabled>
					<option value="">Select Parent Location</option>

				</select>

				<button type="submit">Add Location</button>
			</form>
		</div>
	</div>
	<p>Number of Locations: ${listLocation.size()}</p>
	
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
			<c:forEach var="location" items="${listLocation}">
				<tr>
					<td>${location.locationId}</td>
					<td>${location.locationCode}</td>
					<td>${location.locationName}</td>
					<td>${location.locationType}</td>
					<td><c:if test="${location.parentLocation != null}">
                            ${location.parentLocation.locationId}
                        </c:if></td>
					<td><a href="updateLocation?locationId=${location.locationId}"
						class="action-link">Update</a> | <a
						href="deleteLocation?locationId=${location.locationId}"
						class="action-link-delete">Delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</main>

<%@ include file="partials/footer.jsp"%>

<!-- Include JavaScript for Modal and Form Logic -->
<script>
    const modal = document.getElementById("locationModal");
    const openModalButton = document.getElementById("openModal");
    const closeModalButton = document.querySelector(".close-button");
    const locationTypeSelect = document.getElementById("locationType");
    const parentIdSelect = document.getElementById("parentId");

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
    
    


    function updateParentOptions() {
        const locationType = locationTypeSelect.value;
        console.log(locationType+" here ");
        parentIdSelect.innerHTML = '<option value="">Select Parent Location</option>';
        parentIdSelect.disabled = (locationType === "PROVINCE");

        if (locationType !== "PROVINCE") {
        	const fetchUrl = "getParentLocations?locationType=" + locationType;
        	console.log("Fetch URL:", fetchUrl);

            fetch(fetchUrl)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(data => {
                	console.log(data);
                    data.forEach(location => {
                        const option = document.createElement("option");
                        option.value = location.locationId;
                        option.text = location.locationName;
                        parentIdSelect.add(option);
                    });
                })
                .catch(error => {
                    console.error('Error fetching parent locations:', error);
                });
        }
    }

</script>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="partials/header.jsp"%>

<main>
    <h2>Profile - ${user.firstName} ${user.lastName}</h2>

    <div class="profile-container">
        <div class="profile-summary">
            <h3>Profile Summary</h3>
            <p><strong>First Name:</strong> ${user.firstName}</p>
            <p><strong>Last Name:</strong> ${user.lastName}</p>
            <p><strong>Username:</strong> ${user.userName}</p>
            <p><strong>Phone Number:</strong> ${user.phoneNumber}</p>
            <p><strong>Gender:</strong> ${user.gender}</p>
            <p><strong>Location:</strong> ${user.village}</p>

            <div class="profile-image">
                <h4>Profile Image:</h4>
                <img src="${user.picture != null ? user.picture : 'assets/images/default-image.webp'}"
                    alt="Profile Image" class="profile-image-thumbnail" />
            </div>
        </div>

        <!-- Button to show profile image form -->
        <button class="btn btn-secondary" onclick="toggleForm('profileImageModal')">Edit Profile Image</button>

        <!-- Button to show user information form -->
        <button class="btn btn-secondary" onclick="toggleForm('userInfoModal')">Edit Personal Info</button>

        <!-- Button to show location form -->
        <button class="btn btn-secondary" onclick="toggleForm('locationModal')">Edit Location</button>
    </div>

    <!-- Modal for editing profile image -->
    <div id="profileImageModal" class="modal">
        <div class="modal-content">
            <span class="close-btn" onclick="toggleForm('profileImageModal')">&times;</span>
            <h3>Edit Profile Image</h3>
            <form action="user" method="post" enctype="multipart/form-data">
                <input type="hidden" name="userId" value="${user.personId}" />
                <div class="form-group">
                    <label for="profileImage">Profile Image:</label>
                    <div class="image-preview">
                        <img src="${user.picture != null ? user.picture : 'assets/images/default-image.webp'}"
                            alt="Profile Image" id="profileImagePreview" />
                    </div>
                    <input type="file" id="profileImage" name="profileImage" accept="image/*" onchange="previewImage(event)" />
                </div>
                <button type="submit" class="btn btn-primary">Save Changes</button>
            </form>
        </div>
    </div>

    <!-- Modal for editing user information -->
    <div id="userInfoModal" class="modal">
        <div class="modal-content">
            <span class="close-btn" onclick="toggleForm('userInfoModal')">&times;</span>
            <h3>Edit Personal Information</h3>
            <form action="user" method="post">
                <input type="hidden" name="userId" value="${user.personId}" />
                <div class="form-group">
                    <label for="firstName">First Name:</label>
                    <input type="text" id="firstName" name="firstName" value="${user.firstName}" required />
                </div>
                <div class="form-group">
                    <label for="lastName">Last Name:</label>
                    <input type="text" id="lastName" name="lastName" value="${user.lastName}" required />
                </div>
                <div class="form-group">
                    <label for="phoneNumber">Phone Number:</label>
                    <input type="text" id="phoneNumber" name="phoneNumber" value="${user.phoneNumber}" required />
                </div>
                <div class="form-group">
                    <label for="gender">Gender:</label>
                    <select id="gender" name="gender" required>
                        <option value="MALE" ${user.gender == 'MALE' ? 'selected' : ''}>Male</option>
                        <option value="FEMALE" ${user.gender == 'FEMALE' ? 'selected' : ''}>Female</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">Save Changes</button>
            </form>
        </div>
    </div>

    <!-- Modal for editing location information -->
    <div id="locationModal" class="modal">
        <div class="modal-content">
            <span class="close-btn" onclick="toggleForm('locationModal')">&times;</span>
            <h3>Edit Location</h3>
            <form action="user" method="post">
                <input type="hidden" name="userId" value="${user.personId}" />
                <div class="form-group">
                    <label for="province">Province:</label>
                    <select id="province" name="province" onchange="loadDistricts()" required>
                        <option value="">Select Province</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="district">District:</label>
                    <select id="district" name="district" onchange="loadSectors()" required>
                        <option value="">Select District</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="sector">Sector:</label>
                    <select id="sector" name="sector" onchange="loadCells()" required>
                        <option value="">Select Sector</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="cell">Cell:</label>
                    <select id="cell" name="cell" onchange="loadVillages()" required>
                        <option value="">Select Cell</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="village">Village:</label>
                    <select id="village" name="village" required>
                        <option value="">Select Village</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">Save Changes</button>
            </form>
        </div>
    </div>

</main>

<%@ include file="partials/footer.jsp"%>

<script>
    // Toggle visibility of the forms
    function toggleForm(formId) {
        const form = document.getElementById(formId);
        const allForms = document.querySelectorAll('.form-section');
        allForms.forEach(f => f.style.display = 'none'); // Hide all forms
        form.style.display = form.style.display === 'none' ? 'block' : 'none'; // Toggle current form
    }

    function previewImage(event) {
        const preview = document.getElementById('profileImagePreview');
        const file = event.target.files[0];
        
        if (file) {
            preview.src = URL.createObjectURL(file);
        }
    }

    function loadDistricts() {
        const provinceId = document.getElementById('province').value;
        const districtSelect = document.getElementById('district');
        districtSelect.innerHTML = '<option value="">Select District</option>';

        if (provinceId) {
            const fetchUrl = `getLocations?parentId=${provinceId}&type=DISTRICT`;

            fetch(fetchUrl)
                .then(response => response.json())
                .then(data => {
                    data.forEach(district => {
                        const option = document.createElement("option");
                        option.value = district.locationId;
                        option.text = district.locationName;
                        districtSelect.add(option);
                    });
                })
                .catch(error => console.error('Error fetching districts:', error));
        }
    }

    function loadSectors() {
        const districtId = document.getElementById('district').value;
        const sectorSelect = document.getElementById('sector');
        sectorSelect.innerHTML = '<option value="">Select Sector</option>';

        if (districtId) {
            const fetchUrl = `getLocations?parentId=${districtId}&type=SECTOR`;

            fetch(fetchUrl)
                .then(response => response.json())
                .then(data => {
                    data.forEach(sector => {
                        const option = document.createElement("option");
                        option.value = sector.locationId;
                        option.text = sector.locationName;
                        sectorSelect.add(option);
                    });
                })
                .catch(error => console.error('Error fetching sectors:', error));
        }
    }

    function loadCells() {
        const sectorId = document.getElementById('sector').value;
        const cellSelect = document.getElementById('cell');
        cellSelect.innerHTML = '<option value="">Select Cell</option>';

        if (sectorId) {
            const fetchUrl = `getLocations?parentId=${sectorId}&type=CELL`;

            fetch(fetchUrl)
                .then(response => response.json())
                .then(data => {
                    data.forEach(cell => {
                        const option = document.createElement("option");
                        option.value = cell.locationId;
                        option.text = cell.locationName;
                        cellSelect.add(option);
                    });
                })
                .catch(error => console.error('Error fetching cells:', error));
        }
    }

    function loadVillages() {
        const cellId = document.getElementById('cell').value;
        const villageSelect = document.getElementById('village');
        villageSelect.innerHTML = '<option value="">Select Village</option>';

        if (cellId) {
            const fetchUrl = `getLocations?parentId=${cellId}&type=VILLAGE`;

            fetch(fetchUrl)
                .then(response => response.json())
                .then(data => {
                    data.forEach(village => {
                        const option = document.createElement("option");
                        option.value = village.locationId;
                        option.text = village.locationName;
                        villageSelect.add(option);
                    });
                })
                .catch(error => console.error('Error fetching villages:', error));
        }
    }
</script>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="partials/header.jsp"%>


<main>
    <h2>Edit Profile - ${user.firstName} ${user.lastName}</h2>

    <div class="profile-container">
        <form action="user" method="post" enctype="multipart/form-data">
            <input type="hidden" name="userId" value="${user.personId}"/>

            <div class="form-group">
                <label for="profileImage">Profile Image:</label>
                <div class="image-preview">
                    <img src="${user.picture != null ? user.picture : 'assets/images/default-image.webp'}" 
                         alt="Profile Image" id="profileImagePreview"/>
                </div>
                <input type="file" id="profileImage" name="profileImage" accept="image/*" onchange="previewImage(event)"/>
            </div>

            <div class="form-group">
                <label for="firstName">First Name:</label>
                <input type="text" id="firstName" name="firstName" value="${user.firstName}" required/>
            </div>

            <div class="form-group">
                <label for="lastName">Last Name:</label>
                <input type="text" id="lastName" name="lastName" value="${user.lastName}" required/>
            </div>

            <div class="form-group">
                <label for="userName">Username:</label>
                <input type="text" id="userName" name="userName" value="${user.userName}" readonly/>
            </div>

            <div class="form-group">
                <label for="phoneNumber">Phone Number:</label>
                <input type="text" id="phoneNumber" name="phoneNumber" value="${user.phoneNumber}" required/>
            </div>

            <div class="form-group">
                <label for="gender">Gender:</label>
                <select id="gender" name="gender" required>
                    <option value="MALE" ${user.gender == 'MALE' ? 'selected' : ''}>Male</option>
                    <option value="FEMALE" ${user.gender == 'FEMALE' ? 'selected' : ''}>Female</option>
                </select>
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-primary">Save Changes</button>
            </div>
        </form>
    </div>
</main>

<%@ include file="partials/footer.jsp"%>

<script>
    function previewImage(event) {
        const preview = document.getElementById('profileImagePreview');
        const file = event.target.files[0];
        
        if (file) {
            preview.src = URL.createObjectURL(file);
        }
    }
</script>

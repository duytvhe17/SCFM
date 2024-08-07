<%-- 
    Document   : updateUser.jsp
    Created on : Jun 5, 2024, 9:33:08 PM
    Author     : admin
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Update Profile</title>
        <!--<link rel="stylesheet" href="myprofile.css">-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f5f5f5;
                margin: 0;
                padding: 0;
            }

            html, body {
                height: 100%;
                margin: 0;
            }

            header {
                background-color: #333;
                color: #fff;
            }

            .header-top {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 10px 20px;
            }

            .logo {
                display: flex;
                align-items: center;
            }

            .logo img {
                height: 60px;
                margin-right: 10px;
            }

            .user-menu {
                position: relative;
                display: inline-block;
            }

            .dropdown {
                position: relative;
                display: flex;
                align-items: center;
                cursor: pointer;
            }

            .dropdown-content {
                display: none;
                position: absolute;
                right: 0;
                top: 100%;
                background-color: #1c1c1c;
                min-width: 160px;
                box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
                z-index: 1;
                border-top: 3px solid #ff4655;
            }

            .dropdown-content a {
                color: #fff;
                padding: 12px 16px;
                text-decoration: none;
                display: block;
            }

            .dropdown-content a:hover {
                background-color: #575757;
            }

            .dropdown:hover .dropdown-content {
                display: block;
            }

            .profile-container {
                width: 80%;
                margin: 20px auto;
                background-color: #ffffff;
                box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
                overflow: hidden;
            }

            .student-info {
                text-align: center;
                padding: 20px;
                background-color: #fc8403; /* Updated background color */
                color: #fff; /* Updated text color */
            }

            .profile-picture {
                width: 100px;
                height: 100px;
                border-radius: 50%;
                object-fit: cover;
            }

            .tabs {
                display: flex;
                justify-content: center;
                background-color: #333; /* Same as footer background color */
            }

            .tablink {
                background-color: #333; /* Same as footer background color */
                color: #fff; /* Updated text color */
                padding: 14px 20px;
                cursor: pointer;
                border: none;
                outline: none;
                transition: background-color 0.3s;
            }

            .tablink:hover {
                background-color: #575757;
            }

            .tablink.active {
                background-color: #808080; /* Updated active tab color */
            }

            .tabcontent {
                display: none;
                padding: 20px;
                background-color: #f1f1f1;
            }

            table {
                width: 100%;
                border-collapse: collapse;
            }

            table, th, td {
                border: 1px solid #ddd;
            }

            td {
                padding: 10px;
            }

            footer {
                background-color: #333;
                color: #fff;
                text-align: center;
                padding: 10px;
                bottom: 0;
                margin-top: 160px;
            }
            .update-button {
                background-color: #fc8403;
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s;
                display: block;
                margin: 20px auto;
            }

            .update-button:hover {
                background-color: #e53935;
            }
            .error-messages {
                color: red;
                text-align: center;
                margin-top: 10px;
                font-family: Arial, sans-serif;
            }
            .profile-picture-container {
                position: relative;
                display: inline-block;
            }

            .profile-picture2 {
                width: 100px;
                height: 100px;
                border-radius: 50%;
                object-fit: cover;
                display: block;
            }

            .camera-icon {
                position: absolute;
                bottom: 0;
                right: 0;
                background-color: rgba(0, 0, 0, 0.5);
                border-radius: 50%;
                padding: 5px;
            }

            .camera-icon i {
                color: white;
            }

            .profile-picture-input {
                display: none;
            }
            .profile-picture {
                width: 50px;
                height: 50px;
                border-radius: 50%;
                object-fit: cover;
                margin-right: 10px;
            }

        </style>
    </head>
    <body>
        <c:set var="roleId" value="${roleId}" />
        <c:set var="homePage" value="#" />
        <c:choose>
            <c:when test="${roleId eq 1}">
                <c:set var="homePage" value="manager/home" />
            </c:when>
            <c:when test="${roleId eq 2}">
                <c:set var="homePage" value="staff/home" />
            </c:when>
            <c:when test="${roleId eq 3}">
                <c:set var="homePage" value="student/home" />
            </c:when>
        </c:choose>
        <header>
            <div class="header-top">
                <a href="${homePage}" class="no-underline">
                    <div class="logo">
                        <img src="images/logo.png" alt="FPT Logo">
                    </div>
                </a>
                <input type="hidden" name="userid" value="${userId}" />
                <div class="user-menu">
                    <div class="dropdown">
                        <img src="Avatar/${user.picture}" alt="Profile Picture" class="profile-picture"> <span>${user.fullName}</span>
                        <div class="dropdown-content">
                            <a href="viewprofile">My Profile</a>
                            <a href="updateprofile">Update Profile</a>
                            <a href="changepassword">Change Password</a>
                            <a href="logout?action=logout">Logout</a>
                        </div>
                    </div>
                </div>
            </div>
        </header>
        <main>
            <form action="updateprofile" method="post" enctype="multipart/form-data">
                <div class="profile-container">
                    <div class="student-info">
                        <div class="profile-picture-container">
                            <img id="profilePicturePreview" src="Avatar/${user.picture}" class="profile-picture2">
                            <label for="picture" class="camera-icon">
                                <i class="fas fa-camera"></i>
                            </label>
                            <input type="file" id="picture" name="picture" accept=".png,.jpg" onchange="previewImage(event)" class="profile-picture-input">
                        </div>
                        <h2>${user.fullName}</h2>
                        <p>User Name: ${user.userName}</p>
                        <p>Email: ${user.email}</p>
                    </div>
                    <div class="profile-container">
                        <div class="update-form">
                            <input type="hidden" name="userId" value="${userId}">
                            <table>
                                <tr>
                                    <td><label for="fullName">Full Name:</label></td>
                                    <td><input type="text" id="fullName" name="fullName" value="${user.fullName}" required></td>
                                </tr>
                                <tr>
                                    <td><label for="dateOfBirth">Date of Birth:</label></td>
                                    <td><input type="date" id="dateOfBirth" name="dateOfBirth" value="${user.dateOfBirth}" required></td>
                                </tr>
                                <tr>
                                    <td><label for="gender">Gender:</label></td>
                                    <td>
                                        <select id="gender" name="gender">
                                            <option value="male" ${user.gender ? 'selected' : ''}>Male</option>
                                            <option value="female" ${!user.gender ? 'selected' : ''}>Female</option>
                                        </select>
                                    </td>
                                </tr>
                                <!--                                <tr>
                                                                    <td><label for="gender">Gender:</label></td>
                                                                    <td>
                                                                        <input type="radio" id="male" name="gender" value="male" ${user.gender ? 'checked' : ''}>
                                                                        <label for="male">Male</label>
                                                                        <input type="radio" id="female" name="gender" value="female" ${!user.gender ? 'checked' : ''}>
                                                                        <label for="female">Female</label>
                                                                    </td>
                                                                </tr>-->
                                <tr>
                                    <td><label for="address">Address:</label></td>
                                    <td><input type="text" id="address" name="address" value="${user.address}" required></td>
                                </tr>
                                <tr>
                                    <td><label for="mobile">Mobile:</label></td>
                                    <td><input type="text" id="mobile" name="mobile" value="${user.mobile}" required></td>
                                </tr>
                            </table>
                            <br>
                            <c:if test="${not empty errorMessages}">
                                <div class="error-messages">
                                    <c:out value="${errorMessages}" escapeXml="false"/>
                                </div>
                            </c:if>
                            <% session.removeAttribute("errorMessages"); %>
                            <input type="submit" class="update-button" value="Update"/>
                        </div>
                    </div>
                </div>
            </form>
        </main>
        <footer>
            <p>© 2024 FPT. SUPPORT FOR EVERY STUDENT IN UNIVERSITY.</p>
        </footer>
        <script>
            function confirmSubmission() {
                var isConfirmed = confirm("Are you sure you want to update your profile?");
                return isConfirmed;
            }

            var form = document.querySelector("form");
            form.onsubmit = confirmSubmission;

            function previewImage(event) {
                var file = event.target.files[0];
                if (file) {
                    var fileType = file.type;
                    var validImageTypes = ['image/jpeg', 'image/png'];
                    if (!validImageTypes.includes(fileType)) {
                        alert('Only JPG and PNG files are allowed.');
                        event.target.value = ''; // Clear the input
                        return;
                    }

                    var reader = new FileReader();
                    reader.onload = function () {
                        var output = document.getElementById('profilePicturePreview');
                        output.src = reader.result;
                    };
                    reader.readAsDataURL(file);
                }
            }

            document.getElementById('picture').addEventListener('change', previewImage);
        </script>
    </body>
</html>

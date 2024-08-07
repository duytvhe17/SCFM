<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Student Profile</title>      
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

            .profile-picture2 {
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
                margin-top: 270px;
            }
            .update-button {
                background-color: #e53935;
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
                background-color: #fc8403;
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
    <body>
        <header>

            <div class="header-top">
                <a href="${homePage}" class="no-underline">
                    <div class="logo">
                        <img src="images/logo.png" alt="FPT Logo" >
                    </div>
                </a>
                <input type="hidden" name="userid" value="${userId}" />
                <div class="user-menu">
                    <div class="dropdown">
                        <img src="Avatar/${user.picture}" alt="Profile Picture" class="profile-picture"> <span>${user.fullName}</span>
<!--                        <i class="fa fa-user fa-2x"></i> <span>${fullname}</span>-->
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
            <div class="profile-container">
                <div class="student-info">
                    <img src="Avatar/${user.picture}" alt="Profile Picture" class="profile-picture2">
                    <h2> ${user.fullName}</h2>
                    <p>User Name: ${user.userName}</p>
                    <p>Email: ${user.email}</p>
                </div>
                <div class="tabs">
                    <button class="tablink" onclick="openTab(event, 'Profile')">Profile</button>
                    <button class="tablink" onclick="openTab(event, 'Other')">Other</button>
                </div>
                <div id="Profile" class="tabcontent">
                    <table>
                        <tr>
                            <td>Full name</td>
                            <td>${user.fullName}</td>
                        </tr>
                        <tr>
                            <td>Date of birth</td>
                            <td>${user.dateOfBirth}</td>
                        </tr>
                        <tr>
                            <td>Gender</td>
                            <td><c:choose>
                                    <c:when test="${user.gender}">Male</c:when>
                                    <c:otherwise>Female</c:otherwise>
                                </c:choose></td>
                        </tr>
                        <tr>
                            <td>Address</td>
                            <td>${user.address}</td>
                        </tr>
                        <tr>
                            <td>Phone number</td>
                            <td>${user.mobile}</td>
                        </tr>
                        <tr>
                            <td>Email</td>
                            <td>${user.email}</td>
                        </tr>

                    </table>
                    <!--                     Add Update button 
                                        <button class="update-button" onclick="updateProfile(${user.userId})">Update Profile</button>-->
                </div>
            </div>
        </main>
        <footer>
            <p>© 2024 FPT. SUPPORT FOR EVERY STUDENT IN UNIVERSITY.</p>
        </footer>
        <script>
            function openTab(evt, tabName) {
                var i, tabcontent, tablinks;
                tabcontent = document.getElementsByClassName("tabcontent");
                for (i = 0; i < tabcontent.length; i++) {
                    tabcontent[i].style.display = "none";
                }
                tablinks = document.getElementsByClassName("tablink");
                for (i = 0; i < tablinks.length; i++) {
                    tablinks[i].className = tablinks[i].className.replace(" active", "");
                }
                document.getElementById(tabName).style.display = "block";
                evt.currentTarget.className += " active";
            }
            document.getElementsByClassName("tablink")[0].click();
            function updateProfile(userId) {
                window.location.href = `update?userId=${user.userId}`;
            }
        </script>
    </body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>View Staff</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" integrity="sha384-k6RqeWeci5ZR/Lv4MR0sA0FfDOMZST3v9zzTztpM8FnfQ9E1MdOMm8txVt4C7p5" crossorigin="anonymous">
        <style>
            body {
                display: flex;
                flex-direction: column;
                min-height: 100vh;
                font-family: Arial, sans-serif;
                margin: 0;
            }
            header {
                background-color: #333;
                color: #fff;
                padding: 10px 20px;
                width: 100%;
                display: flex;
                justify-content: space-between;
                align-items: center;
                box-sizing: border-box;
                position: fixed;
                top: 0;
                left: 0;
                z-index: 1000;
            }
            .logo {
                display: flex;
                align-items: center;
            }
            .logo img {
                height: 40px;
                margin-right: 10px;
            }
            .header-top a {
                text-decoration: none;
                color: #fff;
                font-size: 24px;
                font-weight: bold;
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
            .profile-picture {
                width: 50px;
                height: 50px;
                border-radius: 50%;
                object-fit: cover;
                margin-right: 10px;
            }
            .ava-picture {
                width: 100%;
                height: 50px;

                object-fit: cover;
                margin-right: 10px;
            }
            main {
                flex: 1;
                display: flex;
                flex-direction: column;
                align-items: center;
                width: 100%;
            }
            h1 {
                color: #e67e22;
                margin: 20px;
                font-size: 24px;
                text-align: center;
            }
            table {
                width: 80%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            th, td {
                padding: 8px 12px;
                border: 1px solid #ddd;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
            thead th {
                background-color: #007bff; /* Blue background color */
                color: white; /* White text color */
                font-weight: 600;
            }
            .actions {
                display: flex;
                gap: 10px;
            }
            .actions img {
                cursor: pointer;
                width: 30px; /* Adjust the size as needed */
                height: 24px; /* Adjust the size as needed */
            }
            .search-bar {
                margin-top: 20px;
            }
            .search-bar input[type="text"] {
                padding: 8px;
                width: 300px;
                border: 1px solid #ddd;
                border-radius: 4px;
            }
            .search-bar input[type="submit"] {
                padding: 8px 16px;
                border: none;
                background-color: #4CAF50;
                color: white;
                border-radius: 4px;
                cursor: pointer;
            }
            footer {
                background-color: #333;
                color: #fff;
                text-align: center;
                padding: 10px;
                width: 100%;
            }
        </style>
        <script>
            function submitAction(userId, action) {
                document.getElementById('userId').value = userId;
                document.getElementById('action').value = action;
                document.getElementById('actionForm').submit();
            }
        </script>
    </head>
    <body>
        <header>
            <a href="home" class="no-underline">
                <div class="logo">
                    <img src="../images/logo.png" alt="FPT Logo">

                </div>
            </a>
            <div class="user-menu">
                <div class="dropdown">
                    <img src="../Avatar/${user.picture}" alt="Profile Picture" class="profile-picture"> <span>${fullname}</span>
                    <div class="dropdown-content">
                        <a href="../viewprofile">My Profile</a>
                        <a href="../updateprofile">Update Profile</a>
                        <a href="../changepassword">Change Password</a>
                        <a href="../logout?action=logout">Logout</a>
                    </div>
                </div>
            </div>
        </header>
        <main>
            <h1>List of Staff</h1>
            <div class="search-bar">
                <h1>List of Staff</h1>
                <form method="get" action="viewstaff">
                    <input type="text" name="searchQuery" placeholder="Search by name...">
                    <button type="submit">Search</button>
                </form>
            </div>
            <form id="actionForm" method="post" action="viewstaff">
                <input type="hidden" name="userId" id="userId" />
                <input type="hidden" name="action" id="action" />
            </form>
            <table>
                <thead>
                    <tr>
                        <th>Image</th>
                        <th>Full Name</th>
                        <th>Email</th>
                        <th>Gender</th>
                        <th>Mobile</th>
                        <th>Address</th>
                        <th>Date of Birth</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="staff" items="${staff}">
                        <tr>
                            <td><img class="ava-picture" src="../Avatar/${staff.picture}"></td>
                            <td>${staff.fullName}</td>
                            <td>${staff.email}</td>
                            <td>${staff.gender ? "Male" : "Female"}</td>
                            <td>${staff.mobile}</td>
                            <td>${staff.address}</td>
                            <td>${staff.dateOfBirth}</td>

                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </main>
        <footer>
            <p>© 2024 FPT. SUPPORT FOR EVERY STUDENT IN UNIVERSITY.</p>
        </footer>
    </body>
</html>

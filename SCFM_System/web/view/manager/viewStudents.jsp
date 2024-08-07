<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Students</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" integrity="sha384-k6RqeWeci5ZR/Lv4MR0sA0FfDOMZST3v9zzTztpM8FnfQ9E1MdOMm8txVt4C7p5" crossorigin="anonymous">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f5f7;
            margin: 0;
            padding: 0;
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
        .user-menu {
            position: relative;
            display: inline-block;
        }
        .logo {
            display: flex;
            align-items: center;
        }
        .logo img {
            height: 40px;
            margin-right: 10px;
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
        .header-top a {
            text-decoration: none;
            color: #000;
            font-size: 24px;
            font-weight: bold;
        }
        h1 {
            color: #e67e22;
            margin: 20px;
            font-size: 24px;
            text-align: center;
            margin-top: 60px; /* Thêm khoảng trống để tránh bị che khuất */
        }
        .search-bar {
            display: flex;
            justify-content: center;
            margin: 20px;
        }
        .search-bar input[type="text"] {
            width: 300px;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .search-bar button {
            padding: 10px 20px;
            border: none;
            background-color: #007bff;
            color: white;
            cursor: pointer;
            border-radius: 4px;
            margin-left: 10px;
        }
        .search-bar button:hover {
            background-color: #0056b3;
        }
        table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
            background-color: #fff;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        th, td {
            padding: 8px;
            border: 1px solid #ddd;
            text-align: left;
        }
        thead th {
            background-color: #007bff;
            color: white;
            font-weight: 600;
        }
        th {
            background-color: #f9fafb;
            font-weight: 600;
        }
        tr:nth-child(even) {
            background-color: #f4f5f7;
        }
        .actions {
            display: flex;
            gap: 10px;
        }
        .actions img {
            cursor: pointer;
            width: 36px;
            height: 28px;
        }
        .btn-save {
            background-color: #ff0000;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            margin: 20px auto;
            display: block;
            text-align: center;
        }
        .btn-save:hover {
            background-color: #cc0000;
        }
        footer {
            background-color: #333;
            color: #fff;
            text-align: center;
            padding: 10px;
            margin-top: auto;
            width: 100%;
        }
    </style>
    <script>
        function saveAll() {
            if (confirm("Are you sure to change?")) {
                const form = document.getElementById('saveForm');
                form.action = 'viewstudents?action=save';
                form.submit();
            }
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

        </header>
                <h4>Text</h4>
    <h1>List of Students</h1>
    <div class="search-bar">
        <form method="get" action="viewstudents">
            <input type="text" name="searchQuery" placeholder="Search by name...">
            <button type="submit">Search</button>
        </form>
    </div>

    <form id="saveForm" method="post" action="viewStudents">
        <input type="hidden" name="userId" id="userId">
        <input type="hidden" name="action" id="action">
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
                    <th>Ban Account</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="student" items="${students}">
                    <tr>
                        <td><img class="ava-picture" src="../Avatar/${student.picture}"></td>
                        <td>${student.fullName}</td>
                        <td>${student.email}</td>
                        <td>${student.gender ? "Male" : "Female"}</td>
                        <td>${student.mobile}</td>
                        <td>${student.address}</td>
                        <td>${student.dateOfBirth}</td>
                        <td>
                            Active: <input type="checkbox" name="selectedStudents" value="${student.userId}" ${student.active ? "checked" : ""}> 
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <button type="button" class="btn-save" onclick="saveAll()">Save</button>
    </form>
    <footer>
        <p>© 2024 FPT. SUPPORT FOR EVERY STUDENT IN UNIVERSITY.</p>
    </footer>
</body>
</html>

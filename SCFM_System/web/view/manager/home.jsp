<%-- 
    Document   : s2
    Created on : 3 Jun 2024, 23:28:57
    Author     : nguye
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SCFM</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        html, body {
            height: 100%;
            margin: 0;
        }
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f0f0f0;
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

        nav ul {
            list-style-type: none;
            display: flex;
            gap: 20px;
            margin: 0;
            padding: 0;
        }

        nav ul li {
            display: inline;
        }

        nav ul li a {
            color: #fff;
            text-decoration: none;
            font-weight: bold;
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
            top: 100%; /* Ensure dropdown shows below the trigger element */
            background-color: #1c1c1c;
            min-width: 180px;
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

        .header-bottom {
            background-color: #c62828;
            padding: 30px;
            text-align: center;

        }

        .header-bottom h1 {
            margin: 5px;
            font-size: 30px;
            font-weight: bold;
            color: #fff;
        }



        main {
            padding: 20px;
            background-color: #fff;
        }

        .support-options {
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            gap: 20px;
            margin-bottom: 40px;
            margin-top: 40px;
        }
        .no-underline {
            text-decoration: none;
            color: inherit; /* Nếu bạn muốn giữ màu chữ giống với màu chữ cha */
        }


        .option {
            display: flex;
            margin-left: 30px;
            margin-right: 30px;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            width: 1000px; /* Điều chỉnh kích thước tùy ý */
            height: 100px; /* Điều chỉnh kích thước tùy ý */
            background-color: #c62828; /* Màu nền tùy chỉnh */
            color: #fff; /* Màu chữ tùy chỉnh */
            border-radius: 10px; /* Điều chỉnh góc bo tròn tùy ý */
            text-align: center;
            transition: transform 0.3s; /* Thời gian chuyển đổi hiệu ứng */
        }

        .option i {
            margin-bottom: 10px; /* Khoảng cách giữa biểu tượng và văn bản */
        }

        .option p {
            margin: 0; /* Xóa khoảng cách mặc định của đoạn văn */
            font-size: 15px;
            font-weight: bold;
        }
        .option:hover {
            transform: scale(1.05); /* Phóng to lên 10% khi di chuột vào */
        }

        .option i, .option p {
            transition: transform 0.3s; /* Thời gian chuyển đổi hiệu ứng cho nội dung bên trong */
        }

        .option:hover i, .option:hover p {
            transform: scale(1.05); /* Phóng to nội dung bên trong lên 10% khi di chuột vào */
        }

        .track-support {

            text-align: center;
            margin-bottom: 46px;


        }

        .track-support button {
            background-color: #c62828;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;

        }


        footer {
            height: 40px;
            margin-bottom: 0px;
            padding-bottom: 0px;
            background-color: #333;
            color: #fff;
            text-align: center;
            padding: 10px;
        }
        .menu .create-staff {
            background-color: #e57373;
        }
        .menu .create-category {
            background-color: #ba68c8;
        }
        .menu .view-complaints {
            background-color: #7986cb;
        }
        .menu .view-students-staff {
            background-color: #ffb74d;
        }
        .menu .notification {
            background-color: #81c784;
            margin-left: -10px;
            margin-bottom: -20px;
        }
        .button {
            color: white;
            display: inline-block; /* Inline elements with width and height. TL;DR they make the icon buttons stack from left-to-right instead of top-to-bottom */
            position: relative; /* All 'absolute'ly positioned elements are relative to this one */
            padding: 2px 5px; /* Add some padding so it looks nice */
        }
        .content {
            flex-grow: 1;
            background-color: #e0e0e0;
            padding: 20px;
            text-align: center;
            border-radius: 5px;
        }
        .menu {
            margin-top: 30px;
            font-family: Arial, sans-serif;
            margin-right: 50px;
            margin-bottom:  30px;
        }
        .menu button {
            display: block;
            width: 1000px;
            padding: 20px;
            margin-bottom: 12px;
            font-size: 20px;
            color: black;
            border: #1c1c1c;
            cursor: pointer;
            text-align: left;
            border-radius: 3%;
            border: dashed #000 1px;
        }
        .container{
            margin-left: 120px;
        }
        .submenu {
            display: none;
            margin-top: 10px;
            margin-left: 30px;
            width: 800px;
            border-radius: 3%;
        }
        .submenu li {
            background-color: #e0e0e0;
            padding: 10px;
            margin-bottom: 5px;
            cursor: pointer;
            font-family: Arial, sans-serif;
        }
        .button-icon {
            margin-right: 10px;
        }
        .profile-picture {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            object-fit: cover;
            margin-right: 10px;
        }
        .button__badge {
            background-color: #fa3e3e;
            border-radius: 2px;
            color: white;

            padding: 1px 3px;
            margin-bottom:-20px;
            font-size: 10px;

            position: absolute; /* Position the badge within the relatively positioned button */
            top: 0;
            left: 0;
        }
    </style>
</head>

<body>
    <%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    if(session.getAttribute("userId")==null){
       response.sendRedirect("/SCFM/login");
    }           
    %>



    <header>


        <div class="header-top">
            <a href="home" class="no-underline">
                <div class="logo">
                    <img src="../images/logo.png" alt="FPT Logo">
                    <span></span>
                </div>
            </a>

            <input type="hidden" name="userid" value="${userId}" />
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


        </div>
        <div class="header-bottom">
            <h1>QUẢN LÝ</h1>
            <h1>KHIẾU NẠI SINH VIÊN</h1>

        </div>
    </header>



    <main>

        <div class="container">
            <div class="menu">
                <button class="create-staff" onclick="redirectToRegistrationPage()">
                    <i class="fas fa-user-tie button-icon"></i> Create staff
                </button>
                <button class="view-complaints" onclick="toggleSubmenu('complaintsSubmenu')">
                    <i class="fas fa-eye button-icon"></i> View List Complaints
                </button>
                <ul id="complaintsSubmenu" class="submenu">
                    <li><a href="../manager/viewcomplaint">List of pending complaints</a></li>
                    <li><a href="../manager/viewmycompleted">List of processed complaints</a></li>

                </ul>

                <button class="view-students-staff" onclick="toggleSubmenu('studentsStaffSubmenu')">
                    <i class="fas fa-users button-icon"></i> View list students, staff
                </button>
                <ul id="studentsStaffSubmenu" class="submenu">
                    <li><a href="../manager/viewstudents">View list students</a></li>
                    <li><a href="../manager/viewstaff">View list staff</a></li>
                </ul>

                <button class="create-category" onclick="toggleSubmenu('categorySubmenu')">
                    <i class="fas fa-list button-icon"></i> View and update Sample Form Complaint
                </button>
                <ul id="categorySubmenu" class="submenu" >
                    <li><a href="../manager/addcategory">Add new Type Complaint</a></li>
                    <li><a href="../manager/listCategory">View and Update Sample Form</a></li>

                </ul>
                <button class="view-charts" onclick="toggleSubmenu('chartsMenu')">
                    <i class="fas fa-list button-icon"></i>Statistics
                </button>
                <ul id="chartsMenu" class="submenu">
                    <li><a href="../manager/viewTotalComplaintByTime">Complaint Statistics</a></li>
                    <li><a href="../manager/viewTotalQuestionsByCategory">Statistical Chart by Type of Complaint</a></li>
                </ul>

                <a href="../manager/notification" class="no-underline">
                    <div class="notification option">
                        <i class="fa fa-bell button">
                            <c:if test="${requestScope.count>0}">
                                <span class="button__badge">${requestScope.count}</span>
                            </c:if>
                        </i>
                        <p>Notification</p>

                    </div>
                    <!--                            <div class="option notification">
                                        <i class="fa fa-bell fa-3x button">
                    <c:if test="${requestScope.count>0}">
                        <p class="button__badge">${requestScope.count}</p>
                    </c:if>
                    
                </i>
                <p>Notification</p>
            </div>-->
                </a>

            </div>
        </div>
    </main>

    <footer>
        <p>© 2024 FPT. SUPPORT FOR EVERY STUDENT IN UNIVERSITY.</p>
    </footer>
    <script>
        function toggleSubmenu(id) {
            var submenu = document.getElementById(id);
            if (submenu.style.display === "none" || submenu.style.display === "") {
                submenu.style.display = "block";
            } else {
                submenu.style.display = "none";
            }
        }
        function redirectToRegistrationPage() {
            window.location.href = '../manager/createstaff';
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</body>


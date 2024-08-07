<%-- 
    Document   : s2
    Created on : 3 Jun 2024, 23:28:57
    Author     : nguye
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SCFM</title>

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
        background-color: #fc8403;
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
        width: 220px; /* Điều chỉnh kích thước tùy ý */
        height: 240px; /* Điều chỉnh kích thước tùy ý */
        background-color: #fc8403; /* Màu nền tùy chỉnh */
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
        font-size: 16px;
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
        background-color: #fc8403;
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
    .profile-picture {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            object-fit: cover;
            margin-right: 10px;
        }
    /*footer {
        background-color: #333;
        color: white;
        text-align: center;
        padding: 10px;
        position: relative;
        bottom: 0;
        width: 100%;
    }*/
        .button {
      color: white;
      display: inline-block; /* Inline elements with width and height. TL;DR they make the icon buttons stack from left-to-right instead of top-to-bottom */
      position: relative; /* All 'absolute'ly positioned elements are relative to this one */
      padding: 2px 5px; /* Add some padding so it looks nice */
    }

    /* Make the badge float in the top right corner of the button */
    .button__badge {
      background-color: #fa3e3e;
      border-radius: 2px;
      color: white;

      padding: 1px 3px;
      font-size: 10px;

      position: absolute; /* Position the badge within the relatively positioned button */
      top: 0;
      right: 0;
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
            <div>
                <h1>Staff Of University</h1>
            </div>
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


        <div class="support-options">
            <a href="../staff/viewcomplaint" class="no-underline">
                <div class="option">
                    <i class="fa fa-list fa-3x"></i>
                    <p>View List Complaints</p>
                </div></a>
            <a href="../staff/viewMyCompleted" class="no-underline">
                <div class="option">
                    <i class="fa fa-list fa-3x"></i>
                    <p>View All Completed Complaints</p>
                </div></a>
            <a href="../staff/notificationServlet" class="no-underline">
                <div class="option">
                    <i class="fa fa-bell fa-3x button">
                        <c:if test="${requestScope.count>0}">
                            <span class="button__badge">${requestScope.count}</span>
                        </c:if>
                        
                    </i>
                    <p>Notification</p>
                </div></a>
           
        </div>
        
    </main>
    <footer>
        <p>© 2024 FPT. SUPPORT FOR EVERY STUDENT IN UNIVERSITY.</p>
    </footer>
</body>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
    <c:if test="${requestScope.count>0}">
        Swal.fire({
        title: "There is currently ${requestScope.count} announcement",
        width: 600,
        padding: "3em",
        color: "#716add",
        background: "#fff url(/images/trees.png)",
        backdrop: `
          rgba(0,0,123,0.4)
          url("/images/nyan-cat.gif")
          left top
          no-repeat
        `
      });
    </c:if>
</script>



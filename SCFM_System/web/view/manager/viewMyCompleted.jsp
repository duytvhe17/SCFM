<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
    <head>
        <title>List of questions you answered</title>
         <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <style>
            /* Existing CSS */
            body {
                font-family: Arial, sans-serif;
                background-color: #f0f0f0;
                margin: 0;
                display: flex;
                flex-direction: column;
                min-height: 100vh;
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
            .tabs {
                margin-top: 80px;
                display: flex;
                justify-content: center;
            }
            .tabs button {
                background-color: #333;
                color: #fff;
                padding: 14px 20px;
                border: none;
                cursor: pointer;
                margin: 0 4px;
                border-radius: 12px 12px 0 0;
            }
            .tabs button.active {
                background-color: #ff4655;
            }
            .tab-content {
                display: none;
            }
            .tab-content.active {
                display: block;
            }
            table {
                width: 95%;
                border-collapse: collapse;
                margin: 20px auto;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                background-color: #fff;
                table-layout: fixed; /* Thêm dòng này để các cột có chiều rộng cố định */
            }
            th, td {
                padding: 10px;
                text-align: center;
                border: 1px solid black;
                word-wrap: break-word; /* Cho phép xuống dòng khi quá dài */
                white-space: normal; /* Đảm bảo nội dung có thể xuống dòng */
                overflow: hidden; /* Ẩn nội dung tràn */
            }
            th {
                background-color: #f2f2f2;
            }
            footer {
                background-color: #333;
                color: #fff;
                text-align: center;
                padding: 10px;
                width: 100%;
                box-sizing: border-box;
                margin-top: 20px;
            }
            .no-complaints {
                text-align: center;
                margin-top: 20px;
            }
            .profile-picture {
                width: 50px;
                height: 50px;
                border-radius: 50%;
                object-fit: cover;
                margin-right: 10px;
            }
            .container {
                flex: 1;
                display: flex;
                flex-direction: column;
                align-items: center;
                background-color: #fff;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
                max-width: 1000px;
                width: 100%;
                margin: auto;
                margin-top: 100px;
            }
            .view-button {
                background-color: #000;
                color: white;
                border: none;
                padding: 10px 20px;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                font-size: 14px;
                margin: 4px 2px;
                cursor: pointer;
                border-radius: 12px;
            }
            .hidden-row {
                display: none;
            }
            .answer-row.active {
                background-color: orange; /* Background color for active answer row set to orange */
            }
            .pagination {
                text-align: center;
                margin-top: 20px;
            }
            .pagination a, .pagination span {
                display: inline-block;
                padding: 8px 16px;
                margin: 0 4px;
                background-color: #333;
                color: white;
                border-radius: 5px;
                text-decoration: none;
            }
            .pagination a:hover {
                background-color: #ff4655;
            }
            .pagination .current {
                background-color: #ff4655;
                cursor: default;
            }
            .search-bar {
                display: flex;
                justify-content: center;
                margin-bottom: 20px;
            }
            .search-input {
                padding: 10px;
                font-size: 16px;
                border: 2px solid #333;
                border-radius: 8px 0 0 8px;
                outline: none;
            }
            .search-button {
                padding: 10px 20px;
                font-size: 16px;
                background-color: #333;
                color: white;
                border: 2px solid #333;
                border-radius: 0 8px 8px 0;
                cursor: pointer;
                outline: none;
            }
            .search-button:hover {
                background-color: #ff4655;
                border-color: #ff4655;
            }
            .stars {
            font-size: 24px;
            color: gold;
        }
        .star {
            margin-right: 2px;
        }
        </style>
        <script>
            function toggleDetail(rowId) {
                const detailRow = document.getElementById(rowId);
                if (detailRow.style.display === 'none' || detailRow.style.display === '') {
                    detailRow.style.display = 'table-row';
                } else {
                    detailRow.style.display = 'none';
                }
            }

            function showAnswer(rowId) {
                const answerRow = document.getElementById(rowId);
                if (answerRow.classList.contains('active')) {
                    answerRow.classList.remove('active');
                    answerRow.style.display = 'none';
                } else {
                    answerRow.classList.add('active');
                    answerRow.style.display = 'table-row';
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
            </div>
        </header>
        <div class="container">
            <h2>List Of Questions You Answered:</h2>
            <form method="get" action="" class="search-bar">
                <input type="text" name="search" class="search-input" placeholder="Search by question content or full name" value="${param.search}">
                <button type="submit" class="search-button">Search</button>
            </form>
             <table>
            <thead>
                <tr>
                    <th>Time Create</th>
                    <th>Question Content</th>
                    <th>Rating</th> <!-- Add new column for Rating -->
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="answer" items="${answers}">
                    <tr>
                        <td>${answer.time}</td>
                        <td>${answer.question.content}</td>
                        <td>
                            <c:choose>
                                <c:when test="${answer.vote.rating > 0}">
                                    <div class="stars">
                                        <c:forEach var="i" begin="1" end="${answer.vote.rating}">
                                            <i class="fas fa-star star"></i>
                                        </c:forEach>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    No rating
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <button class="view-button" onclick="toggleDetail('detail-${answer.question.questionId}')">View Detail</button>
                            <button class="view-button" onclick="showAnswer('answer-${answer.question.questionId}')">View Answer</button>
                        </td>
                    </tr>
                    <tr id="detail-${answer.question.questionId}" class="hidden-row">
                        <td colspan="4">
                            <strong>Full Name:</strong> ${answer.user.fullName}<br>
                            <strong>Email:</strong> ${answer.user.email}<br>
                            <strong>Mobile:</strong> ${answer.user.mobile}<br>
                            <a href="${pageContext.request.contextPath}/downloadFile?filename=${answer.fileName}">Download File</a>
                        </td>
                    </tr>
                    <tr id="answer-${answer.question.questionId}" class="hidden-row answer-row">
                        <td colspan="4">
                            <strong>Answer Content:</strong> ${answer.content}
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="pagination">
            <c:choose>
                <c:when test="${currentPage > 3}">
                    <a href="?page=1&searchQuery=${param.searchQuery}&categoryId=${param.categoryId}&status=${param.status}">1</a>
                    <span>...</span>
                </c:when>
            </c:choose>
            <c:set var="startPage" value="${currentPage - 2}" />
            <c:set var="endPage" value="${currentPage + 2}" />
            <c:set var="startPage" value="${startPage < 1 ? 1 : startPage}" />
            <c:set var="endPage" value="${endPage > totalPages ? totalPages : endPage}" />
            <c:forEach var="i" begin="${startPage}" end="${endPage}">
                <c:choose>
                    <c:when test="${i > 0 && i <= totalPages}">
                        <c:choose>
                            <c:when test="${i == currentPage}">
                                <span class="current">${i}</span>
                            </c:when>
                            <c:otherwise>
                                <a href="?page=${i}&searchQuery=${param.searchQuery}&categoryId=${param.categoryId}&status=${param.status}">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                </c:choose>
            </c:forEach>
            <c:choose>
                <c:when test="${currentPage < totalPages - 2}">
                    <span>...</span>
                    <a href="?page=${totalPages}&searchQuery=${param.searchQuery}&categoryId=${param.categoryId}&status=${param.status}">${totalPages}</a>
                </c:when>
            </c:choose>
        </div>
        </div>
        <footer>
            <p>© 2024 FPT. SUPPORT FOR EVERY STUDENT IN UNIVERSITY.</p>
        </footer>
    </body>
</html>
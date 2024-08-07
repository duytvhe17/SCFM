<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Complaints</title>
        <style>
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
            table {
                width: 95%;
                border-collapse: collapse;
                margin: 20px auto 20px auto;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                background-color: #fff;
                table-layout: fixed;
            }
            th, td {
                padding: 15px;
                text-align: center;
                border: 1px solid black;
                word-wrap: break-word;
                white-space: normal;
                overflow: hidden;
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
                margin-top: 10px;
            }
            .view-button {
                background-color: #4CAF50;
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
            .search-container {
                display: flex;
                justify-content: center;
                margin-top: 100px;
                align-items: center;
                margin: 20px auto;
            }
            .search-container input[type="text"],
            .search-container select {
                padding: 10px;
                font-size: 16px;
                margin-top: 100px;
                margin-right: 10px;
                border-radius: 5px;
                border: 1px solid #ccc;
            }
            .search-container .search-button {
                background-color: #333;
                color: white;
                border: none;
                padding: 10px 20px;
                text-align: center;
                text-decoration: none;
                font-size: 16px;
                cursor: pointer;
                border-radius: 12px;
                margin-left: 250px;

            }
            .radio-container {
                display: flex;
                justify-content: center;
                align-items: center;
                margin: 10px auto;

            }
            .radio-container input[type="radio"] {
                margin-right: 5px;

            }
            .radio-container label {
                margin-right: 15px;

            }
        </style>
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

        <div class="search-container">
            <form action="viewcomplaint" method="get">
                <input type="text" name="searchQuery" placeholder="Search by content or student name" value="${param.searchQuery}">
                <select name="categoryId">
                    <option value="0">All Categories</option>
                    <c:forEach var="category" items="${categories}">
                        <option value="${category.categoryId}" ${param.categoryId == category.categoryId ? 'selected' : ''}>${category.categoryName}</option>
                    </c:forEach>
                </select>
                <div class="radio-container">
                    <label><input type="radio" name="status" value="all" ${!param.status || param.status == 'all' ? 'checked' : ''} > All</label>
                    <label><input type="radio" name="status" value="processing" ${param.status == 'processing' ? 'checked' : ''}> Processing</label>
                    <label><input type="radio" name="status" value="completed" ${param.status == 'completed' ? 'checked' : ''}> Completed</label>
                    <label><input type="radio" name="status" value="rejected" ${param.status == 'rejected' ? 'checked' : ''}> Rejected</label>
                </div>
                <button type="submit" class="search-button">Search</button>
            </form>
        </div>

        <div class="container">
            <c:if test="${not empty questions}">
                <table>
                    <tr>
                        <th>Time Create</th>
                        <th>Content</th>
                        <th>Category</th>
                        <th>Status</th>
                        <th>Full Name Student</th>
                        <th>Attachment</th>
                        <th>View</th>
                    </tr>
                    <c:forEach var="question" items="${questions}">
                        <tr>
                            <td>${question.timeCreate}</td>
                            <td>${question.content}</td>
                            <td>${question.category.categoryName}</td>
                            <td>${question.status.statusName}</td>          
                            <td>${question.user.fullName}</td>
                            <td>
                                <c:if test="${not empty question.fileName}">
                                    <a href="${pageContext.request.contextPath}/downloadFile?filename=${question.fileName}">Download File</a>
                                </c:if>
                            </td>
                            <td><a href="viewdetails?questionId=${question.questionId}" class="view-button">View</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
            <c:if test="${empty questions}">
                <p class="no-complaints">No complaints found.</p>
            </c:if>
        </div>

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

        <footer>
            <p>© 2024 FPT. SUPPORT FOR EVERY STUDENT IN UNIVERSITY.</p>
        </footer>
    </body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>My Question</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
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
                margin: 20px 0;
            }
            table, th, td {
                border: 1px solid black;
            }
            th, td {
                padding: 15px;
                text-align: center;
            }
            th {
                background-color: #f2f2f2;
            }
            .pagination {
                text-align: center;
                margin-top: 20px;
                margin-bottom: 20px;
            }
            .pagination a {
                margin: 0 5px;
                padding: 8px 16px;
                text-decoration: none;
                border: 1px solid #ddd;
                color: #000;
            }
            .pagination a:hover {
                background-color: #ddd;
            }
            .pagination .active {
                font-weight: bold;
                background-color: #ddd;
                color: #000;
                border: 1px solid #000;
            }

            footer {
                background-color: #333;
                color: #fff;
                text-align: center;
                padding: 10px;
                width: 100%;
                box-sizing: border-box;
            }

            @media (max-width: 600px) {
                .container {
                    padding: 15px;
                }
                header {
                    flex-direction: column;
                    align-items: flex-start;
                }
                .user-menu {
                    margin-top: 10px;
                }
                table {
                    width: 90%;
                }
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
                margin-top: 100px; /* Add this line */
                padding-top: 20px; /* Add this line */
            }
            .container h2 {
                text-align: center;
                color: #333;
                margin-bottom: 20px;
            }

            .search-container {
                display: flex;
                justify-content: center;
                align-items: center;
                margin-top: 150px;
                margin-bottom: 20px;
                width: 100%;
                padding: 10px;
                box-sizing: border-box;
            }

            .search-container input[type="text"] {
                width: 600px;
                height: 35px;
                padding: 10px;
                border: none;
                border-radius: 25px 0 0 25px;
                outline: none;
                font-size: 16px;
            }

            .search-container button {
                height:55px;
                padding: 10px;
                border: none;
                background-color: #fff;
                cursor: pointer;
                border-radius: 0 25px 25px 0;
            }

            .search-container button img {
                width: 35px;
                height: 35px;
            }
             select {
                height: 45px;
                padding: 10px;
                border: none;
                border-radius: 25px;
                outline: none;
                font-size: 16px;
                background-color: #fff;
                color: #333;
                margin-left: 120px;
                margin-top: 50px;
            }

        </style>
    </head>
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
        <form id="frmSearch" action="mycomplaint" method="post">
            <input type="text" placeholder="Search" id="search" name="search">
            <button type="submit">
                <img src="search-icon.png" alt="Search">
            </button>
            <br>
             <select name="categoryId" onchange="document.getElementById('frmSearch').submit()">
                <option value="ALL">All category</option>
                <c:forEach items="${requestScope.cats}" var="c">
                    <option value="${c.categoryId}"
                            <c:if test="${param.categoryId != 'ALL' && param.categoryId eq c.categoryId}"> 
                                selected="selected"
                            </c:if>
                            >${c.categoryName}</option>
                </c:forEach>
            </select>
        </form>
    </div>
    <div class="container">
        <h2>List Complaint not Completed</h2>
        <table>
            <tr>
                <th>No</th>
                <th>Content</th>
                <th>Type of Content</th>
                <th>Time</th>
                <th>Modify</th>
                <th>File</th>
            </tr>
            <c:forEach var="question" items="${data}" varStatus="status">
                <tr>
                    <td>${(currentPage - 1) * 10 + status.count}</td>
                    <td>${question.content}</td>
                    <td>${question.category.categoryName}</td>
                    <td>${question.timeCreate}</td>
                    <td>${question.modify.modifyName}</td>   
                    <td><c:choose>
                                <c:when test="${question.fileName == null}">
                                    None
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/downloadFile?filename=${question.fileName}">File</a>
                                </c:otherwise>
                            </c:choose></td>  
                </tr>
            </c:forEach>
        </table>
        <div class="pagination">
            <c:forEach begin="1" end="${noOfPages}" var="i">
                <a href="mycomplaint?page=${i}" class="${currentPage == i ? 'active' : ''}">${i}</a>
            </c:forEach>
        </div>
    </div>
    <footer>
        <p>© 2024 FPT. SUPPORT FOR EVERY STUDENT IN UNIVERSITY.</p>
    </footer>
</body>

</html>

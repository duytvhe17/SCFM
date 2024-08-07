<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>View list Sample Form Complaint</title>
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
                background-color: #007bff;
                color: #fff;
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
                max-width: 1100px;
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
                cursor: pointer;
                border-radius: 4px;
                margin-left: 10px;
            }

            .search-button {
                background-color: #007bff;
                color: white;
            }

            .search-button:hover {
                background-color: #0056b3;
            }

            .add-category-button {
                background-color: #28a745;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 4px;
                cursor: pointer;
                margin-left: 10px;
            }

            .add-category-button:hover {
                background-color: #218838;
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
        <div class="container">
            <h2>List Category</h2>
            <form class="search-bar" action="listCategory" method="GET">
                <input type="text" name="keyword" placeholder="Search by category name..." value="${param.keyword}">
                <button type="submit" class="search-button">Search</button>
                <button type="button" class="add-category-button" onclick="window.location.href='addcategory'">Add Category</button>
            </form>
            <table>
                <tr>
                    <th>No</th>
                    <th>Name</th>
                    <th>Time</th>
                    <th>Attachment</th>
                    <th>Update</th>
                </tr>
                <c:forEach var="category" items="${data}" varStatus="status">
                    <tr>
                        <td>${(currentPage - 1) * 10 + status.count}</td>
                        <td>${category.categoryName}</td>
                        <td>${category.timeCreate}</td>
                        <td>
                            <c:if test="${not empty category.sampleFormName}">
                                <a href="${pageContext.request.contextPath}/downloadFileM?filename=${category.sampleFormName}">Download File</a>
                            </c:if>
                            <c:if test="${empty category.sampleFormName}">
                                No Attachment
                            </c:if>
                        </td>
                        <td>
                            <form id="complaintForm" action="listCategory" method="POST" enctype="multipart/form-data" onsubmit="confirmSubmission(event)">
                                <input type="hidden" name="categoryId" value="${category.categoryId}">
                                <input type="file" id="attachment" name="attachment" accept=".doc,.docx,.zip,.xlsx,.xls" required>
                                <button type="submit">Upload</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <div class="pagination">
                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <a href="listCategory?page=${i}&keyword=${param.keyword}" class="${currentPage == i ? 'active' : ''}">${i}</a>
                </c:forEach>
            </div>
        </div>
        <footer>
            <p>© 2024 FPT. SUPPORT FOR EVERY STUDENT IN UNIVERSITY.</p>
        </footer>
        <script>
            function validateFileSize() {
                const fileInput = document.getElementById('attachment');
                const fileSize = fileInput.files[0]?.size || 0;
                const maxSize = 10 * 1024 * 1024; // 10MB

                if (fileSize > maxSize) {
                    alert('File size exceeds 10MB. Please upload a smaller file.');
                    return false;
                }

                const fileName = fileInput.files[0]?.name || '';
                const forbiddenExtensions = /\.(exe|dll|bat|js|vbs|ps1)$/i;

                if (forbiddenExtensions.test(fileName)) {
                    alert('File type not allowed. Please upload a different file.');
                    return false;
                }

                return true;
            }

            function confirmSubmission(event) {
                event.preventDefault();
                if (validateFileSize()) {
                    if (confirm('Are you sure you want to submit?')) {
                        event.target.submit();
                    }
                }
            }
        </script>
    </body>
</html>

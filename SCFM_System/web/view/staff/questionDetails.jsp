<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Chi Tiết Khiếu Nại</title>
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

            .container {
                flex: 1;
                padding: 80px 20px 20px;
                max-width: 800px;
                margin: 0 auto;
                background-color: #fff;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                background-color: #fff;
                table-layout: fixed; /* Thêm dòng này để các cột có chiều rộng cố định */
            }

            th, td {
                padding: 15px;
                text-align: left;
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

            .response-form {
                margin-top: 20px;
            }

            .response-form textarea {
                width: 100%;
                height: 100px;
                padding: 10px;
                box-sizing: border-box;
            }

            button {
                padding: 10px 20px;
                background-color: #333;
                color: #fff;
                border: none;
                cursor: pointer;
                margin-top: 10px;
            }

            .response-form button:hover {
                background-color: #575757;
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
            <h1>Question Details</h1>
            <table>
                <tr>
                    <th>QuestionID</th>
                    <td>${question.questionId}</td>
                </tr>
                <tr>
                    <th>Content</th>
                    <td>${question.content}</td>
                </tr>
                <tr>
                    <th>Category</th>
                    <td>${question.category.categoryName}</td>
                </tr>
                <tr>
                    <th>Status</th>
                    <td>${question.status.statusName}</td>
                </tr>
              
                <tr>
                    <th>Full Name</th>
                    <td>${question.user.fullName}</td>
                </tr>
                <tr>
                    <th>Email</th>
                    <td>${question.user.email}</td>
                </tr>
                <tr>
                    <th>Mobile</th>
                    <td>${question.user.mobile}</td>
                </tr>
                <tr>
                    <th>Attachment</th>
                    <td>
                        <c:if test="${not empty question.fileName}">
                            <a href="${pageContext.request.contextPath}/downloadFile?filename=${question.fileName}">Download File</a>
                        </c:if>
                    </td>
                </tr>
            </table>

            <c:if test="${question.status.statusId != 1}">
                <div class="response">
                    <h2>Answer</h2>
                    <p>${answer.content}</p>
                    <p><strong>Answered by:</strong> ${answer.user.fullName}</p>
                    <p><strong>Answered at:</strong> ${answer.time}</p>
                    <c:if test="${not empty answer.fileName}">
                        <a href="${pageContext.request.contextPath}/downloadFile?filename=${answer.fileName}">Download File</a>
                    </c:if>
                </div>
            </c:if>
            <c:if test="${question.status.statusId == 1}">
                <div class="response-form">
                    <form id="complaintForm" action="viewdetails" method="post" enctype="multipart/form-data" onsubmit="confirmSubmission(event);">
                        <input type="hidden" name="questionId" value="${question.questionId}" />
                        <textarea name="responseContent" required></textarea>  
                                        
                    Modify: <input type="radio" name="modify" value="2" checked="checked" /> Private
                    <input type="radio" name="modify" value="1" /> Public
                
                        <div class="upload">
                            <input type="file" id="attachment" name="attachment" accept=".doc,.docx,.zip,.xlsx,.pdf,.xls,.jpg,.png">
                        </div>
                        <p class="note">Maximum file size: 10MB</p>
                        <p class="note">Allowed file types: xlsx, pdf, docx, doc, xls, jpg, png, zip</p>
                        <input type="hidden" name="questionId" value="${question.questionId}" />
                        
                    <input type="hidden" name="action" id="action" value="" />
                        
                        <button type="submit" onclick="setActionAndSubmit(event, 'submit')">Submit</button>
                        <button type="submit" onclick="setActionAndSubmit(event, 'reject')">Reject</button>
                    </form>
                </div>
                

            </c:if>
            <a href="viewcomplaint"><button>Back</button></a>
        </div>

        <footer>
            <p>© 2024 FPT. SUPPORT FOR EVERY STUDENT IN UNIVERSITY.</p>
        </footer>
    </body>
    <script>
        function validateFileSize() {
            const fileInput = document.getElementById('attachment');
            const fileSize = fileInput.files[0]?.size || 0;
            const maxSize = 10 * 1024 * 1024; // 10MB

            if (fileSize > maxSize) {
                alert('File size exceeds 10MB. Please upload a smaller file.');
                return false;
            }
            return true;
        }

        function validateContent() {
            const content = document.querySelector('textarea[name="responseContent"]').value.trim();
            if (!content) {
                alert('Response content is required.');
                return false;
            }
            return true;
        }

        function confirmSubmission(event) {
            event.preventDefault();
            if (validateFileSize() && validateContent()) {
                if (confirm('Are you sure you want to submit this complaint?')) {
                    document.getElementById('complaintForm').submit();
                }
            }
        }

         function setActionAndSubmit(event, action) {
            event.preventDefault();
            document.getElementById('action').value = action;
            confirmSubmission(event);
        }
    </script>
</html>

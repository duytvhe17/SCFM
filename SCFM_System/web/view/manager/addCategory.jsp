<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Add Sample Form Complaint</title>
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
            .container {
                background-color: #fff;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
                max-width: 600px;
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
            .form-group {
                margin-bottom: 20px;
            }
            .form-group label {
                display: block;
                margin-bottom: 5px;
                color: #333;
                font-weight: bold;
            }
            .form-group input,
            .form-group textarea,
            .form-group select {
                width: 100%;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 5px;
                box-sizing: border-box;
                margin-bottom: 10px;
            }
            .form-group textarea {
                resize: vertical;
            }
            .form-group .required {
                color: red;
            }
            .form-group .note {
                color: #999;
                font-size: 0.9em;
            }
            .form-group .upload {
                display: flex;
                align-items: center;
            }
            .form-group .upload input[type="file"] {
                margin-left: 10px;
            }
            .form-group .privacy-options {
                display: flex;
                gap: 20px;
                align-items: center;
                flex-wrap: nowrap;
            }
            .form-group .privacy-options input[type="radio"] {
                margin-right: 5px;
            }
            .submit-btn {
                display: block;
                width: 100%;
                padding: 10px;
                background-color: #c62828;
                color: #fff;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 1em;
                transition: background-color 0.3s ease;
            }
            .submit-btn:hover {
                background-color: #d32f2f;
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
            footer {
                background-color: #333;
                color: #fff;
                text-align: center;
                padding: 10px;
                margin-top: auto;
                width: 100%;
            }
            .modifyId {
                display: flex;
                justify-content: center;
                gap: 20px;
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
            }
            .profile-picture {
                width: 50px;
                height: 50px;
                border-radius: 50%;
                object-fit: cover;
                margin-right: 10px;
            }
            .error-message {
                color: red;
                font-weight: bold;
            }
    </style>
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
                if (confirm('Are you sure you want to submit ?')) {
                    document.getElementById('complaintForm').submit();
                }
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
        <h2>Add Sample Form Complaint</h2>
        <form id="complaintForm" action="addcategory" method="POST" enctype="multipart/form-data" onsubmit="confirmSubmission(event);">
            <div class="form-group">

                <label for="content">Input Name Sample Form Complaint <span class="required">*</span></label>

                <textarea id="content" name="categoryName" rows="2"  required></textarea>
            </div>
            <div class="form-group">
                <label for="attachment">Please upload</label>
                <div class="upload">
                    <input type="file" id="attachment" name="attachment" accept=".doc,.docx,.zip,.xlsx,.xls">
                </div>
                <p class="note">Maximum file size: 10MB</p>
                <p class="note">Allowed file types: xls, doc, zip</p>
            </div>
            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if><br>
            <button type="submit" class="submit-btn">Submit</button>
        </form>
    </div>
</body>

<footer>
    <p>© 2024 FPT. SUPPORT FOR EVERY STUDENT IN UNIVERSITY.</p>
</footer>

</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <title>Change Password</title>
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">

        <style>
            body {
                font-family: 'Roboto', sans-serif;
                background-color: #f9f9f9;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }
            .container {
                width: 400px;
                padding: 20px;
                border: 1px solid #ccc;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                background-color: #fff;
            }
            .container h2 {
                text-align: center;
                font-weight: 500;
                margin-bottom: 20px;
            }
            .form-group {
                margin-bottom: 15px;
                position: relative;
            }
            .form-group label {
                display: block;
                margin-bottom: 5px;
                font-weight: 500;
            }
            .form-group input[type="password"], .form-group input[type="text"] {
                width: 100%;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 5px;
                padding-right: 40px; /* Space for the eye icon */
                font-size: 16px;
                font-family: 'Roboto', sans-serif;
            }
            .form-group .toggle-password {
                position: absolute;
                top: 35px; /* Align with input field */
                right: 10px;
                cursor: pointer;
                font-size: 16px;
            }
            .form-group button {
                width: 100%;
                padding: 10px;
                background-color: #c62828;
                color: #fff;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                font-weight: 500;
            }
            .form-group button:hover {
                background-color: #c62828;
            }
            .password-strength, .password-mismatch, .password-same {
                font-size: 12px;
                margin-top: 5px;
            }
            .password-strength {
                color: green;
                display: none; /* Initially hidden */
            }
            .password-advice, .password-mismatch, .password-same {
                font-size: 12px;
                color: gray;
            }
            .password-mismatch, .password-same {
                color: red;
                display: none; /* Initially hidden */
            }
            h3{
                text-align: center;
            }
            header {
                background-color: #333;
                color: #fff;
                width: 100%;
                position: fixed; /* Fixed position to stay at the top */
                top: 0; /* Aligns the header to the top of the page */
                z-index: 1000; /* Ensures the header is above other content */
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
                width: 100%;
                position: absolute;
                bottom: 0;
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
            <div class="header-top">
                <c:if test="${roleId eq 1}">
                    <a href="manager/home" class="no-underline">
                        <div class="logo">
                            <img src="images/logo.png" alt="FPT Logo">
                        </div>
                    </a>
                </c:if>
                <c:if test="${roleId eq 2}">
                    <a href="staff/home" class="no-underline">
                        <div class="logo">
                            <img src="images/logo.png" alt="FPT Logo">
                        </div>
                    </a>
                </c:if>
                <c:if test="${roleId eq 3}">
                    <a href="student/home" class="no-underline">
                        <div class="logo">
                            <img src="images/logo.png" alt="FPT Logo">
                        </div>
                    </a>
                </c:if>

                <input type="hidden" name="userid" value="${userId}" />
                <div class="user-menu">
                    <div class="dropdown">
                        <img src="Avatar/${user.picture}" alt="Profile Picture" class="profile-picture"> <span>${user.fullName}</span>
                        <div class="dropdown-content">
                            <a href="viewprofile">My Profile</a>
                            <a href="updateprofile">Update Profile</a>
                            <a href="logout?action=logout">Logout</a>
                        </div>
                    </div>
                </div>
            </div>
        </header>
        <main>
            <div class="container">
                <h3>${user.fullName}</h3>
                <h2>Change Password</h2>
                <form action="changepassword" method="post">
                    <input type="hidden" name="userId" value="${user.userId}" />
                    <div class="form-group">
                        <label for="oldPassword">Old Password:</label>
                        <input type="password" id="oldPassword" name="oldPassword" required onkeyup="checkPasswordMatch()">
                        <span class="toggle-password" onclick="togglePasswordVisibility('oldPassword')">👁️</span>
                    </div>
                    <div class="form-group">
                        <label for="newPassword">New Password:</label>
                        <input type="password" id="newPassword" name="newPassword" required onkeyup="checkPasswordStrength(); checkPasswordMatch();">
                        <span class="toggle-password" onclick="togglePasswordVisibility('newPassword')">👁️</span>
                        <div class="password-strength" id="passwordStrength">Password strength: Good</div>
                        <div class="password-advice">Your password should use 8 characters. Don’t use a password from another site, or something too obvious like your pet’s name.</div>
                        <div class="password-same" id="passwordSame">The password cannot be the same as the old password.</div>
                    </div>
                    <div class="form-group">
                        <label for="confirmPassword">Confirm New Password:</label>
                        <input type="password" id="confirmPassword" name="confirmPassword" required onkeyup="checkPasswordMatch()" onfocus="checkPasswordMatch()">
                        <span class="toggle-password" onclick="togglePasswordVisibility('confirmPassword')">👁️</span>
                        <div class="password-mismatch" id="passwordMismatch">The password you entered does not match the above.</div>
                    </div>
                    <h4 style="color: red">${error}</h4>
                    <h4 style="color: green">${message}</h4>

                    <div class="form-group">
                        <button type="submit">Change Password</button>
                    </div>

                </form>
            </div>
        </main>
        <footer>
            <p>© 2024 FPT. SUPPORT FOR EVERY STUDENT IN UNIVERSITY.</p>
        </footer>
        <script>
            function checkPasswordStrength() {
                var password = document.getElementById('newPassword').value;
                var strengthIndicator = document.getElementById('passwordStrength');
                if (password.length >= 8) {
                    strengthIndicator.style.display = 'block';
                } else {
                    strengthIndicator.style.display = 'none';
                }
            }

            function checkPasswordMatch() {
                var oldPassword = document.getElementById('oldPassword').value;
                var newPassword = document.getElementById('newPassword').value;
                var confirmPassword = document.getElementById('confirmPassword').value;
                var mismatchIndicator = document.getElementById('passwordMismatch');
                var sameIndicator = document.getElementById('passwordSame');

                if (confirmPassword !== "") {
                    if (newPassword === confirmPassword) {
                        mismatchIndicator.style.display = 'none';
                    } else {
                        mismatchIndicator.style.display = 'block';
                    }
                }

                if (newPassword === oldPassword) {
                    sameIndicator.style.display = 'block';
                } else {
                    sameIndicator.style.display = 'none';
                }
            }

            function togglePasswordVisibility(id) {
                var input = document.getElementById(id);
                if (input.type === "password") {
                    input.type = "text";
                } else {
                    input.type = "password";
                }
            }
        </script>
    </body>
</html>

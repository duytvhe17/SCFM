<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Đổi mật khẩu</title>
    <script>
        function validateForm() {
            var newPassword = document.getElementById("newPassword").value;
            var confirmPassword = document.getElementById("confirmPassword").value;
            var errorMessage = document.getElementById("error-message");

            if (newPassword !== confirmPassword) {
                errorMessage.textContent = "Mật khẩu nhập vào không khớp, vui lòng nhập lại.";
                return false;
            }
            errorMessage.textContent = ""; // Clear any existing error message
            return true;
        }

        function togglePasswordVisibility(id) {
            var passwordField = document.getElementById(id);
            var eyeIcon = document.getElementById(id + "Eye");
            if (passwordField.type === "password") {
                passwordField.type = "text";
                eyeIcon.classList.remove("fa-eye");
                eyeIcon.classList.add("fa-eye-slash");
            } else {
                passwordField.type = "password";
                eyeIcon.classList.remove("fa-eye-slash");
                eyeIcon.classList.add("fa-eye");
            }

        }
    </script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <style>
        html, body {
            height: 100%;
            margin: 0;
            font-family: Arial, sans-serif;
            display: flex;
            flex-direction: column;
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
        .no-underline {
            text-decoration: none;
            color: inherit;
        }
        .back-button {
            background-color: #d9534f;
            color: white;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
            font-size: 16px;
        }
        .back-button:hover {
            background-color: #c9302c;
        }
        .container {
            flex: 1; /* Đảm bảo container chiếm hết không gian trống */
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            margin: 0 auto;
            width: 300px;
        }
        .form-group {
            margin-bottom: 20px;
            width: 100%;
            max-width: 600px;
        }
        .form-group input {
            width: 100%;
            padding: 20px;
            margin: 5px 0;
            box-sizing: border-box;
            font-size: 15px;
        }
        .btn {
            background-color: #d9534f;
            color: white;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
            font-size: 16px;
        }
        .btn:hover {
            background-color: #c9302c;
        }
        footer {
            background-color: #333;
            color: #fff;
            text-align: center;
            padding: 10px;
            margin-top: auto; /* Đẩy footer xuống đáy */
        }
        .password-container {
            position: relative;
        }
        .toggle-password {
            position: absolute;
            right: 10px;
            top: 50%;
            transform: translateY(-50%);
            cursor: pointer;
        }
        #error-message {
            color: red;
            margin-top: 10px;
        }
        .password-advice, .password-mismatch, .password-same {
            font-size: 12px;
            color: gray;
        }
    </style>
</head>
<body>
    <header>
        <div class="header-top">
            <a href="login" class="no-underline">
                <div class="logo">
                    <img src="images/logo.png" alt="FPT Logo">
                </div>
            </a>

        </div>
    </header>
    <div class="container">
        <h1>Đổi mật khẩu</h1>
        <form action="resetpassword" method="post" onsubmit="return validateForm()">
            <div class="form-group password-container">
                <input type="password" id="newPassword" name="matKhauMoi" placeholder="Mật khẩu mới" required>
                <div class="password-advice">Your password should use 12 characters or more.</div>
                <i class="fas fa-eye toggle-password" id="newPasswordEye" onclick="togglePasswordVisibility('newPassword')"></i>
            </div>
            <div class="form-group password-container">
                <input type="password" id="confirmPassword" name="xacNhanMatKhau" placeholder="Nhập lại mật khẩu" required>
                <i class="fas fa-eye toggle-password" id="confirmPasswordEye" onclick="togglePasswordVisibility('confirmPassword')"></i>
            </div>
            <div id="error-message">
                ${error}
                ${error1}
            </div>
            <div id="success-message" style="color: green;">
                <%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %> <!-- Hiển thị thông báo thành công -->
            </div>
            <input type="submit" class="btn" value="Submit"/>
        </form>
    </div>
    <footer>
        <p>© 2024 FPT. SUPPORT FOR EVERY STUDENT IN UNIVERSITY.</p>
    </footer>
</body>
</html>

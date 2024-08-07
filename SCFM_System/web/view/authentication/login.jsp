


<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <style>
            /* Định dạng hình nền */
            body, html {
                margin: 0;
                padding: 0;
                height: 100%;
                background: url('https://daihoc.fpt.edu.vn/wp-content/uploads/2022/02/HCM-scaled.jpeg') no-repeat center center fixed;
                background-size: cover;
            }

            /* Định dạng form login */
            .login-form {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                padding: 20px;
                background: rgba(255, 255, 255, 0.7); /* Màu nền cho form */
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.3); /* Bóng đổ cho form */
                border-radius: 5px; /* Bo góc cho form */
                margin-top: 20px;
            }

            input[type=text], input[type=password] {
                width: calc(100% - 20px);
                padding: 10px;
                margin-bottom: 10px;
                border: 1px solid #ddd;
                border-radius: 5px; /* Bo góc cho input */
            }

            input[type=submit] {
                width: 100%;
                padding: 10px;
                background-color: #007bff;
                color: white;
                border: none;
                border-radius: 5px; /* Bo góc cho nút submit */
                cursor: pointer;
            }

            input[type=submit]:hover {
                background-color: #0056b3;
            }

            input[type=checkbox] {
                margin-bottom: 20px;
            }
            .login-form {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                padding: 20px;
                background: rgba(255, 165, 0, 0.8); /* Màu cam với độ mờ */
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.3); /* Bóng đổ cho form */
                border-radius: 5px; /* Bo góc cho form */
            }
            .university-text {
                position: absolute; /* Điều chỉnh để dòng chữ nằm trên form */
                top: 2%;
                left: 42%;
                color: white; /* Màu của chữ */
                background-color: #FFA500; /* Màu nền cam, bạn có thể thay đổi mã màu để phù hợp với màu bạn muốn */
                text-align: center; /* Căn giữa chữ */
                padding: 10px 20px; /* Khoảng cách trên/dưới và trái/phải */
                border-radius: 5px; /* Bo tròn góc */
                box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.2); /* Bóng đổ nhẹ */
                display: inline-block; /* Cho phép đặt kích thước padding và để thẻ div không chiếm toàn bộ chiều rộng */
                margin-top: 50px; /* Khoảng cách từ top, bạn có thể điều chỉnh con số này cho phù hợp */
                font-weight: bold; /* Chữ đậm */
                font-size: 24px; /* Kích thước chữ */
                font-weight: bold; /* Độ đậm của chữ */
                
            }
            .log-text{
                margin-bottom: 40px;
                margin-top: 10px;

            }
           

            .google-btn {
                display: flex;
                align-items: center;
                justify-content: center;
                width: 250px;
                height: 40px;
                background-color: white;
                border: 1px solid #ddd;
                border-radius: 4px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                cursor: pointer;
                font-family: Arial, sans-serif;
                font-size: 16px;
                color: #555;
                text-decoration: none;
            }
            .google-btn img {
                width: 20px;
                height: 20px;
                margin-right: 10px;
            }
            .forgot-password {
                margin-left: 45px;
                display: inline-block;
                padding: 10px 20px;
                background-color:#FFA500; /* Màu nền */
                color: black; /* Màu chữ */
                text-decoration: none; /* Bỏ gạch chân */
                border-radius: 5px; /* Bo góc */
                transition: background-color 0.3s ease, color 0.3s ease; /* Hiệu ứng chuyển đổi */
            }

            .forgot-password:hover {
                background-color: #e67e22; /* Màu nền khi hover */
                color: #fff; /* Màu chữ khi hover */
            }

            .register-link {
                margin-left: 10px;
                text-decoration: none; /* Bỏ gạch chân */
                font-weight: bold; /* In đậm */
                color: #007bff; /* Màu chữ */
                transition: color 0.3s ease; /* Hiệu ứng chuyển màu */
            }

            .register-link:hover {
                color: #0056b3; /* Màu chữ khi hover */
            }
                 .er-text{
                color: red;
                background-color: white;
                border-radius: 5px;
                margin-bottom: 20px;
            }
        </style>
    </head>
    <body>
        <div class="university-text">Đại Học FPT Hà Nội</div>
        <div class="login-form">
            <h2 class="log-text">Login</h2>
            <form action="login" method="POST">

                Username <input type="text" name="userName"/> <br/>
                Password <input type="password" name="passWord"/> <br/>
                
                <span class="er-text">${lf}</span><br/>
                <span class="er-text">${inactiveMess}</span><br/>
                <span class="er-text">${messSuccess}</span><br/>
                <input type="submit" value="Login"/>
                
                <p>Chưa có tài khoản? <a href="register?mode=1" class="register-link">Đăng ký ngay</a></p>
                <a href="forgotpassword" class="forgot-password">Forgot password</a> <br><br>
                <a class="google-btn" href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8080/SCFM/login/google&response_type=code
                   &client_id=560130291390-2rvedbcm65gi4sgt3fg88gboh61nte4n.apps.googleusercontent.com&approval_prompt=force">
                    <img src="images/icongg.png" alt="Google logo">
                    Continue with Google
                </a>  
            </form>
        </div>
    </body>
</html>

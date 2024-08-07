<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<head>
    <title>Quên mật khẩu</title>
    <link rel="stylesheet" href="forgot_password.css">
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
    .container {
        flex: 1;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        margin: 0 auto;
        width: 100%;
        max-width: 600px;
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
        padding: 15px;
        cursor: pointer;
        font-size: 16px;
        width: 100%;
        box-sizing: border-box;
    }
    .btn:hover {
        background-color: #c9302c;
    }
    footer {
        background-color: #333;
        color: #fff;
        text-align: center;
        padding: 10px;
        margin-top: auto;
    }
    h2 {
        text-align: center;
    }
    p {
        text-align: center;
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
        <h1>Quên mật khẩu</h1>
        <p>Vui lòng điền tài khoản bạn sử dụng để đăng nhập</p>
        <form action="forgotpassword" method="post">
            <div class="form-group">
                <input type="text" name="email" placeholder="Email" required>
            </div>
            ${error}
            <div class="form-group">
                <button type="submit" class="btn">GỬI MÃ</button>
            </div>
        </form>
    </div>
    <footer>
        <p>© 2024 FPT. SUPPORT FOR EVERY STUDENT IN UNIVERSITY.</p>
    </footer>
</body>


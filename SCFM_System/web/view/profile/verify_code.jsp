<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

    <head>
        <title>Xác nhận mã</title>
        <link rel="stylesheet" href="verify_code.css">
    </head>
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
            flex: 1; 
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            margin: 0 auto;
            width: 300px;
        }
        .form-group {
            display: flex;
            flex-direction: column;
           justify-content: center;
            align-items: center;
            margin-bottom: 220px;
            width: 100%;
            max-width: 100%;
        }
        .form-group input {
            width: 150%;
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
            margin-top: auto; 
        }
    </style>
    <body>
        <header>
            <div class="header-top">
                <a href="login" class="no-underline">
                    <div class="logo">
                        <img src="./images/logo.png" alt="FPT Logo">
                    </div>
                </a>
                <button class="back-button" onclick="history.back()">Back</button>
            </div>
        </header>  
    <div class="container">
        <h1>Nhập mã xác nhận</h1>
        <form action="verifycode" method="post">
            <input type="hidden" name="code" value="${sessionScope.verificationCode}"
                   <div class="form-group">
            <input type="text" id="verificationCode" name="verificationCode" placeholder="Mã xác nhận" required>
            </div>
            <div class="form-group">
                <button type="submit" class="btn">Xác nhận</button>
            </div>
        </form>
    </div>
    <footer>
        <p>© 2024 FPT. SUPPORT FOR EVERY STUDENT IN UNIVERSITY.</p>
    </footer>
</body>

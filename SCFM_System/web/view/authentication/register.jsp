<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            html, body {
                height: 100%;
                margin: 0;
                font-family: Arial, sans-serif;
                background-color: #f0f0f0;
            }

            header, footer {
                background-color: #333;
                color: #fff;
                text-align: center;
                padding: 10px;
            }

            .header-top {
                background-color: #333;
                color: #fff;
                padding: 10px 20px;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .logo img {
                height: 60px;
                margin-right: 10px;
            }

            form {
                max-width: 600px;
                margin: 20px auto;
                padding: 20px;
                background-color: #fff;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }

            form h1 {
                text-align: center;
                margin-bottom: 20px;
            }

            form input[type="text"],
            form input[type="password"],
            form input[type="email"],
            form input[type="date"],
            form input[type="submit"],
            form input[type="radio"] {
                width: calc(100% - 20px);
                padding: 10px;
                margin-bottom: 10px;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 16px;
                box-sizing: border-box;
            }

            form input[type="radio"] {
                width: auto;
                margin-right: 10px;
            }

            form input[type="submit"] {
                background-color: #333;
                color: #fff;
                cursor: pointer;
            }

            .er-text {
                color: red;
                background-color: white;
                border-radius: 5px;
                padding: 5px;
                display: block;
                margin-top: 10px;
            }

            footer p {
                margin: 0;
            }

            @media (max-width: 768px) {
                .header-top {
                    flex-direction: column;
                    align-items: center;
                    text-align: center;
                }

                .logo img {
                    margin-bottom: 10px;
                }
            }
        </style>
    </head>
    <body>
        <div class="header-top">
            <div class="logo">
                <img src="images/logo.png" alt="FPT Logo">
            </div>
        </div>
        <h1 style="text-align: center;">Đăng ký sinh viên</h1>
        <form action="register" method="post">
            Username : <input type="text" name="userName" value="${userName}" /><br><br>
            Password : <input type="password" name="passWord" value="${passWord}" /><br><br>
            MSSV : <input type="text" name="maso" value="${maso}" /><br><br>
            Full Name : <input type="text" name="fullName" value="${fullName}" /><br><br>
            Email : <input type="email" name="email" value="${email}" /><br><br>
            Gender : 
            <input type="radio" name="gender" value="Male" ${gender == 'Male' ? 'checked' : ''}/>Male
            <input type="radio" name="gender" value="Female" ${gender == 'Female' ? 'checked' : ''}/>Female<br><br>
            Mobile : <input type="text" name="mobile" value="${mobile}" /><br><br>
            Address : <input type="text" name="address" value="${address}" /><br><br>
            DOB : <input type="date" name="dateOfBirth" value="${dateOfBirth}" /><br><br>

            <span class="er-text">${mess}</span><br/>

            <input type="submit" name="register" value="Register" /> <br>
        </form>
        <footer>
            <p>© 2024 FPT. SUPPORT FOR EVERY STUDENT IN UNIVERSITY.</p>
        </footer>
    </body>
</html>

<%-- 
    Document   : CreateStaff
    Created on : Jun 25, 2024, 1:15:19 AM
    Author     : ADMIN
--%>

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

            footer {
                background-color: #333;
                color: #fff;
                text-align: center;
                padding: 10px;
                margin-top: auto;
                width: 100%;
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

            .form-group {
                display: flex;
                margin-bottom: 10px;
                align-items: center;
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

            .form-group label {
                width: 30%;
                background-color: #ccffcc;
                padding: 10px;
                font-weight: bold;
                text-align: right;
                box-sizing: border-box;
            }
            .profile-picture {
                width: 50px;
                height: 50px;
                border-radius: 50%;
                object-fit: cover;
                margin-right: 10px;
            }
            .form-group input[type="text"],
            .form-group input[type="password"],
            .form-group input[type="email"],
            .form-group input[type="date"] {
                width: 70%;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 16px;
                box-sizing: border-box;
            }

            .form-group input[type="radio"] {
                margin-left: 10px;
                margin-right: 5px;
            }

            .form-group.gender {
                align-items: center;
            }

            .form-group.gender label {
                width: auto;
                background-color: transparent;
            }

            form input[type="submit"] {
                width: 100%;
                padding: 10px;
                background-color: #e53935;
                color: #fff;
                border: none;
                border-radius: 5px;
                font-size: 16px;
                cursor: pointer;
                margin-bottom: 10px;
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

                .form-group {
                    flex-direction: column;
                    align-items: flex-start;
                }

                .form-group label {
                    width: 100%;
                    text-align: left;
                    margin-bottom: 5px;
                }

                .form-group input[type="text"],
                .form-group input[type="password"],
                .form-group input[type="email"],
                .form-group input[type="date"] {
                    width: 100%;
                }
                .form-group .toggle-password {
                    position: absolute;
                    right: 15px;
                    cursor: pointer;
                    color: #999;
                }
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
                    <h4>Text</h4>
                    <h4>>Text</h4>
        <h1 style="text-align: center;">Create Staff</h1>
        <form action="createstaff" method="post">
            <div class="form-group">
                <label for="userName" >Username:</label>
                <input type="text" id="userName" name="userName" value="${userName}" />
            </div>
            <div class="form-group">
                <label for="passWord">Password:</label>
                <input type="password" id="passWord" name="passWord" value="${passWord}" />
            </div>
            <div class="form-group">
                <label for="maso">MSST :</label>
                <input type="text" id="maso" name="maso" value="${maso}" />
            </div>
            <div class="form-group">
                <label for="fullName">Full Name:</label>
                <input type="text" id="fullName" name="fullName" value="${fullName}" />
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${email}" />
            </div>
            <div class="form-group gender">
                <label>Gender:</label>
                <input type="radio" id="male" name="gender" value="Male" checked="checked" />
                <label for="male">Male</label>
                <input type="radio" id="female" name="gender" value="Female" />
                <label for="female">Female</label>
            </div>
            <div class="form-group">
                <label for="mobile">Mobile:</label>
                <input type="text" id="mobile" name="mobile" value="${mobile}" />
            </div>
            <div class="form-group">
                <label for="address">Address:</label>
                <input type="text" id="address" name="address" value="${address}" />
            </div>
            <div class="form-group">
                <label for="dateOfBirth">DOB:</label>
                <input type="date" id="dateOfBirth" name="dateOfBirth" value="${dateOfBirth}" />
            </div>
            <span class="er-text">${mess}</span><br/>
            <input type="submit" name="register" value="Register" /> <br>

        </form>
        <footer>
            <p>© 2024 FPT. SUPPORT FOR EVERY STUDENT IN UNIVERSITY.</p>
        </footer>
    </body>
</html>


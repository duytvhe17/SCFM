<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Question Statistics</title>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>
        <style>
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f0f2f5;
                color: #333;
                margin: 0;
                padding: 0;
                display: flex;
                flex-direction: column;
                align-items: center;
            }
            h1 {
                color: #444;
                margin: 20px 0;
                margin-top: 100px;
            }

            .chart-container {
                width: 500px;
                height: 500px;
                margin-top: 50px;
                display: flex;
                justify-content: center;
                align-items: center;
                background-color: white;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                border-radius: 10px;
                padding: 20px;
            }

            canvas {
                width: 100%;
                height: 100%;
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
                width: 100%;
                box-sizing: border-box;
                position: fixed;
                bottom: 0;
                left: 0;
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
</style>
</head>
<body>

    <h1>Question Statistics for Month <%= request.getAttribute("month") %></h1>
    <div class="chart-container">
        <canvas id="myPieChart"></canvas>
    </div>

    <script>
        var status1Count = <%= request.getAttribute("status1Count") %>;
        var status2Count = <%= request.getAttribute("status2Count") %>;
        var status3Count = <%= request.getAttribute("status3Count") %>;

        var ctx = document.getElementById('myPieChart').getContext('2d');
        var myPieChart = new Chart(ctx, {
            type: 'pie',
            data: {
                labels: ['Processing', 'Completed', 'Rejected'],
                datasets: [{
                        backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56'],
                        data: [status1Count, status2Count, status3Count]
                    }]
            },
            options: {
                legend: {
                    labels: {
                        fontSize: 18
                    }
                }
            }
        });
    </script>
</body>
<footer>
    <p>© 2024 FPT. SUPPORT FOR EVERY STUDENT IN UNIVERSITY.</p>
</footer>
</html>

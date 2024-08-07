<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Question Chart</title>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f9;
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
                width: 90%;
                max-width: 1200px;
                margin: 20px auto;
                padding: 20px;
                background-color: #fff;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
            }
            canvas {
                width: 100% !important;
                height: 600px !important;
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

<body>

    <h1>Total Questions Per Month</h1>
    <div class="chart-container">
        <canvas id="myChart"></canvas>
    </div>

    <script>
        var xValues = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
        var yValues = [
        <%= request.getAttribute("january") %>,
        <%= request.getAttribute("february") %>,
        <%= request.getAttribute("march") %>,
        <%= request.getAttribute("april") %>,
        <%= request.getAttribute("may") %>,
        <%= request.getAttribute("june") %>,
        <%= request.getAttribute("july") %>,
        <%= request.getAttribute("august") %>,
        <%= request.getAttribute("september") %>,
        <%= request.getAttribute("october") %>,
        <%= request.getAttribute("november") %>,
        <%= request.getAttribute("december") %>
        ];
        var barColors = ["#FF6384", "#36A2EB", "#FFCE56", "#4BC0C0", "#9966FF", "#FF9F40", "#E7E9ED", "#76D7C4", "#F1948A", "#85C1E9", "#F7DC6F", "#F0B27A"];

        var ctx = document.getElementById('myChart').getContext('2d');
        var myChart = new Chart(ctx, {
            type: "bar",
            data: {
                labels: xValues,
                datasets: [{
                        backgroundColor: barColors,
                        data: yValues
                    }]
            },
            options: {
                legend: {display: false},
                title: {
                    display: true,
                    text: "Total Questions Per Month"
                },
                onClick: function (evt, item) {
                    if (item.length > 0) {
                        var monthIndex = item[0]._index + 1; 
                        window.location.href = 'viewTotalStatusForMonth?month=' + monthIndex;
                    }
                },
                scales: {
                    yAxes: [{
                            ticks: {
                                beginAtZero: true
                            }
                        }]
                }
            }
        });
    </script>

</body>
<footer>
    <p>© 2024 FPT. SUPPORT FOR EVERY STUDENT IN UNIVERSITY.</p>
</footer>
</html>

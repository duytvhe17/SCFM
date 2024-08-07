<%-- 
    Document   : viewTotalQuestionsByCategory.jsp
    Created on : Jul 26, 2024, 4:59:29 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Total Questions by Category</title>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
                display: flex;
                flex-direction: column;
                justify-content: space-between;
                align-items: center;
                height: 100vh;
                background-color: #f4f4f9;
            }
            .title {
                margin-top: 140px; 
                font-size: 24px;
                font-weight: bold;
                color: #333;
            }
            .charts-container {
                display: flex;
                justify-content: center;
                align-items: flex-start;
                width: 90%;
                flex-grow: 1;
                padding-top: 70px; 
            }
            .chart-container {
                flex: 1;
                background: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                width: 100%;
                height: 600px; /* Adjust the height as needed */
                box-sizing: border-box;
            }
            h1 {
                text-align: center;
                margin-bottom: 20px;
                color: #333;
                font-size: 16px;
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
        <div class="title">Statistical Chart by Type of Complaint</div>
        <div class="charts-container">
            <div class="chart-container">
                <h1>Total Questions by Category</h1>
                <canvas id="questionsChart"></canvas>
            </div>
        </div>
        <script>
            const ctx1 = document.getElementById('questionsChart').getContext('2d');
            const categoryNames = <%= request.getAttribute("categoryNames") %>;
            const categoryCounts = <%= request.getAttribute("categoryCounts") %>;

            const chartConfig = (ctx, data, label) => {
                return new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: categoryNames,
                        datasets: [{
                                label: label,
                                data: data,
                                backgroundColor: [
                                    'rgba(255, 99, 132, 0.2)',
                                    'rgba(54, 162, 235, 0.2)',
                                    'rgba(255, 206, 86, 0.2)',
                                    'rgba(75, 192, 192, 0.2)',
                                    'rgba(153, 102, 255, 0.2)',
                                    'rgba(255, 159, 64, 0.2)',
                                    'rgba(199, 199, 199, 0.2)',
                                    'rgba(83, 102, 255, 0.2)',
                                    'rgba(200, 206, 86, 0.2)',
                                    'rgba(75, 192, 122, 0.2)',
                                    'rgba(255, 102, 255, 0.2)',
                                    'rgba(255, 159, 100, 0.2)',
                                    'rgba(99, 162, 199, 0.2)',
                                    'rgba(255, 206, 186, 0.2)',
                                    'rgba(155, 102, 55, 0.2)',
                                    'rgba(75, 102, 64, 0.2)',
                                    'rgba(153, 159, 99, 0.2)',
                                    'rgba(54, 199, 86, 0.2)',
                                    'rgba(199, 162, 255, 0.2)',
                                    'rgba(75, 102, 235, 0.2)'
                                ],
                                borderColor: [
                                    'rgba(255, 99, 132, 1)',
                                    'rgba(54, 162, 235, 1)',
                                    'rgba(255, 206, 86, 1)',
                                    'rgba(75, 192, 192, 1)',
                                    'rgba(153, 102, 255, 1)',
                                    'rgba(255, 159, 64, 1)',
                                    'rgba(199, 199, 199, 1)',
                                    'rgba(83, 102, 255, 1)',
                                    'rgba(200, 206, 86, 1)',
                                    'rgba(75, 192, 122, 1)',
                                    'rgba(255, 102, 255, 1)',
                                    'rgba(255, 159, 100, 1)',
                                    'rgba(99, 162, 199, 1)',
                                    'rgba(255, 206, 186, 1)',
                                    'rgba(155, 102, 55, 1)',
                                    'rgba(75, 102, 64, 1)',
                                    'rgba(153, 159, 99, 1)',
                                    'rgba(54, 199, 86, 1)',
                                    'rgba(199, 162, 255, 1)',
                                    'rgba(75, 102, 235, 1)'
                                ],
                                borderWidth: 1
                            }]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        plugins: {
                            legend: {
                                position: 'left',
                                align: 'start'
                            }
                        },
                        scales: {
                            x: {
                                ticks: {
                                    autoSkip: false
                                }
                            },
                            yAxes: [{
                            ticks: {
                                beginAtZero: true,
                                stepSize: 1
                            }
                        }]
                        }
                    }
                });
            };

            chartConfig(ctx1, categoryCounts, 'Total Questions');
        </script>
    </body>
    <footer>
        <p>© 2024 FPT. SUPPORT FOR EVERY STUDENT IN UNIVERSITY.</p>
    </footer>
</html>

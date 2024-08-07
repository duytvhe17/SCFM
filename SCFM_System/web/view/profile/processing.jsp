<%-- 
    Document   : processing
    Created on : Jul 26, 2024, 10:09:16 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Processing</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            text-align: center;
            color: #333;
        }
        .container {
            padding: 20px;
            background-color: #fff;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        h1 {
            font-size: 1.5em;
            margin-bottom: 10px;
        }
        p {
            font-size: 1em;
        }
    </style>
    <script>
        setTimeout(function() {
            window.location.href = '../../viewprofile'; 
        }, 4000); 
    </script>
</head>
<body>
    <div class="container">
        <h1>System is processing your request, please wait...</h1>
        <p>You will be redirected to your profile page shortly.</p>
    </div>
</body>
</html>

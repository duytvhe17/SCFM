
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>All Public Question</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <style>
            

            body {
                font-family: Arial, sans-serif;
                background-color: #f0f0f0;
                margin: 0;
                display: flex;
                flex-direction: column;
                min-height: 100vh;
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
            .pagination {
                text-align: center;
                margin-top: 20px;
                margin-bottom: 20px;
            }
            .pagination a {
                margin: 0 5px;
                padding: 8px 16px;
                text-decoration: none;
                border: 1px solid #ddd;
                color: #000;
            }
            .pagination a:hover {
                background-color: #ddd;
            }
            .pagination .active {
                font-weight: bold;
                background-color: #ddd;
                color: #000;
                border: 1px solid #000;
            }
            footer {
                background-color: #333;
                color: #fff;
                text-align: center;
                padding: 10px;
                width: 100%;
                box-sizing: border-box;
            }

            @media (max-width: 600px) {
                .container {
                    padding: 15px;
                }
                header {
                    flex-direction: column;
                    align-items: flex-start;
                }
                .user-menu {
                    margin-top: 10px;
                }
                table {
                    width: 90%;
                }
            }
            .profile-picture {
                width: 50px;
                height: 50px;
                border-radius: 50%;
                object-fit: cover;
                margin-right: 10px;
            }
            .search-container {
                display: flex;
                justify-content: center;
                align-items: center;
                margin-top: 150px;
                margin-bottom: 20px;
                width: 100%;
                padding: 10px;
                box-sizing: border-box;
            }


            .search-container input[type="text"] {
                width: 600px;
                height: 35px;
                padding: 10px;
                border: none;
                border-radius: 25px 0 0 25px;
                outline: none;
                font-size: 16px;
            }

            .search-container button {
                height:55px;
                padding: 10px;
                border: none;
                background-color: #fff;
                cursor: pointer;
                border-radius: 0 25px 25px 0;
            }

            .search-container button img {
                width: 35px;
                height: 35px;
            }
            .container h2{
                justify-content: flex;
                text-align: center;
            }




            .card {
                background-color: #fff;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                margin: 20px;
                padding: 20px;
                border-radius: 8px;
                display: flex;
                flex-direction: column;
            }

            .card-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 20px;
            }

            .card-content {
                margin-bottom: 20px;
            }

            .card-actions {
                display: flex;
                justify-content: flex-end;
            }

            button {
                padding: 10px 20px;
                background-color: #333;
                color: #fff;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }

            button:hover {
                background-color: #555;
            }

            .hidden {
                display: none;
            }

            
            .card-content{
                margin-left: 20px; /* Lề trái 20px để lùi thẻ vào trong */
            }
            .answer-card2{
                margin-left: 100px;
                background-color: orange;
            }
            .stars {
                display: flex;
                align-items: center;
                margin-top: 10px;
            }
            .star {
                color: #FFD700;
                font-size: 25px;
                margin-right: 5px;
                cursor: pointer;
            }
            .rating-wrapper {
                display: flex;
                justify-content: flex-end;
                align-items: center;
            }


        </style>


        <script>
            function toggleDetail(id) {
                var content = document.getElementById("detail-" + id);
                if (content.classList.contains("hidden")) {
                    content.classList.remove("hidden");
                } else {
                    content.classList.add("hidden");
                }
            }



function showAnswer(id) {
            var answerCard = document.getElementById("answer-card-" + id);
            if (!answerCard) { // If the answer card hasn't been created yet
                var card = document.createElement("div");
                card.id = "answer-card-" + id;
                card.className = "card answer-card2"; // Add new class
                var fileLink = '';
                var fileName = document.getElementById("answer-file-" + id).value;
                if (fileName !== 'null' && fileName !== '') {
                    fileLink = `<p><strong>File:</strong> <a href="${pageContext.request.contextPath}/downloadFile?filename=${fileName}">File</a></p>`;
                } else {
                    fileLink = '<p><strong>File:</strong> None</p>';
                }
                var starss = document.getElementById("answer-vote-" + id).value || 0;
                    var stars = parseInt(starss);
                    var starDisplay = '<div class="stars" >';
                    for (var i = 1; i <= stars; i++) {
                        starDisplay += '<i class="fas fa-star star"></i>';
                    }
                    starDisplay += '</div>';
                    card.innerHTML = `
                        <div class="card-content">
                            <p><strong>Staff's Name:</strong> ` + document.getElementById("answer-user-" + id).value + `</p>
                            <p><strong>Answer:</strong> ` + document.getElementById("answer-content-" + id).value + `</p>
                            <p><strong>Time:</strong> ` + document.getElementById("answer-time-" + id).value + `</p>
                            <p><strong>Status:</strong> ` + document.getElementById("answer-status-" + id).value + `</p>
                            ` + fileLink + `
                            <div class="rating-wrapper">
                                ` + starDisplay + `
                            </div>
                    </div>
                `;
                document.getElementById("card-" + id).after(card); // Insert the answer card right after the question card
            } else {
                answerCard.remove(); // Remove the card if it already exists
            }
        }


        </script>


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
        <div class="search-container">
            <form action="viewAllQuestion" method="post">
                <input type="text" placeholder="Search by question content" id = "search" name="search">
                <button type="submit">
                    <img src="search-icon.png" alt="Search">
                </button>
            </form>
        </div>
        <div class="container">
            <h2>List Of General Complaints</h2>

            <c:forEach var="answer" items="${data}" varStatus="status">
                <div class="card" id="card-${status.index}">
                    <div class="card-header">
                        <span>Question ${(currentPage - 1) * 10 + status.count}: ${answer.question.content}</span>
                        <div>
                            <button onclick="toggleDetail(${status.index})">View Detail</button>
                            <button onclick="showAnswer(${status.index})">View Answer</button>
                        </div>
                    </div>
                    <div class="card-content hidden" id="detail-${status.index}">
                        <p><strong>Type of Complaint:</strong> ${answer.question.category.categoryName}</p>
                        <p><strong>Time:</strong> ${answer.question.timeCreate}</p>
                        <p><strong>File:</strong>
                            <c:choose>
                                <c:when test="${answer.question.fileName == null}">
                                    None
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/downloadFile?filename=${answer.question.fileName}">File</a>
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </div>
                    <input type="hidden" id="answer-user-${status.index}" value="${answer.user.userName}">
                    <input type="hidden" id="answer-content-${status.index}" value="${answer.content}">
                    <input type="hidden" id="answer-time-${status.index}" value="${answer.time}">
                    <input type="hidden" id="answer-status-${status.index}" value="${answer.question.status.statusName}">
                    <input type="hidden" id="answer-file-${status.index}" value="${answer.fileName}">
                    <input type="hidden" id="answer-vote-${status.index}" value="${answer.vote.rating}">
                  

                </div>
            </c:forEach>


            <div class="pagination">
                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <a href="viewAllQuestion?page=${i}" class="${currentPage == i ? 'active' : ''}">${i}</a>
                </c:forEach>
            </div>
        </div>
        <footer>
            <p>© 2024 FPT. SUPPORT FOR EVERY STUDENT IN UNIVERSITY.</p>
        </footer>
    </body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Notification </title>
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
        <!-- Font Awesome -->
        <!-- Google Fonts -->
        <link
            href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
            rel="stylesheet"
            />
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
        <!-- MDB -->

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <!-- Font Awesome -->
        <link
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
            rel="stylesheet"
            />
        <!-- Google Fonts -->
        <link
            href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
            rel="stylesheet"
            />
        <!-- MDB -->
        <link
            href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.2/mdb.min.css"
            rel="stylesheet"
            />
        <style>
            /*body{*/
            /*    margin: 0;*/
            /*    padding: 0;*/
            /*    background-color: #b2e0df;*/
            /*    height: 100vh;*/
            /*    display: flex;*/
            /*    justify-content: center;*/
            /*    align-items: center;*/
            /*}*/


            .checked {
                color: orange;
            }
            .search-container{
                background: #fff;
                height: 30px;
                border-radius: 30px;
                padding: 10px 20px;
                display: flex;
                justify-content: center;
                align-items: center;
                cursor: pointer;
                transition: 0.8s;
                /*box-shadow:inset 2px 2px 2px 0px rgba(255,255,255,.5),
                inset -7px -7px 10px 0px rgba(0,0,0,.1),
               7px 7px 20px 0px rgba(0,0,0,.1),
               4px 4px 5px 0px rgba(0,0,0,.1);
               text-shadow:  0px 0px 6px rgba(255,255,255,.3),
                          -4px -4px 6px rgba(116, 125, 136, .2);
              text-shadow: 2px 2px 3px rgba(255,255,255,0.5);*/
                box-shadow:  4px 4px 6px 0 rgba(255,255,255,.3),
                    -4px -4px 6px 0 rgba(116, 125, 136, .2),
                    inset -4px -4px 6px 0 rgba(255,255,255,.2),
                    inset 4px 4px 6px 0 rgba(0, 0, 0, .2);
            }

            .search-container:hover > .search-input{
                width: 400px;
            }

            .search-container .search-input{
                background: transparent;
                border: none;
                outline:none;
                width: 0px;
                font-weight: 500;
                font-size: 16px;
                transition: 0.8s;

            }

            .search-container .search-btn .fas{
                color: #5cbdbb;
            }

            /*@keyframes hoverShake {*/
            /*    0% {transform: skew(0deg,0deg);}*/
            /*    25% {transform: skew(5deg, 5deg);}*/
            /*    75% {transform: skew(-5deg, -5deg);}*/
            /*    100% {transform: skew(0deg,0deg);}*/
            /*}*/

            .search-container:hover{
                animation: hoverShake 0.15s linear 3;
            }
            .custom-thead {
                background-color: rgba(176, 237, 215, 0.3);
            }
            /* The Modal (background) */
            .modal {
                display: none; /* Hidden by default */
                position: fixed; /* Stay in place */
                z-index: 999; /* Sit on top */
                left: 0;
                top: 0;
                width: 100%; /* Full width */
                height: 100%; /* Full height */
                overflow: auto; /* Enable scroll if needed */
                background-color: rgb(0,0,0); /* Fallback color */
                background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
            }

            /* Modal Content/Box */
            .modal-content {
                background-color: #fefefe;
                margin: 15% auto; /* 15% from the top and centered */
                padding: 20px;
                border: 1px solid #888;
                width: 80%; /* Could be more or less, depending on screen size */
            }

            /* The Close Button */
            .close {
                color: #aaa;
                float: right;
                font-size: 28px;
                font-weight: bold;
            }

            .close:hover,
            .close:focus {
                color: black;
                text-decoration: none;
                cursor: pointer;
            }
            .page-link{
                position: static;
            }
            /* Existing CSS */
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
            .tabs {
                margin-top: 80px;
                display: flex;
                justify-content: center;
            }
            .tabs button {
                background-color: #333;
                color: #fff;
                padding: 14px 20px;
                border: none;
                cursor: pointer;
                margin: 0 4px;
                border-radius: 12px 12px 0 0;
            }
            .tabs button.active {
                background-color: #ff4655;
            }
            .tab-content {
                display: none;
            }
            .tab-content.active {
                display: block;
            }
            table {
                width: 95%;
                border-collapse: collapse;
                margin: 20px auto;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                background-color: #fff;
                table-layout: fixed; /* Thêm dòng này ?? các c?t có chi?u r?ng c? ??nh */
            }
            th, td {
                padding: 15px;
                text-align: center;
                border: 1px solid black;
                word-wrap: break-word; /* Cho phép xu?ng dòng khi quá dài */
                white-space: normal; /* ??m b?o n?i dung có th? xu?ng dòng */
                overflow: hidden; /* ?n n?i dung tràn */
            }
            th {
                background-color: #f2f2f2;
            }
            footer {
                background-color: #333;
                color: #fff;
                text-align: center;
                padding: 10px;
                width: 100%;
                box-sizing: border-box;
                margin-top: 20px;
            }
            .no-complaints {
                text-align: center;
                margin-top: 20px;
            }
            .profile-picture {
                width: 50px;
                height: 50px;
                border-radius: 50%;
                object-fit: cover;
                margin-right: 10px;
            }
            .container {
                flex: 1;
                display: flex;
                flex-direction: column;
                align-items: center;
                background-color: #fff;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
                max-width: 1000px;
                width: 100%;
                margin: auto;
                margin-top: 20px;
            }
            .view-button {
                background-color: #000;
                color: white;
                border: none;
                padding: 10px 20px;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                font-size: 14px;
                margin: 4px 2px;
                cursor: pointer;
                border-radius: 12px;
            }
            .hidden-row {
                display: none;
            }
            .answer-row.active {
                background-color: orange; /* Background color for active answer row set to orange */
            }
            .pagination {
                text-align: center;
                margin-top: 20px;
            }
            .pagination a, .pagination span {
                display: inline-block;
                padding: 8px 16px;
                margin: 0 4px;
                background-color: #333;
                color: white;
                border-radius: 5px;
                text-decoration: none;
            }
            .pagination a:hover {
                background-color: #ff4655;
            }
            .pagination .current {
                background-color: #ff4655;
                cursor: default;
            }
        </style>
        <link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">
        <!-- Fontawesome CDN Link For Icons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" />
    </head>
    <body>
        <div class="container-scroller">
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

            <div class="container-fluid page-body-wrapper" style="margin-top: 100px">
                <!-- partial -->
                <div class="main-panel">
                    <div class="content-wrapper">
                        <div class="input-group rounded">
                            <input id="search" type="search" class="form-control rounded" placeholder="Search" aria-label="Search" aria-describedby="search-addon" onkeyup="updateSearchLink()" value="${requestScope.name}"/>
                            <span class="input-group-text border-0" id="search-addon" >
                                <a id="search-link" class="search-btn" href="#">
                                    <i class="fas fa-search"></i>
                                </a>
                            </span>
                            <a href="?name=" class="close" style="text-align: end; width: 20px; text-decoration: none">&times;</a>
                        </div>

                        <div class="table-responsive" style="border: 1px solid black;border-radius: 10px; margin-top: 15px">
                            <table class=" table table-hover"  id="user-table" style="width: 100%; border-radius: 5px">
                                <thead class="table-dark">
                                <td>ID</td>
                                <td>Full Name</td>         
                                <td>Sender Request</td>
                                <td>Content</td>
                                <td>Status</td>
                                </thead>
                                <tbody >
                                    <c:forEach items="${requestScope.list}" var="noti" varStatus="loop">
                                        <tr>
                                            <td>${noti.no}</td>
                                            <td>${noti.user.fullName}</td>
                                            
                                            <td>${noti.sendUser.fullName}</td>
                                            <td>
                                                <a href="viewcomplaint" class="btn">
                                                    ${noti.notification.content}
                                                </a>

                                            </td>
                                            <td>
                                                <div style="display: flex">
                                                    <c:if test="${noti.isView == true}">
                                                        <button type="button" class="btn btn-success" style="padding: 5px; margin: 5px" value="${noti.notification.notificationId}">Done</button>
                                                    </c:if>
                                                    <c:if test="${noti.isView == false}">
                                                        <button type="button" class="btn btn-warning" style="padding: 5px;margin: 5px" value="${noti.notification.notificationId}">Read now</button>
                                                    </c:if>


                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                </tbody>
                            </table>
                        </div>
                        <br>
                        <!--                <div style="display: flex; justify-content: space-between">
                                            <div class="justify-content-start" style=""> Total ${requestScope.totalMentor} result </div>
                        
                                            <nav aria-label="Page navigation example" >
                                                <ul class="pagination justify-content-end">
                                                    <li class="page-item <c:if test="${requestScope.page==1}"> disabled </c:if>">
                                                        <a class="page-link" href="?page=${requestScope.page-1}&name=${requestScope.name}"
                        <c:if test="${requestScope.page==1}"> tabindex="-1" </c:if>
                >Previous</a>
            </li>
                        <c:forEach var="count" begin="1" end="${requestScope.end_page}">
                            <c:if test="${requestScope.page == count}">
                                <li class="page-item"><a class="page-link active">${count}</a></li>
                            </c:if>
                            <c:if test="${requestScope.page != count}">
                                <li class="page-item"><a class="page-link" href="?page=${count}&name=${requestScope.name}">${count}</a></li>
                            </c:if>
                        </c:forEach>
                        <li class="page-item">
                            <a class="page-link <c:if test="${requestScope.page==requestScope.end_page  or requestScope.end_page == 0}"> disabled </c:if>" href="?page=${requestScope.page+1}&name=${requestScope.name}"
                        <c:if test="${requestScope.page==requestScope.end_page or requestScope.end_page == 0 }"> tabindex="-1" </c:if>
                >Next</a>
            </li>
        </ul>
    </nav>
</div>-->
                    </div>
                </div>
            </div>
            <!-- page-body-wrapper ends -->
        </div>

        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

        <script>

                                // L?y t?t c? các nút có class "ban-btn"
                                var banButtons = document.querySelectorAll('.btn-success');
                                var inactiveButtons = document.querySelectorAll('.btn-warning');
                                var unBanButtons = document.querySelectorAll('.btn-danger');
                                // L?p qua t?ng nút và thêm s? ki?n click


                                inactiveButtons.forEach(function (button) {
                                    button.addEventListener('click', function () {
                                        // L?y giá tr? value c?a nút
                                        var userId = this.getAttribute('value');

                                        swal({
                                            title: "Are you sure for Read?",
                                            text: "This will change Read to Done",
                                            icon: "success",
                                            buttons: true,
                                            dangerMode: false
                                        })
                                                .then((willBan) => {
                                                    if (willBan) {
                                                        // G?i request POST t?i Servlet ?? ban tài kho?n
                                                        active(userId);
                                                    } else {
                                                        swal("Don't active account");
                                                    }
                                                });

                                    });
                                });


                                function active(userId) {
                                    // Send POST request to Servlet
                                    fetch('../staff/notificationServlet', {
                                        method: 'POST',
                                        headers: {
                                            'Content-Type': 'application/x-www-form-urlencoded'
                                        },
                                        body: 'id=' + encodeURIComponent(userId) + '&action=active'
                                    })
                                            .then(response => {
                                                if (response.ok) {
                                                    swal("User active successfully!").then(() => {
                                                        // Reload the page
                                                        location.reload();
                                                    });
                                                } else {
                                                    swal("Failed to active user. Please try again later.");
                                                }
                                            })
                                            .catch(error => {
                                                console.error('Error unbanning user:', error);
                                                swal("Failed to active user. Please try again later.");
                                            });
                                }
                                function updateUserTable() {
                                    location.reload();
                                }


        </script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
                integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
    </body>
</html>

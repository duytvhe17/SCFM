package controller.user;

import dal.UserDBContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import entity.User;

public class resetPasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("view/profile/reset_password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("resetpassword"); // Chuyển hướng tới trang reset mật khẩu nếu người dùng không có trong session
            return;
        }

        String username = user.getUserName();
        String fullName = user.getFullName();
        String email = user.getEmail();

        String newPassword = request.getParameter("matKhauMoi");
        String confirmPassword = request.getParameter("xacNhanMatKhau");

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "New password and confirm password do not match.");
            request.getRequestDispatcher("view/profile/reset_password.jsp").forward(request, response);
            return;
        }

        UserDBContext userDB = new UserDBContext();
        if (!userDB.checkPassword(username, fullName, newPassword)) {
            request.setAttribute("error", "Password is not in correct format.");
            request.getRequestDispatcher("view/profile/reset_password.jsp").forward(request, response);
            return;
        }

        userDB.capNhatMatKhau(email, newPassword);  // Cập nhật mật khẩu trong cơ sở dữ liệu

        request.setAttribute("message", "Password changed successfully.");
        request.getRequestDispatcher("view/profile/reset_password.jsp").forward(request, response);

    }
    }

    


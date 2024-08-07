/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import java.io.IOException;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dal.UserDBContext;
import entity.User;
import java.io.PrintWriter;

/**
 *
 * @author Windows
 */
public class changePasswordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ChangePasswordServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangePasswordServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        int userId = (int) session.getAttribute("userId");

        UserDBContext userDB = new UserDBContext();
        User user = userDB.getUserByUserId(userId);
        int roleId = userDB.getRoleIdByUserId(userId);
        request.setAttribute("user", user);
        request.setAttribute("roleId", roleId);

        request.getRequestDispatcher("view/profile/changePassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = (int) session.getAttribute("userId");

        UserDBContext userDB = new UserDBContext();
        User user1 = userDB.getUserByUserId(userId);
        int roleId = userDB.getRoleIdByUserId(userId);
        request.setAttribute("user", user1);
        request.setAttribute("roleId", roleId);
        if (user == null) {
            response.sendRedirect("changepasswordResult.jsp");
            return;
        }

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "New password and confirmation do not match.");
            request.getRequestDispatcher("view/profile/changePassword.jsp").forward(request, response);
            return;
        }

        UserDBContext userDAO = new UserDBContext();

        boolean isPasswordCorrect = userDAO.getFullByUsernamePassword(user.getUserName(), oldPassword);

        if (!isPasswordCorrect) {
            request.setAttribute("error", "Old password is incorrect.");
            request.getRequestDispatcher("view/profile/changePassword.jsp").forward(request, response);
            return;
        }
        if (!userDAO.checkPassword(user.getUserName(), user.getFullName(), newPassword)) {
            // ghi ro dinh dang pass hop le o giao dien
            request.setAttribute("error", "Password is invalid format.");
            request.getRequestDispatcher("view/profile/changePassword.jsp").forward(request, response);
            return;
        }

        userDAO.changePassword(user.getUserName(), newPassword);
        request.setAttribute("message", "Password changed successfully.");
        request.setAttribute("home", "Home");
        request.getRequestDispatcher("view/profile/changePassword.jsp").forward(request, response);
    }

}

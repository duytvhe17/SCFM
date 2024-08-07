
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.authentication;

/**
 *
 * @author nguye
 */
import dal.UserDBContext;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 *
 * @author phuc
 */
public class loginController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet loginController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet loginController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("view/authentication/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDBContext da = new UserDBContext();
        String username = request.getParameter("userName");
        String password = request.getParameter("passWord");

        boolean checkUser = da.checkUser(username, password);

        if (checkUser) {
            //session userid\
            HttpSession session = request.getSession();
            String fullname = da.getFullNameByUsername(username);
            int userId = da.getUserIdByUsername(username);
            User user = da.getUserByUserId(userId);
            session.setAttribute("user", user);
            session.setAttribute("userId", userId);
            session.setAttribute("fullname", fullname);

//            // Set cookies for username and password
//            Cookie usernameCookie = new Cookie("userName", username);
//            Cookie passwordCookie = new Cookie("passWord", password);
//            usernameCookie.setMaxAge(60 * 60 * 24 * 7); // 7 day
//            passwordCookie.setMaxAge(60 * 60 * 24 * 7); // 7 day
//            response.addCookie(usernameCookie);
//            response.addCookie(passwordCookie);
            boolean checkStudent = da.checkActiveLogin(username, password, "student", 1);
            boolean checkStaff = da.checkActiveLogin(username, password, "staff", 1);
            boolean checkManager = da.checkActiveLogin(username, password, "manager", 1);

            boolean checkStudenInactive = da.checkActiveLogin(username, password, "student", 0);
            //tra ve kq
            if (checkStudent) {
                response.sendRedirect("student/home");
            } else if (checkStudenInactive) {
                String inactiveMess = "Your account has been banned";
                request.setAttribute("inactiveMess", inactiveMess);
                request.getRequestDispatcher("view/authentication/login.jsp").forward(request, response);
            }
            if (checkStaff) {
                response.sendRedirect("staff/home");
            }
            if (checkManager) {
                response.sendRedirect("manager/home");
            }
        } else {
            String lf = "Fail to login";

            request.setAttribute("lf", lf);

            request.getRequestDispatcher("view/authentication/login.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

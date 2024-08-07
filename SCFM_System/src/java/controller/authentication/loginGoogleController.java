/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.authentication;

import dal.UserDBContext;
import entity.GooglePojo;
import entity.User;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import util.GoogleUtils;

/**
 *
 * @author phuc
 */
public class loginGoogleController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet loginGoogleController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet loginGoogleController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String code = request.getParameter("code");
    User user = new User();
    UserDBContext da = new UserDBContext();
    if (code == null || code.isEmpty()) {
        request.getRequestDispatcher("view/authentication/login.jsp").forward(request, response);

    } else {
        String accessToken = GoogleUtils.getToken(code);
        GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);

        String emailGoogle = googlePojo.getEmail();
        String username = googlePojo.getId();
        HttpSession session = request.getSession();
        user = da.getUserByEmail(emailGoogle);

        // Xác nhận nếu có email thì vào account có username và password của email đó
        if (da.checkEmailExist(emailGoogle)) {
            int userId = da.getUserIdByUsername(username);
            user = da.getUserByUserId(userId);
            session.setAttribute("user", user);
            session.setAttribute("userId", userId);

            // Kiểm tra các trường thông tin của người dùng
            if (!da.checkUserNullOrSpace(user)) {
                response.sendRedirect("../updateprofile");
            } else {
                boolean checkStudent = da.checkActiveLogin(user.getUserName(), user.getPassword(), "student",1);
                boolean checkStaff = da.checkActiveLogin(user.getUserName(), user.getPassword(),"staff",1);
                boolean checkManager = da.checkActiveLogin(user.getUserName(), user.getPassword(), "manager",1);

                if (checkStudent) {
                    response.sendRedirect("../student/home");
                } else if (checkStaff) {
                    response.sendRedirect("../staff/home");
                } else if (checkManager) {
                    response.sendRedirect("../manager/home");
                }
            }
        } else {
            boolean accountCreated = da.createAccountFromGoogle(emailGoogle, username);

            if (accountCreated) {
                int userId = da.getUserIdByUsername(username);
                User user1 = da.getUserByUserId(userId);
                session.setAttribute("user", user1);
                session.setAttribute("userId", userId);

                // Kiểm tra các trường thông tin của người dùng
                if (!da.checkUserNullOrSpace(user1)) {
                    response.sendRedirect("../updateprofile");
                } else {
                    boolean checkStudent = da.checkActiveLogin(user1.getUserName(), user1.getPassword(), "student",1);
                    boolean checkStaff = da.checkActiveLogin(user1.getUserName(), user1.getPassword(),"staff",1);
                    boolean checkManager = da.checkActiveLogin(user1.getUserName(), user1.getPassword(), "manager",1);

                    if (checkStudent) {
                        response.sendRedirect("../student/home");
                    } else if (checkStaff) {
                        response.sendRedirect("../staff/home");
                    } else if (checkManager) {
                        response.sendRedirect("../manager/home");
                    }
                }
            }
        }
    }
}

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import dal.UserDBContext;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author nguye
 */
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class updateProfileServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet updateProfileServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updateProfileServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute("user");
        int userId = (int) session.getAttribute("userId");
        UserDBContext userDB = new UserDBContext();
        User user = userDB.getUserByUserId(userId);
        int roleId = userDB.getRoleIdByUserId(userId);
        request.setAttribute("roleId", roleId);
        request.setAttribute("user", user);

        request.getRequestDispatcher("/view/profile/updateProfile.jsp").forward(request, response);
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
        int userId = Integer.parseInt(request.getParameter("userId"));
        UserDBContext db = new UserDBContext();

        String fullName = request.getParameter("fullName");
        Boolean gender = request.getParameter("gender").equals("male");
        String mobile = request.getParameter("mobile");
        String address = request.getParameter("address");
        Date dateOfBirth = java.sql.Date.valueOf(request.getParameter("dateOfBirth"));
        Part picture = request.getPart("picture");
        String pictureName = null;

        User user = db.getUserByUserId(userId);
        if (picture != null && picture.getSize() > 0) {
            String contextPath = request.getServletContext().getRealPath("/");
            Path basePath = Path.of(contextPath).getParent().getParent();
            Path filesPath = basePath.resolve("web/Avatar");

            if (!Files.exists(filesPath)) {
                Files.createDirectories(filesPath);
            }

            String originalFilename = Path.of(picture.getSubmittedFileName()).getFileName().toString();
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            String formattedCurrentTime = currentTime.format(formatter);
            pictureName = "avatar_" + userId + "_" + formattedCurrentTime + "_" + originalFilename;

            Path filePath = filesPath.resolve(pictureName);
            picture.write(filePath.toString());
        } else {
            pictureName = user.getPicture(); // Retain the existing picture
        }

        String errorMessages = "";
        if (db.checkMobileExist(mobile, userId)) {
            errorMessages += "The mobile number already exists in the database. Please choose another one.<br>";
        }
        if (db.getValidPhoneNumber(mobile) == null) {
            errorMessages += "The phone number does not meet the requirements, please go to update to fix it.<br>";
        }
        if (!db.isValidAddress(address)) {
            errorMessages += "The address must contain at least 2 characters.<br>";
        }
        if (!db.isValidFullName(fullName)) {
            errorMessages += "The full name must contain at least 2 words, 3 or more characters and must not contain numbers or special characters.<br>";
        }
        int roleId = db.getRoleIdByUserId(userId);
        int requiredAge = (roleId == 3) ? 16 : 24;
        if (!db.checkEnoughAge(dateOfBirth, requiredAge)) {
            errorMessages += "You must be at least " + requiredAge + " years old.<br>";
        }

        if (!errorMessages.isEmpty()) {
            HttpSession session = request.getSession();
            session.setAttribute("errorMessages", errorMessages);
            response.sendRedirect("updateprofile");
        } else {
            db.updateUser(fullName, gender, mobile, address, dateOfBirth, userId, pictureName);
            if (picture != null && picture.getSize() > 0) {
                response.sendRedirect("view/profile/processing.jsp");
            } else {
                response.sendRedirect("viewprofile");
            }
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

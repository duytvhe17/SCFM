/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.student;

import controller.authentication.BaseRBACController;
import dal.CategoryDBContext;
import dal.ModifyDBContext;
import dal.NotificationDAO;
import dal.QuestionDBContext;
import dal.QuestionStatusDBContext;
import dal.UserDBContext;
import entity.Category;
import entity.Modify;
import entity.Question;
import entity.QuestionStatus;
import entity.Role;
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
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author admin
 */
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class createComplaintServlet extends BaseRBACController {

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
            out.println("<title>Servlet createComplaintServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet createComplaintServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
        CategoryDBContext catDB = new CategoryDBContext();
        ArrayList<Category> cats = catDB.listCategories();

        ModifyDBContext modDB = new ModifyDBContext();
        ArrayList<Modify> mods = modDB.listModifies();

        HttpSession session = request.getSession();
        Integer userId = (int) session.getAttribute("userId");
        UserDBContext userDB = new UserDBContext();
        User user1 = userDB.getUserByUserId(userId);

        QuestionStatusDBContext questaDB = new QuestionStatusDBContext();
        ArrayList<QuestionStatus> stas = questaDB.listQuestionStatus();

        String selectedCategoryId = request.getParameter("categoryId");
        Category selectedCategory = null;
        if (selectedCategoryId != null && !selectedCategoryId.isEmpty()) {
            selectedCategory = catDB.getCategoryById(Integer.parseInt(selectedCategoryId));
        }

        request.setAttribute("user", user1);
        request.setAttribute("stas", stas);
        request.setAttribute("mods", mods);
        request.setAttribute("cats", cats);
        request.setAttribute("selectedCategory", selectedCategory);
        request.getRequestDispatcher("../view/student/createComplaint.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (int) session.getAttribute("userId");

        String categoryId = request.getParameter("categoryId");
        String content = request.getParameter("content");
        int modifyId = 2;
        int statusId = 1;

        List<String> banWords = Arrays.asList("đm", "vl", "vcl", "cc", "đmm");
        boolean hasForbiddenWord = banWords.stream().anyMatch(content::contains);
        if (hasForbiddenWord) {
            request.setAttribute("error", "The question contains invalid words.");
            doGet(request, response);
            return;
        }

        Part attachment = request.getPart("attachment");
        String filename = null;
        if (attachment != null && attachment.getSize() > 0) {
            String contextPath = request.getServletContext().getRealPath("/");
            Path basePath = Path.of(contextPath).getParent().getParent();
            Path filesPath = basePath.resolve("web/FileComplaint");

            if (!Files.exists(filesPath)) {
                Files.createDirectories(filesPath);
            }

            String originalFilename = Path.of(attachment.getSubmittedFileName()).getFileName().toString();
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            String formattedCurrentTime = currentTime.format(formatter);
            filename = "complaint_" + userId + "_" + formattedCurrentTime + "_" + originalFilename;

            Path filePath = filesPath.resolve(filename);
            attachment.write(filePath.toString());
        }

        QuestionDBContext questionDB = new QuestionDBContext();
        Question question = new Question();
        User user1 = new User();
        user1.setUserId(userId);
        Category category = new Category();
        category.setCategoryId(Integer.parseInt(categoryId));
        QuestionStatus status = new QuestionStatus();
        status.setStatusId(statusId);
        Modify modify = new Modify();
        modify.setModifyId(modifyId);

        question.setUser(user1);
        question.setContent(content);
        question.setCategory(category);
        question.setStatus(status);
        question.setModify(modify);
        if (filename != null) {
            question.setFileName(filename);
        }

        questionDB.insertComplaint(question);
        NotificationDAO notificationDAO = new NotificationDAO();
        notificationDAO.createNotificationForAllStaff("You have new message",userId);
        response.sendRedirect("home"); 

    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import controller.authentication.BaseRBACController;
import dal.CategoryDBContext;
import dal.UserDBContext;
import entity.Category;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class addCategory extends BaseRBACController {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (int) session.getAttribute("userId");
        UserDBContext userDB = new UserDBContext();
        User user1 = userDB.getUserByUserId(userId);
        request.setAttribute("user", user1);
        request.getRequestDispatcher("../view/manager/addCategory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (int) session.getAttribute("userId");
        String categoryName = request.getParameter("categoryName");
        Part attachment = request.getPart("attachment");
        String filename = null;
        CategoryDBContext caDB = new CategoryDBContext();

        if (caDB.checkCategoryNameExist(categoryName)) {
            request.setAttribute("error", "Category name already exists. Please choose a different name.");
            doGet(request, response);
            return;
        }

        if (attachment != null && attachment.getSize() > 0) {
            String contextPath = request.getServletContext().getRealPath("/");
            Path basePath = Path.of(contextPath).getParent().getParent();
            Path filesPath = basePath.resolve("web/SampleFileComplaint");

            if (!Files.exists(filesPath)) {
                Files.createDirectories(filesPath);
            }

            String originalFilename = Path.of(attachment.getSubmittedFileName()).getFileName().toString();
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            String formattedCurrentTime = currentTime.format(formatter);
            filename = "samplefile_" + userId + "_" + formattedCurrentTime + "_" + originalFilename;

            Path filePath = filesPath.resolve(filename);
            attachment.write(filePath.toString());
        }

        Category category = new Category();
        category.setCategoryName(categoryName);
        if (filename != null) {
            category.setSampleFormName(filename);
        }
        caDB.insertCategory(category);
        response.sendRedirect("home");
    }

}

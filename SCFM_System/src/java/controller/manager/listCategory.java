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
import java.util.List;
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
public class listCategory extends BaseRBACController {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (int) session.getAttribute("userId");
        UserDBContext userDB = new UserDBContext();
        User user1 = userDB.getUserByUserId(userId);

        String keyword = request.getParameter("keyword");
        CategoryDBContext catDB = new CategoryDBContext();
        List<Category> categories;
        if (keyword != null && !keyword.isEmpty()) {
            categories = catDB.searchCategoryByKeyWord(keyword);
        } else {
            categories = catDB.listCategories();
        }

        int page = 1;
        int recordsPerPage = 10;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        int start = (page - 1) * recordsPerPage;
        int end = Math.min(start + recordsPerPage, categories.size());

        List<Category> list = categories.subList(start, end);

        int noOfRecords = categories.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        request.setAttribute("data", list);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("user", user1);
        request.getRequestDispatcher("../view/manager/listCategory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (int) session.getAttribute("userId");

        Part attachment = request.getPart("attachment");
        String filename = null;
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

        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        CategoryDBContext catDB = new CategoryDBContext();
        catDB.updateCategory(categoryId, filename);
        response.sendRedirect("listCategory");
    }

}

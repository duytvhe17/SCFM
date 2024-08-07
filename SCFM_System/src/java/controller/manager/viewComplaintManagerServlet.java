/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import controller.authentication.BaseRBACController;
import dal.CategoryDBContext;
import dal.QuestionDBContext;
import dal.QuestionStatusDBContext;
import dal.UserDBContext;
import entity.Answer;
import entity.Category;
import entity.Question;
import entity.QuestionStatus;
import entity.Role;
import entity.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author phuc
 */
public class viewComplaintManagerServlet extends BaseRBACController {

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int page = 1;
        int recordsPerPage = 10;

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        String searchQuery = request.getParameter("searchQuery");
        int categoryId = 0;
        if (request.getParameter("categoryId") != null && !request.getParameter("categoryId").isEmpty()) {
            categoryId = Integer.parseInt(request.getParameter("categoryId"));
        }

        String status = request.getParameter("status");

        QuestionDBContext questionDB = new QuestionDBContext();
        int totalRecords = questionDB.getTotalQuestions(searchQuery, categoryId, status);
        int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);

        List<Question> questions = questionDB.getQuestions((page - 1) * recordsPerPage, recordsPerPage, searchQuery, categoryId, status);
        request.setAttribute("questions", questions);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        CategoryDBContext categoryDB = new CategoryDBContext();
        List<Category> categories = categoryDB.listCategories();
        request.setAttribute("categories", categories);

        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");
        UserDBContext db = new UserDBContext();
        User user1 = db.getUserByUserId(userId);

        request.setAttribute("user", user1);

        request.getRequestDispatcher("../view/manager/viewComplaint.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
        processRequest(request, response);
    }
}

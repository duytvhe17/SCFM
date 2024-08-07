/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.student;

import controller.authentication.BaseRBACController;
import dal.CategoryDBContext;
import dal.QuestionDBContext;
import dal.UserDBContext;
import entity.Category;
import entity.Question;
import entity.Role;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class viewMyComplaint extends BaseRBACController {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (int) session.getAttribute("userId");
        UserDBContext userDB = new UserDBContext();
        User user1 = userDB.getUserByUserId(userId);
        QuestionDBContext qDB = new QuestionDBContext();
        List<Question> questions = qDB.getMyQuestionsNotAnswer(userId);
        CategoryDBContext catDB = new CategoryDBContext();
        ArrayList<Category> cats = catDB.listCategories();
        int page = 1;
        int recordsPerPage = 10;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        int start = (page - 1) * recordsPerPage;
        int end = Math.min(start + recordsPerPage, questions.size());

        List<Question> list = questions.subList(start, end);

        int noOfRecords = questions.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        request.setAttribute("data", list);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("user", user1);
        request.setAttribute("cats", cats);

        request.getRequestDispatcher("../view/student/viewMyComplaint.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
       String text = request.getParameter("search");
        String categoryId = request.getParameter("categoryId");
        HttpSession session = request.getSession();
        Integer userId = (int) session.getAttribute("userId");
        CategoryDBContext catDB = new CategoryDBContext();
        ArrayList<Category> cats = catDB.listCategories();

        UserDBContext userDB = new UserDBContext();
        User user1 = userDB.getUserByUserId(userId);
        QuestionDBContext qDB = new QuestionDBContext();
        List<Question> questions;

        if (categoryId == null || categoryId.equals("ALL")) {
            questions = qDB.getMyQuestionsByKeyword(text, userId);
        } else {
            questions = qDB.getMyQuestionsByKeywordAndCategory(text, userId, Integer.parseInt(categoryId));
        }

        int page = 1;
        int recordsPerPage = 10;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        int start = (page - 1) * recordsPerPage;
        int end = Math.min(start + recordsPerPage, questions.size());

        List<Question> list = questions.subList(start, end);

        int noOfRecords = questions.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        request.setAttribute("data", list);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("user", user1);
        request.setAttribute("cats", cats);

        request.getRequestDispatcher("../view/student/viewMyComplaint.jsp").forward(request, response);
    }

}

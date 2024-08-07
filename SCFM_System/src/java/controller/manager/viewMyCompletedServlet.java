/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import controller.authentication.BaseRBACController;
import dal.AnswerDBContext;
import dal.QuestionDBContext;
import dal.UserDBContext;
import entity.Answer;
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
 * @author phuc
 */
public class viewMyCompletedServlet extends BaseRBACController {
     private static final int PAGE_SIZE = 10; 


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {

    HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");

        UserDBContext userDB = new UserDBContext();
        User user1 = userDB.getUserByUserId(userId);

        AnswerDBContext answerDB = new AnswerDBContext();

        String searchQuery = request.getParameter("search") != null ? request.getParameter("search") : "";

        int totalAnswers = answerDB.getTotalCompletedAnswersByUserId(userId, searchQuery);
        int totalPages = (int) Math.ceil((double) totalAnswers / PAGE_SIZE);

        int currentPage = 1;
        if (request.getParameter("page") != null) {
            currentPage = Integer.parseInt(request.getParameter("page"));
        }

        List<Answer> completedAnswers = answerDB.getCompletedAnswersByUserId(userId, currentPage, PAGE_SIZE, searchQuery);

        request.setAttribute("user", user1);
        request.setAttribute("answers", completedAnswers);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchQuery", searchQuery);
        
        request.getRequestDispatcher("../view/manager/viewMyCompleted.jsp").forward(request, response);
    }

        @Override
        protected void doPost
        (HttpServletRequest request, HttpServletResponse response
        , User user, ArrayList<Role> roles) throws ServletException, IOException {

        }
    }

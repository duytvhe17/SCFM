/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import controller.authentication.BaseRBACController;
import dal.QuestionDBContext;
import dal.UserDBContext;
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

/**
 *
 * @author admin
 */
public class viewTotalStatusForMonthServlet extends BaseRBACController {

  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (int) session.getAttribute("userId");
        UserDBContext userDB = new UserDBContext();
        User user1 = userDB.getUserByUserId(userId);
        int month = Integer.parseInt(request.getParameter("month"));
        QuestionDBContext dbContext = new QuestionDBContext();

        int status1Count = dbContext.getTotalQuestionsByStatus(month, 1);
        int status2Count = dbContext.getTotalQuestionsByStatus(month, 2);
        int status3Count = dbContext.getTotalQuestionsByStatus(month, 3);

        request.setAttribute("month", month);
        request.setAttribute("status1Count", status1Count);
        request.setAttribute("status2Count", status2Count);
        request.setAttribute("status3Count", status3Count);
        request.setAttribute("user", user1);
        request.getRequestDispatcher("../view/manager/questionStats.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

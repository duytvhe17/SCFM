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
public class viewTotalComplaintByTimeServlet extends BaseRBACController {

   

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (int) session.getAttribute("userId");
        UserDBContext userDB = new UserDBContext();
        User user1 = userDB.getUserByUserId(userId);
        QuestionDBContext dbContext = new QuestionDBContext();
        int january = dbContext.getTotalQuestion(1);
        int february = dbContext.getTotalQuestion(2);
        int march = dbContext.getTotalQuestion(3);
        int april = dbContext.getTotalQuestion(4);
        int may = dbContext.getTotalQuestion(5);
        int june = dbContext.getTotalQuestion(6);
        int july = dbContext.getTotalQuestion(7);
        int august = dbContext.getTotalQuestion(8);
        int september = dbContext.getTotalQuestion(9);
        int october = dbContext.getTotalQuestion(10);
        int november = dbContext.getTotalQuestion(11);
        int december = dbContext.getTotalQuestion(12);

        request.setAttribute("january", january);
        request.setAttribute("february", february);
        request.setAttribute("march", march);
        request.setAttribute("april", april);
        request.setAttribute("may", may);
        request.setAttribute("june", june);
        request.setAttribute("july", july);
        request.setAttribute("august", august);
        request.setAttribute("september", september);
        request.setAttribute("october", october);
        request.setAttribute("november", november);
        request.setAttribute("december", december);
        request.setAttribute("user", user1);

        request.getRequestDispatcher("../view/manager/questionChart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

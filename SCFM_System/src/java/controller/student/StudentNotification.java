/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.student;

import dal.NotificationDAO;
import dal.UserDBContext;
import entity.Notification;
import entity.NotificationUser;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class StudentNotification extends HttpServlet {
NotificationDAO notificationDAO = new NotificationDAO();

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
        HttpSession hs = request.getSession();
        int uid = (int) hs.getAttribute("userId");
        ArrayList<NotificationUser> list = notificationDAO.getNotificationUsers(1, uid);
        int num = 1;
        for (NotificationUser notificationUser : list) {
            notificationUser.setNo(num);
            num++;
            int nid = notificationUser.getNotification().getNotificationId();
            User student = notificationDAO.getEmployeeAnswer(nid);
            notificationUser.setSendUser(student);
        }
        request.setAttribute("list", list);
        request.getRequestDispatcher("../view/student/notification.jsp").forward(request, response);
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
        String id = request.getParameter("id");
        Notification noti = notificationDAO.getNotificationById(Integer.parseInt(id));
        if(noti != null && noti.isIsView() == false){
            notificationDAO.updateView(Integer.parseInt(id));
            response.setStatus(HttpServletResponse.SC_OK);
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

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
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class NotificationManagerServlet extends HttpServlet {
NotificationDAO notificationDAO = new NotificationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession hs = request.getSession();
        int uid = (int) hs.getAttribute("userId");
        ArrayList<NotificationUser> list = notificationDAO.getNotificationUsers(1, uid);
        UserDBContext context = new UserDBContext();
        int num = 1;
        for (NotificationUser notificationUser : list) {
            notificationUser.setNo(num);
            num++;
            int nid = notificationUser.getNotification().getNotificationId();
            Notification noti = notificationDAO.getNotificationById(nid);
            User student = context.getUserByUserId(noti.getUid());
            notificationUser.setSendUser(student);
        }
        
        request.setAttribute("list", list);
        request.getRequestDispatcher("../view/manager/notification.jsp").forward(request, response);
    }

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

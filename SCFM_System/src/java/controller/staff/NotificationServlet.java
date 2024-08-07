/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.staff;

import controller.authentication.BaseRBACController;
import dal.NotificationDAO;
import dal.UserDBContext;
import entity.Notification;
import entity.NotificationUser;

import entity.Role;
import entity.User;
import java.io.IOException;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 *
 * @author Windows
 */
public class NotificationServlet extends BaseRBACController {

    NotificationDAO notificationDAO = new NotificationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
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
        request.getRequestDispatcher("../view/staff/notification.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
        String id = request.getParameter("id");
        Notification noti = notificationDAO.getNotificationById(Integer.parseInt(id));
        if(noti != null && noti.isIsView() == false){
            notificationDAO.updateView(Integer.parseInt(id));
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}

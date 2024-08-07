package controller.manager;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import controller.authentication.BaseRBACController;
import dal.UserDBContext;
import entity.Role;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class ViewStaffServlet extends BaseRBACController {



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");
        UserDBContext db = new UserDBContext();
        User user1 = db.getUserByUserId(userId);
        
        String keyword = request.getParameter("searchQuery");
        UserDBContext userDB = new UserDBContext();
        List<User> staffs;
        if (keyword != null && !keyword.isEmpty()) {

            staffs = userDB.searchStaffByKeyWord(keyword);

        } else {
            staffs = userDB.getStaff();
        }
        request.setAttribute("staff", staffs);
        request.setAttribute("user", user1);
        request.getRequestDispatcher("../view/manager/viewStaff.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
        String action = request.getParameter("action");
        int userId = Integer.parseInt(request.getParameter("userId"));
        UserDBContext userDB = new UserDBContext();


        // Redirect back to the list view
        response.sendRedirect("viewStudents");
    }


}

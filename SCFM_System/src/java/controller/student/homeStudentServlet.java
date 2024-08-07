package controller.student;

import controller.authentication.BaseRBACController;
import dal.NotificationDAO;
import dal.UserDBContext;
import entity.NotificationUser;
import entity.Role;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author nguye
 */
public class homeStudentServlet extends BaseRBACController {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet homeStudentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet homeStudentServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");
        UserDBContext db = new UserDBContext();
        User user1 = db.getUserByUserId(userId);
        request.setAttribute("user", user1);
        NotificationDAO notificationDAO = new NotificationDAO();
        ArrayList<NotificationUser> list = notificationDAO.getNotificationUsers(0,userId);
        int count = list.size();
        request.setAttribute("count", count);
        request.getRequestDispatcher("../view/student/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

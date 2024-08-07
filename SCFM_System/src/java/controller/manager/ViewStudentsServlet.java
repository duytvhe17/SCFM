package controller.manager;

import controller.authentication.BaseRBACController;
import dal.UserDBContext;
import entity.Role;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewStudentsServlet extends BaseRBACController {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");
        UserDBContext db = new UserDBContext();
        User user1 = db.getUserByUserId(userId);
        
        String keyword = request.getParameter("searchQuery");
        UserDBContext userDB = new UserDBContext();
        List<User> students;
        if (keyword != null && !keyword.isEmpty()) {
            students = userDB.searchStudentByKeyWord(keyword);
        } else {
            students = userDB.getStudents();
        }
        request.setAttribute("students", students);
        request.setAttribute("user", user1);
        request.getRequestDispatcher("../view/manager/viewStudents.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
        String action = request.getParameter("action");
        UserDBContext userDB = new UserDBContext();


        if ("save".equals(action)) {
            String[] selectedStudentIds = request.getParameterValues("selectedStudents");
            List<User> students = userDB.getStudents();

            // Set all students to inactive first
            for (User student : students) {
                userDB.updateUserActiveStatus(student.getUserId(), 0);
            }

            // Activate only the selected students
            if (selectedStudentIds != null) {
                for (String userIdStr : selectedStudentIds) {
                    if (userIdStr != null && !userIdStr.isEmpty()) {
                        int userId = Integer.parseInt(userIdStr);
                        userDB.updateUserActiveStatus(userId, 1);
                    }
                }
            }

        }
        response.sendRedirect("viewstudents");
    }
}

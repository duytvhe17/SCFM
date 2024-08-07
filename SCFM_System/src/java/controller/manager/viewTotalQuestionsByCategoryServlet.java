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
    import java.util.Map;
    import java.util.stream.Collectors;

    /**
     *
     * @author admin
     */
    public class viewTotalQuestionsByCategoryServlet extends BaseRBACController {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
            HttpSession session = request.getSession();
            Integer userId = (int) session.getAttribute("userId");
            UserDBContext userDB = new UserDBContext();
            User user1 = userDB.getUserByUserId(userId);

            QuestionDBContext qdb = new QuestionDBContext();
            Map<String, Integer> questionsByCategory = qdb.viewTotalQuestionsByAllCategories();
            

            String categoryNames = questionsByCategory.keySet().stream()
                    .map(name -> "\"" + name + "\"")
                    .collect(Collectors.joining(", ", "[", "]"));
            String categoryCounts = questionsByCategory.values().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ", "[", "]"));

            

            request.setAttribute("user", user1);
            request.setAttribute("categoryNames", categoryNames);
            request.setAttribute("categoryCounts", categoryCounts);
            
            request.getRequestDispatcher("../view/manager/viewTotalQuestionsByCategory.jsp").forward(request, response);
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

    }

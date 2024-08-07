

package controller.student;

import controller.authentication.BaseRBACController;
import dal.AnswerDBContext;
import dal.QuestionDBContext;
import dal.UserDBContext;
import entity.Answer;
import entity.Question;
import entity.Role;
import entity.User;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author nguye
 */
public class viewPublicAnsweredQuestions extends BaseRBACController {


   
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (int) session.getAttribute("userId");
        UserDBContext userDB = new UserDBContext();
        User user1 = userDB.getUserByUserId(userId);
        AnswerDBContext aDB = new AnswerDBContext();
        List<Answer> answers = aDB.getAllPublicAnswer();
        int page = 1;
        int recordsPerPage = 10;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        int start = (page - 1) * recordsPerPage;
        int end = Math.min(start + recordsPerPage, answers.size());

        List<Answer> list = answers.subList(start, end);

        int noOfRecords = answers.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        request.setAttribute("data", list);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("user", user1);

        request.getRequestDispatcher("../view/student/publicAnsweredQuestions.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
        String text = request.getParameter("search");
        HttpSession session = request.getSession();
        Integer userId = (int) session.getAttribute("userId");
        UserDBContext userDB = new UserDBContext();
        User user1 = userDB.getUserByUserId(userId);
        AnswerDBContext aDB = new AnswerDBContext();
        List<Answer> answers = aDB.getAllPublicAnswerByKey(text);
        
        int page = 1;
        int recordsPerPage = 10;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        int start = (page - 1) * recordsPerPage;
        int end = Math.min(start + recordsPerPage, answers.size());

        List<Answer> list = answers.subList(start, end);

        int noOfRecords = answers.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        request.setAttribute("data", list);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("user", user1);
        request.setAttribute("a", text);
        

        request.getRequestDispatcher("../view/student/publicAnsweredQuestions.jsp").forward(request, response);
    }
}

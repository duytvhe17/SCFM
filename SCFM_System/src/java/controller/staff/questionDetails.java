/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.staff;

import controller.authentication.BaseRBACController;
import dal.AnswerDBContext;
import dal.QuestionDBContext;
import dal.QuestionStatusDBContext;
import dal.UserDBContext;
import entity.Answer;
import entity.Question;
import entity.QuestionStatus;
import entity.Role;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author phuc
 */
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class questionDetails extends BaseRBACController {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
        int questionId = Integer.parseInt(request.getParameter("questionId"));

        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");
        UserDBContext db = new UserDBContext();
        User user1 = db.getUserByUserId(userId);

        QuestionDBContext questionDB = new QuestionDBContext();
        AnswerDBContext answerDB = new AnswerDBContext();
        Question question = questionDB.getQuestionById(questionId);

        if (question.getStatus().getStatusId() != 1) {
            Answer answer = answerDB.getAnswerByQuestionId(questionId);
            request.setAttribute("answer", answer);
        }

        request.setAttribute("user", user1);
        request.setAttribute("question", question);
        request.getRequestDispatcher("../view/staff/questionDetails.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user, ArrayList<Role> roles) throws ServletException, IOException {
        QuestionStatusDBContext qsDB = new QuestionStatusDBContext();
        int modifyId = Integer.parseInt(request.getParameter("modify"));
        AnswerDBContext answerDB = new AnswerDBContext();
        String responseContent = request.getParameter("responseContent");
        String filename = null;
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");

        String action = request.getParameter("action");
        int questionId = Integer.parseInt(request.getParameter("questionId"));

        Part attachment = request.getPart("attachment");

        if (attachment != null && attachment.getSize() > 0) {
            String contextPath = request.getServletContext().getRealPath("/");
            Path basePath = Path.of(contextPath).getParent().getParent();
            Path filesPath = basePath.resolve("web/FileComplaint");

            if (!Files.exists(filesPath)) {
                Files.createDirectories(filesPath);
            }

            String originalFilename = Path.of(attachment.getSubmittedFileName()).getFileName().toString();
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            String formattedCurrentTime = currentTime.format(formatter);
            filename = "answer_" + userId + "_" + formattedCurrentTime + "_" + originalFilename;

            Path filePath = filesPath.resolve(filename);
            attachment.write(filePath.toString());

        }
        if ("reject".equals(action)) {
            answerDB.saveAnswerAndUpdateQuestionStatus(questionId, responseContent, userId, filename, modifyId, 3);
        } else {
            answerDB.saveAnswerAndUpdateQuestionStatus(questionId, responseContent, userId, filename, modifyId, 2);
        }
        //request.getRequestDispatcher("../view/staff/viewComplaint.jsp").forward(request, response);
        response.sendRedirect("viewcomplaint");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

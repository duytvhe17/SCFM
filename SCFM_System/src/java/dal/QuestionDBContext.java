/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Category;
import entity.Modify;
import entity.Question;
import entity.QuestionStatus;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.LinkedHashMap;
/**
 *
 * @author admin
 */
public class QuestionDBContext extends DBContext {

    List<Question> listQuestion = new ArrayList<>();
    UserDBContext userDB = new UserDBContext();
    CategoryDBContext cateDB = new CategoryDBContext();
    QuestionStatusDBContext qsDB = new QuestionStatusDBContext();
    ModifyDBContext mDB = new ModifyDBContext();

    public void insertComplaint(Question question) {
        try {
            String sql = "INSERT INTO [Question]\n"
                    + "                                    ([content]\n"
                    + "                                    ,[userId]\n"
                    + "                                    ,[timeCreate]\n"
                    + "                                    ,[categoryId]\n"
                    + "                                    ,[statusId]\n"
                    + "                                    ,[modifyId]\n"
                    + "                                    ,[fileName])\n"
                    + "                              VALUES\n"
                    + "                                    (?\n"
                    + "                                    ,?\n"
                    + "                                    ,GETDATE()\n"
                    + "                                    ,?\n"
                    + "                                    ,?\n"
                    + "                                    ,?\n"
                    + "                                    ,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setNString(1, question.getContent());
            stm.setInt(2, question.getUser().getUserId());
            stm.setInt(3, question.getCategory().getCategoryId());
            stm.setInt(4, question.getStatus().getStatusId());
            stm.setInt(5, question.getModify().getModifyId());
            stm.setString(6, question.getFileName());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getTotalQuestions(String searchQuery, int categoryId, String status) {
        int total = 0;
        String sql = "SELECT COUNT(*) AS total FROM Question WHERE 1=1";
        if (searchQuery != null && !searchQuery.isEmpty()) {
            sql += " AND (content LIKE ? OR userId IN (SELECT userId FROM User WHERE fullName LIKE ?))";
        }
        if (categoryId > 0) {
            sql += " AND categoryId = ?";
        }
        if (status != null && !status.equals("all")) {
            sql += " AND statusId = ?";
        }

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            int paramIndex = 1;
            if (searchQuery != null && !searchQuery.isEmpty()) {
                stm.setString(paramIndex++, "%" + searchQuery + "%");
                stm.setString(paramIndex++, "%" + searchQuery + "%");
            }
            if (categoryId > 0) {
                stm.setInt(paramIndex++, categoryId);
            }
            if (status != null && !status.equals("all")) {
                int statusId;
                if (status.equals("processing")) {
                    statusId = 1; // assuming statusId 1 is processing
                } else if (status.equals("completed")) {
                    statusId = 2; // assuming statusId 2 is completed
                } else {
                    statusId = 3; // assuming statusId 3 is reject
                }
                stm.setInt(paramIndex++, statusId);
            }
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt("total");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    public List<Question> getQuestions(int offset, int limit, String searchQuery, int categoryId, String status) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM Question WHERE 1=1";
        if (searchQuery != null && !searchQuery.isEmpty()) {
            sql += " AND (content LIKE ? OR userId IN (SELECT userId FROM [User] WHERE fullName LIKE ?))";
        }
        if (categoryId > 0) {
            sql += " AND categoryId = ?";
        }
        if (status != null && !status.equals("all")) {
            sql += " AND statusId = ?";
        }
        sql += " ORDER BY questionId OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            int paramIndex = 1;
            if (searchQuery != null && !searchQuery.isEmpty()) {
                stm.setString(paramIndex++, "%" + searchQuery + "%");
                stm.setString(paramIndex++, "%" + searchQuery + "%");
            }
            if (categoryId > 0) {
                stm.setInt(paramIndex++, categoryId);
            }
            if (status != null && !status.equals("all")) {
                int statusId;
                if (status.equals("processing")) {
                    statusId = 1;
                } else if (status.equals("completed")) {
                    statusId = 2;
                } else {
                    statusId = 3;
                }
                stm.setInt(paramIndex++, statusId);
            }
            stm.setInt(paramIndex++, offset);
            stm.setInt(paramIndex++, limit);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Question q = new Question();
                    q.setQuestionId(rs.getInt("questionId"));
                    q.setContent(rs.getNString("content"));
                    q.setUser(userDB.getUserByUserId(rs.getInt("userId")));
                    q.setTimeCreate(rs.getDate("timeCreate"));
                    q.setCategory(cateDB.getCategoryById(rs.getInt("categoryId")));
                    q.setStatus(qsDB.getStatusById(rs.getInt("statusId")));
                    q.setModify(mDB.getModifyById(rs.getInt("modifyId")));
                    q.setFileName(rs.getString("fileName"));
                    questions.add(q);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }

    public List<Question> getPublicAnsweredQuestions() {

        try {
            String sql = """
                         SELECT [questionId]
                               ,[content]
                               ,[userId]
                               ,[timeCreate]
                               ,[categoryId]
                               ,[statusId]
                               ,[modifyId]
                               ,[fileName]
                           FROM [dbo].[Question]
                           where [modifyId] = 1 and [statusid] =2""";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question q = new Question();
                q.setQuestionId(rs.getInt("questionId"));
                q.setContent(rs.getNString("content"));
                q.setUser(userDB.getUserByUserId(rs.getInt("userId")));
                q.setTimeCreate(rs.getDate("timeCreate"));
                q.setCategory(cateDB.getCategoryById(rs.getInt("categoryId")));
                q.setStatus(qsDB.getStatusById(rs.getInt("statusId")));
                q.setModify(mDB.getModifyById(rs.getInt("modifyId")));
                q.setFileName(rs.getString("fileName"));
                listQuestion.add(q);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listQuestion;

    }

    public List<Question> getPublicAnsweredQuestionsByKeyword(String text) {

        try {
            String sql = "SELECT *FROM Question WHERE modifyId = 1 AND statusId = 2 AND content LIKE '%";
            sql += text + "%'";
            PreparedStatement stm = connection.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question q = new Question();
                q.setQuestionId(rs.getInt("questionId"));
                q.setContent(rs.getNString("content"));
                q.setUser(userDB.getUserByUserId(rs.getInt("userId")));
                q.setTimeCreate(rs.getDate("timeCreate"));
                q.setCategory(cateDB.getCategoryById(rs.getInt("categoryId")));
                q.setStatus(qsDB.getStatusById(rs.getInt("statusId")));
                q.setModify(mDB.getModifyById(rs.getInt("modifyId")));
                q.setFileName(rs.getString("fileName"));
                listQuestion.add(q);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listQuestion;

    }

    public Question getQuestionById(int qId) {

        try {
            String sql = """
                         SELECT [questionId]
                               ,[content]
                               ,[userId]
                               ,[timeCreate]
                               ,[categoryId]
                               ,[statusId]
                               ,[modifyId]
                               ,[fileName]
                           FROM [dbo].[Question]
                           where [questionId] = ?""";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, qId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question q = new Question();
                q.setQuestionId(rs.getInt("questionId"));
                q.setContent(rs.getNString("content"));
                q.setUser(userDB.getUserByUserId(rs.getInt("userId")));
                q.setTimeCreate(rs.getDate("timeCreate"));
                q.setCategory(cateDB.getCategoryById(rs.getInt("categoryId")));
                q.setStatus(qsDB.getStatusById(rs.getInt("statusId")));
                q.setModify(mDB.getModifyById(rs.getInt("modifyId")));
                q.setFileName(rs.getString("fileName"));
                return q;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public List<Question> getMyQuestionsNotAnswer(int userId) {
        try {
            String sql = """
                         SELECT [questionId]
                               ,[content]
                               ,[userId]
                               ,[timeCreate]
                               ,[categoryId]
                               ,[statusId]
                               ,[modifyId]
                               ,[fileName]
                           FROM [dbo].[Question]
                           where userId=? and statusId=1 """;
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question q = new Question();
                q.setQuestionId(rs.getInt("questionId"));
                q.setContent(rs.getNString("content"));
                q.setUser(userDB.getUserByUserId(rs.getInt("userId")));
                q.setTimeCreate(rs.getDate("timeCreate"));
                q.setCategory(cateDB.getCategoryById(rs.getInt("categoryId")));
                q.setStatus(qsDB.getStatusById(rs.getInt("statusId")));
                q.setModify(mDB.getModifyById(rs.getInt("modifyId")));
                q.setFileName(rs.getString("fileName"));
                listQuestion.add(q);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listQuestion;
    }

    public List<Question> getProcessingQuestion() {
        List<Question> listUnanswerQuestion = new ArrayList<>();
        try {
            String sql = """
                         SELECT*
                         FROM Question q
                         JOIN Category c on q.categoryId = c.categoryId
                         JOIN QuestionStatus s on q.statusId = s.statusId
                         JOIN [Modify] m on m.modifyId = q.modifyId
                         JOIN [User] u on q.userId = u.userId
                         WHERE q.statusId =1
                         ORDER BY q.timeCreate DESC
                         """;
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question q = new Question();
                q.setQuestionId(rs.getInt("questionId"));
                q.setContent(rs.getNString("content"));
                q.setUser(userDB.getUserByUserId(rs.getInt("userId")));
                q.setTimeCreate(rs.getDate("timeCreate"));

                Category category = new Category();
                category.setCategoryId(rs.getInt("categoryId"));
                category.setCategoryName(rs.getString("categoryName"));
                q.setCategory(category);

                QuestionStatus status = new QuestionStatus();
                status.setStatusId(rs.getInt("statusId"));
                status.setStatusName(rs.getString("statusName"));
                q.setStatus(status);

                Modify modify = new Modify();
                modify.setModifyId(rs.getInt("modifyId"));
                modify.setModifyName(rs.getString("modifyName"));
                q.setModify(modify);

                q.setFileName(rs.getString("fileName"));

                listUnanswerQuestion.add(q);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listUnanswerQuestion;
    }

    public List<Question> getCompletedQuestion() {
        List<Question> listCompletedQuestion = new ArrayList<>();
        try {
            String sql = """
                         SELECT *
                         FROM Question q
                         JOIN Category c on q.categoryId = c.categoryId
                         JOIN QuestionStatus s on q.statusId = s.statusId
                         JOIN [Modify] m on m.modifyId = q.modifyId
                         JOIN [User] u on q.userId = u.userId
                         WHERE q.statusId =2 OR q.statusId =3
                         ORDER BY q.timeCreate DESC
                         """;
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question q = new Question();
                q.setQuestionId(rs.getInt("questionId"));
                q.setContent(rs.getNString("content"));
                q.setUser(userDB.getUserByUserId(rs.getInt("userId")));
                q.setTimeCreate(rs.getDate("timeCreate"));

                Category category = new Category();
                category.setCategoryId(rs.getInt("categoryId"));
                category.setCategoryName(rs.getString("categoryName"));
                q.setCategory(category);

                QuestionStatus status = new QuestionStatus();
                status.setStatusId(rs.getInt("statusId"));
                status.setStatusName(rs.getString("statusName"));
                q.setStatus(status);

                Modify modify = new Modify();
                modify.setModifyId(rs.getInt("modifyId"));
                modify.setModifyName(rs.getString("modifyName"));
                q.setModify(modify);

                q.setFileName(rs.getString("fileName"));
                listCompletedQuestion.add(q);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCompletedQuestion;
    }

    public List<Question> getQuestionsByCategoryAnd2StatusId(int categoryId, int statusId1, int statusId2) {
        List<Question> questions = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [Question] WHERE statusId IN (?, ?)";
            if (categoryId != 0) {
                sql += " AND categoryId = ?";
            }
            PreparedStatement stm = connection.prepareStatement(sql);

            stm.setInt(1, statusId1);
            stm.setInt(2, statusId2);
            if (categoryId != 0) {
                stm.setInt(3, categoryId);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question q = new Question();
                q.setQuestionId(rs.getInt("questionId"));
                q.setContent(rs.getNString("content"));
                q.setUser(userDB.getUserByUserId(rs.getInt("userId")));
                q.setTimeCreate(rs.getDate("timeCreate"));
                q.setCategory(cateDB.getCategoryById(rs.getInt("categoryId")));
                q.setStatus(qsDB.getStatusById(rs.getInt("statusId")));
                q.setModify(mDB.getModifyById(rs.getInt("modifyId")));
                q.setFileName(rs.getString("fileName"));
                questions.add(q);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public List<Question> getAllQuestion() {
        List<Question> questions = new ArrayList<>();
        try {
            String sql = """
                         SELECT [questionId]
                               ,[content]
                               ,[userId]
                               ,[timeCreate]
                               ,[categoryId]
                               ,[statusId]
                               ,[modifyId]
                               ,[fileName]
                           FROM [dbo].[Question]
                           """;
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question q = new Question();
                q.setQuestionId(rs.getInt("questionId"));
                q.setContent(rs.getNString("content"));
                q.setUser(userDB.getUserByUserId(rs.getInt("userId")));
                q.setTimeCreate(rs.getDate("timeCreate"));
                q.setCategory(cateDB.getCategoryById(rs.getInt("categoryId")));
                q.setStatus(qsDB.getStatusById(rs.getInt("statusId")));
                q.setModify(mDB.getModifyById(rs.getInt("modifyId")));
                q.setFileName(rs.getString("fileName"));
                questions.add(q);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return questions;
    }

    public List<Question> getMyQuestionsByKeyword(String text, int userId) {
        try {
            String sql = "SELECT * FROM Question WHERE userId = ? AND statusId=1  AND content LIKE ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setString(2, "%" + text + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question q = new Question();
                q.setQuestionId(rs.getInt("questionId"));
                q.setContent(rs.getNString("content"));
                q.setUser(userDB.getUserByUserId(rs.getInt("userId")));
                q.setTimeCreate(rs.getDate("timeCreate"));
                q.setCategory(cateDB.getCategoryById(rs.getInt("categoryId")));
                q.setStatus(qsDB.getStatusById(rs.getInt("statusId")));
                q.setModify(mDB.getModifyById(rs.getInt("modifyId")));
                q.setFileName(rs.getString("fileName"));
                listQuestion.add(q);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listQuestion;
    }

    public List<Question> getQuestionsByCategoryId(String categoryId) {
        try {
            String sql = """
                         SELECT q.content,q.userId, q.timeCreate, c.categoryId, c.categoryName, q.statusId, q.modifyId, q.fileName
                          FROM Question q INNER JOIN Category c ON q.categoryId = c.categoryId
                         """;
            if (!categoryId.equals("ALL")) {
                sql += "  WHERE s.subjectid = " + categoryId;
            }
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question q = new Question();
                Category cat = new Category();
                q.setQuestionId(rs.getInt("questionId"));
                q.setContent(rs.getNString("content"));
                q.setUser(userDB.getUserByUserId(rs.getInt("userId")));
                q.setTimeCreate(rs.getDate("timeCreate"));
                q.setCategory(cateDB.getCategoryById(rs.getInt("categoryId")));
                q.setStatus(qsDB.getStatusById(rs.getInt("statusId")));
                q.setModify(mDB.getModifyById(rs.getInt("modifyId")));
                q.setFileName(rs.getString("fileName"));
                listQuestion.add(q);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listQuestion;

    }

    public List<Question> getMyQuestionsByKeywordAndCategory(String keyword, int userId, int categoryId) {

        try {
            String sql = "SELECT * FROM Question WHERE userId = ? AND statusId=1 AND content LIKE ? AND categoryId = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setString(2, "%" + keyword + "%");
            stm.setInt(3, categoryId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question q = new Question();
                q.setQuestionId(rs.getInt("questionId"));
                q.setContent(rs.getNString("content"));
                q.setUser(userDB.getUserByUserId(rs.getInt("userId")));
                q.setTimeCreate(rs.getDate("timeCreate"));
                q.setCategory(cateDB.getCategoryById(rs.getInt("categoryId")));
                q.setStatus(qsDB.getStatusById(rs.getInt("statusId")));
                q.setModify(mDB.getModifyById(rs.getInt("modifyId")));
                q.setFileName(rs.getString("fileName"));
                listQuestion.add(q);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listQuestion;
    }

    public ArrayList<Question> getMyQuestions(int userId) {
        ArrayList<Question> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Question WHERE userId = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question q = new Question();
                q.setQuestionId(rs.getInt("questionId"));
                q.setContent(rs.getNString("content"));
                q.setUser(userDB.getUserByUserId(rs.getInt("userId")));
                q.setTimeCreate(rs.getDate("timeCreate"));
                q.setCategory(cateDB.getCategoryById(rs.getInt("categoryId")));
                q.setStatus(qsDB.getStatusById(rs.getInt("statusId")));
                q.setModify(mDB.getModifyById(rs.getInt("modifyId")));
                q.setFileName(rs.getString("fileName"));
                list.add(q);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Map<String, Integer> viewTotalQuestionsByAllCategories() {
        Map<String, Integer> result = new LinkedHashMap<>();
        String sql = """
                     SELECT TOP 15 c.categoryName, COUNT(q.questionId) AS total 
                                     FROM Question q 
                                     JOIN Category c ON q.categoryId = c.categoryId 
                                     GROUP BY c.categoryName
                     \t\t\t\tOrder by total DESC""";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    result.put(rs.getString("categoryName"), rs.getInt("total"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public Map<String, Integer> viewTotalQuestionsByCategoryWithStatus(int statusId) {
        Map<String, Integer> result = new HashMap<>();
        String sql = "SELECT c.categoryName, COUNT(q.questionId) AS total "
                + "FROM Question q "
                + "JOIN Category c ON q.categoryId = c.categoryId "
                + "WHERE q.statusId = ? "
                + "GROUP BY c.categoryName";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, statusId);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    result.put(rs.getString("categoryName"), rs.getInt("total"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int getTotalQuestion(int month) {
        int totalQuestions = 0;
        String sql = "SELECT COUNT(*) AS Total FROM Question WHERE MONTH(timeCreate) = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, month);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    totalQuestions = rs.getInt("Total");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return totalQuestions;
    }

    public int getTotalQuestionsByStatus(int month, int statusId) {
        int totalQuestions = 0;
        String sql = "SELECT COUNT(*) AS Total FROM Question WHERE MONTH(timeCreate) = ? AND statusId = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, month);
            stm.setInt(2, statusId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    totalQuestions = rs.getInt("Total");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return totalQuestions;
    }

}

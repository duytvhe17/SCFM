/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Answer;
import entity.Question;
import entity.User;
import entity.Vote;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nguye
 */
public class AnswerDBContext extends DBContext {

    UserDBContext userDB = new UserDBContext();
    QuestionDBContext qsDB = new QuestionDBContext();

    public List<Answer> getAllPublicAnswer() {
        List<Answer> listAnswer = new ArrayList<>();
        UserDBContext uDB = new UserDBContext();
        QuestionDBContext qDB = new QuestionDBContext();
        VoteDBContext vDB = new VoteDBContext();
        try {
            String sql = """
                         SELECT a.[content]
                               ,a.[userId]
                               ,[time]
                               ,[answerId]
                           FROM Answer a 
                           INNER JOIN Question q ON a.answerId = q.questionId
                           WHERE q.modifyId =1  
                           ORDER BY [time] DESC """;
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Answer a = new Answer();
                a.setAnswerId(rs.getInt("answerId"));
                a.setContent(rs.getString("content"));
                a.setTime(rs.getDate("time"));
                a.setUser(uDB.getUserByUserId(rs.getInt("userId")));
                a.setQuestion(qDB.getQuestionById(rs.getInt("answerId")));
                a.setVote(vDB.getVoteById(rs.getInt("answerId")));
                listAnswer.add(a);
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(AnswerDBContext.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return listAnswer;
    }

    public List<Answer> getAllPublicAnswerByKey(String key) {
        List<Answer> listAnswer = new ArrayList<>();
        UserDBContext uDB = new UserDBContext();
        QuestionDBContext qDB = new QuestionDBContext();
        VoteDBContext vDB = new VoteDBContext();
        try {
            String sql = """
                         SELECT a.[content]
                               ,a.[userId]
                               ,[time]
                               ,[answerId]
                           FROM Answer a 
                           INNER JOIN Question q ON a.answerId = q.questionId
                           WHERE q.modifyId =1 AND q.[content] LIKE ?  
                         ORDER BY [time] DESC """;
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + key + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Answer a = new Answer();
                a.setAnswerId(rs.getInt("answerId"));
                a.setContent(rs.getString("content"));
                a.setTime(rs.getDate("time"));
                a.setUser(uDB.getUserByUserId(rs.getInt("userId")));
                a.setQuestion(qDB.getQuestionById(rs.getInt("answerId")));
                a.setVote(vDB.getVoteById(rs.getInt("answerId")));
                listAnswer.add(a);
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(AnswerDBContext.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return listAnswer;
    }

    public void saveAnswerAndUpdateQuestionStatus(int questionId, String responseContent, int userId, String fileName, int modifyId, int a) {
        try {

            String insertAnswerSql = """
                                     INSERT INTO Answer (answerId, content, userId, [time],fileName) 
                                     VALUES (?, ?, ?, GETDATE(),?)
                                     """;
            try (PreparedStatement insertAnswerStmt = connection.prepareStatement(insertAnswerSql)) {
                insertAnswerStmt.setInt(1, questionId);
                insertAnswerStmt.setString(2, responseContent);
                insertAnswerStmt.setInt(3, userId);
                insertAnswerStmt.setString(4, fileName);
                int executeUpdate = insertAnswerStmt.executeUpdate();
                System.out.println("Thêm câu trả lời : " + executeUpdate);
            }

            // Cập nhật trạng thái câu hỏi thành "Completed"
            String updateStatusSql = "UPDATE Question SET statusId = ?, modifyId = ? WHERE questionId = ?";
            try (PreparedStatement updateStatusStmt = connection.prepareStatement(updateStatusSql)) {
                updateStatusStmt.setInt(1, a); // Giả sử 2 là ID của trạng thái "Completed"
                updateStatusStmt.setInt(2, modifyId);
                updateStatusStmt.setInt(3, questionId);
                int executeUpdate = updateStatusStmt.executeUpdate();
                System.out.println("Update câu hỏi : " + executeUpdate);
            }

            /*
                lấy ra studentId của bảng question theo questionId
                lấy ra noti id của Noti vừa xử lí, -> cập nhật lại trạng thái đã đọc 
                của noti đó ở bảng noti-user
             */
            QuestionDBContext questionDAO = new QuestionDBContext();
            Question question = questionDAO.getQuestionById(questionId);
            NotificationDAO notificationDAO = new NotificationDAO();
            int uid = question.getUser().getUserId();
            int notiId = notificationDAO.getNewestNotiIdBySenderId(uid);
            notificationDAO.updateView(notiId);
            // them thong bao cho student vao bang noti-user

            notificationDAO.addNotificationUser(new User(uid), notiId);

        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(QuestionDBContext.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public Answer getAnswerByQuestionId(int questionId) {
        String sql = "SELECT * FROM Answer a JOIN [User] u on a.userId = u.userId WHERE answerId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, questionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Answer answer = new Answer();
                answer.setContent(rs.getString("content"));

                answer.setQuestion(qsDB.getQuestionById(rs.getInt("answerId")));

                answer.setUser(userDB.getUserByUserId(rs.getInt("userId")));

                answer.setTime(rs.getTimestamp("time"));
                answer.setFileName(rs.getString("fileName"));
                return answer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

   public List<Answer> getCompletedAnswersByUserId(int userId, int page, int pageSize, String searchQuery) {
    List<Answer> listAnswer = new ArrayList<>();
    UserDBContext uDB = new UserDBContext();
    QuestionDBContext qDB = new QuestionDBContext();
    String sql = """
            SELECT a.userId, a.answerId, a.content, q.content AS questionContent, a.[time], q.userId, a.[fileName], u.fullName, v.rating, u.email, u.mobile
            FROM Answer a
            JOIN Question q ON a.answerId = q.questionId
            JOIN [User] u ON q.userId = u.userId
            LEFT JOIN Vote v ON v.voteId = a.answerId
            WHERE a.userId = ? 
              AND (q.statusId = 2 OR q.statusId = 3)
              AND (q.content LIKE ? OR u.fullName LIKE ?)
            ORDER BY a.time DESC 
            OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
         """;
    List<Answer> answers = new ArrayList<>();
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, userId);
        stmt.setString(2, "%" + searchQuery + "%");
        stmt.setString(3, "%" + searchQuery + "%");
        stmt.setInt(4, (page - 1) * pageSize);
        stmt.setInt(5, pageSize);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Answer a = new Answer();
            a.setAnswerId(rs.getInt("answerId"));
            a.setContent(rs.getString("content"));
            a.setTime(rs.getDate("time"));
            a.setUser(uDB.getUserByUserId(rs.getInt("userId")));
            a.setQuestion(qDB.getQuestionById(rs.getInt("answerId")));

            a.setFileName(rs.getString("fileName"));
            
            Vote v = new Vote();
            // Nếu rating không có, set mặc định là 0 hoặc giá trị hợp lý khác
            v.setRating(rs.getInt("rating"));
            a.setVote(v);
            
            answers.add(a);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return answers;
}

    public int getTotalCompletedAnswersByUserId(int userId, String searchQuery) {
    String sql = """
                SELECT COUNT(*) FROM Answer a 
                JOIN Question q on a.answerId = q.questionId
                JOIN [User] u on q.userId = u.userId
                WHERE a.userId = ? AND (q.statusId = 2 OR q.statusId = 3)
                AND (q.content LIKE ? OR u.fullName LIKE ?)
             """;
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, userId);
        stmt.setString(2, "%" + searchQuery + "%");
        stmt.setString(3, "%" + searchQuery + "%");
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
}

    public List<Answer> getMyAnswerByUser(int userId) {
        List<Answer> listAnswer = new ArrayList<>();
        UserDBContext uDB = new UserDBContext();
        QuestionDBContext qDB = new QuestionDBContext();
        VoteDBContext vDB = new VoteDBContext();
        try {
            String sql = """
                         SELECT a.[content]
                               ,a.[userId]
                               ,a.[time]
                               ,[answerId]
                               ,a.[fileName]
                         

                          
                           FROM Answer a 
                           INNER JOIN Question q ON a.answerId = q.questionId

            
                           WHERE q.[userId] = ?
                         ORDER BY [time] DESC """;
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Answer a = new Answer();
                a.setAnswerId(rs.getInt("answerId"));
                a.setContent(rs.getString("content"));
                a.setTime(rs.getDate("time"));
                a.setUser(uDB.getUserByUserId(rs.getInt("userId")));
                a.setQuestion(qDB.getQuestionById(rs.getInt("answerId")));

                a.setFileName(rs.getString("fileName"));
                a.setVote(vDB.getVoteById(rs.getInt("answerId")));

                listAnswer.add(a);
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(AnswerDBContext.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return listAnswer;
    }

    public List<Answer> getMyAnswerByUserAndKey(int userId, String text) {
        List<Answer> listAnswer = new ArrayList<>();
        UserDBContext uDB = new UserDBContext();
        QuestionDBContext qDB = new QuestionDBContext();
        VoteDBContext vDB = new VoteDBContext();
        try {
            String sql = """
                         SELECT a.[content]
                               ,a.[userId]
                               ,[time]
                               ,[answerId]
                         
                           FROM Answer a 
                           INNER JOIN Question q ON a.answerId = q.questionId
                           WHERE q.[userId] = ? AND q.[content] LIKE ?
                         ORDER BY [time] DESC """;
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setString(2, "%" + text + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Answer a = new Answer();
                a.setAnswerId(rs.getInt("answerId"));
                a.setContent(rs.getString("content"));
                a.setTime(rs.getDate("time"));
                a.setUser(uDB.getUserByUserId(rs.getInt("userId")));
                a.setQuestion(qDB.getQuestionById(rs.getInt("answerId")));

                listAnswer.add(a);
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(AnswerDBContext.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return listAnswer;
    }

}

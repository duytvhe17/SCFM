/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.QuestionStatus;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class QuestionStatusDBContext extends DBContext {

    public ArrayList<QuestionStatus> listQuestionStatus() {
        ArrayList<QuestionStatus> stas = new ArrayList<>();
        try {
            String sql = """
                         SELECT [statusId]
                               ,[statusName]
                           FROM [dbo].[QuestionStatus]""";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                QuestionStatus q = new QuestionStatus();
                q.setStatusId(rs.getInt("statusId"));
                q.setStatusName(rs.getString("statusName"));
                stas.add(q);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionStatus.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stas;
    }

    public QuestionStatus getStatusById(int qsid) {
        try {
            String sql = """
                         SELECT [statusId]
                                 ,[statusName]
                             FROM [dbo].[QuestionStatus]
                             Where [statusId] = ?""";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, qsid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                QuestionStatus qs = new QuestionStatus();
                qs.setStatusId(rs.getInt("statusId"));
                qs.setStatusName(rs.getString("statusName"));
                return qs;
            }

        } catch (SQLException ex) {
            Logger.getLogger(QuestionStatus.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public QuestionStatus getAnsweredStatus() {
        QuestionStatus answeredStatus = null;
        String sql = "SELECT * FROM QuestionStatus WHERE statusId = 2";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                answeredStatus = new QuestionStatus();
                answeredStatus.setStatusId(rs.getInt("statusId"));
                answeredStatus.setStatusName(rs.getString("statusName"));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return answeredStatus;
    }
    
    public void rejectQuestion(int questionId) {
        QuestionStatusDBContext statusDB = new QuestionStatusDBContext();
        QuestionStatus rejectedStatus = statusDB.getStatusById(3); 
        updateQuestionStatus(questionId, rejectedStatus);
    }
    
    public void updateQuestionStatus(int questionId, QuestionStatus status) {
        try{
        String sql = "UPDATE Question SET statusId = ? WHERE questionId = ?";
        
            PreparedStatement stm = connection.prepareStatement(sql); 
            
            stm.setInt(1, status.getStatusId());
            stm.setInt(2, questionId);
            
            stm.executeUpdate();
        } catch (SQLException ex) {
           Logger.getLogger(QuestionStatus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

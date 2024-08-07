/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Vote;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nguye
 */
public class VoteDBContext extends DBContext {

    UserDBContext uDB = new UserDBContext();

    public Vote getVoteById(int voteId) {
        try {
            String sql = """
                         SELECT [voteId]
                               ,[rating]
                               ,[timeCreate]
                               
                           FROM [dbo].[Vote]
                           WHERE voteId = ?""";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, voteId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Vote vote = new Vote();
                vote.setVoteId(voteId);
                vote.setRating(rs.getInt("rating"));
                vote.setTime(rs.getDate("timeCreate"));

                return vote;
            }
        } catch (Exception ex) {
            Logger.getLogger(VoteDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void saveVote(int voteId, int rating) {
        try {
            String sql = """
                         INSERT INTO [dbo].[Vote]
                                    ([voteId]
                                    ,[rating]
                                    ,[timeCreate])
                              VALUES (?,?,GETDATE())""";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, voteId);
            stm.setInt(2, rating);
            stm.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(VoteDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void changeVote(int voteId, int rating){
        try {
            String sql = """
                         UPDATE [dbo].[Vote]
                            SET 
                               [rating] = ?
                               ,[timeCreate] = GETDATE()
                          WHERE voteId = ?""";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, rating);
            stm.setInt(2, voteId);
            stm.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(VoteDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

}

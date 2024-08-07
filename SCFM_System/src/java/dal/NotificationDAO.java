/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Notification;
import entity.NotificationUser;
import entity.Question;
import entity.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Windows
 */
public class NotificationDAO extends DBContext {

    public Notification getNotificationById(int id) {

        try {
            String sql = "SELECT [notificationId]\n"
                    + "      ,[content]\n"
                    + "      ,[senderId]\n"
                    + "  FROM [Notification]\n"
                    + "  where [notificationId] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Notification notification = new Notification();
                notification.setNotificationId(rs.getInt(1));
                notification.setContent(rs.getString(2));
                notification.setUid(rs.getInt(3));
                return notification;
            }

        } catch (SQLException ex) {
            Logger.getLogger(ModifyDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public User getEmployeeAnswer(int notiId) {
        try {
            String sql = "SELECT distinct u.[userId]\n"
                    + "      ,[maso]\n"
                    + "      ,[username]\n"
                    + "      ,[password]\n"
                    + "      ,[fullName]\n"
                    + "      ,[email]\n"
                    + "      ,[gender]\n"
                    + "      ,[mobile]\n"
                    + "      ,[address]\n"
                    + "      ,[dateOfBirth]\n"
                    + "      ,[picture]\n"
                    + "      ,[active]\n"
                    + "  FROM [User] u join Notification_User nu on u.userId = nu.userId\n"
                    + "  join [Notification] n on nu.notificationId = n.notificationId\n"
                    + "  join Answer a on u.userId = a.userId\n"
                    + "  join Question q on a.answerId = q.questionId\n"
                    + "  where q.userId = n.senderId and nu.notificationId = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, notiId);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                User u = new User();
                u.setUserId(rs.getInt("userId"));
                u.setMaso(rs.getString("maso"));
                u.setUserName(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setFullName(rs.getString("fullname"));
                u.setEmail(rs.getString("email"));
                u.setGender(rs.getBoolean("gender"));
                u.setMobile(rs.getString("mobile"));
                u.setAddress(rs.getString("address"));
                u.setDateOfBirth(rs.getDate("dateOfBirth"));
                u.setPicture(rs.getNString("picture"));
                u.setActive(rs.getBoolean("active"));
                return u;
            }
        } catch (SQLException ex) {
            Logger.getLogger(NotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<NotificationUser> getNotificationUsers(int check, int uid) {
        ArrayList<NotificationUser> list = new ArrayList<>();
        try {
            String sql = "SELECT  [notificationId]\n"
                    + "      ,[time]\n"
                    + "      ,[userId]\n"
                    + "      ,[isView]\n"
                    + "  FROM [Notification_User]";
            if (check == 0) {
                sql += " where isView = 0 AND [userId] = ?";
            } else {
                sql += " where [userId] = ?";
            }

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, uid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                NotificationUser notificationUser = new NotificationUser();

                notificationUser.setNotification(getNotificationById(rs.getInt(1)));
                notificationUser.setDate(rs.getDate(2));
                notificationUser.setIsView(rs.getBoolean(4));
                UserDBContext userDBContext = new UserDBContext();
                notificationUser.setUser(userDBContext.getUserByUserId(rs.getInt(3)));
                list.add(notificationUser);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ModifyDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void updateView(int id) {
        try {
            String sql = "UPDATE [Notification_User]\n"
                    + "   SET \n"
                    + "      [isView] = 1\n"
                    + " WHERE [notificationId] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public int getNewestNotiIdBySenderId(int uid) {
        try {
            String getNotiId = "SELECT max([notificationId]) as maxNotiId\n"
                    + "                     FROM [Notification]\n"
                    + "                    where senderId = ?";
            PreparedStatement ps = connection.prepareStatement(getNotiId);
            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("maxNotiId");
            }
        } catch (SQLException ex) {
            Logger.getLogger(NotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public void createNotificationForAllStaff(String content, int uid) {
        // add noti
        addNotification(content, uid);
        // get list employee
        UserDBContext dao = new UserDBContext();
        Set<User> users = dao.listEmployee();
        // add user-noti
        int notiId = getMaxIdNotificationUser();
        for (User user : users) {
            addNotificationUser(user, notiId);
        }
    }

    /**
     * Get max id of Notification - User
     *
     * @return
     */
    public int getMaxIdNotificationUser() {
        try {
            String sql = "select max(notificationId) as [maxid] from Notification";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("maxid");
            }
        } catch (SQLException ex) {
            Logger.getLogger(NotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    /**
     * Add new Notification
     *
     * @param content
     */
    public void addNotification(String content, int uid) {
        try {
            String sql = "INSERT INTO [Notification]\n"
                    + "           ([content]\n"
                    + "           ,[senderId])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, content);
            ps.setInt(2, uid);
            ps.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    // add noti cho user
    public void addNotificationUser(User employee, int notiID) {
        try {
            String sql = """
                         INSERT INTO [Notification_User]
                                    ([notificationId]
                                    ,[time]
                                    ,[userId]
                                    ,[isView])
                              VALUES
                                    (? 
                                    ,CONVERT(datetime,GETDATE())
                                    ,? 
                                    ,?)""";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, notiID);
            ps.setInt(2, employee.getUserId());
            ps.setBoolean(3, false);
            int status = ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Role;
import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RoleDBContext extends DBContext {

    public ArrayList<Role> getByUsernameAndUrl(String username, String url) {
        ArrayList<Role> roles = new ArrayList<>();
        try {
            String sql = """
                  SELECT r.roleid,r.rolename FROM [User] u
                                    INNER JOIN [User_Role] ur ON ur.userId = u.userId
                                    INNER JOIN [Role] r ON r.roleid = ur.roleId
                                    INNER JOIN [Feature_Role] fr ON fr.roleId = r.roleId
                                    INNER JOIN [Feature] f ON f.featureId = fr.featureId
                                    WHERE
                                    u.username = ? AND f.url = ?""";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, url);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Role r = new Role();
                r.setId(rs.getInt("roleId"));
                r.setName(rs.getString("roleName"));
                roles.add(r);

            }

        } catch (SQLException ex) {
            Logger.getLogger(RoleDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return roles;
    }
//    public static void main(String[] args) {
//        for (Role r : new RoleDBContext().getByUsernameAndUrl("sonnt", "/lecturer/timetable")) {
//            System.out.println(r);
//        }
//    }
//    

}

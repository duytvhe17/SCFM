/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Modify;
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
public class ModifyDBContext extends DBContext {

    public ArrayList<Modify> listModifies() {
        ArrayList<Modify> mods = new ArrayList<>();
        try {
            String sql = """
                         SELECT [modifyId]
                               ,[modifyName]
                           FROM [dbo].[Modify]""";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Modify m = new Modify();
                m.setModifyId(rs.getInt("modifyId"));
                m.setModifyName(rs.getString("modifyName"));
                mods.add(m);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ModifyDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mods;
    }

    public Modify getModifyById(int mid) {
        try {
            String sql = """
                         SELECT [modifyId]
                               ,[modifyName]
                           FROM [dbo].[Modify]
                           Where [modifyId] = ?""";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, mid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Modify m = new Modify();
                m.setModifyId(rs.getInt("modifyId"));
                m.setModifyName(rs.getString("modifyName"));
                return m;
            }

        } catch (SQLException ex) {
            Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Category;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.util.List;

/**
 *
 * @author admin
 */
public class CategoryDBContext extends DBContext {

    public ArrayList<Category> listCategories() {
        ArrayList<Category> cats = new ArrayList<>();
        try {
            String sql = """
                         SELECT [categoryId]
                               ,[categoryName]
                               ,[timeCreate]
                               ,[sampleFormName]
                           FROM [SCFM3].[dbo].[Category]
                           ORDER BY timeCreate DESC""";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Category c = new Category();
                c.setCategoryId(rs.getInt("categoryId"));
                c.setCategoryName(rs.getNString("categoryName"));
                c.setTimeCreate(rs.getDate("timeCreate"));
                c.setSampleFormName(rs.getString("sampleFormName"));
                cats.add(c);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cats;
    }

    public Category getCategoryById(int cid) {
        try {
            String sql = """
                         SELECT [categoryId]
                               ,[categoryName]
                               ,[timeCreate]
                               ,[sampleFormName]
                           FROM [dbo].[Category]
                           Where [categoryId] = ?""";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, cid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Category c = new Category();
                c.setCategoryId(rs.getInt("categoryId"));
                c.setCategoryName(rs.getNString("categoryName"));
                c.setTimeCreate(rs.getDate("timeCreate"));
                c.setSampleFormName(rs.getString("sampleFormName"));
                return c;
            }

        } catch (SQLException ex) {
            Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void insertCategory(Category category) {
        try {
            String sql = """
                         INSERT INTO [dbo].[Category]
                                    ([categoryName]
                                    ,[timeCreate]
                                    ,[sampleFormName])
                              VALUES
                                    (? 
                                    ,GETDATE()         
                                    ,?)       """;
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setNString(1, category.getCategoryName());
            stm.setString(2, category.getSampleFormName());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateCategory(int categoryId, String sampleFormName) {
        try {
            String sql = """
                         UPDATE [dbo].[Category]
                            SET [timeCreate] = GETDATE()
                               ,[sampleFormName] = ?
                          WHERE [categoryId] = ?""";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, sampleFormName);
            stm.setInt(2, categoryId);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Category> searchCategoryByKeyWord(String keyword) {
        List<Category> categories = new ArrayList<>();
        try {
            String sql = """
                         SELECT [categoryId]
                               ,[categoryName]
                               ,[timeCreate]
                               ,[sampleFormName]
                           FROM [dbo].[Category]
                           WHERE [categoryName] LIKE ?""";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + keyword + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Category c = new Category();
                c.setCategoryId(rs.getInt("categoryId"));
                c.setCategoryName(rs.getNString("categoryName"));
                c.setTimeCreate(rs.getDate("timeCreate"));
                c.setSampleFormName(rs.getString("sampleFormName"));
                categories.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categories;
    }

    public boolean checkCategoryNameExist(String categoryName) {
        try {
            String sql = """
                         SELECT COUNT(*) AS count
                           FROM [dbo].[Category]
                          WHERE [categoryName] = ?""";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setNString(1, categoryName);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}

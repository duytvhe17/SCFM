/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;

/**
 *
 * @author nguye
 */
public class Category {
    private int categoryId;
    private String categoryName;
    private Date timeCreate;
    private String sampleFormName;

    public Category() {
    }

    public Category(String categoryName, Date timeCreate, String sampleFormName) {
        
        this.categoryName = categoryName;
        this.timeCreate = timeCreate;
        this.sampleFormName = sampleFormName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Date getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(Date timeCreate) {
        this.timeCreate = timeCreate;
    }

    public String getSampleFormName() {
        return sampleFormName;
    }

    public void setSampleFormName(String sampleFormName) {
        this.sampleFormName = sampleFormName;
    }

    @Override
    public String toString() {
        return "Category{" + "categoryId=" + categoryId + ", categoryName=" + categoryName + ", timeCreate=" + timeCreate + '}';
    }
    
    
}

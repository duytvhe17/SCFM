/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author nguye
 */
public class QuestionStatus {
    private int statusId;
    private String statusName;

    public QuestionStatus() {
    }

    public QuestionStatus(int statusId, String statusName) {
        
        this.statusName = statusName;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }


    @Override
    public String toString() {
        return "QuestionStatus{" + "statusId=" + statusId + ", statusName=" + statusName + '}';
    }
    
    
}

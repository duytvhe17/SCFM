/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.sql.Date;

/**
 *
 * @author Windows
 */
public class NotificationUser {
    private int no; // field luu tru so thu tu trong danh sach
    private Notification notification;
    private boolean isView;
    private User user;
    private java.sql.Date date;
    private User sendUser; // Them field luu tru nguoi gui don

    public NotificationUser(Notification notification, boolean isView, User user) {
        this.notification = notification;
        this.isView = isView;
        this.user = user;
    }

    public NotificationUser() {
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    
    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public boolean isIsView() {
        return isView;
    }

    public void setIsView(boolean isView) {
        this.isView = isView;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getSendUser() {
        return sendUser;
    }

    public void setSendUser(User sendUser) {
        this.sendUser = sendUser;
    }
    
    
    
}

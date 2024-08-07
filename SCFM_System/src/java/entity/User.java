package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    private int userId;
    private String maso;
    private String userName;
    private String password;
    private String fullName;
    private String email;
    private boolean gender;
    private String mobile;
    private String address;
    private Date dateOfBirth;
    private String picture;
    private boolean active; 
    private List<Role> roles = new ArrayList<>();
    private List<Question> questions = new ArrayList<>();

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public User() {
    }

    public User(int userId) {
        this.userId = userId;
    }

    public User(String maso, String username, String password, String fullName, String email, boolean gender, String mobile, String address, Date dateOfBirth, String picture, boolean active) {
        this.maso = maso;
        this.userName = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.gender = gender;
        this.mobile = mobile;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.picture = picture;
        this.active = active; 
    }

    public boolean hasRole(String roleName) {
        for (Role role : roles) {
            if (role.getName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMaso() {
        return maso;
    }

    public void setMaso(String maso) {
        this.maso = maso;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isActive() { 
        return active;
    }

    public void setActive(boolean active) { 
        this.active = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", maso=" + maso +
                ", userName=" + userName +
                ", password=" + password +
                ", fullName=" + fullName +
                ", email=" + email +
                ", gender=" + gender +
                ", mobile=" + mobile +
                ", address=" + address +
                ", dateOfBirth=" + dateOfBirth +
                ", picture=" + picture +
                ", active=" + active + 
                ", roles=" + roles +
                ", questions=" + questions +
                '}';
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author nguye
 */
public class Question {
    private int questionId;
    private String content;
    private User user;
    private Date timeCreate;
    private Category category;
    private QuestionStatus status;
    private Modify modify;
    private String fileName;
    private List<Answer> answer = new ArrayList<>();

    public Question() {
    }

    public Question(int questionId, String content, User user, Date timeCreate, Category category, QuestionStatus status, Modify modify, String fileName) {
        this.questionId = questionId;
        this.content = content;
        this.user = user;
        this.timeCreate = timeCreate;
        this.category = category;
        this.status = status;
        this.modify = modify;
        this.fileName = fileName;
    }

    public List<Answer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Answer> answer) {
        this.answer = answer;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(Date timeCreate) {
        this.timeCreate = timeCreate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public QuestionStatus getStatus() {
        return status;
    }

    public void setStatus(QuestionStatus status) {
        this.status = status;
    }

    public Modify getModify() {
        return modify;
    }

    public void setModify(Modify modify) {
        this.modify = modify;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    

    @Override
    public String toString() {
        return "Question{" + "questionId=" + questionId + ", content=" + content + ", user=" + user + ", timeCreate=" + timeCreate + ", category=" + category + ", status=" + status + ", modify=" + modify + '}';
    }
    
    
}

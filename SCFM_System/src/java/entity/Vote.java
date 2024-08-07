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
public class Vote {
    private int voteId;
    private int rating;
    private Date time;
    private User user;

    public Vote() {
    }

    public Vote(int voteId, int rating, Date time, User user) {
        this.voteId = voteId;
        this.rating = rating;
        this.time = time;
        this.user = user;
    }

    public int getVoteId() {
        return voteId;
    }

    public void setVoteId(int voteId) {
        this.voteId = voteId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Vote{" + "voteId=" + voteId + ", rating=" + rating + ", time=" + time + ", user=" + user + '}';
    }
    
}

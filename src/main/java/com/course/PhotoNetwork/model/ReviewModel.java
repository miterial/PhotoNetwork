package com.course.PhotoNetwork.model;

import javax.persistence.*;

@Entity
@Table(name = "review")
public class ReviewModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String content;
    private int rate;

    @ManyToOne
    private UserModel author;

    public ReviewModel() {
    }

    public ReviewModel(String content, int rate, UserModel author) {
        this.content = content;
        this.rate = rate;
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public UserModel getAuthor() {
        return author;
    }

    public void setAuthor(UserModel author) {
        this.author = author;
    }

    public long getId() {
        return id;
    }
}

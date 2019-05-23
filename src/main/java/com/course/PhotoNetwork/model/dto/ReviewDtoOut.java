package com.course.PhotoNetwork.model.dto;

public class ReviewDtoOut {

    private Long authorId;
    private String authorUsername;
    private int rate;
    private String content;

    public ReviewDtoOut() {
    }

    public ReviewDtoOut(Long authorId, String authorUsername, int rate, String content) {
        this.authorId = authorId;
        this.authorUsername = authorUsername;
        this.rate = rate;
        this.content = content;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }
}

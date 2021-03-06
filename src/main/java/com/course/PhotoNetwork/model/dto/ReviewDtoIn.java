package com.course.PhotoNetwork.model.dto;

public class ReviewDtoIn {

    private Long bookingId;
    private Long authorId;
    private Long masterId;
    private Integer rate;
    private String content;

    public ReviewDtoIn() {
    }

    public ReviewDtoIn(Long bookingId, Long authorId, Long masterId, Integer rate, String content) {
        this.bookingId = bookingId;
        this.authorId = authorId;
        this.masterId = masterId;
        this.rate = rate;
        this.content = content;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getMasterId() {
        return masterId;
    }

    public void setMasterId(Long masterId) {
        this.masterId = masterId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
}

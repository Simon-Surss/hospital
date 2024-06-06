package com.bitzh.hospitalsystem.model;

public class Review {
    private int id;
    private int userId;
    private int doctorId;
    private int rating; // 新增评分字段
    private String reviewContent; // 将内容字段改名为 reviewContent

    // 无参数构造方法
    public Review() {
    }

    // 有参数构造方法
    public Review(int id, int userId, int doctorId, int rating, String reviewContent) {
        this.id = id;
        this.userId = userId;
        this.doctorId = doctorId;
        this.rating = rating;
        this.reviewContent = reviewContent;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }
}

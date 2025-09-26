package model;

import java.time.LocalDateTime;

public class Review {
    private Integer reviewId;
    private Integer userId;
    private Integer eventId;
    private Short rating; // SMALLINT
    private String comment;
    private LocalDateTime createdAt;

    // Constructors
    public Review() {}

    public Review(Integer reviewId, Integer userId, Integer eventId, Short rating, String comment, LocalDateTime createdAt) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.eventId = eventId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Integer getReviewId() { return reviewId; }
    public void setReviewId(Integer reviewId) { this.reviewId = reviewId; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public Integer getEventId() { return eventId; }
    public void setEventId(Integer eventId) { this.eventId = eventId; }
    public Short getRating() { return rating; }
    public void setRating(Short rating) { this.rating = rating; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
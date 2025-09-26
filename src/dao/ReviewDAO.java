package dao;

import model.Review;

import java.util.List;

public interface ReviewDAO {
    Review save(Review review);

    Review findById(int reviewId);
    List<Review> findReviewsByEventId(int eventId);
    List<Review> findReviewsByUserId(int userId);
    Double getAverageRatingForEvent(int eventId);

    void update(Review review);

    void delete(int reviewId);
}
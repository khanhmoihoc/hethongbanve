package dao;

import connection.BaseDAO;
import model.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAOImpl extends BaseDAO implements ReviewDAO {

    private Review extractReviewFromResultSet(ResultSet rs) throws SQLException {
        Review review = new Review();
        review.setReviewId(rs.getInt("review_id"));
        review.setUserId(rs.getInt("user_id"));
        review.setEventId(rs.getInt("event_id"));
        review.setRating(rs.getShort("rating"));
        review.setComment(rs.getString("comment"));
        review.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return review;
    }

    @Override
    public Review save(Review review) {
        String SQL = "INSERT INTO reviews (user_id, event_id, rating, comment) VALUES (?, ?, ?, ?) RETURNING review_id, created_at";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, review.getUserId());
            pstmt.setInt(2, review.getEventId());
            pstmt.setShort(3, review.getRating());
            pstmt.setString(4, review.getComment());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    review.setReviewId(rs.getInt("review_id"));
                    review.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    return review;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Review findById(int reviewId) {
        String SQL = "SELECT * FROM reviews WHERE review_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, reviewId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractReviewFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Review> findReviewsByEventId(int eventId) {
        List<Review> reviews = new ArrayList<>();
        String SQL = "SELECT * FROM reviews WHERE event_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, eventId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    reviews.add(extractReviewFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    @Override
    public List<Review> findReviewsByUserId(int userId) {
        List<Review> reviews = new ArrayList<>();
        String SQL = "SELECT * FROM reviews WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    reviews.add(extractReviewFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    @Override
    public Double getAverageRatingForEvent(int eventId) {
        String SQL = "SELECT AVG(rating) AS avg_rating FROM reviews WHERE event_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, eventId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Trả về Double (null nếu không có đánh giá nào)
                    double avg = rs.getDouble("avg_rating");
                    return rs.wasNull() ? null : avg;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Review review) {
        String SQL = "UPDATE reviews SET user_id = ?, event_id = ?, rating = ?, comment = ? WHERE review_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, review.getUserId());
            pstmt.setInt(2, review.getEventId());
            pstmt.setShort(3, review.getRating());
            pstmt.setString(4, review.getComment());
            pstmt.setInt(5, review.getReviewId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int reviewId) {
        String SQL = "DELETE FROM reviews WHERE review_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, reviewId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

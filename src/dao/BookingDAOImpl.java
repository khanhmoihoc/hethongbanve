package dao;

import connection.BaseDAO;
import model.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl extends BaseDAO implements BookingDAO {

    private Booking extractBookingFromResultSet(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setBookingId(rs.getInt("booking_id"));
        booking.setUserId(rs.getInt("user_id"));
        booking.setTicketId(rs.getInt("ticket_id"));
        booking.setBookingTime(rs.getTimestamp("booking_time").toLocalDateTime());
        booking.setPaymentStatus(rs.getString("payment_status"));
        return booking;
    }

    @Override
    public Booking save(Booking booking) {
        String SQL = "INSERT INTO bookings (user_id, ticket_id, payment_status) VALUES (?, ?, ?) RETURNING booking_id, booking_time";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, booking.getUserId());
            pstmt.setInt(2, booking.getTicketId());
            pstmt.setString(3, booking.getPaymentStatus());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    booking.setBookingId(rs.getInt("booking_id"));
                    booking.setBookingTime(rs.getTimestamp("booking_time").toLocalDateTime());
                    return booking;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Booking findById(int bookingId) {
        String SQL = "SELECT * FROM bookings WHERE booking_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, bookingId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractBookingFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Booking> findBookingsByUserId(int userId) {
        List<Booking> bookings = new ArrayList<>();
        String SQL = "SELECT * FROM bookings WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    bookings.add(extractBookingFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public List<Booking> findBookingsByTicketId(int ticketId) {
        List<Booking> bookings = new ArrayList<>();
        String SQL = "SELECT * FROM bookings WHERE ticket_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, ticketId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    bookings.add(extractBookingFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public void update(Booking booking) {
        String SQL = "UPDATE bookings SET user_id = ?, ticket_id = ?, payment_status = ? WHERE booking_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, booking.getUserId());
            pstmt.setInt(2, booking.getTicketId());
            pstmt.setString(3, booking.getPaymentStatus());
            pstmt.setInt(4, booking.getBookingId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePaymentStatus(int bookingId, String status) {
        String SQL = "UPDATE bookings SET payment_status = ? WHERE booking_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, bookingId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int bookingId) {
        String SQL = "DELETE FROM bookings WHERE booking_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, bookingId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package dao;

import connection.BaseDAO;
import model.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl extends BaseDAO implements PaymentDAO {

    private Payment extractPaymentFromResultSet(ResultSet rs) throws SQLException {
        Payment payment = new Payment();
        payment.setPaymentId(rs.getInt("payment_id"));
        payment.setBookingId(rs.getInt("booking_id"));
        payment.setAmount(rs.getBigDecimal("amount"));
        payment.setPaymentMethod(rs.getString("payment_method"));
        payment.setPaymentTime(rs.getTimestamp("payment_time").toLocalDateTime());
        return payment;
    }

    @Override
    public Payment save(Payment payment) {
        String SQL = "INSERT INTO payments (booking_id, amount, payment_method, payment_time) VALUES (?, ?, ?, ?) RETURNING payment_id";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, payment.getBookingId());
            pstmt.setBigDecimal(2, payment.getAmount());
            pstmt.setString(3, payment.getPaymentMethod());
            // Sử dụng payment.getPaymentTime() nếu đã được đặt, nếu không sử dụng CURRENT_TIMESTAMP của DB
            pstmt.setTimestamp(4, Timestamp.valueOf(payment.getPaymentTime()));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    payment.setPaymentId(rs.getInt("payment_id"));
                    return payment;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Payment findById(int paymentId) {
        String SQL = "SELECT * FROM payments WHERE payment_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, paymentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractPaymentFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ... Các phương thức khác tương tự ...

    @Override
    public List<Payment> findPaymentsByBookingId(int bookingId) {
        List<Payment> payments = new ArrayList<>();
        String SQL = "SELECT * FROM payments WHERE booking_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, bookingId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    payments.add(extractPaymentFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    @Override
    public List<Payment> findPaymentsByMethod(String method) {
        List<Payment> payments = new ArrayList<>();
        String SQL = "SELECT * FROM payments WHERE payment_method = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, method);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    payments.add(extractPaymentFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    @Override
    public void update(Payment payment) {
        String SQL = "UPDATE payments SET booking_id = ?, amount = ?, payment_method = ?, payment_time = ? WHERE payment_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, payment.getBookingId());
            pstmt.setBigDecimal(2, payment.getAmount());
            pstmt.setString(3, payment.getPaymentMethod());
            pstmt.setTimestamp(4, Timestamp.valueOf(payment.getPaymentTime()));
            pstmt.setInt(5, payment.getPaymentId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int paymentId) {
        String SQL = "DELETE FROM payments WHERE payment_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, paymentId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
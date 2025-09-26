package dao;

import connection.BaseDAO;
import model.Seat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatDAOImpl extends BaseDAO implements SeatDAO {

    private Seat extractSeatFromResultSet(ResultSet rs) throws SQLException {
        Seat seat = new Seat();
        seat.setSeatId(rs.getInt("seat_id"));
        seat.setVenueId(rs.getInt("venue_id"));
        // Lấy CHAR(1) thành Character
        String row = rs.getString("seat_row");
        if (row != null && !row.isEmpty()) {
            seat.setSeatRow(row.charAt(0));
        }
        seat.setSeatNumber(rs.getInt("seat_number"));
        return seat;
    }

    @Override
    public Seat save(Seat seat) {
        String SQL = "INSERT INTO seats (venue_id, seat_row, seat_number) VALUES (?, ?, ?) RETURNING seat_id";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, seat.getVenueId());
            pstmt.setString(2, String.valueOf(seat.getSeatRow()));
            pstmt.setInt(3, seat.getSeatNumber());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    seat.setSeatId(rs.getInt("seat_id"));
                    return seat;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Seat findById(int seatId) {
        String SQL = "SELECT * FROM seats WHERE seat_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, seatId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractSeatFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Seat> findSeatsByVenueId(int venueId) {
        List<Seat> seats = new ArrayList<>();
        String SQL = "SELECT * FROM seats WHERE venue_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, venueId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    seats.add(extractSeatFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }

    @Override
    public Seat findSeatByDetails(int venueId, char row, int number) {
        String SQL = "SELECT * FROM seats WHERE venue_id = ? AND seat_row = ? AND seat_number = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, venueId);
            pstmt.setString(2, String.valueOf(row));
            pstmt.setInt(3, number);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractSeatFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Seat seat) {
        String SQL = "UPDATE seats SET venue_id = ?, seat_row = ?, seat_number = ? WHERE seat_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, seat.getVenueId());
            pstmt.setString(2, String.valueOf(seat.getSeatRow()));
            pstmt.setInt(3, seat.getSeatNumber());
            pstmt.setInt(4, seat.getSeatId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int seatId) {
        String SQL = "DELETE FROM seats WHERE seat_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, seatId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
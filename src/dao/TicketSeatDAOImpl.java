package dao;

import connection.BaseDAO;
import model.Seat;
import model.Ticket;
import model.TicketSeat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketSeatDAOImpl extends BaseDAO implements TicketSeatDAO {

    // Helper method để ánh xạ ResultSet sang TicketSeat
    private TicketSeat extractTicketSeatFromResultSet(ResultSet rs) throws SQLException {
        TicketSeat ts = new TicketSeat();
        ts.setId(rs.getInt("id"));
        ts.setTicketId(rs.getInt("ticket_id"));
        ts.setSeatId(rs.getInt("seat_id"));
        return ts;
    }

    // Helper method để ánh xạ ResultSet sang Seat (Dữ liệu từ bảng seats)
    private Seat extractSeatFromResultSet(ResultSet rs) throws SQLException {
        Seat seat = new Seat();
        seat.setSeatId(rs.getInt("seat_id"));
        seat.setVenueId(rs.getInt("venue_id"));
        String row = rs.getString("seat_row");
        if (row != null && !row.isEmpty()) {
            seat.setSeatRow(row.charAt(0));
        }
        seat.setSeatNumber(rs.getInt("seat_number"));
        return seat;
    }

    // Helper method để ánh xạ ResultSet sang Ticket (Dữ liệu từ bảng tickets)
    private Ticket extractTicketFromResultSet(ResultSet rs) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setTicketId(rs.getInt("ticket_id"));
        ticket.setEventId(rs.getInt("event_id"));
        ticket.setSeatNumber(rs.getString("seat_number"));
        ticket.setPrice(rs.getBigDecimal("price"));
        ticket.setIsSold(rs.getBoolean("is_sold"));
        return ticket;
    }

    @Override
    public TicketSeat save(TicketSeat ticketSeat) {
        String SQL = "INSERT INTO ticket_seat (ticket_id, seat_id) VALUES (?, ?) RETURNING id";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, ticketSeat.getTicketId());
            pstmt.setInt(2, ticketSeat.getSeatId());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ticketSeat.setId(rs.getInt("id"));
                    return ticketSeat;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public TicketSeat findById(int id) {
        String SQL = "SELECT * FROM ticket_seat WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractTicketSeatFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // TRIỂN KHAI BỔ SUNG: Tìm kiếm Seat POJO dựa trên Ticket ID
    @Override
    public Seat findSeatByTicketId(int ticketId) {
        // JOIN ticket_seat (ts) với seats (s)
        String SQL = "SELECT s.* FROM seats s JOIN ticket_seat ts ON s.seat_id = ts.seat_id WHERE ts.ticket_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, ticketId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Ánh xạ các cột từ bảng seats
                    return extractSeatFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Ticket findTicketBySeatId(int seatId) {
        // JOIN ticket_seat (ts) với tickets (t)
        String SQL = "SELECT t.* FROM tickets t JOIN ticket_seat ts ON t.ticket_id = ts.ticket_id WHERE ts.seat_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, seatId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Ánh xạ các cột từ bảng tickets
                    return extractTicketFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteByTicketId(int ticketId) {
        String SQL = "DELETE FROM ticket_seat WHERE ticket_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, ticketId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String SQL = "DELETE FROM ticket_seat WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
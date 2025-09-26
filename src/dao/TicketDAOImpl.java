package dao;

import connection.BaseDAO;
import model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketDAOImpl extends BaseDAO implements TicketDAO {

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
    public Ticket save(Ticket ticket) {
        String SQL = "INSERT INTO tickets (event_id, seat_number, price, is_sold) VALUES (?, ?, ?, ?) RETURNING ticket_id";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, ticket.getEventId());
            pstmt.setString(2, ticket.getSeatNumber());
            pstmt.setBigDecimal(3, ticket.getPrice());
            pstmt.setBoolean(4, ticket.getIsSold());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ticket.setTicketId(rs.getInt("ticket_id"));
                    return ticket;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Ticket findById(int ticketId) {
        String SQL = "SELECT * FROM tickets WHERE ticket_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, ticketId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractTicketFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Ticket> findTicketsByEventId(int eventId) {
        List<Ticket> tickets = new ArrayList<>();
        String SQL = "SELECT * FROM tickets WHERE event_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, eventId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tickets.add(extractTicketFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public List<Ticket> findAvailableTicketsByEventId(int eventId) {
        List<Ticket> tickets = new ArrayList<>();
        String SQL = "SELECT * FROM tickets WHERE event_id = ? AND is_sold = FALSE";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, eventId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tickets.add(extractTicketFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public void update(Ticket ticket) {
        String SQL = "UPDATE tickets SET event_id = ?, seat_number = ?, price = ?, is_sold = ? WHERE ticket_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, ticket.getEventId());
            pstmt.setString(2, ticket.getSeatNumber());
            pstmt.setBigDecimal(3, ticket.getPrice());
            pstmt.setBoolean(4, ticket.getIsSold());
            pstmt.setInt(5, ticket.getTicketId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void markAsSold(int ticketId) {
        String SQL = "UPDATE tickets SET is_sold = TRUE WHERE ticket_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, ticketId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int ticketId) {
        String SQL = "DELETE FROM tickets WHERE ticket_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, ticketId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


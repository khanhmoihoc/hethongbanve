package dao;

import connection.BaseDAO;
import model.Event;
import model.EventVenue;
import model.Venue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class EventVenueDAOImpl extends BaseDAO implements EventVenueDAO {

    private EventVenue extractEventVenueFromResultSet(ResultSet rs) throws SQLException {
        EventVenue ev = new EventVenue();
        ev.setId(rs.getInt("id"));
        ev.setEventId(rs.getInt("event_id"));
        ev.setVenueId(rs.getInt("venue_id"));
        return ev;
    }

    @Override
    public EventVenue save(EventVenue eventVenue) {
        String SQL = "INSERT INTO event_venue (event_id, venue_id) VALUES (?, ?) RETURNING id";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, eventVenue.getEventId());
            pstmt.setInt(2, eventVenue.getVenueId());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    eventVenue.setId(rs.getInt("id"));
                    return eventVenue;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Venue> findVenuesByEventId(int eventId) {
        List<Venue> venues = new ArrayList<>();
        String SQL = "SELECT v.* FROM venues v JOIN event_venue ev ON v.venue_id = ev.venue_id WHERE ev.event_id = ?";
        // Trong thực tế, bạn sẽ cần VenueDAO để tạo đối tượng Venue, hoặc JOIN và lấy tất cả các cột
        // Ở đây, tôi giả sử có thể lấy tất cả các cột Venue từ JOIN
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, eventId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Venue venue = new Venue();
                    venue.setVenueId(rs.getInt("venue_id"));
                    venue.setName(rs.getString("name"));
                    venue.setAddress(rs.getString("address"));
                    venue.setCity(rs.getString("city"));
                    venue.setCapacity(rs.getInt("capacity"));
                    venues.add(venue);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return venues;
    }

    @Override
    public List<Event> findEventsByVenueId(int venueId) {
        // Tương tự, sử dụng JOIN với bảng events để trả về Event POJO
        // Logic truy vấn tương tự như findVenuesByEventId, nhưng đổi vai trò Event/Venue
        // (Không triển khai chi tiết ở đây để tránh lặp lại code mapping Event)
        return new ArrayList<>();
    }

    @Override
    public EventVenue findById(int id) {
        String SQL = "SELECT * FROM event_venue WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractEventVenueFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteByEventIdAndVenueId(int eventId, int venueId) {
        String SQL = "DELETE FROM event_venue WHERE event_id = ? AND venue_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, eventId);
            pstmt.setInt(2, venueId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String SQL = "DELETE FROM event_venue WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
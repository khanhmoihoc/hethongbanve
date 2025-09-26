package dao;

import connection.BaseDAO;
import model.Venue;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class VenueDAOImpl extends BaseDAO implements VenueDAO {

    private Venue extractVenueFromResultSet(ResultSet rs) throws SQLException {
        Venue venue = new Venue();
        venue.setVenueId(rs.getInt("venue_id"));
        venue.setName(rs.getString("name"));
        venue.setAddress(rs.getString("address"));
        venue.setCity(rs.getString("city"));
        venue.setCapacity(rs.getInt("capacity"));
        return venue;
    }

    @Override
    public Venue save(Venue venue) {
        String SQL = "INSERT INTO venues (name, address, city, capacity) VALUES (?, ?, ?, ?) RETURNING venue_id";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, venue.getName());
            pstmt.setString(2, venue.getAddress());
            pstmt.setString(3, venue.getCity());
            pstmt.setInt(4, venue.getCapacity());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    venue.setVenueId(rs.getInt("venue_id"));
                    return venue;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Venue findById(int venueId) {
        String SQL = "SELECT * FROM venues WHERE venue_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, venueId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractVenueFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Venue> findAll() {
        List<Venue> venues = new ArrayList<>();
        String SQL = "SELECT * FROM venues";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                venues.add(extractVenueFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return venues;
    }

    @Override
    public List<Venue> findVenuesByCity(String city) {
        List<Venue> venues = new ArrayList<>();
        String SQL = "SELECT * FROM venues WHERE city = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, city);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    venues.add(extractVenueFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return venues;
    }

    @Override
    public void update(Venue venue) {
        String SQL = "UPDATE venues SET name = ?, address = ?, city = ?, capacity = ? WHERE venue_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, venue.getName());
            pstmt.setString(2, venue.getAddress());
            pstmt.setString(3, venue.getCity());
            pstmt.setInt(4, venue.getCapacity());
            pstmt.setInt(5, venue.getVenueId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int venueId) {
        String SQL = "DELETE FROM venues WHERE venue_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, venueId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
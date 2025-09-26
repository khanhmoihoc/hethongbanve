package dao;

import model.Venue;

import java.util.List;

public interface VenueDAO {
    Venue save(Venue venue);

    Venue findById(int venueId);
    List<Venue> findAll();
    List<Venue> findVenuesByCity(String city);

    void update(Venue venue);

    void delete(int venueId);
}
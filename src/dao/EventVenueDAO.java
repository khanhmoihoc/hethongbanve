package dao;

import model.Event;
import model.EventVenue;
import model.Venue;

import java.util.List;

public interface EventVenueDAO {
    EventVenue save(EventVenue eventVenue);

    List<Venue> findVenuesByEventId(int eventId);
    List<Event> findEventsByVenueId(int venueId);
    EventVenue findById(int id);

    void deleteByEventIdAndVenueId(int eventId, int venueId);
    void delete(int id);
}

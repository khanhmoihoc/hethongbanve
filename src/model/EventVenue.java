package model;

public class EventVenue {
    private Integer id;
    private Integer eventId;
    private Integer venueId;

    // Constructors
    public EventVenue() {}

    public EventVenue(Integer id, Integer eventId, Integer venueId) {
        this.id = id;
        this.eventId = eventId;
        this.venueId = venueId;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getEventId() { return eventId; }
    public void setEventId(Integer eventId) { this.eventId = eventId; }
    public Integer getVenueId() { return venueId; }
    public void setVenueId(Integer venueId) { this.venueId = venueId; }
}
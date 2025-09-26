package model;

public class Venue {
    private Integer venueId;
    private String name;
    private String address;
    private String city;
    private Integer capacity;

    // Constructors
    public Venue() {}

    public Venue(Integer venueId, String name, String address, String city, Integer capacity) {
        this.venueId = venueId;
        this.name = name;
        this.address = address;
        this.city = city;
        this.capacity = capacity;
    }

    // Getters and Setters
    public Integer getVenueId() { return venueId; }
    public void setVenueId(Integer venueId) { this.venueId = venueId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
}
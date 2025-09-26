package model;

import java.time.LocalDateTime;

public class Event {
    private Integer eventId;
    private String title;
    private String description;
    private String eventType; // movie, concert, bus, other
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private LocalDateTime createdAt;

    // Constructors
    public Event() {}

    public Event(Integer eventId, String title, String description, String eventType, LocalDateTime startTime, LocalDateTime endTime, String location, LocalDateTime createdAt) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.eventType = eventType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Integer getEventId() { return eventId; }
    public void setEventId(Integer eventId) { this.eventId = eventId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
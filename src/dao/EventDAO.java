package dao;

import model.Event;

import java.util.List;
import java.time.LocalDateTime;

public interface EventDAO {
    Event save(Event event);

    Event findById(int eventId);
    List<Event> findAll();
    List<Event> findEventsByType(String eventType);
    List<Event> findEventsBetween(LocalDateTime start, LocalDateTime end);

    void update(Event event);

    void delete(int eventId);
}
package dao;

import model.Event;

import java.time.LocalDateTime;
import java.util.List;

public class EventDAOImpl implements EventDAO {
    @Override
    public Event save(Event event) {
        return null;
    }

    @Override
    public Event findById(int eventId) {
        return null;
    }

    @Override
    public List<Event> findAll() {
        return List.of();
    }

    @Override
    public List<Event> findEventsByType(String eventType) {
        return List.of();
    }

    @Override
    public List<Event> findEventsBetween(LocalDateTime start, LocalDateTime end) {
        return List.of();
    }

    @Override
    public void update(Event event) {

    }

    @Override
    public void delete(int eventId) {

    }
}

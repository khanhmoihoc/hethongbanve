package dao;

import model.Ticket;

import java.util.List;

public interface TicketDAO {
    Ticket save(Ticket ticket);

    Ticket findById(int ticketId);
    List<Ticket> findTicketsByEventId(int eventId);
    List<Ticket> findAvailableTicketsByEventId(int eventId);

    void update(Ticket ticket);
    void markAsSold(int ticketId);

    void delete(int ticketId);
}
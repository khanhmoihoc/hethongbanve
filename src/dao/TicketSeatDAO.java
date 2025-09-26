package dao;

import model.Seat;
import model.Ticket;
import model.TicketSeat;

import java.util.List;

public interface TicketSeatDAO {
    TicketSeat save(TicketSeat ticketSeat);

    TicketSeat findById(int id);
    Seat findSeatByTicketId(int ticketId);
    Ticket findTicketBySeatId(int seatId);

    // D - Delete
    void deleteByTicketId(int ticketId);
    void delete(int id);
}

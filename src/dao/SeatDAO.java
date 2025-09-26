package dao;

import model.Seat;

import java.util.List;

public interface SeatDAO {
    Seat save(Seat seat);

    Seat findById(int seatId);
    List<Seat> findSeatsByVenueId(int venueId);
    Seat findSeatByDetails(int venueId, char row, int number);

    void update(Seat seat);

    void delete(int seatId);
}

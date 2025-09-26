package dao;

import model.Booking;

import java.util.List;

public interface BookingDAO {
    Booking save(Booking booking);

    Booking findById(int bookingId);
    List<Booking> findBookingsByUserId(int userId);
    List<Booking> findBookingsByTicketId(int ticketId);

    void update(Booking booking);
    void updatePaymentStatus(int bookingId, String status);

    void delete(int bookingId);
}

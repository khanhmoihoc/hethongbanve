package dao;

import model.Payment;

import java.util.List;

public interface PaymentDAO {
    Payment save(Payment payment);

    Payment findById(int paymentId);
    List<Payment> findPaymentsByBookingId(int bookingId);
    List<Payment> findPaymentsByMethod(String method);

    void update(Payment payment);

    void delete(int paymentId);
}

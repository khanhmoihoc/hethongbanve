package model;

import java.time.LocalDateTime;

public class Booking {
    private Integer bookingId;
    private Integer userId;
    private Integer ticketId;
    private LocalDateTime bookingTime;
    private String paymentStatus; // pending, paid, cancelled

    // Constructors
    public Booking() {}

    public Booking(Integer bookingId, Integer userId, Integer ticketId, LocalDateTime bookingTime, String paymentStatus) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.ticketId = ticketId;
        this.bookingTime = bookingTime;
        this.paymentStatus = paymentStatus;
    }

    // Getters and Setters
    public Integer getBookingId() { return bookingId; }
    public void setBookingId(Integer bookingId) { this.bookingId = bookingId; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public Integer getTicketId() { return ticketId; }
    public void setTicketId(Integer ticketId) { this.ticketId = ticketId; }
    public LocalDateTime getBookingTime() { return bookingTime; }
    public void setBookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; }
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
}
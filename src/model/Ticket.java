package model;

import java.math.BigDecimal;

public class Ticket {
    private Integer ticketId;
    private Integer eventId;
    private String seatNumber;
    private BigDecimal price;
    private Boolean isSold;

    // Constructors
    public Ticket() {}

    public Ticket(Integer ticketId, Integer eventId, String seatNumber, BigDecimal price, Boolean isSold) {
        this.ticketId = ticketId;
        this.eventId = eventId;
        this.seatNumber = seatNumber;
        this.price = price;
        this.isSold = isSold;
    }

    // Getters and Setters
    public Integer getTicketId() { return ticketId; }
    public void setTicketId(Integer ticketId) { this.ticketId = ticketId; }
    public Integer getEventId() { return eventId; }
    public void setEventId(Integer eventId) { this.eventId = eventId; }
    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Boolean getIsSold() { return isSold; }
    public void setIsSold(Boolean isSold) { this.isSold = isSold; }
}
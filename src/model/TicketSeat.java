package model;

public class TicketSeat {
    private Integer id;
    private Integer ticketId;
    private Integer seatId;

    // Constructors
    public TicketSeat() {}

    public TicketSeat(Integer id, Integer ticketId, Integer seatId) {
        this.id = id;
        this.ticketId = ticketId;
        this.seatId = seatId;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getTicketId() { return ticketId; }
    public void setTicketId(Integer ticketId) { this.ticketId = ticketId; }
    public Integer getSeatId() { return seatId; }
    public void setSeatId(Integer seatId) { this.seatId = seatId; }
}
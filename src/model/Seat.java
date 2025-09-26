package model;

public class Seat {
    private Integer seatId;
    private Integer venueId;
    private Character seatRow; // CHAR(1)
    private Integer seatNumber;

    // Constructors
    public Seat() {}

    public Seat(Integer seatId, Integer venueId, Character seatRow, Integer seatNumber) {
        this.seatId = seatId;
        this.venueId = venueId;
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
    }

    // Getters and Setters
    public Integer getSeatId() { return seatId; }
    public void setSeatId(Integer seatId) { this.seatId = seatId; }
    public Integer getVenueId() { return venueId; }
    public void setVenueId(Integer venueId) { this.venueId = venueId; }
    public Character getSeatRow() { return seatRow; }
    public void setSeatRow(Character seatRow) { this.seatRow = seatRow; }
    public Integer getSeatNumber() { return seatNumber; }
    public void setSeatNumber(Integer seatNumber) { this.seatNumber = seatNumber; }
}
package data;

import java.util.*;

/**
 * DatabaseManager - Quản lý dữ liệu từ data.sql
 * Đây là simulation database, trong thực tế nên dùng JDBC với PostgreSQL
 */
public class DatabaseManager {
    
    // Singleton pattern
    private static DatabaseManager instance;
    
    // Data storage (simulation of database tables)
    private List<User> users;
    private List<Event> events;
    private List<Venue> venues;
    private List<Ticket> tickets;
    
    private DatabaseManager() {
        initializeData();
    }
    
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    
    private void initializeData() {
        initializeUsers();
        initializeEvents();
        initializeVenues();
        initializeTickets();
    }
    
    private void initializeUsers() {
        users = new ArrayList<>();
        users.add(new User(1, "Nguyễn Văn A", "nguyenvana@example.com", "0123456789", "customer", "2025-06-11 13:00:00"));
        users.add(new User(2, "Trần Thị B", "tranthib@example.com", "0987654321", "admin", "2025-06-11 13:00:00"));
        users.add(new User(3, "Lê Văn C", "levanc@example.com", "0912345678", "customer", "2025-06-11 13:00:00"));
        users.add(new User(4, "Phạm Thị D", "phamthid@example.com", "0932145678", "customer", "2025-06-11 13:00:00"));
        users.add(new User(5, "Hoàng Văn E", "hoangvane@example.com", "0945678901", "customer", "2025-06-11 13:00:00"));
        users.add(new User(6, "Vũ Thị F", "vuthif@example.com", "0956789012", "customer", "2025-06-11 13:00:00"));
        users.add(new User(7, "Đặng Văn G", "dangvang@example.com", "0967890123", "customer", "2025-06-11 13:00:00"));
        users.add(new User(8, "Bùi Thị H", "buithih@example.com", "0978901234", "admin", "2025-06-11 13:00:00"));
        users.add(new User(9, "Ngô Văn I", "ngovani@example.com", "0989012345", "customer", "2025-06-11 13:00:00"));
        users.add(new User(10, "Đỗ Thị J", "dothij@example.com", "0990123456", "customer", "2025-06-11 13:00:00"));
    }
    
    private void initializeEvents() {
        events = new ArrayList<>();
        events.add(new Event(1, "Hòa nhạc Rock", "Buổi hòa nhạc rock ngoài trời", "concert", "2025-07-01 19:00:00", "2025-07-01 22:00:00", "Công viên Trung tâm"));
        events.add(new Event(2, "Chiếu phim Hành động", "Buổi chiếu phim bom tấn", "movie", "2025-07-02 20:00:00", "2025-07-02 22:30:00", "Rạp Galaxy"));
        events.add(new Event(3, "Chuyến xe buýt Du lịch", "Chuyến đi tham quan thành phố", "bus", "2025-07-03 08:00:00", "2025-07-03 17:00:00", "Bến xe Miền Đông"));
        events.add(new Event(4, "Triển lãm Nghệ thuật", "Trưng bày các tác phẩm nghệ thuật hiện đại", "other", "2025-07-04 09:00:00", "2025-07-04 17:00:00", "Bảo tàng Mỹ thuật"));
        events.add(new Event(5, "Hòa nhạc Cổ điển", "Biểu diễn giao hưởng bởi dàn nhạc quốc gia", "concert", "2025-07-05 19:30:00", "2025-07-05 21:30:00", "Nhà hát Lớn"));
        events.add(new Event(6, "Phim Tình cảm", "Buổi chiếu phim lãng mạn", "movie", "2025-07-06 18:00:00", "2025-07-06 20:30:00", "Rạp CGV"));
        events.add(new Event(7, "Chuyến xe buýt Đà Lạt", "Tour du lịch Đà Lạt 2 ngày", "bus", "2025-07-07 06:00:00", "2025-07-08 18:00:00", "Bến xe Miền Tây"));
        events.add(new Event(8, "Hội thảo Công nghệ", "Sự kiện chia sẻ về AI và Blockchain", "other", "2025-07-08 09:00:00", "2025-07-08 16:00:00", "Trung tâm Hội nghị"));
        events.add(new Event(9, "Hòa nhạc Pop", "Biểu diễn của các ca sĩ nổi tiếng", "concert", "2025-07-09 20:00:00", "2025-07-09 23:00:00", "Sân vận động Mỹ Đình"));
        events.add(new Event(10, "Phim Hoạt hình", "Phim dành cho gia đình và trẻ em", "movie", "2025-07-10 14:00:00", "2025-07-10 16:00:00", "Rạp Lotte"));
        events.add(new Event(11, "Chuyến xe buýt Vũng Tàu", "Tour biển Vũng Tàu 1 ngày", "bus", "2025-07-11 07:00:00", "2025-07-11 20:00:00", "Bến xe Miền Đông"));
        events.add(new Event(12, "Lễ hội Ẩm thực", "Trải nghiệm các món ăn truyền thống", "other", "2025-07-12 10:00:00", "2025-07-12 22:00:00", "Quảng trường Ba Đình"));
    }
    
    private void initializeVenues() {
        venues = new ArrayList<>();
        venues.add(new Venue(1, "Nhà thi đấu Thành phố", "123 Đường Lê Lợi", "Hà Nội", 5000));
        venues.add(new Venue(2, "Rạp Galaxy", "456 Đường Nguyễn Huệ", "TP. Hồ Chí Minh", 300));
        venues.add(new Venue(3, "Nhà hát Lớn", "1 Tràng Tiền", "Hà Nội", 600));
        venues.add(new Venue(4, "Rạp CGV", "789 Đường Trần Hưng Đạo", "TP. Hồ Chí Minh", 400));
        venues.add(new Venue(5, "Sân vận động Mỹ Đình", "Đường Lê Đức Thọ", "Hà Nội", 40000));
        venues.add(new Venue(6, "Bến xe Miền Đông", "292 Đinh Bộ Lĩnh", "TP. Hồ Chí Minh", 1000));
        venues.add(new Venue(7, "Bảo tàng Mỹ thuật", "66 Nguyễn Thái Học", "Hà Nội", 500));
        venues.add(new Venue(8, "Trung tâm Hội nghị", "57 Nguyễn Thị Minh Khai", "Đà Nẵng", 150));
        venues.add(new Venue(9, "Rạp Lotte", "123 Đường 3 Tháng 2", "TP. Hồ Chí Minh", 350));
        venues.add(new Venue(10, "Quảng trường Ba Đình", "Đường Hùng Vương", "Hà Nội", 10000));
    }
    
    private void initializeTickets() {
        tickets = new ArrayList<>();
        tickets.add(new Ticket(1, 1, "A1", 500000.00, false));
        tickets.add(new Ticket(2, 1, "A2", 500000.00, true));
        tickets.add(new Ticket(3, 2, "B1", 150000.00, false));
        tickets.add(new Ticket(4, 2, "B2", 150000.00, true));
        tickets.add(new Ticket(5, 5, "C1", 700000.00, false));
        tickets.add(new Ticket(6, 6, "D1", 120000.00, true));
        tickets.add(new Ticket(7, 6, "D2", 120000.00, false));
        tickets.add(new Ticket(8, 9, "E1", 1000000.00, false));
        tickets.add(new Ticket(9, 9, "E2", 1000000.00, true));
        tickets.add(new Ticket(10, 10, "H1", 100000.00, false));
        tickets.add(new Ticket(11, 10, "H2", 100000.00, false));
        tickets.add(new Ticket(12, 3, "F1", 300000.00, false));
        tickets.add(new Ticket(13, 7, "F1", 400000.00, true));
    }
    
    // CRUD Operations for Users
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
    
    public void addUser(User user) {
        user.setId(getNextUserId());
        users.add(user);
    }
    
    public void updateUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                users.set(i, user);
                break;
            }
        }
    }
    
    public void deleteUser(int userId) {
        users.removeIf(user -> user.getId() == userId);
    }
    
    // CRUD Operations for Events
    public List<Event> getAllEvents() {
        return new ArrayList<>(events);
    }
    
    public void addEvent(Event event) {
        event.setId(getNextEventId());
        events.add(event);
    }
    
    public void updateEvent(Event event) {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getId() == event.getId()) {
                events.set(i, event);
                break;
            }
        }
    }
    
    public void deleteEvent(int eventId) {
        events.removeIf(event -> event.getId() == eventId);
        tickets.removeIf(ticket -> ticket.getEventId() == eventId);
    }
    
    // CRUD Operations for Venues
    public List<Venue> getAllVenues() {
        return new ArrayList<>(venues);
    }
    
    public void addVenue(Venue venue) {
        venue.setId(getNextVenueId());
        venues.add(venue);
    }
    
    public void updateVenue(Venue venue) {
        for (int i = 0; i < venues.size(); i++) {
            if (venues.get(i).getId() == venue.getId()) {
                venues.set(i, venue);
                break;
            }
        }
    }
    
    public void deleteVenue(int venueId) {
        venues.removeIf(venue -> venue.getId() == venueId);
    }
    
    // CRUD Operations for Tickets
    public List<Ticket> getTicketsByEventId(int eventId) {
        return tickets.stream()
                .filter(ticket -> ticket.getEventId() == eventId)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    public void addTicket(Ticket ticket) {
        ticket.setId(getNextTicketId());
        tickets.add(ticket);
    }
    
    public void updateTicket(Ticket ticket) {
        for (int i = 0; i < tickets.size(); i++) {
            if (tickets.get(i).getId() == ticket.getId()) {
                tickets.set(i, ticket);
                break;
            }
        }
    }
    
    public void deleteTicket(int ticketId) {
        tickets.removeIf(ticket -> ticket.getId() == ticketId);
    }
    
    // Helper methods to get next IDs
    private int getNextUserId() {
        return users.isEmpty() ? 1 : users.stream().mapToInt(User::getId).max().orElse(0) + 1;
    }
    
    private int getNextEventId() {
        return events.isEmpty() ? 1 : events.stream().mapToInt(Event::getId).max().orElse(0) + 1;
    }
    
    private int getNextVenueId() {
        return venues.isEmpty() ? 1 : venues.stream().mapToInt(Venue::getId).max().orElse(0) + 1;
    }
    
    private int getNextTicketId() {
        return tickets.isEmpty() ? 1 : tickets.stream().mapToInt(Ticket::getId).max().orElse(0) + 1;
    }
    
    // Data model classes
    public static class User {
        private int id;
        private String fullName;
        private String email;
        private String phone;
        private String role;
        private String createdAt;
        
        public User(int id, String fullName, String email, String phone, String role, String createdAt) {
            this.id = id;
            this.fullName = fullName;
            this.email = email;
            this.phone = phone;
            this.role = role;
            this.createdAt = createdAt;
        }
        
        // Getters and Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
        public String getCreatedAt() { return createdAt; }
        public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    }
    
    public static class Event {
        private int id;
        private String title;
        private String description;
        private String eventType;
        private String startTime;
        private String endTime;
        private String location;
        
        public Event(int id, String title, String description, String eventType, String startTime, String endTime, String location) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.eventType = eventType;
            this.startTime = startTime;
            this.endTime = endTime;
            this.location = location;
        }
        
        // Getters and Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getEventType() { return eventType; }
        public void setEventType(String eventType) { this.eventType = eventType; }
        public String getStartTime() { return startTime; }
        public void setStartTime(String startTime) { this.startTime = startTime; }
        public String getEndTime() { return endTime; }
        public void setEndTime(String endTime) { this.endTime = endTime; }
        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }
    }
    
    public static class Venue {
        private int id;
        private String name;
        private String address;
        private String city;
        private int capacity;
        
        public Venue(int id, String name, String address, String city, int capacity) {
            this.id = id;
            this.name = name;
            this.address = address;
            this.city = city;
            this.capacity = capacity;
        }
        
        // Getters and Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public int getCapacity() { return capacity; }
        public void setCapacity(int capacity) { this.capacity = capacity; }
    }
    
    public static class Ticket {
        private int id;
        private int eventId;
        private String seatNumber;
        private double price;
        private boolean isSold;
        
        public Ticket(int id, int eventId, String seatNumber, double price, boolean isSold) {
            this.id = id;
            this.eventId = eventId;
            this.seatNumber = seatNumber;
            this.price = price;
            this.isSold = isSold;
        }
        
        // Getters and Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public int getEventId() { return eventId; }
        public void setEventId(int eventId) { this.eventId = eventId; }
        public String getSeatNumber() { return seatNumber; }
        public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
        public boolean isSold() { return isSold; }
        public void setSold(boolean sold) { isSold = sold; }
    }
}
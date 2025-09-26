-- Users table
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    full_name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password_hash VARCHAR(255),
    phone VARCHAR(20),
    role VARCHAR(20) DEFAULT 'customer', -- admin / customer,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Events table
CREATE TABLE events (
    event_id SERIAL PRIMARY KEY,
    title VARCHAR(200),
    description TEXT,
    event_type VARCHAR(20) CHECK (event_type IN ('movie', 'concert', 'bus', 'other')),
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    location VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Venues table
CREATE TABLE venues (
    venue_id SERIAL PRIMARY KEY,
    name VARCHAR(150),
    address VARCHAR(255),
    city VARCHAR(100),
    capacity INTEGER
);

-- Event_venue table
CREATE TABLE event_venue (
    id SERIAL PRIMARY KEY,
    event_id INTEGER,
    venue_id INTEGER,
    FOREIGN KEY (event_id) REFERENCES events(event_id) ON DELETE CASCADE,
    FOREIGN KEY (venue_id) REFERENCES venues(venue_id) ON DELETE CASCADE
);

-- Tickets table
CREATE TABLE tickets (
    ticket_id SERIAL PRIMARY KEY,
    event_id INTEGER,
    seat_number VARCHAR(10),
    price DECIMAL(10, 2),
    is_sold BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (event_id) REFERENCES events(event_id) ON DELETE CASCADE
);

-- Bookings table
CREATE TABLE bookings (
    booking_id SERIAL PRIMARY KEY,
    user_id INTEGER,
    ticket_id INTEGER,
    booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_status VARCHAR(20) CHECK (payment_status IN ('pending', 'paid', 'cancelled')),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (ticket_id) REFERENCES tickets(ticket_id) ON DELETE CASCADE
);

-- Payments table
CREATE TABLE payments (
    payment_id SERIAL PRIMARY KEY,
    booking_id INTEGER,
    amount DECIMAL(10, 2),
    payment_method VARCHAR(20) CHECK (payment_method IN ('credit_card', 'momo', 'paypal', 'cash')),
    payment_time TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id) ON DELETE CASCADE
);

-- Seats table
CREATE TABLE seats (
    seat_id SERIAL PRIMARY KEY,
    venue_id INTEGER,
    seat_row CHAR(1),
    seat_number INTEGER,
    UNIQUE (venue_id, seat_row, seat_number),
    FOREIGN KEY (venue_id) REFERENCES venues(venue_id) ON DELETE CASCADE
);

-- Ticket_seat table
CREATE TABLE ticket_seat (
    id SERIAL PRIMARY KEY,
    ticket_id INTEGER,
    seat_id INTEGER,
    FOREIGN KEY (ticket_id) REFERENCES tickets(ticket_id) ON DELETE CASCADE,
    FOREIGN KEY (seat_id) REFERENCES seats(seat_id) ON DELETE CASCADE
);

-- Reviews table
CREATE TABLE reviews (
    review_id SERIAL PRIMARY KEY,
    user_id INTEGER,
    event_id INTEGER,
    rating SMALLINT CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (event_id) REFERENCES events(event_id) ON DELETE CASCADE
);

-- Chèn dữ liệu vào bảng users (10 bản ghi, mật khẩu: "matkhau123")
INSERT INTO users (full_name, email, password_hash, phone, role, created_at) VALUES
('Nguyễn Văn A', 'nguyenvana@example.com', '$2b$10$1a2b3c4d5e6f7g8h9i0j1k2l3m4n5o6p7q8r9s0t1u2v3w4x5y6z', '0123456789', 'customer', '2025-06-11 13:00:00'),
('Trần Thị B', 'tranthib@example.com', '$2b$10$2b3c4d5e6f7g8h9i0j1k2l3m4n5o6p7q8r9s0t1u2v3w4x5y6z7', '0987654321', 'admin', '2025-06-11 13:00:00'),
('Lê Văn C', 'levanc@example.com', '$2b$10$3c4d5e6f7g8h9i0j1k2l3m4n5o6p7q8r9s0t1u2v3w4x5y6z7a8', '0912345678', 'customer', '2025-06-11 13:00:00'),
('Phạm Thị D', 'phamthid@example.com', '$2b$10$4d5e6f7g8h9i0j1k2l3m4n5o6p7q8r9s0t1u2v3w4x5y6z7a8b9', '0932145678', 'customer', '2025-06-11 13:00:00'),
('Hoàng Văn E', 'hoangvane@example.com', '$2b$10$5e6f7g8h9i0j1k2l3m4n5o6p7q8r9s0t1u2v3w4x5y6z7a8b9c0', '0945678901', 'customer', '2025-06-11 13:00:00'),
('Vũ Thị F', 'vuthif@example.com', '$2b$10$6f7g8h9i0j1k2l3m4n5o6p7q8r9s0t1u2v3w4x5y6z7a8b9c0d1', '0956789012', 'customer', '2025-06-11 13:00:00'),
('Đặng Văn G', 'dangvang@example.com', '$2b$10$7g8h9i0j1k2l3m4n5o6p7q8r9s0t1u2v3w4x5y6z7a8b9c0d1e2', '0967890123', 'customer', '2025-06-11 13:00:00'),
('Bùi Thị H', 'buithih@example.com', '$2b$10$8h9i0j1k2l3m4n5o6p7q8r9s0t1u2v3w4x5y6z7a8b9c0d1e2f3', '0978901234', 'admin', '2025-06-11 13:00:00'),
('Ngô Văn I', 'ngovani@example.com', '$2b$10$9i0j1k2l3m4n5o6p7q8r9s0t1u2v3w4x5y6z7a8b9c0d1e2f3g4', '0989012345', 'customer', '2025-06-11 13:00:00'),
('Đỗ Thị J', 'dothij@example.com', '$2b$10$0j1k2l3m4n5o6p7q8r9s0t1u2v3w4x5y6z7a8b9c0d1e2f3g4h5', '0990123456', 'customer', '2025-06-11 13:00:00');

-- Chèn dữ liệu vào bảng events (12 bản ghi)
INSERT INTO events (title, description, event_type, start_time, end_time, location, created_at) VALUES
('Hòa nhạc Rock', 'Buổi hòa nhạc rock ngoài trời', 'concert', '2025-07-01 19:00:00', '2025-07-01 22:00:00', 'Công viên Trung tâm', '2025-06-11 13:00:00'),
('Chiếu phim Hành động', 'Buổi chiếu phim bom tấn', 'movie', '2025-07-02 20:00:00', '2025-07-02 22:30:00', 'Rạp Galaxy', '2025-06-11 13:00:00'),
('Chuyến xe buýt Du lịch', 'Chuyến đi tham quan thành phố', 'bus', '2025-07-03 08:00:00', '2025-07-03 17:00:00', 'Bến xe Miền Đông', '2025-06-11 13:00:00'),
('Triển lãm Nghệ thuật', 'Trưng bày các tác phẩm nghệ thuật hiện đại', 'other', '2025-07-04 09:00:00', '2025-07-04 17:00:00', 'Bảo tàng Mỹ thuật', '2025-06-11 13:00:00'),
('Hòa nhạc Cổ điển', 'Biểu diễn giao hưởng bởi dàn nhạc quốc gia', 'concert', '2025-07-05 19:30:00', '2025-07-05 21:30:00', 'Nhà hát Lớn', '2025-06-11 13:00:00'),
('Phim Tình cảm', 'Buổi chiếu phim lãng mạn', 'movie', '2025-07-06 18:00:00', '2025-07-06 20:30:00', 'Rạp CGV', '2025-06-11 13:00:00'),
('Chuyến xe buýt Đà Lạt', 'Tour du lịch Đà Lạt 2 ngày', 'bus', '2025-07-07 06:00:00', '2025-07-08 18:00:00', 'Bến xe Miền Tây', '2025-06-11 13:00:00'),
('Hội thảo Công nghệ', 'Sự kiện chia sẻ về AI và Blockchain', 'other', '2025-07-08 09:00:00', '2025-07-08 16:00:00', 'Trung tâm Hội nghị', '2025-06-11 13:00:00'),
('Hòa nhạc Pop', 'Biểu diễn của các ca sĩ nổi tiếng', 'concert', '2025-07-09 20:00:00', '2025-07-09 23:00:00', 'Sân vận động Mỹ Đình', '2025-06-11 13:00:00'),
('Phim Hoạt hình', 'Phim dành cho gia đình và trẻ em', 'movie', '2025-07-10 14:00:00', '2025-07-10 16:00:00', 'Rạp Lotte', '2025-06-11 13:00:00'),
('Chuyến xe buýt Vũng Tàu', 'Tour biển Vũng Tàu 1 ngày', 'bus', '2025-07-11 07:00:00', '2025-07-11 20:00:00', 'Bến xe Miền Đông', '2025-06-11 13:00:00'),
('Lễ hội Ẩm thực', 'Trải nghiệm các món ăn truyền thống', 'other', '2025-07-12 10:00:00', '2025-07-12 22:00:00', 'Quảng trường Ba Đình', '2025-06-11 13:00:00');

-- Chèn dữ liệu vào bảng venues (10 bản ghi)
INSERT INTO venues (name, address, city, capacity) VALUES
('Nhà thi đấu Thành phố', '123 Đường Lê Lợi', 'Hà Nội', 5000),
('Rạp Galaxy', '456 Đường Nguyễn Huệ', 'TP. Hồ Chí Minh', 300),
('Nhà hát Lớn', '1 Tràng Tiền', 'Hà Nội', 600),
('Rạp CGV', '789 Đường Trần Hưng Đạo', 'TP. Hồ Chí Minh', 400),
('Sân vận động Mỹ Đình', 'Đường Lê Đức Thọ', 'Hà Nội', 40000),
('Bến xe Miền Đông', '292 Đinh Bộ Lĩnh', 'TP. Hồ Chí Minh', 1000),
('Bảo tàng Mỹ thuật', '66 Nguyễn Thái Học', 'Hà Nội', 500),
('Trung tâm Hội nghị', '57 Nguyễn Thị Minh Khai', '2020', '150'),
('Rạp Lotte', '123 Đường 3 Tháng 2', 'TP. Hồ Chí Minh', 350),
('Quảng trường Ba Đình', 'Đường Hùng Vương', 'Hà Nội', 10000);

-- Chèn dữ liệu vào bảng event_venue (12 bản ghi)
INSERT INTO event_venue (event_id, venue_id) VALUES
(1, 1),
(2, 2),
(3, 6),
(4, 7),
(5, 3),
(6, 4),
(7, 6),
(8, 8),
(9, 5),
(10, 9),
(11, 6),
(12, 10);

-- Chèn dữ liệu vào bảng seats (15 bản ghi)
INSERT INTO seats (venue_id, seat_row, seat_number) VALUES
(1, 'A', 1), (1, 'A', 2), (1, 'B', 1),
(2, 'A', 1), (2, 'A', 2),
(3, 'C', 1), (3, 'C', 2),
(4, 'D', 1), (4, 'D', 2),
(5, 'E', 1), (5, 'E', 2),
(6, 'F', 1),
(7, 'G', 1),
(9, 'H', 1), (9, 'H', 2);

-- Chèn dữ liệu vào bảng tickets (15 bản ghi)
INSERT INTO tickets (event_id, seat_number, price, is_sold) VALUES
(1, 'A1', 500000.00, FALSE),
(1, 'A2', 500000.00, TRUE),
(1, 'B1', 600000.00, FALSE),
(2, 'A1', 150000.00, FALSE),
(2, 'A2', 150000.00, TRUE),
(5, 'C1', 700000.00, FALSE),
(5, 'C2', 700000.00, FALSE),
(6, 'D1', 120000.00, TRUE),
(6, 'D2', 120000.00, FALSE),
(9, 'E1', 1000000.00, FALSE),
(9, 'E2', 1000000.00, TRUE),
(10, 'H1', 100000.00, FALSE),
(10, 'H2', 100000.00, FALSE),
(3, 'F1', 300000.00, FALSE),
(7, 'F1', 400000.00, TRUE);

-- Chèn dữ liệu vào bảng ticket_seat (15 bản ghi)
INSERT INTO ticket_seat (ticket_id, seat_id) VALUES
(1, 1), (2, 2), (3, 3),
(4, 4), (5, 5),
(6, 6), (7, 7),
(8, 8), (9, 9),
(10, 10), (11, 11),
(12, 14), (13, 15),
(14, 12),
(15, 12);

-- Chèn dữ liệu vào bảng bookings (12 bản ghi)
INSERT INTO bookings (user_id, ticket_id, booking_time, payment_status) VALUES
(1, 1, '2025-06-11 13:30:00', 'pending'),
(2, 2, '2025-06-11 13:45:00', 'paid'),
(3, 3, '2025-06-11 14:00:00', 'pending'),
(4, 4, '2025-06-11 14:15:00', 'paid'),
(5, 6, '2025-06-11 14:30:00', 'pending'),
(6, 8, '2025-06-11 14:45:00', 'paid'),
(7, 10, '2025-06-11 15:00:00', 'pending'),
(8, 11, '2025-06-11 15:15:00', 'paid'),
(9, 12, '2025-06-11 15:30:00', 'pending'),
(10, 14, '2025-06-11 15:45:00', 'paid'),
(1, 5, '2025-06-11 16:00:00', 'pending'),
(3, 7, '2025-06-11 16:15:00', 'paid');

-- Chèn dữ liệu vào bảng payments (10 bản ghi)
INSERT INTO payments (booking_id, amount, payment_method, payment_time) VALUES
(2, 500000.00, 'credit_card', '2025-06-11 13:50:00'),
(4, 150000.00, 'momo', '2025-06-11 14:20:00'),
(6, 120000.00, 'paypal', '2025-06-11 14:50:00'),
(8, 1000000.00, 'credit_card', '2025-06-11 15:20:00'),
(10, 300000.00, 'momo', '2025-06-11 15:50:00'),
(12, 700000.00, 'cash', '2025-06-11 16:20:00'),
(1, 2, 'credit_card', '2025-06-11 16:00:00'),
(3, 600000.00, 'momo', '2025-06-11 16:30:00'),
(5, 700000.00, 'paypal', '2025-06-11 16:40:00'),
(7, 1000000.00, 'cash', '2025-06-11 16:50:00');

-- Chèn dữ liệu vào bảng reviews (10 bản ghi)
INSERT INTO reviews (user_id, event_id, rating, comment, created_at) VALUES
(1, 1, 5, 'Hòa nhạc rất tuyệt, âm thanh sống động!', '2025-06-11 15:00:00'),
(2, 2, 4, 'Phim hay nhưng ghế hơi chật', '2025-06-11 15:30:00'),
(3, 5, 5, 'Giao hưởng tuyệt vời!', '2025-06-11 16:00:00'),
(4, 6, 3, 'Phim ổn nhưng âm thanh cần cải thiện', '2025-06-11 16:30:00'),
(5, 9, 4, 'Ca sĩ biểu diễn rất nhiệt huyết!', '2025-06-11 17:00:00'),
(6, 10, 5, 'Phim hoạt hình rất đáng yêu', '2025-06-11 17:30:00'),
(7, 3, 4, 'Chuyến đi vui nhưng xe hơi cũ', '2025-06-11 18:00:00'),
(8, 7, 5, 'Tour Đà Lạt rất thú vị!', '2025-06-11 18:30:00'),
(9, 1, 3, 'Hòa nhạc đông nhưng tổ chức hơi lộn xộn', '2025-06-11 19:00:00'),
(10, 2, 4, 'Phim ổn, rạp sạch sẽ', '2025-06-11 19:30:00');
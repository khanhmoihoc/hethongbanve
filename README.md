# Hệ Thống Bán Vé (Ticket Booking System)

# 🎫 Hệ Thống Quản Lý Bán Vé (Ticket Management System)

[![Java](https://img.shields.io/badge/Java-8%2B-orange## 🔧 Phát triển

### Cấu trúc code:
- **UI Package**: Chứa các giao diện Swing
- **Data Package**: DatabaseManager - mô phỏng database
- **Model Package**: Các class đối tượng (User, Event, Venue...)
- **DAO Package**: Pattern truy xuất dữ liệu

### Mở rộng:
- Có thể tích hợp database thật thay vì mô phỏng
- Thêm chức năng báo cáo và thống kê
- Tích hợp thanh toán online

## 📷 Screenshots

![Main Interface](https://via.placeholder.com/800x500/2c3e50/ffffff?text=Main+Interface)

## 🤝 Contributing

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

Distributed under the MIT License. See `LICENSE` for more information.

## 👥 Authors

- **Your Name** - *Initial work* - [YourGitHub](https://github.com/yourusername)

## 🙏 Acknowledgments

- Java Swing Documentation
- Nimbus Look and Feel
- All contributors and testers

---
*Phiên bản: 2.0 | Ngày cập nhật: 2025 | Ngôn ngữ: Java Swing*www.oracle.com/java/)
[![Swing](https://img.shields.io/badge/GUI-Java%20Swing-blue.svg)](https://docs.oracle.com/javase/tutorial/uiswing/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

Ứng dụng quản lý bán vé sự kiện được xây dựng bằng Java Swing với giao diện desktop hiện đại và thân thiện.

## 🚀 Tính năng chính

- **Quản lý Người dùng**: Thêm, sửa, xóa và xem thông tin khách hàng và quản trị viên
- **Quản lý Sự kiện**: Tạo và quản lý các sự kiện với thông tin vé và chỗ ngồi
- **Quản lý Địa điểm**: Quản lý các venue với thông tin sức chứa và địa chỉ
- **Giao diện đa tab**: Dễ dàng chuyển đổi giữa các chức năng quản lý
- **Dữ liệu mô phỏng**: Sử dụng dữ liệu có sẵn từ database PostgreSQL

## 📋 Yêu cầu hệ thống

### Phần mềm cần thiết:
- **Java JDK 8 trở lên** (hoặc JRE để chỉ chạy ứng dụng)
- **Windows OS** (đã test trên Windows 10/11)

### Kiểm tra Java:
Mở Command Prompt và gõ:
```bash
java -version
```
Nếu thấy thông tin phiên bản Java thì đã cài đặt thành công.

## 🛠️ Hướng dẫn cài đặt

### Cách 1: Tải và cài đặt Java (nếu chưa có)

1. **Tải Java JDK**:
   - Truy cập: https://www.oracle.com/java/technologies/downloads/
   - Hoặc dùng OpenJDK: https://adoptium.net/

2. **Cài đặt Java**:
   - Chạy file installer đã tải
   - Làm theo hướng dẫn cài đặt
   - **Quan trọng**: Tick vào "Add to PATH" trong quá trình cài đặt

3. **Kiểm tra cài đặt**:
   - Mở Command Prompt mới
   - Gõ: `java -version`
   - Nếu hiện thông tin Java → thành công

### Cách 2: Tải dự án

1. **Tải source code**:
   - Download file ZIP của dự án
   - Giải nén vào thư mục bất kỳ

2. **Cấu trúc thư mục**:
```
HeThongBanVe/
├── src/
│   ├── ui/           # Giao diện người dùng
│   ├── data/         # Lớp quản lý dữ liệu
│   ├── model/        # Các model class
│   ├── connection/   # Kết nối database
│   └── dao/          # Data Access Objects
├── run.bat           # File chạy ứng dụng
├── data.sql          # Dữ liệu mẫu (tham khảo)
├── docker-compose.yml # Docker config (optional)
└── README.md         # File này
```

## ▶️ Cách chạy ứng dụng

### Phương pháp đơn giản nhất:
1. Mở thư mục chứa dự án
2. **Double-click vào file `run.bat`**
3. Chờ ứng dụng khởi động

### Chạy thủ công (nếu run.bat không hoạt động):
1. Mở Command Prompt
2. Navigate đến thư mục dự án:
   ```bash
   cd "đường_dẫn_đến_thư_mục_HeThongBanVe"
   ```
3. Biên dịch:
   ```bash
   javac -cp src src\ui\*.java src\data\*.java src\model\*.java
   ```
4. Chạy:
   ```bash
   cd src
   java -cp . ui.Main
   ```

## 🎯 Cách sử dụng

### Khi ứng dụng khởi động:
- Sẽ hiện cửa sổ chính với 3 tab:
  - **👥 Quản lý User**: Quản lý tài khoản người dùng
  - **🎪 Quản lý Sự kiện**: Tạo và quản lý events
  - **🏟️ Quản lý Địa điểm**: Quản lý venues

### Chức năng chính từng tab:

#### Tab Quản lý User:
- **Thêm**: Nhấn "Thêm User" → nhập thông tin → "Lưu"
- **Sửa**: Chọn user trong bảng → "Sửa User"  
- **Xóa**: Chọn user → "Xóa User"
- **Tìm kiếm**: Gõ từ khóa trong ô tìm kiếm

#### Tab Quản lý Sự kiện:
- **Thêm sự kiện**: "Thêm Sự kiện" → điền form → "Lưu"
- **Xem vé**: Chọn sự kiện → "Xem Vé" (hiển thị danh sách vé)
- **Xem chỗ ngồi**: Trong dialog vé → "Xem chỗ ngồi"

#### Tab Quản lý Địa điểm:
- **Thêm venue**: "Thêm Venue" → nhập thông tin
- **Quản lý ghế**: Chọn venue → "Quản lý Ghế"

## 🧪 Dữ liệu mẫu

Ứng dụng đi kèm với dữ liệu mẫu:
- **10 users**: Bao gồm admin và customers
- **12 events**: Các sự kiện âm nhạc, thể thao, văn hóa
- **10 venues**: Các địa điểm tổ chức khác nhau
- **13 tickets**: Vé với giá và loại chỗ ngồi đa dạng

## ❗ Xử lý lỗi thường gặp

### Lỗi: "java không được nhận dạng"
**Nguyên nhân**: Java chưa cài hoặc chưa add vào PATH
**Giải pháp**: 
1. Cài đặt lại Java với tùy chọn "Add to PATH"
2. Hoặc thêm thủ công vào System PATH

### Lỗi: "Could not find or load main class"
**Nguyên nhân**: Classpath không đúng
**Giải pháp**: 
1. Đảm bảo chạy từ đúng thư mục
2. Dùng file `run.bat` được cung cấp

### Lỗi: Giao diện hiển thị không đẹp
**Nguyên nhân**: Look and Feel không tương thích
**Giải pháp**: Ứng dụng tự động fallback về Metal Look and Feel

### Lỗi: "Access Denied" khi chạy run.bat
**Nguyên nhân**: Không có quyền thực thi
**Giải pháp**: 
1. Click chuột phải → "Run as Administrator"
2. Hoặc chạy thủ công bằng Command Prompt

## 📞 Hỗ trợ

Nếu gặp vấn đề:
1. Kiểm tra lại yêu cầu hệ thống
2. Đảm bảo Java đã cài đặt đúng cách
3. Thử chạy thủ công nếu run.bat không hoạt động
4. Kiểm tra quyền truy cập thư mục

## 🔧 Phát triển

### Cấu trúc code:
- **UI Package**: Chứa các giao diện Swing
- **Data Package**: DatabaseManager - mô phỏng database
- **Model Package**: Các class đối tượng (User, Event, Venue...)
- **DAO Package**: Pattern truy xuất dữ liệu

### Mở rộng:
- Có thể tích hợp database thật thay vì mô phỏng
- Thêm chức năng báo cáo và thống kê
- Tích hợp thanh toán online

---
*Phiên bản: 1.0 | Ngày cập nhật: 2025 | Ngôn ngữ: Java Swing*
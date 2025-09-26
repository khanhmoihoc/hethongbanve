package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public abstract class BaseDAO {

    private static final String URL = "jdbc:postgresql://localhost:5432/your_database_name";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";


//    protected Connection getConnection() throws SQLException {
//        try {
//            Class.forName("org.postgresql.Driver");
//            return DriverManager.getConnection(URL, USER, PASSWORD);
//        } catch (ClassNotFoundException e) {
//            System.err.println("PostgreSQL JDBC Driver không tìm thấy.");
//            throw new SQLException("Lỗi Driver: " + e.getMessage());
//        }
//    }

    protected Connection getConnection() throws SQLException {
        // Tải driver (thường không cần thiết với JDBC 4.0+)
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


//    protected static void closeConnection(Connection connection) {
//        if (connection != null) {
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
//            }
//        }
//    }

    public static void close(AutoCloseable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (Exception e) {
                // Log lỗi đóng tài nguyên
                System.err.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
            }
        }
    }
}
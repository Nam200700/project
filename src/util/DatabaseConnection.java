package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    private final String server = "localhost";
    private final String port = "3306";
    private final String database = "qltv";
    private final String userName = "root";
    private final String password = "18102007";

    private DatabaseConnection() {
        // private constructor để Singleton hoạt động đúng
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    public void connectToDatabase() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://" + server + ":" + port + "/" + database + "?useSSL=false&serverTimezone=UTC";
                connection = DriverManager.getConnection(url, userName, password);
                System.out.println("✅ Kết nối cơ sở dữ liệu thành công.");
            } catch (ClassNotFoundException e) {
                System.err.println("❌ Không tìm thấy Driver MySQL.");
                e.printStackTrace();
                throw new SQLException("Driver không tìm thấy", e);
            } catch (SQLException e) {
                System.err.println("❌ Kết nối thất bại.");
                e.printStackTrace();
                throw e;
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("🔌 Đã ngắt kết nối cơ sở dữ liệu.");
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Lỗi khi đóng kết nối.");
            e.printStackTrace();
        }
    }
}

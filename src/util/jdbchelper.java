/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class jdbchelper {
      // Thông tin kết nối đến MySQL

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/qltv";
    private static final String USER = "root";
    private static final String PASSWORD = "tranhainam123";

    // Đăng ký driver MySQL khi class được nạp
    static {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to register MySQL driver", e);
        }
    }

    public static Connection getconnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    public static int executeUpdate(String sql, Object... args) {
        try (Connection conn = getconnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Gán các tham số vào PreparedStatement
            setParams(pstmt, args);

            // Thực thi câu lệnh UPDATE và trả về số dòng bị ảnh hưởng
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            // In ra lỗi để dễ dàng theo dõi
            System.err.println("Lỗi thực thi SQL: " + e.getMessage());

            // Trả về 0 nếu có lỗi (hoặc có thể trả về một giá trị khác tùy vào yêu cầu)
            return 0;
        }
    }

    public static ResultSet executeQuery(String sql, Object... args) {
        try {
            Connection conn = getconnection(); // Lấy kết nối
            PreparedStatement pstmt = conn.prepareStatement(sql);
            setParams(pstmt, args); // Gán tham số vào SQL
            return pstmt.executeQuery(); // Trả về ResultSet (phải đóng khi sử dụng xong)
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Trả về null nếu có lỗi
        }
    }

    private static void setParams(PreparedStatement pstmt, Object... args) throws SQLException {
        for (int i = 0; i < args.length; i++) {
            pstmt.setObject(i + 1, args[i]); // Gán giá trị vào dấu "?"
        }
    }

    public static int getCountFromTable(String tableName, String columnName) {
        // Kiểm tra và đảm bảo tên bảng và cột hợp lệ (nên có cơ chế kiểm tra hợp lệ)
        if (tableName == null || columnName == null || tableName.isEmpty() || columnName.isEmpty()) {
            throw new IllegalArgumentException("Tên bảng và cột không được để trống.");
        }

        String sql = "SELECT COUNT(*) AS " + columnName + " FROM " + tableName;
        try (Connection connection = getconnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                return resultSet.getInt(columnName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Trả về 0 nếu có lỗi hoặc không có kết quả
    }

    // Phương thức thực thi truy vấn SELECT với ResultSetMapper
    public static <T> List<T> executeQuery(String query, ResultSetMapper<T> mapper, String... params) {
        List<T> results = new ArrayList<>();
        try (Connection conn = getconnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setString(i + 1, "%" + params[i] + "%");
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add(mapper.map(rs)); // Chuyển ResultSet thành đối tượng theo mapper
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    // Định nghĩa interface ResultSetMapper bên trong JdbcHelper
    public interface ResultSetMapper<T> {

        T map(ResultSet rs) throws SQLException;
    }

    public static void closeResources(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close(); // Đóng ResultSet nếu không null
            }
            if (pstmt != null) {
                pstmt.close(); // Đóng PreparedStatement nếu không null
            }
            if (conn != null) {
                conn.close(); // Đóng Connection nếu không null
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
        }
    }
}

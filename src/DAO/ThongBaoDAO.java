package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.jdbchelper;

public class ThongBaoDAO {

    // Lấy danh sách thông báo từ cơ sở dữ liệu
    public List<String> getNotifications(int maTaiKhoan) {
        List<String> notifications = new ArrayList<>();
        String query = "SELECT TieuDe, NoiDung FROM ThongBao WHERE MaTaiKhoan = ? ORDER BY NgayTao DESC";

        try (Connection conn = jdbchelper.getconnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, maTaiKhoan);  // ID tài khoản
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String title = rs.getString("TieuDe");
                String content = rs.getString("NoiDung");
                notifications.add(title + ": " + content);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notifications;
    }
    
   public void deleteAllNotifications(int maTaiKhoan) {
    String query = "DELETE FROM ThongBao WHERE maTaiKhoan = ?"; // Câu lệnh SQL để xóa thông báo theo tài khoản
    try (Connection conn = jdbchelper.getconnection(); 
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, maTaiKhoan); // Thiết lập giá trị cho tham số
        stmt.executeUpdate(); // Thực thi câu lệnh SQL
    } catch (SQLException e) {
        e.printStackTrace(); // In lỗi nếu có
    }
}

}

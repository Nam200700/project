package DAO;

import Entity.ThongBao;
import Entity.user;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.jdbchelper;

public class ThongBaoDAO {

    public List<String> getNotifications(int maTaiKhoan) {
        List<String> notifications = new ArrayList<>();
        String query = "SELECT TieuDe, NoiDung, NgayTao FROM ThongBao WHERE MaTaiKhoan = ? ORDER BY NgayTao DESC";

        try (Connection conn = jdbchelper.getconnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, maTaiKhoan);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String title = rs.getString("TieuDe");
                    String content = rs.getString("NoiDung");
                    Timestamp date = rs.getTimestamp("NgayTao");

                    String notification = String.format("[%s] %s: %s",
                            date.toLocalDateTime().toLocalTime().withNano(0), title, content);
                    notifications.add(notification);
                }
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy thông báo: " + e.getMessage());
        }

        return notifications;
    }

    public void deleteAllNotifications(int maTaiKhoan) {
        String query = "DELETE FROM ThongBao WHERE maTaiKhoan = ?"; // Câu lệnh SQL để xóa thông báo theo tài khoản
        try (Connection conn = jdbchelper.getconnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, maTaiKhoan); // Thiết lập giá trị cho tham số
            stmt.executeUpdate(); // Thực thi câu lệnh SQL
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi nếu có
        }
    }

    public boolean insertForAllThuThu(ThongBao tb) {
        String sql = "INSERT INTO ThongBao (TieuDe, NoiDung, MaTaiKhoan) "
                + "SELECT ?, ?, MaTaiKhoan FROM TAIKHOAN WHERE MaQuyen = 1";
        try (Connection conn = jdbchelper.getconnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tb.getTieuDe());
            stmt.setString(2, tb.getNoiDung());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi chèn thông báo: " + e.getMessage());
            return false;
        }
    }

    // Lấy tất cả thông báo
    public List<ThongBao> getAll() {
        List<ThongBao> list = new ArrayList<>();
        String sql = "SELECT * FROM ThongBao ORDER BY NgayTao DESC";

        try (ResultSet rs = jdbchelper.executeQuery(sql)) {
            while (rs.next()) {
                ThongBao tb = new ThongBao();
                tb.setMaTB(rs.getInt("MaTB"));
                tb.setTieuDe(rs.getString("TieuDe"));
                tb.setNoiDung(rs.getString("NoiDung"));
                tb.setNgayTao(rs.getTimestamp("NgayTao"));
                list.add(tb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Xóa thông báo theo mã
    public boolean delete(int maTB) {
        String sql = "DELETE FROM ThongBao WHERE MaTB = ?";
        return jdbchelper.executeUpdate(sql, maTB) > 0;
    }

}

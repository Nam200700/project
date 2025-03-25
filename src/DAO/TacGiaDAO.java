package DAO;  // Đảm bảo dòng này có tồn tại và viết đúng

import Entity.TacGia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import util.jdbchelper;

public class TacGiaDAO {

    public static void insert(TacGia tg) {
        String sql = "INSERT INTO tacgia (TenTacGia) VALUES (?)";
        int result = jdbchelper.executeUpdate(sql, tg.getTenTacGia());

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Thêm tác giả thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm tác giả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void update(TacGia tg) {
        String sql = "UPDATE tacgia SET TenTacGia = ? WHERE MaTacGia = ?";
        int result = jdbchelper.executeUpdate(sql, tg.getTenTacGia(), tg.getMaTacGia());

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy tác giả!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static boolean delete(String matacgia) {
        String sql = "DELETE FROM tacgia WHERE MaTacGia = ?";
        int result = jdbchelper.executeUpdate(sql, matacgia);

        if (result > 0) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Không thể xóa tác giả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static List<TacGia> getAll() {
        List<TacGia> tacgia = new ArrayList<>();
        String sql = "SELECT MaTacGia, TenTacGia FROM tacgia";

        try (ResultSet rs = jdbchelper.executeQuery(sql)) {
            while (rs.next()) {
                TacGia tg = new TacGia();
                tg.maTacGia = rs.getString("MaTacGia");
                tg.tenTacGia = rs.getString("TenTacGia");
                tacgia.add(tg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tacgia;
    }

}

package DAO;

import java.sql.Date;
import java.util.*;
import javax.swing.JOptionPane;
import util.jdbchelper;
import Entity.DocGia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DocGiaDAO {

    // Kiểm tra tài khoản đã có thẻ độc giả chưa
    public boolean kiemTraTheDocGia(int maTaiKhoan) {
        String sql = "SELECT COUNT(*) FROM DocGia WHERE MaTaiKhoan = ?";
        try (Connection conn = jdbchelper.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maTaiKhoan);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true; // Đã có thẻ
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Chưa có thẻ
    }

// Đăng ký độc giả (gọi khi đăng ký tài khoản xong)
    public String dangKyDocGia(int maTaiKhoan, DocGia the) throws Exception {
        if (kiemTraTheDocGia(maTaiKhoan)) {
            return "Tài khoản này đã có thẻ độc giả!";
        }
        if (themTheDocGia(the)) {
            return "Đăng ký thẻ độc giả thành công!";
        }
        return "Đăng ký thất bại, vui lòng thử lại!";
    }

    // Thêm thẻ độc giả mới
    public static boolean themTheDocGia(DocGia dg) {
        String sql = "INSERT INTO DocGia ( HoTen, GioiTinh, DiaChi, SoDienThoai) VALUES ( ?, ?, ?, ?)";
        try (Connection conn = jdbchelper.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dg.getHoTen());
            ps.setString(2, dg.getGioiTinh());
            ps.setString(3, dg.getDiaChi());
            ps.setString(4, dg.getSoDienThoai());

            return ps.executeUpdate() > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi SQL: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }


   

    public static void update(DocGia dg) {
        String sql = "UPDATE docgia SET HoTen = ?, GioiTinh = ?, SoDienThoai = ?, DiaChi =? WHERE MaDocGia = ?";
        int result = jdbchelper.executeUpdate(sql, dg.getHoTen(), dg.getGioiTinh(), dg.getSoDienThoai(), dg.getDiaChi());

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Cập nhật độc giả thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy độc giả!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static boolean delete(String maDocGia) {
        String sql = "DELETE FROM docgia WHERE MaDocGia = ?";
        int result = jdbchelper.executeUpdate(sql, maDocGia);

        if (result > 0) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Không thể xóa độc giả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static List<DocGia> getAll() {
        List<DocGia> docGiaList = new ArrayList<>();
        String sql = "SELECT * FROM docgia";

        try (ResultSet rs = jdbchelper.executeQuery(sql)) {
            while (rs.next()) {
                DocGia dg = new DocGia();
                dg.setMaDocGia(rs.getString("MaDocGia"));
                dg.setHoTen(rs.getString("HoTen"));
                dg.setGioiTinh(rs.getString("GioiTinh"));
                dg.setDiaChi(rs.getString("DiaChi")); // ✅ BỔ SUNG LẤY ĐỊA CHỈ
                dg.setSoDienThoai(rs.getString("SoDienThoai"));
                docGiaList.add(dg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return docGiaList;
    }

}

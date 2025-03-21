package DAO;

import Entity.DocGia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import util.jdbchelper;

public class DocGiaDAO1 {
    public boolean capNhatDocGia(DocGia dg) {
        String sql = "UPDATE DocGia SET HoTen = ?, GioiTinh = ?, SoDienThoai = ?, NgayDangKy = ?, MaTaiKhoan = ? WHERE MaDocGia = ?";
        try (Connection conn = jdbchelper.getconnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, dg.getHoTen());
            stmt.setString(2, dg.getGioiTinh());
            stmt.setString(3, dg.getSoDienThoai());
            stmt.setDate(4, new java.sql.Date(dg.getNgayDangKy().getTime())); // Chuyển đổi Date
            stmt.setInt(5, dg.getMaTaiKhoan());
            stmt.setInt(6, dg.getMaDocGia());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean xoaDocGia(int maDocGia) {
    String sql = "DELETE FROM DocGia WHERE MaDocGia = ?";
    try (Connection conn = jdbchelper.getconnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, maDocGia);
        return stmt.executeUpdate() > 0; // Trả về true nếu có dòng bị xóa
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

}

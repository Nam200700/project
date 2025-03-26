/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
import util.jdbchelper;
import Entity.DocGia;
/**
 *
 * @author ACER
 */
public class DocGiaDAO {

    public static void insert(DocGia dg) {
        String sql = "INSERT INTO docgia (HoTen, GioiTinh, SoDienThoai, DiaChi, NgayDangKy, MaTaiKhoan) VALUES (?, ?, ?, ?, ?, ?)";
        int result = jdbchelper.executeUpdate(sql, dg.getHoTen(), dg.getGioiTinh(), dg.getSoDienThoai(), dg.getDiachi(), dg.getNgayDangKy(), dg.getMaTaiKhoan());

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Thêm độc giả thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm độc giả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void update(DocGia dg) {
        String sql = "UPDATE docgia SET HoTen = ?, GioiTinh = ?, SoDienThoai = ?, DiaChi = ?, NgayDangKy = ?, MaTaiKhoan = ? WHERE MaDocGia = ?";
        int result = jdbchelper.executeUpdate(sql, dg.getHoTen(), dg.getGioiTinh(), dg.getSoDienThoai(), dg.getDiachi(), dg.getNgayDangKy(), dg.getMaTaiKhoan(), dg.getMaDocGia());

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Cập nhật độc giả thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy độc giả!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static boolean delete(int maDocGia) {
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
                dg.setMaDocGia(rs.getInt("MaDocGia"));
                dg.setHoTen(rs.getString("HoTen"));
                dg.setGioiTinh(rs.getString("GioiTinh"));
                dg.setSoDienThoai(rs.getString("SoDienThoai"));
                dg.setDiachi(rs.getString("DiaChi"));
                dg.setNgayDangKy(rs.getDate("NgayDangKy"));
                dg.setMaTaiKhoan(rs.getInt("MaTaiKhoan"));
                docGiaList.add(dg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return docGiaList;
    }
}

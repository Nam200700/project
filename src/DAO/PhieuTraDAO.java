/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.PhieuTra;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import util.jdbchelper;

/**
 *
 * @author ACER
 */
public class PhieuTraDAO {

    public static boolean insert(PhieuTra pt) {
        String sql = "INSERT INTO phieutra (MaPhieuMuon,NgayTra) VALUES (?,?)";
        int result = jdbchelper.executeUpdate(sql, pt.getMaPhieuMuon(), pt.getNgayTra());

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Thêm phiếu trả thành công!");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm phiếu trả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static void update(PhieuTra pt) {
        String sql = "UPDATE phieutra SET MaPhieuMuon = ?,NgayTra =? WHERE MaPhieuTra = ?";
        int result = jdbchelper.executeUpdate(sql, pt.getMaPhieuMuon(), pt.getNgayTra(), pt.getMaPhieuTra());

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy phiếu trả!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static boolean delete(String maPhieuTra) {
        String sql = "DELETE FROM phieutra WHERE MaPhieuTra = ?";
        int result = jdbchelper.executeUpdate(sql, maPhieuTra);

        if (result > 0) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Không thể xóa phiếu trả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static List<PhieuTra> getAll() {
        List<PhieuTra> dsPhieuTra = new ArrayList<>();
        String sql = "SELECT MaPhieuTra, MaPhieuMuon, NgayTra FROM phieutra";

        try (ResultSet rs = jdbchelper.executeQuery(sql)) {
            while (rs.next()) {
                PhieuTra ptra = new PhieuTra();
                ptra.setMaPhieuTra(rs.getString("MaPhieuTra"));
                ptra.setMaPhieuMuon(rs.getString("MaPhieuMuon"));
                ptra.setNgayTra(rs.getDate("NgayTra"));
                dsPhieuTra.add(ptra);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dsPhieuTra;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.ChiTietPhieuTra;
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
public class ChiTietPhieuTraDAO {

    // Thêm mới phiếu mượn
    public static boolean insert(ChiTietPhieuTra ctpt) {
        String sql = "INSERT INTO chitiettra (MaPhieuTra,MaSach,SoLuong,TinhTrangSach,TenSach) VALUES (?,?,?,?,?)";
        int result = jdbchelper.executeUpdate(sql, ctpt.getMaPhieuTra(), ctpt.getMaSach(), ctpt.getSoluong(), ctpt.getTinhTrangSach(),ctpt.getTenSach());

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Thêm chi tiết phiếu trả thành công!");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm chi tiết phiếu trả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Xóa phiếu mượn
    public static boolean delete(String maPhieuTra, String tenSach) {
        String sql = "DELETE FROM chitiettra WHERE MaPhieuTra = ? AND TenSach = ?";
        int result = jdbchelper.executeUpdate(sql, maPhieuTra, tenSach);

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Xóa chi tiết phiếu trả thành công!");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Không thể xóa chi tiết phiếu trả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Lấy danh sách tất cả phiếu mượn
    public static List<ChiTietPhieuTra> getAll() {
        List<ChiTietPhieuTra> dschitietPhieuTra = new ArrayList<>();
        String sql = "SELECT MaPhieuTra, TenSach, SoLuong, TinhTrangSach FROM chitiettra";

        try (ResultSet rs = jdbchelper.executeQuery(sql)) {
            while (rs.next()) {
                ChiTietPhieuTra ctpt = new ChiTietPhieuTra();
                ctpt.setMaPhieuTra(rs.getString("MaPhieuTra"));
                ctpt.setTenSach(rs.getString("TenSach"));
                ctpt.setSoluong(rs.getInt("SoLuong"));
                ctpt.setTinhTrangSach(rs.getString("TinhTrangSach"));
                dschitietPhieuTra.add(ctpt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dschitietPhieuTra;
    }

//    public static int tinhTongSoLuong(String maPhieuMuon) {
//        int tongSoLuong = 0;
//        String sql = "SELECT SUM(SoLuong) FROM chitietmuon WHERE MaPhieuMuon = ?";
//
//        try (ResultSet rs = jdbchelper.executeQuery(sql, maPhieuMuon)) {
//            if (rs.next()) {
//                tongSoLuong = rs.getInt(1);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return tongSoLuong;
//    }
//    public static int checkSoLuongSach(String maSach) {
//        int soLuongConLai = 0;
//        String sql = "SELECT SoLuong FROM Sach WHERE MaSach = ?";
//
//        try (ResultSet rs = jdbchelper.executeQuery(sql, maSach)) {
//            if (rs.next()) {
//                soLuongConLai = rs.getInt("SoLuong");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return soLuongConLai;
//    }
    public static boolean tangsoluong(String tenSach, int soLuongThem) {
        String sql = "UPDATE Sach SET SoLuong = SoLuong + ? WHERE TenSach = ?";
        int result = jdbchelper.executeUpdate(sql, soLuongThem, tenSach);

        return result > 0;
    }

    public static boolean giamsoluong(String tenSach, int soLuongTra) {
        String sql = "UPDATE Sach SET SoLuong = SoLuong - ? WHERE TenSach = ?";
        int result = jdbchelper.executeUpdate(sql, soLuongTra, tenSach);

        return result > 0;
    }
    // có chi tiết phiếu không để chạy điều kiện xóa của phiếu trả
    public static boolean hasDetails(String maPhieuTra) {
        String sql = "SELECT COUNT(*) FROM chitiettra WHERE MaPhieuTra = ?";

        try (ResultSet rs = jdbchelper.executeQuery(sql, maPhieuTra)) {
            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu có ít nhất 1 bản ghi, trả về true
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}

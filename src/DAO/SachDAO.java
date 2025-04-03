/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.Sach;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import util.jdbchelper;

public class SachDAO {

    public static boolean insert(Sach sach) {
        String sql = "INSERT INTO sach (TenSach,MaTheLoai,MaTacGia,MaNhaXuatBan,MaDauSach,NamXuatBan,LanTaiBan,NgonNgu,SoLuong) VALUES (?,?,?,?,?,?,?,?,?)";
        int result = jdbchelper.executeUpdate(sql, sach.getTenSach(), sach.getMaTheLoai(),
                sach.getMaTacGia(), sach.getMaNhaXuatBan(), sach.getMaDauSach(), sach.getNamXuatBan(),
                sach.getLanTaiBan(), sach.getNgonNgu(), sach.getSoLuong());

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Thêm sách thành công!");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static void update(Sach sach) {
        String sql = "UPDATE sach SET TenSach = ?,MaTheLoai = ?,MaTacGia = ?,MaNhaXuatBan = ?,MaDauSach = ?,NamXuatBan = ?,LanTaiBan = ?,NgonNgu = ?,SoLuong = ? WHERE MaSach = ?";
        int result = jdbchelper.executeUpdate(sql, sach.getTenSach(), sach.getMaTheLoai(),
                sach.getMaTacGia(), sach.getMaNhaXuatBan(), sach.getMaDauSach(), sach.getNamXuatBan(),
                sach.getLanTaiBan(), sach.getNgonNgu(), sach.getSoLuong(), sach.getMaSach());

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Cập nhật sách thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy sách!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static boolean delete(int maSach) {
        String sql = "DELETE FROM sach WHERE MaSach = ?";
        int result = jdbchelper.executeUpdate(sql, maSach);

        if (result > 0) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Không thể xóa nhà xuất bản!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static List<Sach> getAll() {
        List<Sach> dsSach = new ArrayList<>();
        String sql = "SELECT MaSach,TenSach,MaTheLoai,MaTacGia,MaNhaXuatBan"
                + ",MaDauSach,NamXuatBan,LanTaiBan,NgonNgu,SoLuong FROM sach";

        try (ResultSet rs = jdbchelper.executeQuery(sql)) {
            while (rs.next()) {
                Sach sc = new Sach();
                sc.setMaSach(rs.getInt("MaSach"));
                sc.setTenSach(rs.getString("TenSach"));
                sc.setMaTheLoai(rs.getInt("MaTheLoai"));
                sc.setMaTacGia(rs.getInt("MaTacGia"));
                sc.setMaNhaXuatBan(rs.getInt("MaNhaXuatBan"));
                sc.setMaDauSach(rs.getString("MaDauSach"));
                sc.setNamXuatBan(rs.getInt("NamXuatBan"));
                sc.setLanTaiBan(rs.getInt("LanTaiBan"));
                sc.setNgonNgu(rs.getString("NgonNgu"));
                sc.setSoLuong(rs.getInt("SoLuong"));
                dsSach.add(sc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dsSach;
    }

    public List<Sach> searchBooks(String keyword) {
        String query = "SELECT MaSach, TenSach, MaTheLoai, MaTacGia, MaNhaXuatBan, MaDauSach, NamXuatBan, LanTaiBan, NgonNgu, SoLuong "
                + "FROM sach "
                + "WHERE MaSach LIKE ? OR TenSach LIKE ? OR NamXuatBan LIKE ?";

        return jdbchelper.executeQuery(query, rs -> new Sach(
                rs.getInt("MaSach"),
                rs.getString("TenSach"),
                rs.getInt("MaTheLoai"),
                rs.getInt("MaTacGia"),
                rs.getInt("MaNhaXuatBan"),
                rs.getString("MaDauSach"),
                rs.getInt("NamXuatBan"),
                rs.getInt("LanTaiBan"),
                rs.getString("NgonNgu"),
                rs.getInt("SoLuong")
        ), keyword, keyword, keyword);
    }

}

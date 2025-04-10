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
        String sql = "INSERT INTO sach (TenSach,MaTheLoai,MaTacGia,MaNhaXuatBan,MaDauSach,NamXuatBan,LanTaiBan,NgonNgu,SoLuong,MaKhuVuc) VALUES (?,?,?,?,?,?,?,?,?,?)";
        int result = jdbchelper.executeUpdate(sql, sach.getTenSach(), sach.getMaTheLoai(),
                sach.getMaTacGia(), sach.getMaNhaXuatBan(), sach.getMaDauSach(), sach.getNamXuatBan(),
                sach.getLanTaiBan(), sach.getNgonNgu(), sach.getSoLuong(), sach.getMaKhuVuc());

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Thêm sách thành công!");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean update(Sach sach) {
        String sql = "UPDATE Sach SET TenSach=?, NgonNgu=?, MaTheLoai=?, MaTacGia=?, MaNhaXuatBan=?, MaKhuVuc=?, NamXuatBan=?, SoLuong=?, LanTaiBan=? WHERE MaDauSach=?";
        try {
            int rows = jdbchelper.executeUpdate(sql,
                    sach.getTenSach(),
                    sach.getNgonNgu(),
                    sach.getMaTheLoai(),
                    sach.getMaTacGia(),
                    sach.getMaNhaXuatBan(),
                    sach.getMaKhuVuc(),
                    sach.getNamXuatBan(),
                    sach.getSoLuong(),
                    sach.getLanTaiBan(),
                    sach.getMaDauSach()
            );
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
                + ",MaDauSach,NamXuatBan,LanTaiBan,NgonNgu,SoLuong,MaKhuVuc FROM sach";

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
                sc.setMaKhuVuc(rs.getInt("MaKhuVuc"));
                dsSach.add(sc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dsSach;
    }

    public static List<Sach> QRScangetall() {
        List<Sach> dsSach = new ArrayList<>();
        String sql = "SELECT s.MaSach, s.TenSach, s.MaTheLoai, t.TenTheLoai, s.MaTacGia, tg.TenTacGia, "
                + "s.MaNhaXuatBan, nxb.TenNhaXuatBan, s.MaDauSach, s.NamXuatBan, s.LanTaiBan, s.NgonNgu, s.SoLuong "
                + "FROM sach s "
                + "JOIN theloai t ON s.MaTheLoai = t.MaTheLoai "
                + "JOIN tacgia tg ON s.MaTacGia = tg.MaTacGia "
                + "JOIN nhaxuatban nxb ON s.MaNhaXuatBan = nxb.MaNhaXuatBan";

        try (ResultSet rs = jdbchelper.executeQuery(sql)) {
            while (rs.next()) {
                Sach sc = new Sach();
                sc.setMaSach(rs.getInt("MaSach"));
                sc.setTenSach(rs.getString("TenSach"));
                sc.setMaTheLoai(rs.getInt("MaTheLoai"));
                sc.setTenTheLoai(rs.getString("TenTheLoai"));  // Lấy tên thể loại
                sc.setMaTacGia(rs.getInt("MaTacGia"));
                sc.setTenTacGia(rs.getString("TenTacGia"));    // Lấy tên tác giả
                sc.setMaNhaXuatBan(rs.getInt("MaNhaXuatBan"));
                sc.setTenNhaXuatBan(rs.getString("TenNhaXuatBan")); // Lấy tên nhà xuất bản
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

    public static List<Sach> getSachByKhuVuc(int maKhuVuc) {
        List<Sach> dsTheoKhuVuc = new ArrayList<>();
        for (Sach sach : getAll()) {
            if (sach.getMaKhuVuc() == maKhuVuc) {
                dsTheoKhuVuc.add(sach);
            }
        }
        return dsTheoKhuVuc;
    }

}

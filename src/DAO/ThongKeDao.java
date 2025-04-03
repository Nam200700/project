/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.util.ArrayList;
import java.util.List;
import util.jdbchelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author admin
 */
public class ThongKeDao {
     // Thống kê độc giả theo giới tính
    public List<Object[]> thongKeDocGiaTheoGioiTinh() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT GioiTinh, COUNT(*) AS SoLuong FROM DOCGIA GROUP BY GioiTinh";

        try (Connection conn = jdbchelper.getconnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new Object[]{rs.getString("GioiTinh"), rs.getInt("SoLuong")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Thống kê sách có lượt mượn nhiều nhất theo tháng
    public List<Object[]> thongKeSachMuonNhieuTheoThang(int thang, int nam) {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT SACH.TenSach, COUNT(CHITIETMUON.MaSach) AS LuotMuon
            FROM CHITIETMUON 
            JOIN PHIEUMUON ON CHITIETMUON.MaPhieuMuon = PHIEUMUON.MaPhieuMuon
            JOIN SACH ON CHITIETMUON.MaSach = SACH.MaSach
            WHERE MONTH(PHIEUMUON.NgayMuon) = ? AND YEAR(PHIEUMUON.NgayMuon) = ?
            GROUP BY SACH.TenSach
            ORDER BY LuotMuon DESC
        """;

        try (Connection conn = jdbchelper.getconnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, thang);
            stmt.setInt(2, nam);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Object[]{rs.getString("TenSach"), rs.getInt("LuotMuon")});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}

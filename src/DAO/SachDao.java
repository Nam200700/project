/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.Sach;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.jdbchelper;

/**
 *
 * @author admin
 */
public class SachDao {
       private Connection conn;

    public SachDao() {
         try {
            conn = jdbchelper.getconnection(); // gọi đúng phương thức kết nối
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }
    
    
    

    // Xóa sách
    public boolean delete(String tenSach) {
        String sql = "DELETE FROM sach WHERE TenSach=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenSach);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy danh sách tất cả sách
    public List<Sach> getAll() {
        List<Sach> list = new ArrayList<>();
        String sql = "SELECT * FROM sach";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Sach s = new Sach(
                    rs.getString("TenSach"),
                    rs.getString("MaTheLoai"),
                    rs.getString("MaTacGia"),
                    rs.getString("MaNhaXuatBan"),
                    rs.getInt("NamXuatBan"),
                    rs.getString("NgonNgu"),
                    rs.getInt("SoLuong"),
                    rs.getInt("LanTaiBan")
                );
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}

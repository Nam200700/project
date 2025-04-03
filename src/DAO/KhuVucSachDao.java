/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import util.jdbchelper;


/**
 *
 * @author admin
 */
public class KhuVucSachDao {

    public void themKhuVuc(String tenKhuVuc, int tang, int ke, int viTri, int maSach, int soLuong) {
        String sql = "INSERT INTO KhuVucSach (TenKhuVuc, Tang, Ke, ViTri, MaSach, SoLuong) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = jdbchelper.getconnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenKhuVuc);
            ps.setInt(2, tang);
            ps.setInt(3, ke);
            ps.setInt(4, viTri);
            ps.setInt(5, maSach);
            ps.setInt(6, soLuong);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Thêm thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void xoaKhuVuc(int maKhuVuc) {
        String sql = "DELETE FROM KhuVucSach WHERE MaKhuVuc = ?";
        try (Connection conn = jdbchelper.getconnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maKhuVuc);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Xóa thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void suaKhuVuc(int maKhuVuc, String tenKhuVuc, int tang, int ke, int viTri, int maSach, int soLuong) {
        String sql = "UPDATE KhuVucSach SET TenKhuVuc=?, Tang=?, Ke=?, ViTri=?, MaSach=?, SoLuong=? WHERE MaKhuVuc=?";
        try (Connection conn = jdbchelper.getconnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenKhuVuc);
            ps.setInt(2, tang);
            ps.setInt(3, ke);
            ps.setInt(4, viTri);
            ps.setInt(5, maSach);
            ps.setInt(6, soLuong);
            ps.setInt(7, maKhuVuc);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Sửa thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

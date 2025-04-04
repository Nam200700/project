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

    public boolean themKhuVuc(String tenKhuVuc, int tang, int ke, int viTri, int maSach, int soLuong) {
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
        return true; // Trả về true khi thêm thành công
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false; // Trả về false khi gặp lỗi
}


    

   public boolean xoaKhuVuc(int maKhuVuc) {
    String sql = "DELETE FROM KhuVucSach WHERE MaKhuVuc = ?";
    try (Connection conn = jdbchelper.getconnection(); 
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, maKhuVuc);
        int rowsAffected = ps.executeUpdate();  // Kiểm tra số dòng bị xóa
        
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Xóa thành công!");
            return true;  // Xóa thành công
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy khu vực cần xóa.");
            return false;  // Không tìm thấy khu vực
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Xảy ra lỗi khi xóa khu vực.");
        return false;  // Lỗi khi thực hiện xóa
    }
}


public boolean suaKhuVuc(String tenKhuVuc, int tang, int ke, int viTri, int maSach, int soLuong, int maKhuVuc) {
    String sql = "UPDATE KhuVucSach SET tenKhuVuc = ?, tang = ?, ke = ?, viTri = ?, maSach = ?, soLuong = ? WHERE maKhuVuc = ?";
    try (Connection conn = jdbchelper.getconnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, tenKhuVuc);
        stmt.setInt(2, tang);
        stmt.setInt(3, ke);
        stmt.setInt(4, viTri);
        stmt.setInt(5, maSach);
        stmt.setInt(6, soLuong);
        stmt.setInt(7, maKhuVuc); // Điều kiện WHERE

        // Debug: In câu lệnh SQL ra console để kiểm tra
        System.out.println("Câu lệnh SQL: " + stmt.toString());

        // Thực thi câu lệnh cập nhật
        int rowsUpdated = stmt.executeUpdate();
        return rowsUpdated > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Entity.TacGia;
import Entity.TheLoai;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import util.jdbchelper;
/**
 *
 * @author huynh
 */
public class TheLoaiDAO {
   public boolean ThemTheLoai(TheLoai tl) {
    String sql = "Insert Into TheLoai (MaTheLoai , TenTheLoai)Values (?,?)";
    
    try (Connection conn = jdbchelper.getconnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, String.valueOf(tl.getMaTheLoai()));
        stmt.setString(2, tl.getTenTheLoai());

        return stmt.executeUpdate() > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean capNhatTheLoai(TheLoai tl) {
    String sql = "UPDATE TacGia SET TenTacGia = ? WHERE MaTacGia = ?";
    
    try (Connection conn = jdbchelper.getconnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, String.valueOf(tl.getMaTheLoai()));
        stmt.setString(2, tl.getTenTheLoai());

        int rowsUpdated = stmt.executeUpdate();
        return rowsUpdated > 0; // Nếu có ít nhất 1 dòng bị ảnh hưởng -> Cập nhật thành công

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}

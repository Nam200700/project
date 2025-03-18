/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Entity.TheLoai;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import util.jdbchelper;
/**
 *
 * @author Acer
 */
public class TheLoaiDAO {
    public boolean themTacGia(TheLoai tg) {
        String sql = "INSERT INTO TacGia (MaTacGia, TenTacGia) VALUES (?, ?)";
        
        try ( Connection conn= jdbchelper.getconnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
         stmt.setString(1, String.valueOf(tg.getMaTheLoai()));
            stmt.setString(2, tg.getTenTheLoai());

            return stmt.executeUpdate() > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

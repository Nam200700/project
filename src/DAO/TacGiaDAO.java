package DAO;  // Đảm bảo dòng này có tồn tại và viết đúng

import Entity.TacGia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import util.jdbchelper;

public class TacGiaDAO {
    public boolean themTacGia(TacGia tg) {
        String sql = "INSERT INTO TacGia (MaTacGia, TenTacGia) VALUES (?, ?)";
        
        try (Connection conn = jdbchelper.getconnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, tg.getMaTacGia());
            stmt.setString(2, tg.getTenTacGia());

            return stmt.executeUpdate() > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean capNhatTacGia(TacGia tg) {
    String sql = "UPDATE TacGia SET TenTacGia = ? WHERE MaTacGia = ?";
    
    try (Connection conn = jdbchelper.getconnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
         
        stmt.setString(1, tg.getTenTacGia());
        stmt.setString(2, tg.getMaTacGia());

        int rowsUpdated = stmt.executeUpdate();
        return rowsUpdated > 0; // Nếu có ít nhất 1 dòng bị ảnh hưởng -> Cập nhật thành công

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

}

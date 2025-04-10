package DAO;

import Entity.PhieuPhat;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.jdbchelper;

public class PhieuPhatDAO {
    
    // Lấy danh sách tất cả phiếu phạt
    public List<PhieuPhat> getAllPhieuPhat() {
        List<PhieuPhat> list = new ArrayList<>();
        String query = "SELECT * FROM PHIEUPHAT";
        
        try (Connection conn = jdbchelper.getconnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                PhieuPhat phieuPhat = new PhieuPhat(
                    rs.getInt("MaPhieuPhat"),
                    rs.getInt("MaPhieuTra"),
                    rs.getDouble("SoTienPhat"),
                    rs.getString("LyDo"),
                    rs.getString("TrangThaiThanhToan")
                );
                list.add(phieuPhat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm mới phiếu phạt
    public void addPhieuPhat(int maPhieuTra, double soTienPhat, String lyDo, String trangThai) {
        String query = "INSERT INTO PHIEUPHAT (MaPhieuTra, SoTienPhat, LyDo, TrangThaiThanhToan) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = jdbchelper.getconnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, maPhieuTra);
            stmt.setDouble(2, soTienPhat);
            stmt.setString(3, lyDo);
            stmt.setString(4, trangThai);
            
            stmt.executeUpdate();
            System.out.println("Thêm mới phiếu phạt thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật phiếu phạt
    public void updatePhieuPhat(int maPhieuPhat, int maPhieuTra, double soTienPhat, String lyDo, String trangThai) {
        String query = "UPDATE PHIEUPHAT SET MaPhieuTra = ?, SoTienPhat = ?, LyDo = ?, TrangThaiThanhToan = ? WHERE MaPhieuPhat = ?";
        
        try (Connection conn = jdbchelper.getconnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, maPhieuTra);
            stmt.setDouble(2, soTienPhat);
            stmt.setString(3, lyDo);
            stmt.setString(4, trangThai);
            stmt.setInt(5, maPhieuPhat);
            
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Cập nhật phiếu phạt thành công!");
            } else {
                System.out.println("Không tìm thấy phiếu phạt để cập nhật!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa phiếu phạt
    public void deletePhieuPhat(int maPhieuPhat) {
        String query = "DELETE FROM PHIEUPHAT WHERE MaPhieuPhat = ?";
        
        try (Connection conn =jdbchelper.getconnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, maPhieuPhat);
            
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Xóa phiếu phạt thành công!");
            } else {
                System.out.println("Không tìm thấy phiếu phạt để xóa!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

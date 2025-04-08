/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Entity.KhuVucSach;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import util.jdbchelper;
import java.sql.Statement;
/**
 *
 * @author admin
 */
public class KhuVucSachDao {
     private Connection connection;

    public KhuVucSachDao(Connection connection) {
        this.connection = connection;
    }

    public boolean themKhuVuc(KhuVucSach khuVuc) {
        String sql = "INSERT INTO KhuVuc (TenKhuVuc, Tang, Ke, ViTri) VALUES (?, ?, ?, ?)";
        try (Connection conn = jdbchelper.getconnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, khuVuc.getTenKhuVuc());
            stmt.setInt(2, khuVuc.getTang());
            stmt.setInt(3, khuVuc.getKe());
            stmt.setInt(4, khuVuc.getViTri());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoa(String maKhuVuc) {
        String sql = "DELETE FROM KhuVuc WHERE MaKhuVuc = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, maKhuVuc);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

// Phương thức cập nhật khu vực sách
    public boolean updateKhuVuc(KhuVucSach khuVucSach) {
        String sql = "UPDATE KhuVuc SET TenKhuVuc = ?, Tang = ?, Ke = ?, ViTri = ? WHERE MaKhuVuc = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Set giá trị cho câu lệnh SQL
            stmt.setString(1, khuVucSach.getTenKhuVuc());
            stmt.setInt(2, khuVucSach.getTang());
            stmt.setInt(3, khuVucSach.getKe());
            stmt.setInt(4, khuVucSach.getViTri());
            stmt.setInt(5, khuVucSach.getMaKhuVuc());

            // Thực thi câu lệnh cập nhật
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0; // Nếu có ít nhất 1 dòng bị ảnh hưởng, trả về true
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false; // Nếu có lỗi xảy ra, trả về false
        }
    }




    public boolean kiemTraKhuVucCoSach(String maKhuVuc) {
        String sql = "SELECT COUNT(*) FROM sach WHERE maKhuVuc = ?";
        try (
                PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setString(1, maKhuVuc);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Trả về true nếu có sách
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<KhuVucSach> getAllKhuVucWithSoLuong() {
        List<KhuVucSach> list = new ArrayList<>();
        String sql = """
    SELECT kv.MaKhuVuc, kv.TenKhuVuc, kv.Tang, kv.Ke, kv.ViTri, SUM(s.SoLuong) AS SoLuong
    FROM KhuVuc kv
    LEFT JOIN SACH s ON kv.MaKhuVuc = s.MaKhuVuc
    GROUP BY kv.MaKhuVuc, kv.TenKhuVuc, kv.Tang, kv.Ke, kv.ViTri
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                KhuVucSach kv = new KhuVucSach();
                kv.setMaKhuVuc(rs.getInt("MaKhuVuc"));
                kv.setTenKhuVuc(rs.getString("TenKhuVuc"));
                kv.setTang(rs.getInt("Tang"));
                kv.setKe(rs.getInt("Ke"));
                kv.setViTri(rs.getInt("ViTri"));
                kv.setSoLuong(rs.getInt("SoLuong")); // cần thêm thuộc tính này trong Entity
                list.add(kv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}

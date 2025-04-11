/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.NhaXuatBan;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import util.jdbchelper;

/**
 *
 * @author ACER
 */
public class NhaXuatBanDAO {

    public static void insert(NhaXuatBan nxb) {
        String sql = "INSERT INTO nhaxuatban (TenNhaXuatBan) VALUES (?)";
        int result = jdbchelper.executeUpdate(sql, nxb.getTennhaxuatban());

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Thêm nhà xuất bản thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm nhà xuất bản!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void update(NhaXuatBan nxb) {
        String sql = "UPDATE nhaxuatban SET TenNhaXuatBan = ? WHERE MaNhaXuatBan = ?";
        int result = jdbchelper.executeUpdate(sql, nxb.getTennhaxuatban(), nxb.getManhaxuatban());

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy nhà xuất bản!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static boolean delete(String maNhaXuatBan) {
        String sql = "DELETE FROM nhaxuatban WHERE MaNhaXuatBan = ?";
        int result = jdbchelper.executeUpdate(sql, maNhaXuatBan);

        if (result > 0) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Không thể xóa nhà xuất bản!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static List<NhaXuatBan> getAll() {
        List<NhaXuatBan> dsNhaXuatBan = new ArrayList<>();
        String sql = "SELECT MaNhaXuatBan, TenNhaXuatBan FROM nhaxuatban";

        try (ResultSet rs = jdbchelper.executeQuery(sql)) {
            while (rs.next()) {
                NhaXuatBan nxb = new NhaXuatBan();
                nxb.setManhaxuatban(rs.getString("MaNhaXuatBan"));
                nxb.setTennhaxuatban(rs.getString("TenNhaXuatBan"));
                dsNhaXuatBan.add(nxb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dsNhaXuatBan;
    }
    // truy vấn dữ liệu xem coi có bị trùng nhà xuất bản
    public static boolean kiemTraTrungTen(String tenNXB) {
        String query = "SELECT COUNT(*) FROM nhaxuatban WHERE TenNhaXuatBan = ?";
        try {
            ResultSet rs = jdbchelper.executeQuery(query, tenNXB);
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}

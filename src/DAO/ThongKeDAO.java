package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Entity.ThongKeSach;

public class ThongKeDAO {
    
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/qltv", "root", "0359910800");
    }

    public static List<ThongKeSach> getSachMuonNhieuNhat() {
        List<ThongKeSach> list = new ArrayList<>();
        String sql = "SELECT s.MaSach, s.TenSach, COUNT(m.MaMuon) AS SoLanMuon " +
                     "FROM Sach s JOIN MuonSach m ON s.MaSach = m.MaSach " +
                     "GROUP BY s.MaSach, s.TenSach " +
                     "ORDER BY SoLanMuon DESC LIMIT 5";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                list.add(new ThongKeSach(
                    rs.getString("MaSach"),
                    rs.getString("TenSach"),
                    rs.getInt("SoLanMuon")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<ThongKeSach> getSachMuonItNhat() {
        List<ThongKeSach> list = new ArrayList<>();
        String sql = "SELECT s.MaSach, s.TenSach, COUNT(m.MaMuon) AS SoLanMuon " +
                     "FROM Sach s JOIN MuonSach m ON s.MaSach = m.MaSach " +
                     "GROUP BY s.MaSach, s.TenSach " +
                     "ORDER BY SoLanMuon ASC LIMIT 5";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                list.add(new ThongKeSach(
                    rs.getString("MaSach"),
                    rs.getString("TenSach"),
                    rs.getInt("SoLanMuon")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

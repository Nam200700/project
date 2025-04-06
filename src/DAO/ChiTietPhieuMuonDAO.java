package DAO;

import Entity.ChiTietPhieuMuon;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import util.jdbchelper;

public class ChiTietPhieuMuonDAO {

    // Thêm mới phiếu mượn
    public static boolean insert(ChiTietPhieuMuon ctpm) {
        String sql = "INSERT INTO chitietmuon (MaPhieuMuon,MaSach,SoLuong,TenSach) VALUES (?,?,?,?)";
        int result = jdbchelper.executeUpdate(sql, ctpm.getMaphieuMuon(), ctpm.getMaSach(), ctpm.getSoLuong(), ctpm.getTenSach());

        if (result > 0) {
//            JOptionPane.showMessageDialog(null, "Thêm chi tiết phiếu mượn thành công!");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm chi tiết phiếu mượn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Xóa chi tiết phiếu mượn theo Mã Phiếu Mượn và Mã Sách
    public static boolean delete(String maPhieuMuon, String tenSach) {
        String sql = "DELETE FROM chitietmuon WHERE MaPhieuMuon = ? AND TenSach = ?";
        int result = jdbchelper.executeUpdate(sql, maPhieuMuon, tenSach);

        if (result > 0) {
//            JOptionPane.showMessageDialog(null, "Xóa chi tiết phiếu mượn thành công!");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Không thể xóa chi tiết phiếu mượn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Lấy danh sách tất cả phiếu mượn
    public static List<ChiTietPhieuMuon> getAll() {
        List<ChiTietPhieuMuon> dschitietPhieuMuon = new ArrayList<>();
        String sql = "SELECT MaPhieuMuon, TenSach, SoLuong FROM chitietmuon";

        try (ResultSet rs = jdbchelper.executeQuery(sql)) {
            while (rs.next()) {
                ChiTietPhieuMuon ctpm = new ChiTietPhieuMuon();
                ctpm.setMaphieuMuon(rs.getString("MaPhieuMuon"));
                ctpm.setTenSach(rs.getString("TenSach"));
                ctpm.setSoLuong(rs.getInt("SoLuong"));
                dschitietPhieuMuon.add(ctpm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dschitietPhieuMuon;
    }

    // tính tổng số lượng theo mã phiếu mượn 
    public static int tinhTongSoLuong(String maPhieuMuon) {
        int tongSoLuong = 0;
        String sql = "SELECT SUM(SoLuong) FROM chitietmuon WHERE MaPhieuMuon = ?";

        try (ResultSet rs = jdbchelper.executeQuery(sql, maPhieuMuon)) {
            if (rs.next()) {
                tongSoLuong = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tongSoLuong;
    }

    // kiểm tra số lượng của mã sách 
    public static int checkSoLuongSach(String maSach) {
        int soLuongConLai = 0;
        String sql = "SELECT SoLuong FROM Sach WHERE MaSach = ?";

        try (ResultSet rs = jdbchelper.executeQuery(sql, maSach)) {
            if (rs.next()) {
                soLuongConLai = rs.getInt("SoLuong");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return soLuongConLai;
    }

    public static void giamsoluong(String tenSach, int soLuongMuon) {
        String sql = "UPDATE Sach SET SoLuong = SoLuong - ? WHERE TenSach = ?";

        int result = jdbchelper.executeUpdate(sql, soLuongMuon, tenSach);

        if (result <= 0) {
            JOptionPane.showMessageDialog(null, "Cập nhật số lượng sách thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean tangsoluong(String tenSach, int soLuongThem) {
        String sql = "UPDATE Sach SET SoLuong = SoLuong + ? WHERE TenSach = ?";
        int result = jdbchelper.executeUpdate(sql, soLuongThem, tenSach);

        return result > 0;
    }

    // có chi tiết phiếu không để chạy điều kiện xóa của phiếu mượn
    public static boolean hasDetails(String maPhieuMuon) {
        String sql = "SELECT COUNT(*) FROM chitietmuon WHERE MaPhieuMuon = ?";

        try (ResultSet rs = jdbchelper.executeQuery(sql, maPhieuMuon)) {
            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu có ít nhất 1 bản ghi, trả về true
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}

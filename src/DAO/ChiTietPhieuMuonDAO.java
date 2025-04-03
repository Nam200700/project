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
        String sql = "INSERT INTO chitietmuon (MaPhieuMuon,MaSach,SoLuong) VALUES (?,?,?)";
        int result = jdbchelper.executeUpdate(sql, ctpm.getMaphieuMuon(), ctpm.getMaSach(), ctpm.getSoLuong());

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Thêm chi tiết phiếu mượn thành công!");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm chi tiết phiếu mượn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Cập nhật phiếu mượn (vd: cập nhật trạng thái)
    public static void update(ChiTietPhieuMuon ctpm) {
        String sql = "UPDATE chitietmuon SET MaPhieuMuon = ?, MaSach = ?, SoLuong = ?";
        int result = jdbchelper.executeUpdate(sql, ctpm.getMaphieuMuon(), ctpm.getMaSach(), ctpm.getSoLuong());

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Cập nhật chi tiết phiếu mượn thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy chi tiết phiếu mượn!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Xóa chi tiết phiếu mượn theo Mã Phiếu Mượn và Mã Sách
    public static boolean delete(String maPhieuMuon, String maSach) {
        String sql = "DELETE FROM chitietmuon WHERE MaPhieuMuon = ? AND MaSach = ?";
        int result = jdbchelper.executeUpdate(sql, maPhieuMuon, maSach);

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Xóa chi tiết phiếu mượn thành công!");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Không thể xóa chi tiết phiếu mượn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Lấy danh sách tất cả phiếu mượn
    public static List<ChiTietPhieuMuon> getAll() {
        List<ChiTietPhieuMuon> dschitietPhieuMuon = new ArrayList<>();
        String sql = "SELECT MaPhieuMuon, MaSach, SoLuong FROM chitietmuon";

        try (ResultSet rs = jdbchelper.executeQuery(sql)) {
            while (rs.next()) {
                ChiTietPhieuMuon ctpm = new ChiTietPhieuMuon();
                ctpm.setMaphieuMuon(rs.getString("MaPhieuMuon"));
                ctpm.setMaSach(rs.getString("MaSach"));
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

    public static void giamsoluong(String maSach, int soLuongMuon) {
        String sql = "UPDATE Sach SET SoLuong = SoLuong - ? WHERE MaSach = ?";

        int result = jdbchelper.executeUpdate(sql, soLuongMuon, maSach);

        if (result <= 0) {
            JOptionPane.showMessageDialog(null, "Cập nhật số lượng sách thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean tangsoluong(String maSach, int soLuongThem) {
        String sql = "UPDATE Sach SET SoLuong = SoLuong + ? WHERE MaSach = ?";
        int result = jdbchelper.executeUpdate(sql, soLuongThem, maSach);

        return result > 0;
    }

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

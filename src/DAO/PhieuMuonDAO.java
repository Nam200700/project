package DAO;

import Entity.PhieuMuon;
import com.sun.jdi.connect.spi.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import util.jdbchelper;

public class PhieuMuonDAO {

    // Thêm mới phiếu mượn
    public static boolean insert(PhieuMuon pm) {
        String sql = "INSERT INTO phieumuon (MaDocGia,NgayMuon, HanTra) VALUES (?,?,?)";
        int result = jdbchelper.executeUpdate(sql, pm.getMaDocGia(), pm.getNgayMuon(), pm.getHanTra());

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Thêm phiếu mượn thành công!");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm phiếu mượn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Cập nhật phiếu mượn (vd: cập nhật trạng thái)
    public static void update(PhieuMuon pm) {
        String sql = "UPDATE phieumuon SET MaDocGia = ?, NgayMuon = ?, HanTra = ? WHERE MaPhieuMuon = ?";
        int result = jdbchelper.executeUpdate(sql, pm.getMaDocGia(), pm.getNgayMuon(), pm.getHanTra(), pm.getMaPhieuMuon());

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Cập nhật phiếu mượn thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy phiếu mượn!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Xóa phiếu mượn
    public static boolean delete(int maPhieuMuon) {
        String sql = "DELETE FROM phieumuon WHERE MaPhieuMuon = ?";
        int result = jdbchelper.executeUpdate(sql, maPhieuMuon);

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Xóa phiếu mượn thành công!");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Không thể xóa phiếu mượn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Lấy danh sách tất cả phiếu mượn
    public static List<PhieuMuon> getAll() {
        List<PhieuMuon> dsPhieuMuon = new ArrayList<>();
        String sql = "SELECT MaPhieuMuon, MaDocGia, NgayMuon, HanTra, TrangThai FROM phieumuon";

        try (ResultSet rs = jdbchelper.executeQuery(sql)) {
            while (rs.next()) {
                PhieuMuon pm = new PhieuMuon();
                pm.setMaPhieuMuon(rs.getString("MaPhieuMuon"));
                pm.setMaDocGia(rs.getString("MaDocGia"));
                pm.setNgayMuon(rs.getDate("NgayMuon"));
                pm.setHanTra(rs.getDate("HanTra"));
                pm.setTrangthai(rs.getString("TrangThai"));
                dsPhieuMuon.add(pm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dsPhieuMuon;
    }

    // cập nhật trạng thái khi tạo phiếu trả
    public static boolean updateTrangThai(String maPhieuMuon, String trangThai) {
        String sql = "UPDATE phieumuon SET TrangThai = ? WHERE MaPhieuMuon = ?";

        int result = jdbchelper.executeUpdate(sql, trangThai, maPhieuMuon);

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Cập nhật trạng thái thành công!");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy phiếu mượn để cập nhật trạng thái!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

//    public static String getTrangThai(String maPhieuMuon) {
//        String sql = "SELECT TrangThai FROM phieumuon WHERE MaPhieuMuon = ?";
//
//        try (ResultSet rs = jdbchelper.executeQuery(sql, maPhieuMuon)) {
//            return (rs != null && rs.next()) ? rs.getString("TrangThai") : null;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    // lấy trạng thái để kiểm tra khi mà tạo phiếu trả
    public static String getTrangThai(String maPhieuMuon) {
        String sql = "SELECT TrangThai FROM phieumuon WHERE MaPhieuMuon = ?";

        try (ResultSet rs = jdbchelper.executeQuery(sql, maPhieuMuon)) {
            // Nếu có kết quả, trả về TrangThai
            if (rs != null && rs.next()) {
                return rs.getString("TrangThai");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In ra lỗi nếu có
        }

        // Nếu không có kết quả hoặc lỗi, trả về null
        return null;
    }

}

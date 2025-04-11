package DAO;

import util.jdbchelper;
import raven.chart.data.pie.DefaultPieDataset; // Chắc chắn rằng bạn nhập đúng thư viện này
import java.sql.ResultSet;
import java.sql.SQLException;

public class ThongKeDAO {

    public static DefaultPieDataset<String> createPieData() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>(); // Sử dụng DefaultPieDataset của raven

        String sql = """
                 SELECT k.TenKhuVuc, COUNT(*) AS SoLuong
                 FROM SACH s
                 JOIN KhuVuc k ON s.MaKhuVuc = k.MaKhuVuc
                 GROUP BY k.TenKhuVuc
                 """;

        try (ResultSet rs = jdbchelper.executeQuery(sql)) {
            while (rs.next()) {
                String tenKhuVuc = rs.getString("TenKhuVuc"); // ← sửa ở đây
                int soLuong = rs.getInt("SoLuong");
                dataset.setValue(tenKhuVuc, soLuong); // ← sửa ở đây
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataset;
    }

    public static DefaultPieDataset<String> createPieDataByTheLoai() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>(); // Sử dụng DefaultPieDataset của raven

        String sql = """
    SELECT tl.TenTheLoai, COUNT(*) AS SoLuong
    FROM SACH s
    JOIN THELOAI tl ON s.MaTheLoai = tl.MaTheLoai
    GROUP BY tl.TenTheLoai
    """;
        try (ResultSet rs = jdbchelper.executeQuery(sql)) {
            while (rs.next()) {
                String theLoai = rs.getString("TenTheLoai");
                int soLuong = rs.getInt("SoLuong");
                dataset.setValue(theLoai, soLuong);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataset;
    }

    public static DefaultPieDataset<String> createPieDataByNgonNgu() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>(); // Sử dụng DefaultPieDataset của raven

        String sql = "SELECT NgonNgu, COUNT(*) AS SoLuong FROM SACH GROUP BY NgonNgu";

        try (ResultSet rs = jdbchelper.executeQuery(sql, new Object[]{})) {
            while (rs.next()) {
                String ngonNgu = rs.getString("NgonNgu");
                int soLuong = rs.getInt("SoLuong");
                dataset.setValue(ngonNgu, soLuong);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataset;
    }

    public static DefaultPieDataset<String> createBarDataByTheLoaiSoluong() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>(); // Sử dụng DefaultPieDataset của JFreeChart

        // Câu lệnh SQL để lấy số lượng sách mượn theo thể loại
        String sql = "SELECT THELOAI.TenTheLoai, SUM(CHITIETMUON.SoLuong) AS SoLuongMuon "
                + "FROM CHITIETMUON "
                + "JOIN SACH ON CHITIETMUON.MaSach = SACH.MaSach "
                + "JOIN THELOAI ON SACH.MaTheLoai = THELOAI.MaTheLoai "
                + "GROUP BY THELOAI.TenTheLoai "
                + "LIMIT 0, 5000";

        try (ResultSet rs = jdbchelper.executeQuery(sql, new Object[]{})) {
            // Duyệt qua kết quả trả về và thêm vào dataset
            while (rs.next()) {
                String tenTheLoai = rs.getString("TenTheLoai"); // Lấy tên thể loại
                int soLuongMuon = rs.getInt("SoLuongMuon"); // Lấy số lượng sách mượn theo thể loại
                dataset.setValue(tenTheLoai, soLuongMuon); // Thêm dữ liệu vào dataset
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi nếu có vấn đề khi truy vấn
        }

        return dataset; // Trả về dataset đã chứa dữ liệu
    }

    public static DefaultPieDataset<String> createBarDataByMonth() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>(); // Sử dụng DefaultPieDataset của JFreeChart

        // Câu lệnh SQL để lấy số lượng sách mượn theo tháng (không cần năm)
        String sql = "SELECT MONTH(PHIEUMUON.NgayMuon) AS Thang, "
                + "SUM(CHITIETMUON.SoLuong) AS SoLuongMuon "
                + "FROM PHIEUMUON "
                + "JOIN CHITIETMUON ON PHIEUMUON.MaPhieuMuon = CHITIETMUON.MaPhieuMuon "
                + "GROUP BY MONTH(PHIEUMUON.NgayMuon) "
                + "ORDER BY Thang";

        try (ResultSet rs = jdbchelper.executeQuery(sql, new Object[]{})) {
            while (rs.next()) {
                int thang = rs.getInt("Thang"); // Lấy tháng
                int soLuongMuon = rs.getInt("SoLuongMuon"); // Lấy số lượng sách mượn
                dataset.setValue("Tháng " + thang, soLuongMuon); // Thêm vào dataset
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataset; // Trả về dataset đã chứa dữ liệu
    }

    public static DefaultPieDataset<String> createPieDataByNXB() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();

        String sql = "SELECT NXB.TenNhaXuatBan, COUNT(*) AS SoLuongSach "
                + "FROM SACH S "
                + "JOIN NHAXUATBAN NXB ON S.MaNhaXuatBan = NXB.MaNhaXuatBan "
                + "GROUP BY NXB.TenNhaXuatBan";

        try (ResultSet rs = jdbchelper.executeQuery(sql, new Object[]{})) {
            while (rs.next()) {
                String tenNXB = rs.getString("TenNhaXuatBan");
                int soLuong = rs.getInt("SoLuongSach");
                dataset.setValue(tenNXB, soLuong);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataset;
    }

    public static DefaultPieDataset<String> createPieDataByTrangThaiPM() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();

        String sql = "SELECT PM.TrangThai, COUNT(*) AS SoLuong "
                + "FROM PHIEUMUON PM "
                + "GROUP BY PM.TrangThai "
                + "ORDER BY SoLuong DESC";

        try (ResultSet rs = jdbchelper.executeQuery(sql, new Object[]{})) {
            while (rs.next()) {
                String trangThai = rs.getString("TrangThai");
                int soLuong = rs.getInt("SoLuong");
                dataset.setValue(trangThai, soLuong);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataset;
    }

}

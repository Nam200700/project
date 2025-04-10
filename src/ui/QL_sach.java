/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import DAO.SachDAO;
import Entity.Sach;
import java.awt.Font;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import raven.drawer.TabbedForm;
import util.jdbchelper;
/**
 *
 * @author ACER
 */
public class QL_sach extends TabbedForm {

    List<Sach> dsSach = new ArrayList<Sach>();
    SachDAO sachdao = new SachDAO();

    /**
     * Creates new form QL_sach
     */
    public QL_sach() {
          initComponents();
        loadNhaXuatBanID();
        loadTacGiaID();
        loadTheLoaiID();
        guitrasach();
        loadKhuVucID();
        loadTable();
        loadNgonNguComboBox();
    }

    public void guitrasach() {
        // Bo góc cho JTextField

        txt_tensach.putClientProperty("JComponent.roundRect", true);
        txt_tensach.putClientProperty("JTextField.placeholderText", "Nhập tên sách...");
        txt_tensach.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txt_madausach.putClientProperty("JComponent.roundRect", true);
        txt_madausach.putClientProperty("JTextField.placeholderText", "Nhập mã đầu sách...");
        txt_madausach.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txt_namxuatban.putClientProperty("JComponent.roundRect", true);
        txt_namxuatban.putClientProperty("JTextField.placeholderText", "Nhập năm xuất bản...");
        txt_namxuatban.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txt_soluong.putClientProperty("JComponent.roundRect", true);
        txt_soluong.putClientProperty("JTextField.placeholderText", "Nhập số lượng sách...");
        txt_soluong.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txt_lantainban.putClientProperty("JComponent.roundRect", true);
        txt_lantainban.putClientProperty("JTextField.placeholderText", "Nhập lần tái bản...");
        txt_lantainban.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txt_timkiem.putClientProperty("JTextField.placeholderText", "Vui lòng nhập tên hoặc mã để tìm kiếm...");
        // Cập nhật lại JButton với bo góc
        btn_them.setIcon(new ImageIcon(getClass().getResource("/image/add.png")));
        btn_capnhat.setIcon(new ImageIcon(getClass().getResource("/image/edit.png")));
        btn_xoa.setIcon(new ImageIcon(getClass().getResource("/image/delete.png")));

    }

   private List<String> maTheLoaiArr = new ArrayList<>();

    private void loadTheLoaiID() {
        String query = "SELECT MaTheLoai, TenTheLoai FROM TheLoai";
        maTheLoaiArr.clear();
        cbb_theloai.removeAllItems();

        try {
            ResultSet rs = jdbchelper.executeQuery(query);
            while (rs.next()) {
                String ma = rs.getString("MaTheLoai");
                String ten = rs.getString("TenTheLoai");
                maTheLoaiArr.add(ma);
                cbb_theloai.addItem(ten);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải thể loại!");
        }
    }

    private List<String> maKhuVucArr = new ArrayList<>();

    private void loadKhuVucID() {
        String query = "SELECT MaKhuVuc, TenKhuVuc FROM KhuVuc";
        maKhuVucArr.clear();
        cbo_Makhuvuc.removeAllItems();

        try {
            ResultSet rs = jdbchelper.executeQuery(query);
            while (rs.next()) {
                String ma = rs.getString("MaKhuVuc");
                String ten = rs.getString("TenKhuVuc");
                maKhuVucArr.add(ma);
                cbo_Makhuvuc.addItem(ten);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải khu vực!");
        }
    }

    private List<String> maTacGiaArr = new ArrayList<>();

    // load dữ liệu mã tác giả
    private void loadTacGiaID() {
        String query = "SELECT MaTacGia, TenTacGia FROM TacGia";
        maTacGiaArr.clear();
        cbb_tacgia.removeAllItems();

        try {
            ResultSet rs = jdbchelper.executeQuery(query);
            while (rs.next()) {
                String ma = rs.getString("MaTacGia");
                String ten = rs.getString("TenTacGia");
                maTacGiaArr.add(ma);
                cbb_tacgia.addItem(ten);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải tác giả!");
        }
    }

    private List<String> maNXBArr = new ArrayList<>();

    // load dữ liệu mã nhà xuất bản
    private void loadNhaXuatBanID() {
        String query = "SELECT MaNhaXuatBan, TenNhaXuatBan FROM NhaXuatBan";
        maNXBArr.clear();
        cbb_nhaxuatban.removeAllItems();

        try {
            ResultSet rs = jdbchelper.executeQuery(query);
            while (rs.next()) {
                String ma = rs.getString("MaNhaXuatBan");
                String ten = rs.getString("TenNhaXuatBan");
                maNXBArr.add(ma);
                cbb_nhaxuatban.addItem(ten);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải nhà xuất bản!");
        }
    }

 public void addSach() {
    try {
        // Kiểm tra các ô nhập liệu bắt buộc
        if (txt_tensach.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên sách!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txt_madausach.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã đầu sách!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txt_namxuatban.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập năm xuất bản!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txt_soluong.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txt_lantainban.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập lần tái bản!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Kiểm tra hợp lệ combobox
        if (cbb_theloai.getSelectedIndex() < 0 || cbb_theloai.getSelectedIndex() >= maTheLoaiArr.size()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thể loại hợp lệ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (cbb_tacgia.getSelectedIndex() < 0 || cbb_tacgia.getSelectedIndex() >= maTacGiaArr.size()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tác giả hợp lệ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (cbb_nhaxuatban.getSelectedIndex() < 0 || cbb_nhaxuatban.getSelectedIndex() >= maNXBArr.size()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà xuất bản hợp lệ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (cbo_Makhuvuc.getSelectedIndex() < 0 || cbo_Makhuvuc.getSelectedIndex() >= maKhuVucArr.size()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn mã khu vực hợp lệ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (cbo_NgonNgu.getSelectedIndex() < 0 || cbo_NgonNgu.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngôn ngữ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy dữ liệu từ form
        int maTheLoai = Integer.parseInt(maTheLoaiArr.get(cbb_theloai.getSelectedIndex()));
        int maTacGia = Integer.parseInt(maTacGiaArr.get(cbb_tacgia.getSelectedIndex()));
        int maNXB = Integer.parseInt(maNXBArr.get(cbb_nhaxuatban.getSelectedIndex()));
        int maKhuVuc = Integer.parseInt(maKhuVucArr.get(cbo_Makhuvuc.getSelectedIndex()));

        int namXuatBan = Integer.parseInt(txt_namxuatban.getText().trim());
        int soLuong = Integer.parseInt(txt_soluong.getText().trim());
        int lanTaiBan = Integer.parseInt(txt_lantainban.getText().trim());
        String ngonNgu = cbo_NgonNgu.getSelectedItem().toString();

        // Tạo đối tượng sách
        Sach sach = new Sach();
        sach.setTenSach(txt_tensach.getText().trim());
        sach.setMaDauSach(txt_madausach.getText().trim());
        sach.setNamXuatBan(namXuatBan);
        sach.setNgonNgu(ngonNgu);
        sach.setSoLuong(soLuong);
        sach.setLanTaiBan(lanTaiBan);
        sach.setMaTheLoai(maTheLoai);
        sach.setMaTacGia(maTacGia);
        sach.setMaNhaXuatBan(maNXB);
        sach.setMaKhuVuc(maKhuVuc);

        // Gọi DAO để thêm sách vào cơ sở dữ liệu
        boolean isSuccess = SachDAO.insert(sach);
        if (isSuccess) {
            // Hiển thị tên vào bảng
            String tenTheLoai = cbb_theloai.getSelectedItem().toString();
            String tenTacGia = cbb_tacgia.getSelectedItem().toString();
            String tenNXB = cbb_nhaxuatban.getSelectedItem().toString();
            String tenKhuVuc = cbo_Makhuvuc.getSelectedItem().toString();

            DefaultTableModel model = (DefaultTableModel) tbl_sach.getModel();
            model.addRow(new Object[]{
                txt_madausach.getText().trim(),
                txt_tensach.getText().trim(),
                ngonNgu,
                tenTheLoai,
                tenTacGia,
                tenNXB,
                tenKhuVuc,
                namXuatBan,
                soLuong,
                lanTaiBan
            });

            clean(); // reset form
            JOptionPane.showMessageDialog(this, "Thêm sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Thêm sách thất bại! Vui lòng kiểm tra dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho năm xuất bản, số lượng và lần tái bản!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}


   public void updateSach() {
    int index = tbl_sach.getSelectedRow();
    if (index == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn sách từ bảng để cập nhật!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Kiểm tra dữ liệu nhập
    if (txt_tensach.getText().trim().isEmpty()
            || txt_madausach.getText().trim().isEmpty()
            || txt_namxuatban.getText().trim().isEmpty()
            || txt_soluong.getText().trim().isEmpty()
            || txt_lantainban.getText().trim().isEmpty()
            || cbo_NgonNgu.getSelectedItem() == null
            || cbb_theloai.getSelectedIndex() < 0
            || cbb_tacgia.getSelectedIndex() < 0
            || cbb_nhaxuatban.getSelectedIndex() < 0
            || cbo_Makhuvuc.getSelectedIndex() < 0) {

        JOptionPane.showMessageDialog(this, "Vui lòng nhập/ chọn đầy đủ thông tin!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        // Lấy mã sách từ bảng (giả sử mã sách nằm ở cột 0)
        int maSach = Integer.parseInt(tbl_sach.getValueAt(index, 0).toString());

        // Lấy các giá trị từ form
        String tenSach = txt_tensach.getText().trim();
        String maDauSach = txt_madausach.getText().trim();
        int namXuatBan = Integer.parseInt(txt_namxuatban.getText().trim());
        int soLuong = Integer.parseInt(txt_soluong.getText().trim());
        int lanTaiBan = Integer.parseInt(txt_lantainban.getText().trim());
        String ngonNgu = cbo_NgonNgu.getSelectedItem().toString();

        int maTheLoai = Integer.parseInt(maTheLoaiArr.get(cbb_theloai.getSelectedIndex()));
        int maTacGia = Integer.parseInt(maTacGiaArr.get(cbb_tacgia.getSelectedIndex()));
        int maNXB = Integer.parseInt(maNXBArr.get(cbb_nhaxuatban.getSelectedIndex()));
        int maKhuVuc = Integer.parseInt(maKhuVucArr.get(cbo_Makhuvuc.getSelectedIndex()));

        // Tạo đối tượng sách
        Sach sach = new Sach();
        sach.setMaSach(maSach);
        sach.setTenSach(tenSach);
        sach.setMaDauSach(maDauSach);
        sach.setNamXuatBan(namXuatBan);
        sach.setSoLuong(soLuong);
        sach.setLanTaiBan(lanTaiBan);
        sach.setNgonNgu(ngonNgu);
        sach.setMaTheLoai(maTheLoai);
        sach.setMaTacGia(maTacGia);
        sach.setMaNhaXuatBan(maNXB);
        sach.setMaKhuVuc(maKhuVuc);

        // Gọi DAO cập nhật
        boolean isSuccess = SachDAO.update(sach);
        if (isSuccess) {
            // Cập nhật lại bảng
            DefaultTableModel model = (DefaultTableModel) tbl_sach.getModel();
            model.setValueAt(maSach, index, 0); // không cần thiết nếu mã sách không đổi
            model.setValueAt(tenSach, index, 1);
            model.setValueAt(cbb_theloai.getSelectedItem().toString(), index, 2);
            model.setValueAt(cbb_tacgia.getSelectedItem().toString(), index, 3);
            model.setValueAt(cbb_nhaxuatban.getSelectedItem().toString(), index, 4);
            model.setValueAt(maDauSach, index, 5);
            model.setValueAt(namXuatBan, index, 6);
            model.setValueAt(ngonNgu, index, 7);
            model.setValueAt(soLuong, index, 8);
            model.setValueAt(lanTaiBan, index, 9);
            model.setValueAt(cbo_Makhuvuc.getSelectedItem().toString(), index, 10);

            JOptionPane.showMessageDialog(this, "Cập nhật sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            clean();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật sách thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}


    public void removeSach() {
        int[] selectedRows = tbl_sach.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Chưa chọn sách nào để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa sách đã chọn?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                int index = selectedRows[i];

                Object maObj = tbl_sach.getValueAt(index, 0); // Giả sử cột 0 là MaSach
                int maSach = Integer.parseInt(maObj.toString()); // Ép sang int

                boolean isDeleted = SachDAO.delete(maSach);
                if (isDeleted) {
                    dsSach.removeIf(sach -> sach.getMaSach() == maSach);
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể xóa sách có mã " + maSach + " do ràng buộc dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            loadTable();
            JOptionPane.showMessageDialog(this, "Xóa sách thành công!");
            if (tbl_sach.getRowCount() > 0) {
                int newIndex = Math.min(selectedRows[0], tbl_sach.getRowCount() - 1);
                tbl_sach.setRowSelectionInterval(newIndex, newIndex);
                loadRowIndexField(newIndex);
            } else {
                clean();
            }
        }
    }

    public void loadTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_sach.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ trong bảng

        try {
            String query = "SELECT s.MaSach, s.TenSach, tl.TenTheLoai, tg.TenTacGia, "
                    + "nxb.TenNhaXuatBan, s.MaDauSach, s.NamXuatBan, s.NgonNgu, "
                    + "s.SoLuong, s.LanTaiBan, kv.TenKhuVuc "
                    + // <-- LẤY TENKhuVuc
                    "FROM Sach s "
                    + "JOIN TheLoai tl ON s.MaTheLoai = tl.MaTheLoai "
                    + "JOIN TacGia tg ON s.MaTacGia = tg.MaTacGia "
                    + "JOIN NhaXuatBan nxb ON s.MaNhaXuatBan = nxb.MaNhaXuatBan "
                    + "JOIN KhuVuc kv ON s.MaKhuVuc = kv.MaKhuVuc";

            ResultSet rs = jdbchelper.executeQuery(query);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("MaSach"),
                    rs.getString("TenSach"),
                    rs.getString("TenTheLoai"),
                    rs.getString("TenTacGia"),
                    rs.getString("TenNhaXuatBan"),
                    rs.getString("MaDauSach"),
                    rs.getInt("NamXuatBan"),
                    rs.getString("NgonNgu"),
                    rs.getInt("SoLuong"),
                    rs.getInt("LanTaiBan"),
                    rs.getString("TenKhuVuc") // <-- ĐỔI thành TenKhuVuc
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi load dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadNgonNguComboBox() {
    cbo_NgonNgu.removeAllItems();
    cbo_NgonNgu.addItem("Tiếng Việt");
    cbo_NgonNgu.addItem("Tiếng Anh");
}


    public void loadRowIndexField(int rowIndex) {
    int maSach = (int) tbl_sach.getValueAt(rowIndex, 0);
    String tenSach = (String) tbl_sach.getValueAt(rowIndex, 1);
    int maTheLoai = (int) tbl_sach.getValueAt(rowIndex, 2);
    int maTacGia = (int) tbl_sach.getValueAt(rowIndex, 3);
    int maNhaXuatBan = (int) tbl_sach.getValueAt(rowIndex, 4);
    String maDauSach = (String) tbl_sach.getValueAt(rowIndex, 5);
    int namXuatBan = (int) tbl_sach.getValueAt(rowIndex, 6);
    String ngonNgu = (String) tbl_sach.getValueAt(rowIndex, 7);
    int soLuong = (int) tbl_sach.getValueAt(rowIndex, 8);
    int lanTaiBan = (int) tbl_sach.getValueAt(rowIndex, 9);
    int maKhuVuc = (int) tbl_sach.getValueAt(rowIndex, 10);

    txt_tensach.setText(tenSach);
    cbb_theloai.setSelectedItem(maTheLoai);
    cbb_tacgia.setSelectedItem(maTacGia);
    cbb_nhaxuatban.setSelectedItem(maNhaXuatBan);
    txt_madausach.setText(maDauSach);
    txt_namxuatban.setText(String.valueOf(namXuatBan));
    cbo_NgonNgu.setSelectedItem(ngonNgu); // ✅ đã sửa dòng này
    txt_soluong.setText(String.valueOf(soLuong));
    txt_lantainban.setText(String.valueOf(lanTaiBan));
    cbo_Makhuvuc.setSelectedItem(maKhuVuc);
}


    public void clean() {
        txt_tensach.setText("");
        cbb_theloai.setSelectedIndex(0);
        cbb_tacgia.setSelectedIndex(0);
        cbb_nhaxuatban.setSelectedIndex(0);
        txt_madausach.setText("");
        txt_namxuatban.setText("");
        cbo_NgonNgu.setSelectedIndex(0);
        txt_soluong.setText("");
        txt_lantainban.setText("");
        cbo_Makhuvuc.setSelectedIndex(0);
    }

  public void clickSach() {
    // Kiểm tra bảng có dữ liệu không
    if (tbl_sach.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "Không có dữ liệu trong bảng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Lấy dòng đang chọn
    int row = tbl_sach.getSelectedRow();

    // Kiểm tra xem đã chọn dòng chưa
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Chưa chọn dòng nào!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Lấy dữ liệu từ bảng an toàn
    String tenSach     = String.valueOf(tbl_sach.getValueAt(row, 1));
    String theLoai     = String.valueOf(tbl_sach.getValueAt(row, 2));
    String tacGia      = String.valueOf(tbl_sach.getValueAt(row, 3));
    String nhaXuatBan  = String.valueOf(tbl_sach.getValueAt(row, 4));
    String maDauSach   = String.valueOf(tbl_sach.getValueAt(row, 5));
    String namXuatBan  = String.valueOf(tbl_sach.getValueAt(row, 6));
    String ngonNgu     = String.valueOf(tbl_sach.getValueAt(row, 7));
    String soLuong     = String.valueOf(tbl_sach.getValueAt(row, 8));
    String lanTaiBan   = String.valueOf(tbl_sach.getValueAt(row, 9));
    String maKhuVuc    = String.valueOf(tbl_sach.getValueAt(row, 10));

    // Gán dữ liệu vào các ô nhập
    txt_tensach.setText(tenSach);
    cbb_theloai.setSelectedItem(theLoai);
    cbb_tacgia.setSelectedItem(tacGia);
    cbb_nhaxuatban.setSelectedItem(nhaXuatBan);
    txt_madausach.setText(maDauSach);
    txt_namxuatban.setText(namXuatBan);
    cbo_NgonNgu.setSelectedItem(ngonNgu);
    txt_soluong.setText(soLuong);
    txt_lantainban.setText(lanTaiBan);
    cbo_Makhuvuc.setSelectedItem(maKhuVuc);
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundedPanel1 = new swing.RoundedPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_sach = new javax.swing.JTable();
        txt_timkiem = new javax.swing.JTextField();
        btn_timkiem = new javax.swing.JButton();
        btn_them = new javax.swing.JButton();
        btn_xoa = new javax.swing.JButton();
        btn_capnhat = new javax.swing.JButton();
        txt_tensach = new javax.swing.JTextField();
        cbb_theloai = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbb_tacgia = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cbb_nhaxuatban = new javax.swing.JComboBox<>();
        txt_madausach = new javax.swing.JTextField();
        txt_namxuatban = new javax.swing.JTextField();
        txt_soluong = new javax.swing.JTextField();
        txt_lantainban = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cbo_NgonNgu = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cbo_Makhuvuc = new javax.swing.JComboBox<>();

        setOpaque(false);

        roundedPanel1.setPreferredSize(new java.awt.Dimension(1050, 547));

        tbl_sach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã sách", "Tên sách", "Thể loại sách", "Tác giả", "Nhà xuất bản", "Mã đầu sách", "Năm xuất bản", "Ngôn ngữ ", "Số lượng", "Lần tái bản", "Mã khu vực"
            }
        ));
        tbl_sach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_sachMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_sach);

        btn_timkiem.setText("Tìm kiếm");
        btn_timkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_timkiemActionPerformed(evt);
            }
        });

        btn_them.setText("Thêm");
        btn_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themActionPerformed(evt);
            }
        });

        btn_xoa.setText("Xóa");
        btn_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoaActionPerformed(evt);
            }
        });

        btn_capnhat.setText("Sửa");
        btn_capnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_capnhatActionPerformed(evt);
            }
        });

        cbb_theloai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Thể loại sách");

        jLabel2.setText("Tác giả");

        cbb_tacgia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Nhà xuất bản");

        cbb_nhaxuatban.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txt_madausach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_madausachActionPerformed(evt);
            }
        });

        txt_lantainban.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_lantainbanActionPerformed(evt);
            }
        });

        jLabel4.setText("Ngôn ngữ");

        cbo_NgonNgu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbo_NgonNgu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_NgonNguActionPerformed(evt);
            }
        });

        jLabel5.setText("Mã khu vực");

        cbo_Makhuvuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGap(0, 4, Short.MAX_VALUE)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addGap(293, 293, 293)
                                .addComponent(jLabel3))
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addGap(291, 291, 291)
                                .addComponent(cbb_nhaxuatban, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(51, 51, 51)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(cbo_NgonNgu, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbo_Makhuvuc, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(144, 144, 144)
                        .addComponent(btn_capnhat, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addComponent(txt_namxuatban, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54)
                                .addComponent(txt_lantainban, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(59, 59, 59)
                                .addComponent(txt_soluong, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_them, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(214, 214, 214))
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(cbb_theloai, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(51, 51, 51)
                                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(cbb_tacgia, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                                        .addComponent(txt_tensach, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(55, 55, 55)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_madausach, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(197, 197, 197)
                                .addComponent(btn_timkiem)
                                .addGap(18, 18, 18)
                                .addComponent(txt_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_timkiem)))
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_tensach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_madausach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(7, 7, 7)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbb_theloai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbb_tacgia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbb_nhaxuatban, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_NgonNgu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_Makhuvuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_namxuatban, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_lantainban, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_soluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_them)
                    .addComponent(btn_capnhat)
                    .addComponent(btn_xoa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1038, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(112, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbo_NgonNguActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_NgonNguActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbo_NgonNguActionPerformed

    private void txt_lantainbanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_lantainbanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_lantainbanActionPerformed

    private void txt_madausachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_madausachActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_madausachActionPerformed

    private void btn_capnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_capnhatActionPerformed
        updateSach();
    }//GEN-LAST:event_btn_capnhatActionPerformed

    private void btn_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaActionPerformed
        removeSach();
    }//GEN-LAST:event_btn_xoaActionPerformed

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
        addSach();
    }//GEN-LAST:event_btn_themActionPerformed

    private void btn_timkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_timkiemActionPerformed
        ////
    }//GEN-LAST:event_btn_timkiemActionPerformed

    private void tbl_sachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_sachMouseClicked
        clickSach();
    }//GEN-LAST:event_tbl_sachMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_capnhat;
    private javax.swing.JButton btn_them;
    private javax.swing.JButton btn_timkiem;
    private javax.swing.JButton btn_xoa;
    private javax.swing.JComboBox<String> cbb_nhaxuatban;
    private javax.swing.JComboBox<String> cbb_tacgia;
    private javax.swing.JComboBox<String> cbb_theloai;
    private javax.swing.JComboBox<String> cbo_Makhuvuc;
    private javax.swing.JComboBox<String> cbo_NgonNgu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private swing.RoundedPanel roundedPanel1;
    private javax.swing.JTable tbl_sach;
    private javax.swing.JTextField txt_lantainban;
    private javax.swing.JTextField txt_madausach;
    private javax.swing.JTextField txt_namxuatban;
    private javax.swing.JTextField txt_soluong;
    private javax.swing.JTextField txt_tensach;
    private javax.swing.JTextField txt_timkiem;
    // End of variables declaration//GEN-END:variables
}

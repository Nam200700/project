/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import DAO.ChiTietPhieuTraDAO;
import Entity.ChiTietPhieuTra;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import raven.drawer.TabbedForm;
import util.jdbchelper;
import java.sql.SQLException;

/**
 *
 * @author ACER
 */
public class QL_chitietphieutra extends TabbedForm {

    List<ChiTietPhieuTra> dsChiTietPhieuTras = new ArrayList<>();

    /**
     * Creates new form QL_chitietphieutra
     */
    public QL_chitietphieutra() {
        initComponents();
        loadTenSachID();
        loadMaPhieuTraID();
        soluong();
        fillTable();
        TinhTrangSach();
    }

    private void loadMaPhieuTraID() {
        String query = getSelectMaPhieuTraQuery(); // Gọi câu lệnh SELECT từ phương thức khác
        try {
            // Sử dụng jdbcHelper để thực thi truy vấn và trả về ResultSet
            ResultSet rs = jdbchelper.executeQuery(query); // Dùng ResultSet trực tiếp

            cbb_maphieutra.removeAllItems(); // Xóa tất cả các mục hiện có trong ComboBox

            // Duyệt qua kết quả trong ResultSet
            while (rs.next()) {
                String maPhieuTra = rs.getString("MaPhieuTra"); // Lấy mã phiếu trả từ ResultSet
                cbb_maphieutra.addItem(maPhieuTra); // Thêm maphieutra vào ComboBox
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách mã phiếu trả.");
        }
    }

    private String getSelectMaPhieuTraQuery() {
        return "SELECT MaPhieuTra FROM phieutra"; // Sửa câu lệnh này tùy thuộc vào cơ sở dữ liệu của bạn
    }

    private void loadTenSachID() {
        String query = getSelectMaSachCodeQuery(); // Gọi câu lệnh SELECT từ phương thức khác
        try {
            // Sử dụng jdbcHelper để thực thi truy vấn và trả về ResultSet
            ResultSet rs = jdbchelper.executeQuery(query); // Dùng ResultSet trực tiếp

            cbb_tensach.removeAllItems(); // Xóa tất cả các mục hiện có trong ComboBox

            // Duyệt qua kết quả trong ResultSet
            while (rs.next()) {
                String maSach = rs.getString("TenSach"); // Lấy mã sách từ ResultSet
                cbb_tensach.addItem(maSach); // Thêm masach vào ComboBox
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách tên sách");
        }
    }

    private String getSelectMaSachCodeQuery() {
        return "SELECT TenSach FROM chitietmuon"; // Sửa câu lệnh này tùy thuộc vào cơ sở dữ liệu của bạn
    }

    public void soluong() {
        // Mảng các phần tử
        String[] items = {"1"};

        // Xóa các mục hiện có trong ComboBox trước khi thêm mới
        cbb_soluong.removeAllItems();

        // Thêm các phần tử vào JComboBox từ mảng items
        for (String item : items) {
            cbb_soluong.addItem(item);
        }
    }

    public void TinhTrangSach() {
        // Mảng các phần tử
        String[] items = {"Tốt", "Hư", "Mất"};

        // Xóa các mục hiện có trong ComboBox trước khi thêm mới
        cbb_tinhtrangsach.removeAllItems();

        // Thêm các phần tử vào JComboBox từ mảng items
        for (String item : items) {
            cbb_tinhtrangsach.addItem(item);
        }
    }

    // truy vấn từ mã phiếu trả ra mã phiếu mượn và từ mã phiếu mượn truy vấn ra tên sách và số lượng
    // Truy vấn mã phiếu mượn từ mã phiếu trả và hiển thị thông tin sách
    private void hienThiThongTinSachmuon() {
        String maPhieuTra = (String) cbb_maphieutra.getSelectedItem(); // Lấy mã phiếu trả được chọn

        if (maPhieuTra == null) {
            return;
        }

        String maPhieuMuon = null;
        String query1 = "SELECT MaPhieuMuon FROM phieutra WHERE MaPhieuTra = ?"; // Lấy mã phiếu mượn từ mã phiếu trả

        // Truy vấn mã phiếu mượn
        try {
            ResultSet rs1 = jdbchelper.executeQuery(query1, maPhieuTra);
            if (rs1.next()) {
                maPhieuMuon = rs1.getString("MaPhieuMuon"); // Lấy mã phiếu mượn
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy mã phiếu mượn", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Dừng nếu có lỗi
        }

        if (maPhieuMuon != null) {
            // Xóa dữ liệu cũ
            cbb_tensach.removeAllItems();
//            cbb_soluong.removeAllItems();

            // Truy vấn danh sách tên sách từ mã phiếu mượn
            String query2 = "SELECT TenSach FROM chitietmuon WHERE MaPhieuMuon = ?";
            try {
                ResultSet rs2 = jdbchelper.executeQuery(query2, maPhieuMuon);
                while (rs2.next()) {
                    String tenSach = rs2.getString("TenSach");
//                    int soLuong = getSoLuongMua(tenSach); // Truy vấn số lượng từ tên sách

                    // Thêm tên sách và số lượng vào combobox
                    cbb_tensach.addItem(tenSach);
//                    cbb_soluong.addItem(String.valueOf(soLuong));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi lấy thông tin sách", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

// Truy vấn số lượng sách từ tên sách để đẩy lên combobox khi click vào cbb_tensach
    private int getSoLuongMuon(String tenSach) {
        int soLuong = 0;
        String query = "SELECT SoLuong FROM chitietmuon WHERE TenSach = ?";

        try {
            ResultSet rs = jdbchelper.executeQuery(query, tenSach);
            if (rs.next()) {
                soLuong = rs.getInt("SoLuong");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy số lượng sách", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return soLuong;
    }

    // hiện thị mã sách khi chọn vào combobox tên sách
    private void hienThiThongTin() {
        String tenSach = (String) cbb_tensach.getSelectedItem(); // Lấy tên sách từ combobox

        if (tenSach == null) {
            return;
        }

        // Truy vấn mã sách từ tên sách
        String queryMaSach = "SELECT MaSach FROM sach WHERE TenSach = ?";
        String maSach = null;

        try {
            ResultSet rs = jdbchelper.executeQuery(queryMaSach, tenSach); // Thực thi truy vấn
            if (rs.next()) {
                maSach = rs.getString("MaSach"); // Lấy mã sách
            }
        } catch (SQLException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi lấy mã sách");
            return;
        }

        if (maSach == null) {
            return;
        }

        // Truy vấn thông tin sách khác dựa trên mã sách
        String queryThongTinSach = "SELECT MaDauSach, NamXuatBan, LanTaiBan, NgonNgu, SoLuong FROM sach WHERE MaSach = ?";

        try {
            ResultSet rs = jdbchelper.executeQuery(queryThongTinSach, maSach); // Thực thi truy vấn
            if (rs.next()) {
                // Hiển thị thông tin
                lbl_masach.setText(maSach);
                lbl_madausach.setText(rs.getString("MaDauSach"));
                lbl_namxuatban.setText(rs.getString("NamXuatBan"));
                lbl_lantaiban.setText(rs.getString("LanTaiBan"));
                lbl_ngonngu.setText(rs.getString("NgonNgu"));
                lbl_soluong.setText(rs.getString("SoLuong"));
            } else {
                lbl_masach.setText("Không tìm thấy thông tin sách");
                lbl_madausach.setText("Không tìm thấy thông tin");
                lbl_namxuatban.setText("Không tìm thấy thông tin");
                lbl_lantaiban.setText("Không tìm thấy thông tin");
                lbl_ngonngu.setText("Không tìm thấy thông tin");
                lbl_soluong.setText("Không tìm thấy thông tin");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi lấy thông tin sách");
        }
    }

    public void addChiTietPhieuTra() {
        // Kiểm tra nếu combobox rỗng
        if (cbb_maphieutra.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Mã phiếu trả trống không thể thêm!!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (cbb_tensach.getSelectedItem() == null || cbb_soluong.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đầy đủ thông tin!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maPhieuTra = cbb_maphieutra.getSelectedItem().toString().trim();
        String tenSach = cbb_tensach.getSelectedItem().toString().trim();
        int soluong = Integer.parseInt(cbb_soluong.getSelectedItem().toString());
        String tinhTrangSach = cbb_tinhtrangsach.getSelectedItem().toString().trim();
        String maSach = lbl_masach.getText().trim();

        // Kiểm tra xem chi tiết phiếu trả đã tồn tại chưa (tránh trùng lặp)
        for (ChiTietPhieuTra ctpt : dsChiTietPhieuTras) {
            if (ctpt.getMaPhieuTra().equals(maPhieuTra) && ctpt.getTenSach().equals(tenSach)) {
                JOptionPane.showMessageDialog(this, "Chi tiết phiếu trả đã tồn tại!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        // Tạo đối tượng ChiTietPhieuTra và thêm vào danh sách
        ChiTietPhieuTra ctpt = new ChiTietPhieuTra(maPhieuTra, maSach, soluong, tinhTrangSach, tenSach);
        dsChiTietPhieuTras.add(ctpt);

        // Lưu dữ liệu vào cơ sở dữ liệu
        if (ChiTietPhieuTraDAO.insert(ctpt)) {
            // **Cập nhật số lượng sách trong kho (cộng lại)**
            ChiTietPhieuTraDAO.tangsoluong(tenSach, soluong);

            // Cập nhật giao diện
            fillTable();
            clean();
            JOptionPane.showMessageDialog(this, "Thêm chi tiết phiếu trả thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void removeChiTietPhieuTra() {
        // Lấy danh sách các dòng được chọn trong bảng
        int[] selectedRows = tbl_chitietphieutra.getSelectedRows();

        // Kiểm tra nếu không có dòng nào được chọn
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Chưa chọn sách nào để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Xác nhận trước khi xóa
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa sách đã chọn khỏi chi tiết phiếu trả?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            try {
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    int index = selectedRows[i];
                    String tenSach = (String) tbl_chitietphieutra.getValueAt(index, 1); // Lấy Mã Sách từ bảng
                    String maPhieu = (String) tbl_chitietphieutra.getValueAt(index, 0); // Lấy Mã Phiếu từ bảng

                    // Gọi phương thức xóa chi tiết phiếu trả từ DAO
                    boolean result = ChiTietPhieuTraDAO.delete(maPhieu, tenSach);

                    if (result) {
                        // Lấy thông tin số lượng sách đã trả từ bảng ChiTietPhieuTra
                        ChiTietPhieuTra ctpt = null;
                        for (ChiTietPhieuTra item : dsChiTietPhieuTras) {
                            if (item.getTenSach().equals(tenSach) && item.getMaPhieuTra().equals(maPhieu)) {
                                ctpt = item;
                                break;
                            }
                        }

                        if (ctpt != null) {
                            // Lấy số lượng sách đã trả
                            int soLuongTra = ctpt.getSoluong();

                            // Tiến hành giảm số lượng sách trong kho
                            boolean updateKhoSuccess = ChiTietPhieuTraDAO.giamsoluong(tenSach, soLuongTra);

                            if (!updateKhoSuccess) {
                                JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật số lượng sách trong kho!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }

                        // Xóa khỏi danh sách chi tiết phiếu trả
                        dsChiTietPhieuTras.removeIf(ct -> ct.getTenSach().equals(tenSach) && ct.getMaPhieuTra().equals(maPhieu));
                    } else {
                        JOptionPane.showMessageDialog(this, "Không thể xóa chi tiết phiếu trả cho sách mã " + tenSach, "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return; // Dừng vòng lặp nếu xóa không thành công
                    }
                }

                // Cập nhật lại bảng sau khi xóa
                fillTable();
                JOptionPane.showMessageDialog(this, "Xóa chi tiết phiếu trả thành công!");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa sách: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void fillTable() {
        List<ChiTietPhieuTra> ctpm = ChiTietPhieuTraDAO.getAll();
        dsChiTietPhieuTras.clear();
        dsChiTietPhieuTras.addAll(ctpm);

        DefaultTableModel model = (DefaultTableModel) tbl_chitietphieutra.getModel();
        model.setRowCount(0);

        for (ChiTietPhieuTra ct : dsChiTietPhieuTras) {
            Object[] row = new Object[]{ct.getMaPhieuTra(), ct.getTenSach(), ct.getSoluong(), ct.getTinhTrangSach()};
            model.addRow(row);
        }
    }

    public void loadRowIndexField(int newRowIndex) {
        String maPhieuTra = (String) tbl_chitietphieutra.getValueAt(newRowIndex, 0);
        String tenSach = (String) tbl_chitietphieutra.getValueAt(newRowIndex, 1);
        String soluong = (String) tbl_chitietphieutra.getValueAt(newRowIndex, 2);
        String tinhTrangSach = (String) tbl_chitietphieutra.getValueAt(newRowIndex, 3);
        cbb_maphieutra.setSelectedItem(maPhieuTra);
        cbb_tensach.setSelectedItem(tenSach);
        cbb_soluong.setSelectedItem(soluong);
        cbb_tinhtrangsach.setSelectedItem(tinhTrangSach);
    }

    public void clean() {
        cbb_maphieutra.setSelectedIndex(0);
        cbb_tensach.setSelectedIndex(0);
        cbb_soluong.setSelectedIndex(0);
        cbb_tinhtrangsach.setSelectedIndex(0);
    }

    public void clickChiTietPhieuTra() {
        // Kiểm tra nếu bảng không có dữ liệu
        if (tbl_chitietphieutra.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu trong bảng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy chỉ số dòng được chọn
        int row = tbl_chitietphieutra.getSelectedRow();

        // Kiểm tra xem có dòng nào được chọn không
        if (row != -1) {
            // Lấy dữ liệu từ bảng
            String maPhieuTra = tbl_chitietphieutra.getValueAt(row, 0).toString(); // Mã phiếu mượn
            String tenSach = tbl_chitietphieutra.getValueAt(row, 1).toString();      // Mã sách
            String soLuong = tbl_chitietphieutra.getValueAt(row, 2).toString();     // Số lượng
            String tinhtrangsach = tbl_chitietphieutra.getValueAt(row, 3).toString();     // Số lượng
            // Cập nhật vào combobox
            cbb_maphieutra.setSelectedItem(maPhieuTra);
            cbb_tensach.setSelectedItem(tenSach);
            cbb_soluong.setSelectedItem(soLuong);
            cbb_tinhtrangsach.setSelectedItem(tinhtrangsach);
        } else {
            JOptionPane.showMessageDialog(this, "Chưa chọn dòng nào!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
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
        tbl_chitietphieutra = new javax.swing.JTable();
        btn_xoachitiet = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        txt_timkiem = new javax.swing.JTextField();
        cbb_tensach = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbb_soluong = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        btn_taophieuchitiet = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cbb_maphieutra = new javax.swing.JComboBox<>();
        cbb_tinhtrangsach = new javax.swing.JComboBox<>();
        roundedPanel2 = new swing.RoundedPanel();
        lbl_masach = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbl_madausach = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbl_namxuatban = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lbl_lantaiban = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lbl_ngonngu = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lbl_soluong = new javax.swing.JLabel();

        setOpaque(false);

        tbl_chitietphieutra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã phiếu trả", "Tên sách", "Số lượng", "Tình trạng sách"
            }
        ));
        jScrollPane1.setViewportView(tbl_chitietphieutra);

        btn_xoachitiet.setText("xóa phiếu chi tiết");
        btn_xoachitiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoachitietActionPerformed(evt);
            }
        });

        jButton4.setText("Tìm kiếm");

        cbb_tensach.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbb_tensach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbb_tensachActionPerformed(evt);
            }
        });

        jLabel1.setText("Tên sách");

        jLabel2.setText("Số lượng sách");

        cbb_soluong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Tình trạng sách");

        btn_taophieuchitiet.setText("Tạo phiếu chi tiết");
        btn_taophieuchitiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_taophieuchitietActionPerformed(evt);
            }
        });

        jLabel4.setText("Mã phiếu trả");

        cbb_maphieutra.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbb_maphieutra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbb_maphieutraActionPerformed(evt);
            }
        });

        cbb_tinhtrangsach.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txt_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jButton4)
                .addGap(287, 287, 287))
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(8, 8, 8)
                                .addComponent(cbb_maphieutra, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(29, 29, 29)
                                .addComponent(cbb_tensach, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(41, 41, 41)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(cbb_soluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbb_tinhtrangsach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(93, 93, 93)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_taophieuchitiet)
                            .addComponent(btn_xoachitiet)))
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 757, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_taophieuchitiet)
                            .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(cbb_maphieutra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbb_tensach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1))
                            .addComponent(btn_xoachitiet)))
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cbb_soluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cbb_tinhtrangsach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addGap(23, 23, 23))
        );

        roundedPanel2.setPreferredSize(new java.awt.Dimension(160, 522));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Mã sách :");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Thông tin sách");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Mã đầu sách :");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Năm xuất bản :");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Lần tái bản :");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Ngôn ngữ :");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setText("Số lượng :");

        javax.swing.GroupLayout roundedPanel2Layout = new javax.swing.GroupLayout(roundedPanel2);
        roundedPanel2.setLayout(roundedPanel2Layout);
        roundedPanel2Layout.setHorizontalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15)
                    .addComponent(jLabel13)
                    .addComponent(jLabel11)
                    .addComponent(jLabel9)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(84, 84, 84))
                    .addComponent(lbl_masach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_madausach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_namxuatban, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_lantaiban, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_ngonngu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_soluong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        roundedPanel2Layout.setVerticalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel6)
                .addGap(35, 35, 35)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_masach, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_madausach, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_namxuatban, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_lantaiban, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_ngonngu, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_soluong, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(roundedPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(roundedPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
                    .addComponent(roundedPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(61, 61, 61))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_taophieuchitietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_taophieuchitietActionPerformed
        addChiTietPhieuTra();
    }//GEN-LAST:event_btn_taophieuchitietActionPerformed

    private void btn_xoachitietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoachitietActionPerformed
        removeChiTietPhieuTra();
    }//GEN-LAST:event_btn_xoachitietActionPerformed

    private void cbb_tensachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbb_tensachActionPerformed

        // Kiểm tra nếu không có tên sách nào được chọn
        String tenSach = (String) cbb_tensach.getSelectedItem();
        if (tenSach == null) {
            return; // Nếu không có tên sách thì không làm gì
        }

        // Gọi phương thức getSoLuongMua để lấy số lượng của sách được chọn
        int soLuong = getSoLuongMuon(tenSach);

        // Hiển thị số lượng lên combobox hoặc label
        cbb_soluong.removeAllItems(); // Xóa các mục cũ trong combobox số lượng
        cbb_soluong.addItem(String.valueOf(soLuong)); // Thêm số lượng vào combobox số lượng
        // hiển thị thông tin sách từ tên sách
        hienThiThongTin();
    }//GEN-LAST:event_cbb_tensachActionPerformed

    private void cbb_maphieutraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbb_maphieutraActionPerformed
        hienThiThongTinSachmuon();
    }//GEN-LAST:event_cbb_maphieutraActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_taophieuchitiet;
    private javax.swing.JButton btn_xoachitiet;
    private javax.swing.JComboBox<String> cbb_maphieutra;
    private javax.swing.JComboBox<String> cbb_soluong;
    private javax.swing.JComboBox<String> cbb_tensach;
    private javax.swing.JComboBox<String> cbb_tinhtrangsach;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_lantaiban;
    private javax.swing.JLabel lbl_madausach;
    private javax.swing.JLabel lbl_masach;
    private javax.swing.JLabel lbl_namxuatban;
    private javax.swing.JLabel lbl_ngonngu;
    private javax.swing.JLabel lbl_soluong;
    private swing.RoundedPanel roundedPanel1;
    private swing.RoundedPanel roundedPanel2;
    private javax.swing.JTable tbl_chitietphieutra;
    private javax.swing.JTextField txt_timkiem;
    // End of variables declaration//GEN-END:variables
}

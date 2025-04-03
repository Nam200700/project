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
        loadMaSachID();
        loadMaPhieuTraID();
        soluong();
        fillTable();
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

    private void loadMaSachID() {
        String query = getSelectMaSachCodeQuery(); // Gọi câu lệnh SELECT từ phương thức khác
        try {
            // Sử dụng jdbcHelper để thực thi truy vấn và trả về ResultSet
            ResultSet rs = jdbchelper.executeQuery(query); // Dùng ResultSet trực tiếp

            cbb_masach.removeAllItems(); // Xóa tất cả các mục hiện có trong ComboBox

            // Duyệt qua kết quả trong ResultSet
            while (rs.next()) {
                String maSach = rs.getString("MaSach"); // Lấy mã sách từ ResultSet
                cbb_masach.addItem(maSach); // Thêm masach vào ComboBox
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách mã sách");
        }
    }

    private String getSelectMaSachCodeQuery() {
        return "SELECT MaSach FROM chitietmuon"; // Sửa câu lệnh này tùy thuộc vào cơ sở dữ liệu của bạn
    }

    public void soluong() {
        // Mảng các phần tử
        String[] items = {"1", "2", "3"};

        // Xóa các mục hiện có trong ComboBox trước khi thêm mới
        cbb_soluong.removeAllItems();

        // Thêm các phần tử vào JComboBox từ mảng items
        for (String item : items) {
            cbb_soluong.addItem(item);
        }
    }

    private void hienThiThongTinSach() {
        String maPhieuTra = (String) cbb_maphieutra.getSelectedItem(); // Lấy mã phiếu trả được chọn

        if (maPhieuTra == null) {
            return;
        }

        String query1 = "SELECT MaPhieuMuon FROM phieutra WHERE MaPhieuTra = ?"; // Lấy mã phiếu mượn

        try {
            ResultSet rs1 = jdbchelper.executeQuery(query1, maPhieuTra);
            if (rs1.next()) {
                String maPhieuMuon = rs1.getString("MaPhieuMuon"); // Lấy mã phiếu mượn

                // Lấy danh sách mã sách và số lượng từ mã phiếu mượn
                String query2 = "SELECT MaSach, SoLuong FROM chitietmuon WHERE MaPhieuMuon = ?";
                ResultSet rs2 = jdbchelper.executeQuery(query2, maPhieuMuon);

                // Xóa dữ liệu cũ
                cbb_masach.removeAllItems();
                cbb_soluong.removeAllItems();

                while (rs2.next()) {
                    String maSach = rs2.getString("MaSach");
                    int soLuong = rs2.getInt("SoLuong");

                    cbb_masach.addItem(maSach); // Thêm mã sách vào combobox
                    cbb_soluong.addItem(String.valueOf(soLuong)); // Thêm số lượng vào combobox
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy thông tin sách", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // hiện thị tên sách khi chọn vào combobox mã sách
    private void hienThiTenSach() {
        String maSach = (String) cbb_masach.getSelectedItem(); // Lấy mã sách được chọn

        if (maSach == null) {
            lbl_tensach.setText(" ");
            return;
        }

        String query = "SELECT TenSach FROM sach WHERE MaSach = ?"; // Câu lệnh truy vấn

        try {
            ResultSet rs = jdbchelper.executeQuery(query, maSach); // Thực thi truy vấn

            if (rs.next()) {
                lbl_tensach.setText(rs.getString("TenSach")); // Hiển thị tên sách
            } else {
                lbl_tensach.setText("Không tìm thấy tên sách");
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi lấy tên sách");
        }
    }

    // Hiển thị mã đầu sách khi chọn vào combobox mã sách
    private void hienThiMaDauSach() {
        String maSach = (String) cbb_masach.getSelectedItem(); // Lấy mã sách được chọn

        if (maSach == null) {
            lbl_madausach.setText(" ");
            return;
        }

        String query = "SELECT MaDauSach FROM sach WHERE MaSach = ?"; // Câu lệnh truy vấn

        try {
            ResultSet rs = jdbchelper.executeQuery(query, maSach); // Thực thi truy vấn

            if (rs.next()) {
                lbl_madausach.setText(rs.getString("MaDauSach")); // Hiển thị mã đầu sách
            } else {
                lbl_madausach.setText("Không tìm thấy mã đầu sách");
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi lấy mã đầu sách");
        }
    }

    // Hiển thị năm xuất bản khi chọn vào combobox mã sách
    private void hienThiNamXuatBan() {
        String maSach = (String) cbb_masach.getSelectedItem(); // Lấy mã sách được chọn

        if (maSach == null) {
            lbl_namxuatban.setText(" ");
            return;
        }

        String query = "SELECT NamXuatBan FROM sach WHERE MaSach = ?"; // Câu lệnh truy vấn

        try {
            ResultSet rs = jdbchelper.executeQuery(query, maSach); // Thực thi truy vấn

            if (rs.next()) {
                lbl_namxuatban.setText(rs.getString("NamXuatBan")); // Hiển thị năm xuất bản
            } else {
                lbl_namxuatban.setText("Không tìm thấy năm xuất bản");
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi lấy năm xuất bản");
        }
    }

    // Hiển thị lần tái bản khi chọn vào combobox mã sách
    private void hienThiLanTaiBan() {
        String maSach = (String) cbb_masach.getSelectedItem(); // Lấy mã sách được chọn

        if (maSach == null) {
            lbl_lantaiban.setText(" ");
            return;
        }

        String query = "SELECT LanTaiBan FROM sach WHERE MaSach = ?"; // Câu lệnh truy vấn

        try {
            ResultSet rs = jdbchelper.executeQuery(query, maSach); // Thực thi truy vấn

            if (rs.next()) {
                lbl_lantaiban.setText(rs.getString("LanTaiBan")); // Hiển thị lần tái bản
            } else {
                lbl_lantaiban.setText("Không tìm thấy lần tái bản");
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi lấy lần tái bản");
        }
    }

    // Hiển thị ngôn ngữ khi chọn vào combobox mã sách
    private void hienThiNgonNguSach() {
        String maSach = (String) cbb_masach.getSelectedItem(); // Lấy mã sách được chọn

        if (maSach == null) {
            lbl_ngonngu.setText(" ");
            return;
        }

        String query = "SELECT NgonNgu FROM sach WHERE MaSach = ?"; // Câu lệnh truy vấn

        try {
            ResultSet rs = jdbchelper.executeQuery(query, maSach); // Thực thi truy vấn

            if (rs.next()) {
                lbl_ngonngu.setText(rs.getString("NgonNgu")); // Hiển thị ngôn ngữ
            } else {
                lbl_ngonngu.setText("Không tìm thấy ngôn ngữ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi lấy ngôn ngữ");
        }
    }

    // Hiển thị số lượng khi chọn vào combobox mã sách
    private void hienThiSoLuongSach() {
        String maSach = (String) cbb_masach.getSelectedItem(); // Lấy mã sách được chọn

        if (maSach == null) {
            lbl_soluong.setText(" ");
            return;
        }

        String query = "SELECT SoLuong FROM sach WHERE MaSach = ?"; // Câu lệnh truy vấn

        try {
            ResultSet rs = jdbchelper.executeQuery(query, maSach); // Thực thi truy vấn

            if (rs.next()) {
                lbl_soluong.setText(rs.getString("SoLuong")); // Hiển thị số lượng
            } else {
                lbl_soluong.setText("Không tìm thấy số lượng");
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi lấy số lượng");
        }
    }

    public void addChiTietPhieuTra() {
        // Kiểm tra nếu combobox rỗng
        if (cbb_maphieutra.getSelectedItem() == null || cbb_masach.getSelectedItem() == null || cbb_soluong.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đầy đủ thông tin!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maPhieuTra = cbb_maphieutra.getSelectedItem().toString().trim();
        String maSach = cbb_masach.getSelectedItem().toString().trim();
        int soluong = Integer.parseInt(cbb_soluong.getSelectedItem().toString());
        String tinhTrangSach = txt_tinhtrangsach.getText().trim();

//        // Kiểm tra tổng số lượng sách đã trả của mã phiếu này (tối đa 3)
//        int tongSoLuongHienTai = ChiTietPhieuTraDAO.tinhTongSoLuong(maPhieuTra);
//        System.out.println("Tổng số lượng hiện tại của phiếu " + maPhieuTra + ": " + tongSoLuongHienTai);
//        int tong = tongSoLuongHienTai + soluong;
//        System.out.println("Tổng số lượng hiện tại của phiếu " + maPhieuTra + ": " + tong);
//        if (tong > 3) {
//            JOptionPane.showMessageDialog(this, "Mỗi phiếu trả chỉ được trả tối đa 3 sách!", "Lỗi", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
        // Kiểm tra xem chi tiết phiếu trả đã tồn tại chưa (tránh trùng lặp)
        for (ChiTietPhieuTra ctpt : dsChiTietPhieuTras) {
            if (ctpt.getMaPhieuTra().equals(maPhieuTra) && ctpt.getMaSach().equals(maSach)) {
                JOptionPane.showMessageDialog(this, "Chi tiết phiếu trả đã tồn tại!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        // Tạo đối tượng ChiTietPhieuTra và thêm vào danh sách
        ChiTietPhieuTra ctpt = new ChiTietPhieuTra(maPhieuTra, maSach, soluong, tinhTrangSach);
        dsChiTietPhieuTras.add(ctpt);

        // Lưu dữ liệu vào cơ sở dữ liệu
        if (ChiTietPhieuTraDAO.insert(ctpt)) {
            // **Cập nhật số lượng sách trong kho (cộng lại)**
            ChiTietPhieuTraDAO.tangsoluong(maSach, soluong);

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
                    String maSach = (String) tbl_chitietphieutra.getValueAt(index, 1); // Lấy Mã Sách từ bảng
                    String maPhieu = (String) tbl_chitietphieutra.getValueAt(index, 0); // Lấy Mã Phiếu từ bảng

                    // Gọi phương thức xóa chi tiết phiếu trả từ DAO
                    boolean result = ChiTietPhieuTraDAO.delete(maPhieu, maSach);

                    if (result) {
                        // Lấy thông tin số lượng sách đã trả từ bảng ChiTietPhieuTra
                        ChiTietPhieuTra ctpt = null;
                        for (ChiTietPhieuTra item : dsChiTietPhieuTras) {
                            if (item.getMaSach().equals(maSach) && item.getMaPhieuTra().equals(maPhieu)) {
                                ctpt = item;
                                break;
                            }
                        }

                        if (ctpt != null) {
                            // Lấy số lượng sách đã trả
                            int soLuongTra = ctpt.getSoluong();

                            // Tiến hành giảm số lượng sách trong kho
                            boolean updateKhoSuccess = ChiTietPhieuTraDAO.giamsoluong(maSach, soLuongTra);

                            if (!updateKhoSuccess) {
                                JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật số lượng sách trong kho!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }

                        // Xóa khỏi danh sách chi tiết phiếu trả
                        dsChiTietPhieuTras.removeIf(ct -> ct.getMaSach().equals(maSach) && ct.getMaPhieuTra().equals(maPhieu));
                    } else {
                        JOptionPane.showMessageDialog(this, "Không thể xóa chi tiết phiếu trả cho sách mã " + maSach, "Lỗi", JOptionPane.ERROR_MESSAGE);
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
            Object[] row = new Object[]{ct.getMaPhieuTra(), ct.getMaSach(), ct.getSoluong(), ct.getTinhTrangSach()};
            model.addRow(row);
        }
    }

    public void loadRowIndexField(int newRowIndex) {
        String maPhieuTra = (String) tbl_chitietphieutra.getValueAt(newRowIndex, 0);
        String maSach = (String) tbl_chitietphieutra.getValueAt(newRowIndex, 1);
        String soluong = (String) tbl_chitietphieutra.getValueAt(newRowIndex, 3);
        String tinhTrangSach = (String) tbl_chitietphieutra.getValueAt(newRowIndex, 4);
        cbb_maphieutra.setSelectedItem(maPhieuTra);
        cbb_masach.setSelectedItem(maSach);
        cbb_soluong.setSelectedItem(soluong);
        txt_tinhtrangsach.setText(tinhTrangSach);
    }

    public void clean() {
        cbb_maphieutra.setSelectedIndex(0);
        cbb_masach.setSelectedIndex(0);
        cbb_soluong.setSelectedIndex(0);
        txt_tinhtrangsach.setText("");
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
            String maSach = tbl_chitietphieutra.getValueAt(row, 1).toString();      // Mã sách
            String soLuong = tbl_chitietphieutra.getValueAt(row, 2).toString();     // Số lượng
            String tinhtrangsach = tbl_chitietphieutra.getValueAt(row, 3).toString();     // Số lượng
            // Cập nhật vào combobox
            cbb_maphieutra.setSelectedItem(maPhieuTra);
            cbb_masach.setSelectedItem(maSach);
            cbb_soluong.setSelectedItem(soLuong);
            txt_tinhtrangsach.setText(tinhtrangsach);
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
        cbb_masach = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbb_soluong = new javax.swing.JComboBox<>();
        txt_tinhtrangsach = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btn_taophieuchitiet = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cbb_maphieutra = new javax.swing.JComboBox<>();
        roundedPanel2 = new swing.RoundedPanel();
        lbl_tensach = new javax.swing.JLabel();
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
                "Mã phiếu trả", "Mã sách", "Số lượng", "Tình trạng sách"
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

        cbb_masach.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbb_masach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbb_masachActionPerformed(evt);
            }
        });

        jLabel1.setText("Mã sách");

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
                        .addGap(106, 106, 106)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(8, 8, 8)
                                .addComponent(cbb_maphieutra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbb_masach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(62, 62, 62)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(cbb_soluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(txt_tinhtrangsach, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(108, 108, 108)
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
                    .addComponent(btn_taophieuchitiet)
                    .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(cbb_soluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(cbb_maphieutra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbb_masach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)
                        .addComponent(txt_tinhtrangsach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addComponent(btn_xoachitiet))
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
        jLabel5.setText("Tên sách :");

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
                    .addComponent(lbl_tensach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_madausach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_namxuatban, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_lantaiban, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_ngonngu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_soluong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        roundedPanel2Layout.setVerticalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel6)
                .addGap(35, 35, 35)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_tensach, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
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

    private void cbb_masachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbb_masachActionPerformed
        hienThiTenSach();
        hienThiMaDauSach();
        hienThiNamXuatBan();
        hienThiLanTaiBan();
        hienThiNgonNguSach();
        hienThiSoLuongSach();
    }//GEN-LAST:event_cbb_masachActionPerformed

    private void cbb_maphieutraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbb_maphieutraActionPerformed
        hienThiThongTinSach();
    }//GEN-LAST:event_cbb_maphieutraActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_taophieuchitiet;
    private javax.swing.JButton btn_xoachitiet;
    private javax.swing.JComboBox<String> cbb_maphieutra;
    private javax.swing.JComboBox<String> cbb_masach;
    private javax.swing.JComboBox<String> cbb_soluong;
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
    private javax.swing.JLabel lbl_namxuatban;
    private javax.swing.JLabel lbl_ngonngu;
    private javax.swing.JLabel lbl_soluong;
    private javax.swing.JLabel lbl_tensach;
    private swing.RoundedPanel roundedPanel1;
    private swing.RoundedPanel roundedPanel2;
    private javax.swing.JTable tbl_chitietphieutra;
    private javax.swing.JTextField txt_timkiem;
    private javax.swing.JTextField txt_tinhtrangsach;
    // End of variables declaration//GEN-END:variables
}

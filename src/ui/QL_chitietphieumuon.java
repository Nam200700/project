/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import DAO.ChiTietPhieuMuonDAO;
import Entity.ChiTietPhieuMuon;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import raven.drawer.TabbedForm;
import util.jdbchelper;

/**
 *
 * @author ACER
 */
public class QL_chitietphieumuon extends TabbedForm {

    List<ChiTietPhieuMuon> dsChiTietPhieuMuon = new ArrayList<>();

    /**
     * Creates new form QL_chitietphieumuon
     */
    public QL_chitietphieumuon() {
        initComponents();
        loadPhieuMuonID();
        loadMaSachID();
        soluong();
        fillTable();
    }

    private void loadPhieuMuonID() {
        String query = getSelectPhieuMuonCodeQuery(); // Gọi câu lệnh SELECT từ phương thức khác
        try {
            // Sử dụng jdbcHelper để thực thi truy vấn và trả về ResultSet
            ResultSet rs = jdbchelper.executeQuery(query); // Dùng ResultSet trực tiếp

            cbb_maphieumuon.removeAllItems(); // Xóa tất cả các mục hiện có trong ComboBox

            // Duyệt qua kết quả trong ResultSet
            while (rs.next()) {
                String maPhieuMuon = rs.getString("MaPhieuMuon"); // Lấy mã phiếu mượn từ ResultSet
                cbb_maphieumuon.addItem(maPhieuMuon); // Thêm maphieumuon vào ComboBox
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách sinh viên.");
        }
    }

    private String getSelectPhieuMuonCodeQuery() {
        return "SELECT MaPhieuMuon FROM phieumuon"; // Sửa câu lệnh này tùy thuộc vào cơ sở dữ liệu của bạn
    }

    private void loadMaSachID() {
        String query = getSelectMaSachCodeQuery(); // Gọi câu lệnh SELECT từ phương thức khác
        try {
            // Sử dụng jdbcHelper để thực thi truy vấn và trả về ResultSet
            ResultSet rs = jdbchelper.executeQuery(query); // Dùng ResultSet trực tiếp

            cbb_masach.removeAllItems(); // Xóa tất cả các mục hiện có trong ComboBox

            // Duyệt qua kết quả trong ResultSet
            while (rs.next()) {
                String maSach = rs.getString("MaSach"); // Lấy mã phiếu mượn từ ResultSet
                cbb_masach.addItem(maSach); // Thêm maphieumuon vào ComboBox
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách sinh viên.");
        }
    }

    private String getSelectMaSachCodeQuery() {
        return "SELECT MaSach FROM sach"; // Sửa câu lệnh này tùy thuộc vào cơ sở dữ liệu của bạn
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

    // hiển thị tên sách khi chọn vào combobox mã sách
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

    // nút thêm chi tiết mượn sách
    public void addChiTietPhieuMuon() {
        // Kiểm tra nếu combobox rỗng
        if (cbb_maphieumuon.getSelectedItem() == null || cbb_masach.getSelectedItem() == null || cbb_soluong.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đầy đủ thông tin!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maPhieuMuon = cbb_maphieumuon.getSelectedItem().toString().trim();
        String maSach = cbb_masach.getSelectedItem().toString().trim();
        int soluong = Integer.parseInt(cbb_soluong.getSelectedItem().toString());

        // Kiểm tra tổng số lượng sách đã mượn của mã phiếu này
        int tongSoLuongHienTai = ChiTietPhieuMuonDAO.tinhTongSoLuong(maPhieuMuon);
        if (tongSoLuongHienTai + soluong > 3) {
            JOptionPane.showMessageDialog(this, "Mỗi phiếu mượn chỉ được mượn tối đa 3 sách!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Kiểm tra xem chi tiết phiếu mượn đã tồn tại chưa (tránh trùng lặp)
        for (ChiTietPhieuMuon ctpm : dsChiTietPhieuMuon) {
            if (ctpm.getMaphieuMuon().equals(maPhieuMuon) && ctpm.getMaSach().equals(maSach)) {
                JOptionPane.showMessageDialog(this, "Chi tiết phiếu mượn đã tồn tại!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        // Kiểm tra số lượng sách trong kho có đủ không
        int soLuongConLai = ChiTietPhieuMuonDAO.checkSoLuongSach(maSach);
        if (soluong > soLuongConLai) {
            JOptionPane.showMessageDialog(this, "Số lượng sách trong kho không đủ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Tạo đối tượng ChiTietPhieuMuon và thêm vào danh sách
        ChiTietPhieuMuon ctpm = new ChiTietPhieuMuon(maPhieuMuon, maSach, soluong);
        dsChiTietPhieuMuon.add(ctpm);

        // Lưu dữ liệu vào cơ sở dữ liệu
        if (ChiTietPhieuMuonDAO.insert(ctpm)) {
            // Cập nhật lại số lượng sách trong bảng Sach
            ChiTietPhieuMuonDAO.giamsoluong(maSach, soluong);

            // Cập nhật giao diện
            fillTable();
            clean();
            JOptionPane.showMessageDialog(this, "Thêm chi tiết phiếu mượn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void removeChiTietPhieuMuon() {
        int[] selectedRows = tbl_chitietphieumuon.getSelectedRows();

        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Chưa chọn sách nào để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa sách đã chọn khỏi chi tiết phiếu mượn?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            try {
                Map<String, Integer> sachCanXoa = new HashMap<>(); // Lưu tổng số lượng theo từng Mã Phiếu Mượn + Mã Sách

                // Lấy tổng số lượng sách cần xóa theo từng phiếu mượn
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    int index = selectedRows[i];
                    String maPhieuMuon = (String) tbl_chitietphieumuon.getValueAt(index, 0); // Lấy Mã Phiếu Mượn
                    String maSach = (String) tbl_chitietphieumuon.getValueAt(index, 1); // Lấy Mã Sách
                    int soLuongMuon = (int) tbl_chitietphieumuon.getValueAt(index, 2); // Lấy số lượng sách mượn

                    // Ghép mã phiếu mượn + mã sách để đảm bảo không xóa sai dữ liệu
                    String key = maPhieuMuon + "-" + maSach;
                    sachCanXoa.put(key, sachCanXoa.getOrDefault(key, 0) + soLuongMuon);
                }

                // Tiến hành xóa từng mục theo từng phiếu mượn
                for (Map.Entry<String, Integer> entry : sachCanXoa.entrySet()) {
                    String key = entry.getKey();
                    int tongSoLuongXoa = entry.getValue();

                    // Tách lại Mã Phiếu Mượn và Mã Sách từ key
                    String[] parts = key.split("-");
                    String maPhieuMuon = parts[0];
                    String maSach = parts[1];

                    // Xóa trong database theo Mã Phiếu Mượn + Mã Sách
                    ChiTietPhieuMuonDAO.delete(maPhieuMuon, maSach);

                    // Cập nhật lại số lượng trong kho sách
                    ChiTietPhieuMuonDAO.tangsoluong(maSach, tongSoLuongXoa);

                    // Xóa trong danh sách tạm
                    dsChiTietPhieuMuon.removeIf(ctpm -> ctpm.getMaphieuMuon().equals(maPhieuMuon) && ctpm.getMaSach().equals(maSach));
                }

                fillTable();
                JOptionPane.showMessageDialog(this, "Xóa chi tiết phiếu mượn thành công!");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa sách: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void fillTable() {
        List<ChiTietPhieuMuon> ctpm = ChiTietPhieuMuonDAO.getAll();
        dsChiTietPhieuMuon.clear();
        dsChiTietPhieuMuon.addAll(ctpm);

        DefaultTableModel model = (DefaultTableModel) tbl_chitietphieumuon.getModel();
        model.setRowCount(0);

        for (ChiTietPhieuMuon ct : dsChiTietPhieuMuon) {
            Object[] row = new Object[]{ct.getMaphieuMuon(), ct.getMaSach(), ct.getSoLuong()};
            model.addRow(row);
        }
    }

    public void loadRowIndexField(int newRowIndex) {
        String maPhieuMuon = (String) tbl_chitietphieumuon.getValueAt(newRowIndex, 0);
        String maSach = (String) tbl_chitietphieumuon.getValueAt(newRowIndex, 1);
        String soluong = (String) tbl_chitietphieumuon.getValueAt(newRowIndex, 3);
        cbb_maphieumuon.setSelectedItem(maPhieuMuon);
        cbb_masach.setSelectedItem(maSach);
        cbb_soluong.setSelectedItem(soluong);
    }

    public void clean() {
        cbb_maphieumuon.setSelectedIndex(0);
        cbb_masach.setSelectedIndex(0);
        cbb_soluong.setSelectedIndex(0);
    }

    public void clickChiTietPhieuMuon() {
        // Kiểm tra nếu bảng không có dữ liệu
        if (tbl_chitietphieumuon.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu trong bảng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy chỉ số dòng được chọn
        int row = tbl_chitietphieumuon.getSelectedRow();

        // Kiểm tra xem có dòng nào được chọn không
        if (row != -1) {
            // Lấy dữ liệu từ bảng
            String maPhieuMuon = tbl_chitietphieumuon.getValueAt(row, 0).toString(); // Mã phiếu mượn
            String maSach = tbl_chitietphieumuon.getValueAt(row, 1).toString();      // Mã sách
            String soLuong = tbl_chitietphieumuon.getValueAt(row, 2).toString();     // Số lượng

            // Cập nhật vào combobox
            cbb_maphieumuon.setSelectedItem(maPhieuMuon);
            cbb_masach.setSelectedItem(maSach);
            cbb_soluong.setSelectedItem(soLuong);
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
        tbl_chitietphieumuon = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        cbb_maphieumuon = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cbb_masach = new javax.swing.JComboBox<>();
        cbb_soluong = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        btn_taophieuchitiet = new javax.swing.JButton();
        btn_xoaphieuchitiet = new javax.swing.JButton();
        roundedPanel2 = new swing.RoundedPanel();
        jLabel4 = new javax.swing.JLabel();
        lbl_tensach = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbl_madausach = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lbl_namxuatban = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lbl_lantaiban = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lbl_ngonngu = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lbl_soluong = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();

        setOpaque(false);

        tbl_chitietphieumuon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã phiếu mượn", "Mã sách", "Số lượng"
            }
        ));
        jScrollPane1.setViewportView(tbl_chitietphieumuon);

        jLabel1.setText("Mã phiếu mượn");

        cbb_maphieumuon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Mã sách");

        cbb_masach.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbb_masach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbb_masachActionPerformed(evt);
            }
        });

        cbb_soluong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Số lượng");

        btn_taophieuchitiet.setText("Tạo chi tiết phiếu");
        btn_taophieuchitiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_taophieuchitietActionPerformed(evt);
            }
        });

        btn_xoaphieuchitiet.setText("Xóa phiếu chi tiết");
        btn_xoaphieuchitiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoaphieuchitietActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbb_masach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(cbb_maphieumuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(65, 65, 65)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbb_soluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_xoaphieuchitiet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_taophieuchitiet, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 743, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbb_maphieumuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbb_soluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(btn_taophieuchitiet))
                .addGap(18, 18, 18)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(cbb_masach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_xoaphieuchitiet))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Tên sách :");

        lbl_tensach.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lbl_tensachAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Mã đầu sách :");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Năm xuất bản :");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Lần tái bản :");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Ngôn ngữ :");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Số lượng :");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel16.setText("Thông tin sách");

        javax.swing.GroupLayout roundedPanel2Layout = new javax.swing.GroupLayout(roundedPanel2);
        roundedPanel2.setLayout(roundedPanel2Layout);
        roundedPanel2Layout.setHorizontalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel2Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_soluong, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lbl_ngonngu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_namxuatban, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_madausach, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_tensach, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel2Layout.createSequentialGroup()
                            .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel12)
                                .addComponent(jLabel10)
                                .addComponent(jLabel8)
                                .addComponent(jLabel6)
                                .addComponent(jLabel4)
                                .addGroup(roundedPanel2Layout.createSequentialGroup()
                                    .addGap(1, 1, 1)
                                    .addComponent(jLabel14))
                                .addComponent(jLabel16))
                            .addGap(15, 15, 15))
                        .addComponent(lbl_lantaiban, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        roundedPanel2Layout.setVerticalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel16)
                .addGap(40, 40, 40)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_tensach, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_madausach, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_namxuatban, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_lantaiban, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_ngonngu, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_soluong, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(92, Short.MAX_VALUE))
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
                .addContainerGap(42, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(roundedPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_xoaphieuchitietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaphieuchitietActionPerformed
        removeChiTietPhieuMuon();
    }//GEN-LAST:event_btn_xoaphieuchitietActionPerformed

    private void btn_taophieuchitietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_taophieuchitietActionPerformed
        addChiTietPhieuMuon();
    }//GEN-LAST:event_btn_taophieuchitietActionPerformed

    private void lbl_tensachAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lbl_tensachAncestorAdded

    }//GEN-LAST:event_lbl_tensachAncestorAdded

    private void cbb_masachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbb_masachActionPerformed
        hienThiTenSach();
        hienThiMaDauSach();
        hienThiNamXuatBan();
        hienThiLanTaiBan();
        hienThiNgonNguSach();
        hienThiSoLuongSach();
    }//GEN-LAST:event_cbb_masachActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_taophieuchitiet;
    private javax.swing.JButton btn_xoaphieuchitiet;
    private javax.swing.JComboBox<String> cbb_maphieumuon;
    private javax.swing.JComboBox<String> cbb_masach;
    private javax.swing.JComboBox<String> cbb_soluong;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_lantaiban;
    private javax.swing.JLabel lbl_madausach;
    private javax.swing.JLabel lbl_namxuatban;
    private javax.swing.JLabel lbl_ngonngu;
    private javax.swing.JLabel lbl_soluong;
    private javax.swing.JLabel lbl_tensach;
    private swing.RoundedPanel roundedPanel1;
    private swing.RoundedPanel roundedPanel2;
    private javax.swing.JTable tbl_chitietphieumuon;
    // End of variables declaration//GEN-END:variables
}

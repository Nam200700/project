/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import DAO.PhieuMuonDAO;
import DAO.PhieuTraDAO;
import Entity.PhieuMuon;
import Entity.PhieuTra;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import raven.drawer.TabbedForm;
import swing.RoundTablemuonsach;
import swing.RoundTabletrasach;
import util.jdbchelper;

/**
 *
 * @author ACER
 */
public class QL_Muon extends TabbedForm {

    List<PhieuMuon> dsPhieuMuon = new ArrayList<>();

    /**
     * Creates new form QL_Muon
     */
    public QL_Muon() {
        initComponents();
        fillTable();
        guimuonsach();
        loadDocGiaID();
    }

    private void loadDocGiaID() {
        String query = getSelectDocGiaCodeQuery(); // Gọi câu lệnh SELECT từ phương thức khác
        try {
            // Sử dụng jdbcHelper để thực thi truy vấn và trả về ResultSet
            ResultSet rs = jdbchelper.executeQuery(query); // Dùng ResultSet trực tiếp

            cbb_madocgia.removeAllItems(); // Xóa tất cả các mục hiện có trong ComboBox

            // Duyệt qua kết quả trong ResultSet
            while (rs.next()) {
                String maDocGia = rs.getString("MaDocGia"); // Lấy mã phiếu mượn từ ResultSet
                cbb_madocgia.addItem(maDocGia); // Thêm maphieumuon vào ComboBox
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách độc giả.");
        }
    }

    private String getSelectDocGiaCodeQuery() {
        return "SELECT MaDocGia FROM docgia"; // Sửa câu lệnh này tùy thuộc vào cơ sở dữ liệu của bạn
    }

    public void guimuonsach() {
        // Áp dụng FlatLaf
        FlatLightLaf.setup();
        UIManager.put("Component.arc", 20); // Bo góc toàn bộ UI

        // Kiểm tra nếu `tbltheloai` chưa khởi tạo
        if (tbl_phieumuon == null) {
            DefaultTableModel model = new DefaultTableModel(
                    new Object[][]{},
                    new String[]{"", ""}
            );
            tbl_phieumuon = new RoundTablemuonsach(model);
        } else {
            DefaultTableModel model = (DefaultTableModel) tbl_phieumuon.getModel();
            tbl_phieumuon = new RoundTablemuonsach(model); // Tạo lại với model cũ
        }

        tbl_phieumuon.setPreferredScrollableViewportSize(new Dimension(500, 200)); // Kích thước mặc định

        // Đảm bảo jScrollPane1 đang chứa `tbltacgia`
        jScrollPane1.setViewportView(tbl_phieumuon); // Cập nhật bảng vào ScrollPane
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder()); // Xóa viền
        jScrollPane1.getViewport().setOpaque(false); // Nền trong suốt

        // Cập nhật lại JButton với bo góc
        btn_taophieumuon.setIcon(new ImageIcon(getClass().getResource("/image/add.png")));
        btn_chinhsuaphieu.setIcon(new ImageIcon(getClass().getResource("/image/edit.png")));
        btn_xoaphieumuon.setIcon(new ImageIcon(getClass().getResource("/image/delete.png")));

        // Refresh UI
        SwingUtilities.updateComponentTreeUI(jScrollPane1);
    }

    public void addtaophieu() {
        // Kiểm tra các trường nhập liệu
        String maDocgia = cbb_madocgia.getSelectedItem().toString().trim();

        if (maDocgia.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã độc giả không được để trống!!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        java.sql.Date ngayMuon = new java.sql.Date(System.currentTimeMillis()); // Ngày mượn là hôm nay
        java.sql.Date hanTra = new java.sql.Date(ngayMuon.getTime() + (7 * 24 * 60 * 60 * 1000)); // Hạn trả = hôm nay + 7 ngày

        // Tạo đối tượng phiếu trả
        PhieuMuon pm = new PhieuMuon();
        pm.setMaDocGia(maDocgia);
        pm.setNgayMuon(ngayMuon);
        pm.setHanTra(hanTra);
        // Thêm vào cơ sở dữ liệu
        boolean isInserted = PhieuMuonDAO.insert(pm);
        if (isInserted) {
            dsPhieuMuon.add(pm);
            fillTable();
            clean();
            JOptionPane.showMessageDialog(this, "Thêm phiếu mượn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Thêm phiếu mượn thất bại! Kiểm tra lại dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void removephieumuon() {
        int[] selectedRows = tbl_phieumuon.getSelectedRows();

        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Chưa chọn phiếu trả nào để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa phiếu trả đã chọn?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            try {
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    int index = selectedRows[i];
                    String maPhieuMuon = (String) tbl_phieumuon.getValueAt(index, 0);

                    boolean isDeleted = PhieuTraDAO.delete(maPhieuMuon);

                    if (isDeleted) {
                        dsPhieuMuon.removeIf(pmuon -> pmuon.getMaPhieuMuon().equals(maPhieuMuon));
                    } else {
                        JOptionPane.showMessageDialog(this, "Không thể xóa phiếu trả " + maPhieuMuon + " do ràng buộc dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                fillTable();
                JOptionPane.showMessageDialog(this, "Xóa phiếu trả thành công!");

                if (tbl_phieumuon.getRowCount() > 0) {
                    int newIndex = Math.min(selectedRows[0], tbl_phieumuon.getRowCount() - 1);
                    tbl_phieumuon.setRowSelectionInterval(newIndex, newIndex);
                    loadRowIndexField(newIndex);
                } else {
                    clean();
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa phiếu mượn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void updatePhieuMuon() {
        int index = tbl_phieumuon.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Chưa chọn hàng nào để cập nhật!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (index >= dsPhieuMuon.size()) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lấy phiếu mượn cần cập nhật
        PhieuMuon phieuMuon = dsPhieuMuon.get(index);

        // Kiểm tra trạng thái
        String trangThai = PhieuMuonDAO.getTrangThai(phieuMuon.getMaPhieuMuon());
        if ("Đã trả".equalsIgnoreCase(trangThai)) {
            JOptionPane.showMessageDialog(this, "Phiếu mượn đã trả, không thể chỉnh sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy dữ liệu từ form
        String maDocgia = cbb_madocgia.getSelectedItem().toString().trim();

        try {
            java.sql.Date ngayMuon = new java.sql.Date(System.currentTimeMillis()); // Ngày mượn là hôm nay
            java.sql.Date hanTra = new java.sql.Date(ngayMuon.getTime() + (7 * 24 * 60 * 60 * 1000)); // Hạn trả = hôm nay + 7 ngày

            // Cập nhật dữ liệu vào đối tượng phiếu mượn
            phieuMuon.setMaDocGia(maDocgia);
            phieuMuon.setNgayMuon(ngayMuon);
            phieuMuon.setHanTra(hanTra);

            // Gọi DAO để cập nhật
            PhieuMuonDAO.update(phieuMuon);
            fillTable(); // Cập nhật lại bảng
            clean(); // Xóa dữ liệu nhập
            JOptionPane.showMessageDialog(this, "Cập nhật phiếu mượn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật phiếu mượn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void fillTable() {
        List<PhieuMuon> ptra = PhieuMuonDAO.getAll();
        dsPhieuMuon.clear();
        dsPhieuMuon.addAll(ptra);

        DefaultTableModel model = (DefaultTableModel) tbl_phieumuon.getModel();
        model.setRowCount(0);

        for (PhieuMuon muon : dsPhieuMuon) {
            Object[] row = new Object[]{muon.getMaPhieuMuon(), muon.getMaDocGia(), muon.getNgayMuon(), muon.getHanTra(), muon.getTrangthai()};
            model.addRow(row);
        }
    }

    public void loadRowIndexField(int newRowIndex) {
        if (newRowIndex < 0 || newRowIndex >= tbl_phieumuon.getRowCount()) {
            return; // Tránh lỗi nếu chỉ số không hợp lệ
        }

        String maDocgia = (String) tbl_phieumuon.getValueAt(newRowIndex, 1);
//        String ngayMuon = (String) tbl_phieumuon.getValueAt(newRowIndex, 2); // Đảm bảo đúng cột
//        String ngayTra = (String) tbl_phieumuon.getValueAt(newRowIndex, 3); // Đảm bảo đúng cột
//        String trangthai = (String) tbl_phieumuon.getValueAt(newRowIndex, 4); // Đảm bảo đúng cột

        cbb_madocgia.setSelectedItem(maDocgia);
    }

    public void clean() {
        cbb_madocgia.setSelectedIndex(-1);
    }

    public void clickphieumuon() {
        // Kiểm tra xem bảng có dữ liệu hay không
        if (tbl_phieumuon.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu trong bảng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy chỉ số dòng được chọn
        int row = tbl_phieumuon.getSelectedRow();

        // Kiểm tra xem có dòng nào được chọn không
        if (row != -1) {
            // Lấy dữ liệu từ bảng và điền vào các trường nhập liệu
            String madocgia = tbl_phieumuon.getValueAt(row, 1).toString();
            // Cập nhật các trường nhập liệu

            cbb_madocgia.setSelectedItem(madocgia);

        } else {
            // Nếu không có dòng nào được chọn
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
        tbl_phieumuon = new javax.swing.JTable();
        btn_taophieumuon = new javax.swing.JButton();
        btn_xoaphieumuon = new javax.swing.JButton();
        btn_chinhsuaphieu = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cbb_madocgia = new javax.swing.JComboBox<>();

        setOpaque(false);

        tbl_phieumuon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã phiếu mượn", "Mã độc giả", "Ngày mượn", "Hạn trả", "Trạng thái"
            }
        ));
        tbl_phieumuon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_phieumuonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_phieumuon);

        btn_taophieumuon.setText("Tạo phiếu mượn");
        btn_taophieumuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_taophieumuonActionPerformed(evt);
            }
        });

        btn_xoaphieumuon.setText("Xóa phiếu mượn");
        btn_xoaphieumuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoaphieumuonActionPerformed(evt);
            }
        });

        btn_chinhsuaphieu.setText("Chỉnh sửa phiếu mượn");
        btn_chinhsuaphieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_chinhsuaphieuActionPerformed(evt);
            }
        });

        jLabel1.setText("Mã độc giả");

        cbb_madocgia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                        .addComponent(btn_taophieumuon, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66)
                        .addComponent(btn_xoaphieumuon, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(btn_chinhsuaphieu, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 747, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39))))
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addGap(318, 318, 318)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(cbb_madocgia, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbb_madocgia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                .addGap(26, 26, 26)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_taophieumuon)
                    .addComponent(btn_xoaphieumuon)
                    .addComponent(btn_chinhsuaphieu))
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_taophieumuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_taophieumuonActionPerformed
        addtaophieu();
    }//GEN-LAST:event_btn_taophieumuonActionPerformed

    private void tbl_phieumuonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_phieumuonMouseClicked
        clickphieumuon();
    }//GEN-LAST:event_tbl_phieumuonMouseClicked

    private void btn_xoaphieumuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaphieumuonActionPerformed
        removephieumuon();
    }//GEN-LAST:event_btn_xoaphieumuonActionPerformed

    private void btn_chinhsuaphieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_chinhsuaphieuActionPerformed
        updatePhieuMuon();
    }//GEN-LAST:event_btn_chinhsuaphieuActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_chinhsuaphieu;
    private javax.swing.JButton btn_taophieumuon;
    private javax.swing.JButton btn_xoaphieumuon;
    private javax.swing.JComboBox<String> cbb_madocgia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private swing.RoundedPanel roundedPanel1;
    private javax.swing.JTable tbl_phieumuon;
    // End of variables declaration//GEN-END:variables
}

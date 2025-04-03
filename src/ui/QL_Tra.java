/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import DAO.PhieuMuonDAO;
import DAO.PhieuTraDAO;
import Entity.PhieuTra;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
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
import swing.RoundTabletrasach;
import util.jdbchelper;

/**
 *
 * @author ACER
 */
public class QL_Tra extends TabbedForm {

    List<PhieuTra> dsphieutra = new ArrayList<>();

    /**
     * Creates new form QL_Tra
     */
    public QL_Tra() {
        initComponents();
        // Bo góc cho JTextField
        guitrasach();
        loadPhieuMuonID();
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
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách mã phiếu mượn");
        }
    }

    private String getSelectPhieuMuonCodeQuery() {
        return "SELECT MaPhieuMuon FROM phieumuon"; // Sửa câu lệnh này tùy thuộc vào cơ sở dữ liệu của bạn
    }

    public void guitrasach() {
        // Áp dụng FlatLaf
        FlatLightLaf.setup();
        UIManager.put("Component.arc", 20); // Bo góc toàn bộ UI

        // Kiểm tra nếu `tbltheloai` chưa khởi tạo
        if (tblphieutra == null) {
            DefaultTableModel model = new DefaultTableModel(
                    new Object[][]{},
                    new String[]{"Mã The loai", "Tên The loai"}
            );
            tblphieutra = new RoundTabletrasach(model);
        } else {
            DefaultTableModel model = (DefaultTableModel) tblphieutra.getModel();
            tblphieutra = new RoundTabletrasach(model); // Tạo lại với model cũ
        }

        tblphieutra.setPreferredScrollableViewportSize(new Dimension(500, 200)); // Kích thước mặc định

        // Đảm bảo jScrollPane1 đang chứa `tbltacgia`
        jScrollPane1.setViewportView(tblphieutra); // Cập nhật bảng vào ScrollPane
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder()); // Xóa viền
        jScrollPane1.getViewport().setOpaque(false); // Nền trong suốt

        // Cập nhật lại JButton với bo góc
        btn_taophieu.setIcon(new ImageIcon(getClass().getResource("/image/add.png")));
        btn_chinhsuaphieu.setIcon(new ImageIcon(getClass().getResource("/image/edit.png")));
        btn_xoaphieu.setIcon(new ImageIcon(getClass().getResource("/image/delete.png")));

        // Refresh UI
        SwingUtilities.updateComponentTreeUI(jScrollPane1);
    }

    public void addtaophieu() {
        // Kiểm tra các trường nhập liệu
        String maPhieuMuon = cbb_maphieumuon.getSelectedItem().toString().trim();

        // Kiểm tra trạng thái phiếu mượn
        String trangThai = PhieuMuonDAO.getTrangThai(maPhieuMuon);
        if (trangThai == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu mượn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (trangThai.equals("Đã trả")) {
            JOptionPane.showMessageDialog(this, "Mã phiếu mượn này đã được trả, không thể tạo phiếu trả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }


        // Kiểm tra trùng mã phiếu mượn
        for (PhieuTra pt : dsphieutra) {
            if (pt.getMaPhieuMuon().equals(maPhieuMuon)) {
                JOptionPane.showMessageDialog(this, "Mã phiếu mượn đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Chuyển đổi ngày trả từ String sang java.sql.Date
        
        java.sql.Date ngayTra = new java.sql.Date(System.currentTimeMillis()); // Ngày mượn là hôm nay

        // Tạo đối tượng phiếu trả
        PhieuTra pt = new PhieuTra();
        pt.setMaPhieuMuon(maPhieuMuon);
        pt.setNgayTra(ngayTra);

        // Thêm vào cơ sở dữ liệu
        boolean isInserted = PhieuTraDAO.insert(pt);
        if (isInserted) {
            // Cập nhật trạng thái phiếu mượn thành "Đã trả"
            boolean isUpdated = PhieuMuonDAO.updateTrangThai(maPhieuMuon, "Đã trả");

            if (isUpdated) {
                dsphieutra.add(pt);
                fillTable();
                clean();
                JOptionPane.showMessageDialog(this, "Thêm phiếu trả thành công và cập nhật trạng thái!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật trạng thái thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Thêm phiếu trả thất bại! Kiểm tra lại dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void removetaophieu() {
        int[] selectedRows = tblphieutra.getSelectedRows();

        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Chưa chọn phiếu trả nào để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa phiếu trả đã chọn?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            try {
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    int index = selectedRows[i];
                    String maPhieuTra = (String) tblphieutra.getValueAt(index, 0);
                    String maPhieuMuon = (String) tblphieutra.getValueAt(index, 1);
                    boolean isDeleted = PhieuTraDAO.delete(maPhieuTra);
                    boolean isUpdated = PhieuMuonDAO.updateTrangThai(maPhieuMuon, "Đang Mượn");

                    if (isDeleted) {
                        dsphieutra.removeIf(ptr -> ptr.getMaPhieuTra().equals(maPhieuTra));
                    } else {
                        JOptionPane.showMessageDialog(this, "Không thể xóa phiếu trả " + maPhieuTra + " do ràng buộc dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                fillTable();
                JOptionPane.showMessageDialog(this, "Xóa phiếu trả thành công và cập nhật trạng thái!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                if (tblphieutra.getRowCount() > 0) {
                    int newIndex = Math.min(selectedRows[0], tblphieutra.getRowCount() - 1);
                    tblphieutra.setRowSelectionInterval(newIndex, newIndex);
                    loadRowIndexField(newIndex);
                } else {
                    clean();
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa phiếu trả: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void updatetaophieu() {
        int index = tblphieutra.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Chưa chọn hàng nào để cập nhật!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (index >= dsphieutra.size()) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PhieuTra phieutra = dsphieutra.get(index);

        String maPhieuMuon = cbb_maphieumuon.getSelectedItem().toString().trim();
        

        if (maPhieuMuon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Các trường không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            java.sql.Date ngayTra = new java.sql.Date(System.currentTimeMillis()); // Ngày mượn là hôm nay

            phieutra.setMaPhieuMuon(maPhieuMuon);
            phieutra.setNgayTra(ngayTra);

            PhieuTraDAO.update(phieutra);
            fillTable();
            JOptionPane.showMessageDialog(this, "Cập nhật phiếu trả thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật phiếu trả: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void fillTable() {
        List<PhieuTra> ptra = PhieuTraDAO.getAll();
        dsphieutra.clear();
        dsphieutra.addAll(ptra);

        DefaultTableModel model = (DefaultTableModel) tblphieutra.getModel();
        model.setRowCount(0);

        for (PhieuTra tra : dsphieutra) {
            Object[] row = new Object[]{tra.getMaPhieuTra(), tra.getMaPhieuMuon(), tra.getNgayTra()};
            model.addRow(row);
        }
    }

    public void loadRowIndexField(int newRowIndex) {
        if (newRowIndex < 0 || newRowIndex >= tblphieutra.getRowCount()) {
            return; // Tránh lỗi nếu chỉ số không hợp lệ
        }

        String maPhieuMuon = (String) tblphieutra.getValueAt(newRowIndex, 1);

        cbb_maphieumuon.setSelectedItem(maPhieuMuon);
    }

    public void clean() {
        cbb_maphieumuon.setSelectedIndex(-1); // Đặt lại JComboBox về trạng thái chưa chọn
    }

    public void clickphieutra() {
        // Kiểm tra xem bảng có dữ liệu hay không
        if (tblphieutra.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu trong bảng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy chỉ số dòng được chọn
        int row = tblphieutra.getSelectedRow();

        // Kiểm tra xem có dòng nào được chọn không
        if (row != -1) {
            // Lấy dữ liệu từ bảng và điền vào các trường nhập liệu
            String maphieumuon = tblphieutra.getValueAt(row, 1).toString();

            // Cập nhật các trường nhập liệu
            cbb_maphieumuon.setSelectedItem(maphieumuon);

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
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblphieutra = new javax.swing.JTable();
        btn_taophieu = new javax.swing.JButton();
        btn_xoaphieu = new javax.swing.JButton();
        btn_chinhsuaphieu = new javax.swing.JButton();
        cbb_maphieumuon = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));
        setOpaque(false);

        jLabel1.setText("Mã phiếu mượn");

        tblphieutra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã phiếu trả", "Mã phiếu mượn", "Ngày trả"
            }
        ));
        tblphieutra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblphieutraMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblphieutra);

        btn_taophieu.setText("Tạo phiếu trả");
        btn_taophieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_taophieuActionPerformed(evt);
            }
        });

        btn_xoaphieu.setText("Xóa phiếu trả");
        btn_xoaphieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoaphieuActionPerformed(evt);
            }
        });

        btn_chinhsuaphieu.setText("Chỉnh sửa phiếu trả");
        btn_chinhsuaphieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_chinhsuaphieuActionPerformed(evt);
            }
        });

        cbb_maphieumuon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundedPanel1Layout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addComponent(btn_taophieu, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_xoaphieu, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(btn_chinhsuaphieu, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addContainerGap(75, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 762, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(66, 66, 66))
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addGap(339, 339, 339)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbb_maphieumuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbb_maphieumuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_taophieu)
                    .addComponent(btn_chinhsuaphieu)
                    .addComponent(btn_xoaphieu))
                .addGap(52, 52, 52))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(89, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_chinhsuaphieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_chinhsuaphieuActionPerformed
        updatetaophieu();
    }//GEN-LAST:event_btn_chinhsuaphieuActionPerformed

    private void btn_taophieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_taophieuActionPerformed
        addtaophieu();
    }//GEN-LAST:event_btn_taophieuActionPerformed

    private void btn_xoaphieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaphieuActionPerformed
        removetaophieu();
    }//GEN-LAST:event_btn_xoaphieuActionPerformed

    private void tblphieutraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblphieutraMouseClicked
        clickphieutra();
    }//GEN-LAST:event_tblphieutraMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_chinhsuaphieu;
    private javax.swing.JButton btn_taophieu;
    private javax.swing.JButton btn_xoaphieu;
    private javax.swing.JComboBox<String> cbb_maphieumuon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private swing.RoundedPanel roundedPanel1;
    private javax.swing.JTable tblphieutra;
    // End of variables declaration//GEN-END:variables
}

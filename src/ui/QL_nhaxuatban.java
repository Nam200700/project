/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import DAO.NhaXuatBanDAO;
import Entity.NhaXuatBan;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import raven.drawer.TabbedForm;
import swing.RoundTablenhaxuatban;
import swing.RoundTabletheloai;

/**
 *
 * @author ACER
 */
public class QL_nhaxuatban extends TabbedForm {

    List<NhaXuatBan> dsNhaXuatBan = new ArrayList<>();

    /**
     * Creates new form QL_nhaxuatban
     */
    public QL_nhaxuatban() {
        initComponents();
        fillTable();
        guinhaxuatban();
        // Bo góc cho JTextField
        txt_tennhaxuatban.putClientProperty("JComponent.roundRect", true);
        txt_tennhaxuatban.putClientProperty("JTextField.placeholderText", "Nhập tên nhà xuất bản...");
        txt_tennhaxuatban.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btn_them.putClientProperty("JComponent.roundRect", true);
        btn_them.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.setBackground(Color.white);
        panel.setBounds(50, 50, 250, 150);

    }
    public void guinhaxuatban() {
        // Áp dụng FlatLaf
        FlatLightLaf.setup();
        UIManager.put("Component.arc", 20); // Bo góc toàn bộ UI

        // Kiểm tra nếu `tbltheloai` chưa khởi tạo
        if (tbl_nhaxuatban == null) {
            DefaultTableModel model = new DefaultTableModel(
                    new Object[][]{},
                    new String[]{"Mã The loai", "Tên The loai"}
            );
            tbl_nhaxuatban = new RoundTablenhaxuatban(model);
        } else {
            DefaultTableModel model = (DefaultTableModel) tbl_nhaxuatban.getModel();
            tbl_nhaxuatban = new RoundTablenhaxuatban(model); // Tạo lại với model cũ
        }

        tbl_nhaxuatban.setPreferredScrollableViewportSize(new Dimension(500, 200)); // Kích thước mặc định

        // Đảm bảo jScrollPane1 đang chứa `tbltacgia`
        jScrollPane1.setViewportView(tbl_nhaxuatban); // Cập nhật bảng vào ScrollPane
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder()); // Xóa viền
        jScrollPane1.getViewport().setOpaque(false); // Nền trong suốt

        // Bo góc cho JTextField
        txt_tennhaxuatban.putClientProperty("JComponent.roundRect", true);
        txt_tennhaxuatban.putClientProperty("JTextField.placeholderText", "Nhập tên thể loại...");
        txt_tennhaxuatban.putClientProperty("JTextField.showClearButton", true);
        txt_tennhaxuatban.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Cập nhật lại JButton với bo góc
        btn_them.setIcon(new ImageIcon(getClass().getResource("/image/add.png")));
        btn_capnhat.setIcon(new ImageIcon(getClass().getResource("/image/edit.png")));
        btn_xoa.setIcon(new ImageIcon(getClass().getResource("/image/delete.png")));

        // Refresh UI
        SwingUtilities.updateComponentTreeUI(jScrollPane1);
    }
    public void addNhaXuatBan() {
        // Kiểm tra các trường nhập liệu
        if (txt_tennhaxuatban.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập tên nhà xuất bản", "Error", JOptionPane.WARNING_MESSAGE);
            JOptionPane.showMessageDialog(this, "Thêm thất bại", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Tạo đối tượng nhà xuất bản
        NhaXuatBan nxb = new NhaXuatBan();
        nxb.setTennhaxuatban(txt_tennhaxuatban.getText());

        // Thêm nhà xuất bản vào danh sách và cập nhật giao diện
        dsNhaXuatBan.add(nxb);
        // Lưu nhà xuất bản vào cơ sở dữ liệu
        NhaXuatBanDAO.insert(nxb);
        fillTable();
        clean();
    }

    public void updateNhaXuatBan() {
        // Kiểm tra xem có dòng nào được chọn không
        int index = tbl_nhaxuatban.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Chưa chọn hàng nào để cập nhật!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Kiểm tra danh sách có dữ liệu hợp lệ không
        if (index >= dsNhaXuatBan.size()) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lấy thông tin tác giả từ danh sách
        NhaXuatBan nhaxuatban = dsNhaXuatBan.get(index);
        String tennhaxuatban = txt_tennhaxuatban.getText().trim();

        // Kiểm tra tên tác giả có bị rỗng không
        if (tennhaxuatban.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên nhà xuất bản không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Cập nhật thông tin tác giả
        nhaxuatban.setTennhaxuatban(tennhaxuatban);

        // Cập nhật vào cơ sở dữ liệu
        try {
            NhaXuatBanDAO.update(nhaxuatban);
            fillTable();
            JOptionPane.showMessageDialog(this, "Cập nhật nhà xuất bản thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật nhà xuất bản: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void removeNhaXuatBan() {
        // Lấy danh sách các dòng được chọn trong bảng
        int[] selectedRows = tbl_nhaxuatban.getSelectedRows();

        // Kiểm tra nếu không có dòng nào được chọn
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Chưa chọn nhà xuất bản nào để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Xác nhận trước khi xóa
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhà xuất bản đã chọn?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            try {
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    int index = selectedRows[i];
                    String maNhaXuatBan = (String) tbl_nhaxuatban.getValueAt(index, 0);

                    // Xóa khỏi cơ sở dữ liệu
                    boolean isDeleted = NhaXuatBanDAO.delete(maNhaXuatBan);

                    if (isDeleted) {
                        dsNhaXuatBan.removeIf(nxb -> nxb.getManhaxuatban().equals(maNhaXuatBan));
                    } else {
                        JOptionPane.showMessageDialog(this, "Không thể xóa nhà xuất bản " + maNhaXuatBan + " do ràng buộc dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Cập nhật lại bảng sau khi xóa
                fillTable();
                JOptionPane.showMessageDialog(this, "Xóa nhà xuất bản thành công!");

                if (tbl_nhaxuatban.getRowCount() > 0) {
                    int newIndex = Math.min(selectedRows[0], tbl_nhaxuatban.getRowCount() - 1);
                    tbl_nhaxuatban.setRowSelectionInterval(newIndex, newIndex);
                    loadRowIndexField(newIndex);
                } else {
                    clean();
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa nhà xuất bản: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void fillTable() {
        List<NhaXuatBan> nxbList = NhaXuatBanDAO.getAll();
        dsNhaXuatBan.clear();
        dsNhaXuatBan.addAll(nxbList);

        DefaultTableModel model = (DefaultTableModel) tbl_nhaxuatban.getModel();
        model.setRowCount(0);

        for (NhaXuatBan nxb : dsNhaXuatBan) {
            Object[] row = new Object[]{nxb.getManhaxuatban(), nxb.getTennhaxuatban()};
            model.addRow(row);
        }
    }

    public void loadRowIndexField(int newRowIndex) {
        String maNhaXuatBan = (String) tbl_nhaxuatban.getValueAt(newRowIndex, 0);
        String tenNhaXuatBan = (String) tbl_nhaxuatban.getValueAt(newRowIndex, 1);

        txt_tennhaxuatban.setText(tenNhaXuatBan);
    }

    public void clean() {
        txt_tennhaxuatban.setText("");
    }

    public void clickNhaXuatBan() {
        // Kiểm tra xem bảng có dữ liệu hay không
        if (tbl_nhaxuatban.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu trong bảng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy chỉ số dòng được chọn
        int row = tbl_nhaxuatban.getSelectedRow();

        // Kiểm tra xem có dòng nào được chọn không
        if (row != -1) {
            // Lấy dữ liệu từ bảng và điền vào các trường nhập liệu
            String hoTen = tbl_nhaxuatban.getValueAt(row, 1).toString();     // Họ tên

            // Cập nhật các trường nhập liệu
            txt_tennhaxuatban.setText(hoTen);

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

        panel = new swing.RoundPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_nhaxuatban = new javax.swing.JTable();
        btn_them = new javax.swing.JButton();
        btn_xoa = new javax.swing.JButton();
        btn_capnhat = new javax.swing.JButton();
        txt_tennhaxuatban = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setOpaque(false);

        panel.setBackground(new java.awt.Color(255, 255, 255));

        tbl_nhaxuatban.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã nhà xuất bản", "Tên nhà xuất bản"
            }
        ));
        tbl_nhaxuatban.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_nhaxuatbanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_nhaxuatban);

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

        btn_capnhat.setText("Cập nhật");
        btn_capnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_capnhatActionPerformed(evt);
            }
        });

        jLabel1.setText("Tên nhà xuất bản");

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(jLabel1)
                .addGap(27, 27, 27)
                .addComponent(txt_tennhaxuatban, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_them)
                .addGap(18, 18, 18)
                .addComponent(btn_xoa)
                .addGap(18, 18, 18)
                .addComponent(btn_capnhat)
                .addGap(47, 47, 47))
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 770, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_them)
                    .addComponent(btn_xoa)
                    .addComponent(btn_capnhat)
                    .addComponent(txt_tennhaxuatban, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(62, Short.MAX_VALUE)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_capnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_capnhatActionPerformed
        updateNhaXuatBan();
    }//GEN-LAST:event_btn_capnhatActionPerformed

    private void btn_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaActionPerformed
        removeNhaXuatBan();
    }//GEN-LAST:event_btn_xoaActionPerformed

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
        addNhaXuatBan();
    }//GEN-LAST:event_btn_themActionPerformed

    private void tbl_nhaxuatbanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_nhaxuatbanMouseClicked
        clickNhaXuatBan();
    }//GEN-LAST:event_tbl_nhaxuatbanMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_capnhat;
    private javax.swing.JButton btn_them;
    private javax.swing.JButton btn_xoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private swing.RoundPanel panel;
    private javax.swing.JTable tbl_nhaxuatban;
    private javax.swing.JTextField txt_tennhaxuatban;
    // End of variables declaration//GEN-END:variables
}

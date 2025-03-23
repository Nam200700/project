/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import DAO.NhaXuatBanDAO;
import Entity.NhaXuatBan;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import raven.drawer.TabbedForm;

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

        btn_them = new javax.swing.JButton();
        btn_xoa = new javax.swing.JButton();
        btn_capnhat = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_nhaxuatban = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txt_tennhaxuatban = new javax.swing.JTextField();

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

        jLabel1.setText("Tên nhà xuất bản");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txt_tennhaxuatban, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_capnhat)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_them)
                                .addGap(18, 18, 18)
                                .addComponent(btn_xoa)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txt_tennhaxuatban, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(116, 116, 116)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_them)
                            .addComponent(btn_xoa))
                        .addGap(18, 18, 18)
                        .addComponent(btn_capnhat))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(44, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
       addNhaXuatBan();
    }//GEN-LAST:event_btn_themActionPerformed

    private void btn_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaActionPerformed
        removeNhaXuatBan();
    }//GEN-LAST:event_btn_xoaActionPerformed

    private void btn_capnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_capnhatActionPerformed
        updateNhaXuatBan();
    }//GEN-LAST:event_btn_capnhatActionPerformed

    private void tbl_nhaxuatbanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_nhaxuatbanMouseClicked
        clickNhaXuatBan();
    }//GEN-LAST:event_tbl_nhaxuatbanMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_capnhat;
    private javax.swing.JButton btn_them;
    private javax.swing.JButton btn_xoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_nhaxuatban;
    private javax.swing.JTextField txt_tennhaxuatban;
    // End of variables declaration//GEN-END:variables
}

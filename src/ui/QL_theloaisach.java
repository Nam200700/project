/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import DAO.TheLoaiDAO;
import Entity.TheLoai;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import raven.drawer.TabbedForm;

/**
 *
 * @author ACER
 */
public class QL_theloaisach extends TabbedForm {

    List<TheLoai> theloaisach = new ArrayList<TheLoai>();
    DefaultTableModel model;

    /**
     * Creates new form QL_theloaisach
     */
    public QL_theloaisach() {
        initComponents();
        fillTable();
    }

    public void addtheloai() {
        // Kiểm tra các trường nhập liệu
        if (txt_tenloaisach.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập tên thể loại sách", "Error", JOptionPane.WARNING_MESSAGE);
            JOptionPane.showMessageDialog(this, "Thêm thất bại", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Tạo đối tượng thể loại sách
        TheLoai tl = new TheLoai();

        tl.setTentheloai(txt_tenloaisach.getText());

        // Thêm thể loại sách vào danh sách và cập nhật giao diện
        theloaisach.add(tl);
        // Lưu thể loại sách vào cơ sở dữ liệu
        TheLoaiDAO.insert(tl);
        fillTable();
        clean();
    }

    public void updatetheloaisach() {
        // Kiểm tra xem có dòng nào được chọn không
        int index = tblloaisach.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Chưa chọn hàng nào để cập nhật!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Kiểm tra danh sách có dữ liệu hợp lệ không
        if (index >= theloaisach.size()) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lấy thông tin thể loại sách từ danh sách
        TheLoai theloai = theloaisach.get(index);
        String tentheloaisach = txt_tenloaisach.getText().trim();

        // Kiểm tra tên thể loại sách có bị rỗng không
        if (tentheloaisach.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên nhà xuất bản không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Cập nhật thông tin the loai sach
        theloai.setTentheloai(tentheloaisach);

        // Cập nhật vào cơ sở dữ liệu
        try {
            TheLoaiDAO.update(theloai);
            fillTable();
            JOptionPane.showMessageDialog(this, "Cập nhật thể loại sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật thể loại sách: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void removeTheLoai() {
        // Lấy danh sách các dòng được chọn trong bảng
        int[] selectedRows = tblloaisach.getSelectedRows();

        // Kiểm tra nếu không có dòng nào được chọn
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Chưa chọn thể loại nào để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Xác nhận trước khi xóa
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa thể loại đã chọn?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            try {
                // Duyệt qua từng dòng được chọn và xóa khỏi cơ sở dữ liệu
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    int index = selectedRows[i];
                    String tentheloai = (String) tblloaisach.getValueAt(index, 0); // Lấy tên thể loại

                    // Xóa khỏi cơ sở dữ liệu
                    boolean isDeleted = TheLoaiDAO.delete(tentheloai);

                    if (isDeleted) {
                        // Xóa khỏi danh sách theloaisach
                        theloaisach.removeIf(tl -> tl.getTentheloai().equals(tentheloai));
                    } else {
                        JOptionPane.showMessageDialog(this, "Không thể xóa thể loại '" + tentheloai + "' do ràng buộc dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Cập nhật lại bảng sau khi xóa
                fillTable();
                JOptionPane.showMessageDialog(this, "Xóa thể loại thành công!");

                // Nếu còn dữ liệu trong bảng, chọn lại một dòng
                if (tblloaisach.getRowCount() > 0) {
                    int newIndex = Math.min(selectedRows[0], tblloaisach.getRowCount() - 1);
                    tblloaisach.setRowSelectionInterval(newIndex, newIndex);
                    loadRowindexfield(newIndex);
                } else {
                    clean(); // Nếu không còn dữ liệu, làm sạch form
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa thể loại: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void fillTable() {
        // Lấy dữ liệu từ cơ sở dữ liệu
        List<TheLoai> theloai1 = TheLoaiDAO.getAll();

        theloaisach.clear(); // Xóa danh sách cũ
        theloaisach.addAll(theloai1); // Cập nhật danh sách mới

        // Cập nhật JTable
        model = (DefaultTableModel) tblloaisach.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ trong bảng

        for (TheLoai tl : theloaisach) {
            Object[] row = new Object[]{tl.matheloai, tl.tentheloai};
            model.addRow(row); // Thêm dữ liệu vào bảng
        }
    }

    public void loadRowindexfield(int newrowintdex) {
        String matheloai = (String) tblloaisach.getValueAt(newrowintdex, 0);
        String tentheloai = (String) tblloaisach.getValueAt(newrowintdex, 1);

        txt_tenloaisach.setText(tentheloai);

    }

    public void clean() {
        txt_tenloaisach.setText("");
    }

    public void clickTheLoaiSach() {
        // Kiểm tra xem bảng có dữ liệu hay không
        if (tblloaisach.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu trong bảng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy chỉ số dòng được chọn
        int row = tblloaisach.getSelectedRow();

        // Kiểm tra xem có dòng nào được chọn không
        if (row != -1) {
            // Lấy dữ liệu từ bảng và điền vào các trường nhập liệu
            String hoTen = tblloaisach.getValueAt(row, 1).toString();     // Họ tên

            // Cập nhật các trường nhập liệu
            txt_tenloaisach.setText(hoTen);

        } else {
            // Nếu không có dòng nào được chọn
            JOptionPane.showMessageDialog(this, "Chưa chọn dòng nào!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     *
     * /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_them = new javax.swing.JButton();
        btn_xoa = new javax.swing.JButton();
        btn_sua = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblloaisach = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txt_tenloaisach = new javax.swing.JTextField();

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

        btn_sua.setText("Sửa");
        btn_sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suaActionPerformed(evt);
            }
        });

        tblloaisach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã loại sách", "Tên loại sách"
            }
        ));
        tblloaisach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblloaisachMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblloaisach);

        jButton4.setText("Tìm kiếm");

        jLabel1.setText("Tên loại sách:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(jButton4)
                .addGap(193, 193, 193))
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 62, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_them, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_sua, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addComponent(btn_xoa))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txt_tenloaisach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(37, 37, 37)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 671, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txt_tenloaisach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_xoa)
                            .addComponent(btn_them))
                        .addGap(30, 30, 30)
                        .addComponent(btn_sua))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(116, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
        addtheloai();
    }//GEN-LAST:event_btn_themActionPerformed

    private void btn_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaActionPerformed
        removeTheLoai();
    }//GEN-LAST:event_btn_xoaActionPerformed

    private void btn_suaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaActionPerformed
        updatetheloaisach();
    }//GEN-LAST:event_btn_suaActionPerformed

    private void tblloaisachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblloaisachMouseClicked
        clickTheLoaiSach();
    }//GEN-LAST:event_tblloaisachMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_sua;
    private javax.swing.JButton btn_them;
    private javax.swing.JButton btn_xoa;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tblloaisach;
    private javax.swing.JTextField txt_tenloaisach;
    // End of variables declaration//GEN-END:variables
}

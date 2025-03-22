/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import DAO.theloaiDAO;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import Entity.theloai;
import javax.swing.table.DefaultTableModel;
import raven.drawer.TabbedForm;

/**
 *
 * @author ACER
 */
public class QL_theloaisach extends TabbedForm {

    List<theloai> theloaisach = new ArrayList<theloai>();

    /**
     * Creates new form QL_theloaisach
     */
    public QL_theloaisach() {
        initComponents();
        fillTable();
    }
//     private void openFormCon() {
//        JDialog formCon = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm loại sách", true);
//        formCon.setSize(400, 250);
//        formCon.setLayout(new BorderLayout());
//
//        // Panel chứa form nhập liệu
//        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10)); // 3 hàng, 2 cột
//        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Tạo khoảng cách với viền
//
//        panel.add(new JLabel("Mã loại sách:"));
//        JTextField txtMaLoaiSach = new JTextField();
//        panel.add(txtMaLoaiSach);
//
//        panel.add(new JLabel("Tên loại sách:"));
//        JTextField txtTenLoaiSach = new JTextField();
//        panel.add(txtTenLoaiSach);
//
//        panel.add(new JLabel("Ghi chú:"));
//        JTextField txtGhiChu = new JTextField();
//        panel.add(txtGhiChu);
//
//        // Panel chứa nút Lưu & Hủy
//        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
//        JButton btnLuu = new JButton("Lưu");
//        JButton btnHuy = new JButton("Hủy");
//
//        btnLuu.addActionListener(e -> {
//            JOptionPane.showMessageDialog(formCon, "Lưu thành công!");
//            formCon.dispose();
//        });
//
//        btnHuy.addActionListener(e -> formCon.dispose());
//
//        buttonPanel.add(btnLuu);
//        buttonPanel.add(btnHuy);
//
//        // Thêm vào form con
//        formCon.add(panel, BorderLayout.CENTER);
//        formCon.add(buttonPanel, BorderLayout.SOUTH);
//
//        // Hiển thị form con
//        formCon.setLocationRelativeTo(this);
//        formCon.setVisible(true);
//    }

    public void addtheloai() {
        // Kiểm tra các trường nhập liệu
        if (txt_tentheloai.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập tên thể loại sách", "Error", JOptionPane.WARNING_MESSAGE);
            JOptionPane.showMessageDialog(this, "Thêm thất bại", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Tạo đối tượng ngành học
        theloai tl = new theloai();

        tl.setTentheloai(txt_tentheloai.getText());

        // Thêm ngành học vào danh sách và cập nhật giao diện
        theloaisach.add(tl);
        // Lưu ngành học vào cơ sở dữ liệu
        theloaiDAO.insert(tl);
        fillTable();
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
                    boolean isDeleted = theloaiDAO.delete(tentheloai);

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
        List<theloai> theloai1 = theloaiDAO.getAll();

        theloaisach.clear(); // Xóa danh sách cũ
        theloaisach.addAll(theloai1); // Cập nhật danh sách mới

        // Cập nhật JTable
        DefaultTableModel model = (DefaultTableModel) tblloaisach.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ trong bảng

        for (theloai tl : theloaisach) {
            Object[] row = new Object[]{tl.matheloai, tl.tentheloai};
            model.addRow(row); // Thêm dữ liệu vào bảng
        }
    }

    public void loadRowindexfield(int newrowintdex) {
        String matheloai = (String) tblloaisach.getValueAt(newrowintdex, 0);
        String tentheloai = (String) tblloaisach.getValueAt(newrowintdex, 1);

        txt_tentheloai.setText(tentheloai);

    }

    public void clean() {
        txt_tentheloai.setText("");
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
        jLabel2 = new javax.swing.JLabel();
        txt_tentheloai = new javax.swing.JTextField();

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

        tblloaisach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã loại sách", "Tên loại sách", "Ghi chú"
            }
        ));
        jScrollPane1.setViewportView(tblloaisach);

        jButton4.setText("Tìm kiếm");

        jLabel2.setText("Tên thể loại :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 126, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 671, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btn_xoa)
                        .addComponent(btn_them))
                    .addComponent(btn_sua))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(jButton4)
                        .addGap(193, 193, 193))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txt_tentheloai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txt_tentheloai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(btn_them)
                        .addGap(18, 18, 18)
                        .addComponent(btn_xoa)
                        .addGap(18, 18, 18)
                        .addComponent(btn_sua)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
        addtheloai();
    }//GEN-LAST:event_btn_themActionPerformed

    private void btn_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaActionPerformed
        removeTheLoai();
    }//GEN-LAST:event_btn_xoaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_sua;
    private javax.swing.JButton btn_them;
    private javax.swing.JButton btn_xoa;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tblloaisach;
    private javax.swing.JTextField txt_tentheloai;
    // End of variables declaration//GEN-END:variables
}

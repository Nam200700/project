/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import DAO.userDAO;
import Entity.user;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import raven.drawer.TabbedForm;
import util.AES;

/**
 *
 * @author ACER
 */
public class QL_taikhoan extends TabbedForm {

    List<user> us = new ArrayList<user>();

    public QL_taikhoan() {
        initComponents();
        filltable();
        loadcbo();
    }

    public void addTaiKhoan() {
        if (txtTenTaiKhoan.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập tên đăng nhập", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txtMatKhau.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập mật khẩu", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!txtEmail.getText().trim().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng email!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (cboQuyenHan.getSelectedIndex() < 0 || cboQuyenHan.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn quyền hạn", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy tên quyền từ combobox
        String tenQuyen = cboQuyenHan.getSelectedItem().toString();

        // Lấy mã quyền từ tên quyền
        int maQuyen = userDAO.getMaQuyenFromTenQuyen(tenQuyen);

        // Kiểm tra nếu không có quyền nào tương ứng
        if (maQuyen == -1) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy quyền: " + tenQuyen, "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tạo user mới
        user taikhoan = new user();
        taikhoan.setFullname(txtTenTaiKhoan.getText().trim());
        String encryptedPassword = AES.encrypt(txtMatKhau.getText().trim());
        taikhoan.setPassword(encryptedPassword);
        taikhoan.setEmail(txtEmail.getText().trim());
        taikhoan.setMaQuyen(maQuyen);
        taikhoan.setTenQuyen(tenQuyen); // nếu cần hiển thị lên bảng

        // Thêm user
        if (userDAO.insertUser(taikhoan)) {
            JOptionPane.showMessageDialog(this, "Thêm tài khoản thành công!");
            filltable();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm tài khoản thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadcbo() {
        userDAO.loadTenQuyenToComboBox(cboQuyenHan);
    }

    public void updateTaiKhoan() {
        int selectedRow = tblTaiKhoan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản cần cập nhật", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (txtTenTaiKhoan.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập tên đăng nhập", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txtMatKhau.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập mật khẩu", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!txtEmail.getText().trim().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng email!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (cboQuyenHan.getSelectedIndex() < 0 || cboQuyenHan.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn quyền hạn", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy tên quyền từ combobox
        String tenQuyen = cboQuyenHan.getSelectedItem().toString();
        int maQuyen = userDAO.getMaQuyenFromTenQuyen(tenQuyen);
        if (maQuyen == -1) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy quyền: " + tenQuyen, "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lấy mã tài khoản từ bảng
        int maTaiKhoan = (int) tblTaiKhoan.getValueAt(selectedRow, 0);

        // Tạo user mới
        user taikhoan = new user();
        taikhoan.setMaTaiKhoan(maTaiKhoan);
        taikhoan.setFullname(txtTenTaiKhoan.getText().trim());
        taikhoan.setPassword(AES.encrypt(txtMatKhau.getText().trim()));
        taikhoan.setEmail(txtEmail.getText().trim());
        taikhoan.setMaQuyen(maQuyen);
        taikhoan.setTenQuyen(tenQuyen);

        if (userDAO.updateUser(taikhoan)) {
            JOptionPane.showMessageDialog(this, "Cập nhật tài khoản thành công!");
            filltable();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật tài khoản thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteTaiKhoan() {
        int selectedRow = tblTaiKhoan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản cần xóa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa tài khoản này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Lấy mã tài khoản từ bảng
        int maTaiKhoan = (int) tblTaiKhoan.getValueAt(selectedRow, 0);

        if (userDAO.deleteUser(maTaiKhoan)) {
            JOptionPane.showMessageDialog(this, "Xóa tài khoản thành công!");
            filltable(); // Cập nhật lại bảng sau khi xóa
        } else {
            JOptionPane.showMessageDialog(this, "Xóa tài khoản thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void filltable() {
        List<user> tacgia1 = userDAO.getAll();

        us.clear(); // Xóa danh sách cũ
        us.addAll(tacgia1); // Cập nhật danh sách mới

        // Cập nhật JTable
        DefaultTableModel model = (DefaultTableModel) tblTaiKhoan.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ trong bảng

        for (user tg : us) {
            Object[] row = new Object[]{tg.getMaTaiKhoan(), tg.getFullname(), tg.getEmail(), tg.getTenQuyen()};
            model.addRow(row); // Thêm dữ liệu vào bảng
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundPanel1 = new swing.RoundPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTenTaiKhoan = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtMatKhau = new javax.swing.JTextField();
        cboQuyenHan = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTaiKhoan = new javax.swing.JTable();
        btnThem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1100, 700));

        roundPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Tên tài khoản");

        jLabel2.setText("Mật khẩu");

        jLabel3.setText("Email");

        jLabel4.setText("Quyền hạn");

        tblTaiKhoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã ", "Tên đăng nhập", "Email", "Quyền "
            }
        ));
        jScrollPane1.setViewportView(tblTaiKhoan);

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnCapNhat.setText("Cập nhật");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 875, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(txtTenTaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addComponent(txtMatKhau))
                .addGap(77, 77, 77)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(txtEmail)
                    .addComponent(cboQuyenHan, 0, 169, Short.MAX_VALUE))
                .addGap(66, 66, 66)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCapNhat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(26, 26, 26)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addComponent(btnThem)
                    .addComponent(btnCapNhat)
                    .addComponent(btnXoa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboQuyenHan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(roundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(77, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(roundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        addTaiKhoan();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        updateTaiKhoan();
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        deleteTaiKhoan();
    }//GEN-LAST:event_btnXoaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboQuyenHan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private swing.RoundPanel roundPanel1;
    private javax.swing.JTable tblTaiKhoan;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMatKhau;
    private javax.swing.JTextField txtTenTaiKhoan;
    // End of variables declaration//GEN-END:variables
}

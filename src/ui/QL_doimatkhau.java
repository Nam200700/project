/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import util.*;

/**
 *
 * @author ACER
 */
public class QL_doimatkhau extends javax.swing.JPanel {

    /**
     * Creates new form QL_doimatkhau
     */
    public QL_doimatkhau() {
        initComponents();
    }
    // cách 1 chưa sài jdbchelper
    // Phương thức thay đổi mật khẩu
//    private void changePassword() {
//        // Lấy mật khẩu cũ, mật khẩu mới và xác nhận mật khẩu mới từ các trường nhập liệu
//        char[] oldPassword = txt_mkcu.getPassword();  // Mật khẩu cũ
//        char[] newPassword = txt_mkmoi.getPassword(); // Mật khẩu mới
//        char[] confirmPassword = txt_nhaplaimk.getPassword(); // Xác nhận mật khẩu mới
//
//        // Chuyển mảng ký tự thành chuỗi
//        String oldPasswordStr = new String(oldPassword);
//        String newPasswordStr = new String(newPassword);
//        String confirmPasswordStr = new String(confirmPassword);
//
//        // Kiểm tra nếu mật khẩu mới và xác nhận mật khẩu mới không khớp
//        if (!newPasswordStr.equals(confirmPasswordStr)) {
//            // Hiển thị thông báo nếu mật khẩu không khớp
//            JOptionPane.showMessageDialog(this, "Mật khẩu mới không khớp!");
//            return;  // Dừng phương thức nếu mật khẩu không khớp
//        }
//
//        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/qltv", "root", "18102007")) {
//            // Mã hóa mật khẩu cũ bằng AES
//            String encryptedOldPassword = AES.encrypt(oldPasswordStr);
//
//            // Kiểm tra mật khẩu cũ trong cơ sở dữ liệu
//            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM user WHERE password = ?");
//            checkStmt.setString(1, encryptedOldPassword);  // Truyền mật khẩu cũ đã mã hóa vào câu lệnh SQL
//            ResultSet rs = checkStmt.executeQuery(); // Thực hiện truy vấn
//
//            // Nếu mật khẩu cũ đúng
//            if (rs.next()) {
//                // Mã hóa mật khẩu mới bằng AES
//                String encryptedNewPassword = AES.encrypt(newPasswordStr);
//
//                // Cập nhật mật khẩu mới vào cơ sở dữ liệu
//                PreparedStatement updateStmt = conn.prepareStatement("UPDATE user SET password = ? WHERE id = ?");
//                updateStmt.setString(1, encryptedNewPassword);  // Truyền mật khẩu mới đã mã hóa vào câu lệnh SQL
//                updateStmt.setInt(2, rs.getInt("id"));  // Cập nhật cho người dùng có id tương ứng
//                updateStmt.executeUpdate();  // Thực hiện cập nhật
//
//                // Hiển thị thông báo thành công
//                JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công!");
//            } else {
//                // Nếu mật khẩu cũ không đúng
//                JOptionPane.showMessageDialog(this, "Mật khẩu cũ không đúng!");
//            }
//        } catch (Exception ex) {
//            // In ra lỗi và hiển thị thông báo lỗi nếu có bất kỳ sự cố nào
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra!");
//        }
//    }
        // cách tối ưu c2
//    private void changePassword() {
//        // Lấy mật khẩu từ các trường nhập liệu
//        String oldPassword = new String(txt_mkcu.getPassword());  // Mật khẩu cũ
//        String newPassword = new String(txt_mkmoi.getPassword()); // Mật khẩu mới
//        String confirmPassword = new String(txt_nhaplaimk.getPassword()); // Xác nhận mật khẩu mới
//
//        // Kiểm tra mật khẩu mới và xác nhận mật khẩu
//        if (!newPassword.equals(confirmPassword)) {
//            JOptionPane.showMessageDialog(this, "Mật khẩu mới không khớp!");
//            return;
//        }
//
//        try {
//            // Mã hóa mật khẩu cũ
//            String encryptedOldPassword = AES.encrypt(oldPassword);
//
//            // Kiểm tra mật khẩu cũ trong cơ sở dữ liệu
//            String checkQuery = "SELECT id FROM user WHERE password = ?";
//            try (ResultSet rs = jdbchelper.executeQuery(checkQuery, encryptedOldPassword)) {
//                if (rs.next()) { // Nếu tìm thấy tài khoản với mật khẩu cũ
//                    int userId = rs.getInt("id"); // Lấy ID người dùng
//
//                    // Mã hóa mật khẩu mới
//                    String encryptedNewPassword = AES.encrypt(newPassword);
//
//                    // Cập nhật mật khẩu mới
//                    String updateQuery = "UPDATE user SET password = ? WHERE id = ?";
//                    int rowsUpdated = jdbchelper.executeUpdate(updateQuery, encryptedNewPassword, userId);
//
//                    if (rowsUpdated > 0) {
//                        JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công!");
//                    } else {
//                        JOptionPane.showMessageDialog(this, "Đổi mật khẩu thất bại!");
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(this, "Mật khẩu cũ không đúng!");
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra!");
//        }
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btn_change = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_mkmoi = new javax.swing.JPasswordField();
        txt_mkcu = new javax.swing.JPasswordField();
        txt_nhaplaimk = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        btn_change.setText("Đổi mật khẩu");
        btn_change.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_changeActionPerformed(evt);
            }
        });

        jLabel1.setText("Mật khẩu cũ :");

        jLabel2.setText("Mật khẩu mới :");

        jLabel3.setText("Nhập lại mật khẩu ở trên :");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel4.setText("Đổi mật khẩu");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(btn_change))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_mkcu)
                            .addComponent(txt_mkmoi)
                            .addComponent(txt_nhaplaimk))))
                .addGap(94, 94, 94))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_mkcu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_mkmoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_nhaplaimk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addComponent(btn_change)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_changeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_changeActionPerformed

    }//GEN-LAST:event_btn_changeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_change;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txt_mkcu;
    private javax.swing.JPasswordField txt_mkmoi;
    private javax.swing.JPasswordField txt_nhaplaimk;
    // End of variables declaration//GEN-END:variables
}

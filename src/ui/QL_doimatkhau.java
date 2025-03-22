/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import raven.drawer.TabbedForm;
import util.*;

/**
 *
 * @author ACER
 */
public class QL_doimatkhau extends TabbedForm {

    /**
     * Creates new form QL_doimatkhau
     */
    public QL_doimatkhau() {
        initComponents();
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("raven.themes");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatMacLightLaf.setup();
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
    private void changePassword() {
        // Lấy mật khẩu từ các trường nhập liệu
        String oldPassword = new String(txt_mkcu.getPassword());  // Mật khẩu cũ
        String newPassword = new String(txt_mkmoi.getPassword()); // Mật khẩu mới
        String confirmPassword = new String(txt_nhaplaimk.getPassword()); // Xác nhận mật khẩu mới

        // Kiểm tra mật khẩu mới và xác nhận mật khẩu
        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu mới không khớp!");
            return;
        }

        try {
            // Mã hóa mật khẩu cũ
            String encryptedOldPassword = AES.encrypt(oldPassword);

            // Kiểm tra mật khẩu cũ trong cơ sở dữ liệu
            String checkQuery = "SELECT MaTaiKhoan FROM taikhoan WHERE MatKhau = ?";
            try (ResultSet rs = jdbchelper.executeQuery(checkQuery, encryptedOldPassword)) {
                if (rs.next()) { // Nếu tìm thấy tài khoản với mật khẩu cũ
                    int userId = rs.getInt("MaTaiKhoan"); // Lấy ID người dùng

                    // Mã hóa mật khẩu mới
                    String encryptedNewPassword = AES.encrypt(newPassword);

                    // Cập nhật mật khẩu mới
                    String updateQuery = "UPDATE taikhoan SET MatKhau = ? WHERE MaTaiKhoan = ?";
                    int rowsUpdated = jdbchelper.executeUpdate(updateQuery, encryptedNewPassword, userId);

                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công!");
                        clear();
                    } else {
                        JOptionPane.showMessageDialog(this, "Đổi mật khẩu thất bại!");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Mật khẩu cũ không đúng!");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra!");
        }
    }

    private void clear() {
        txt_mkcu.setText("");
        txt_mkmoi.setText("");
        txt_nhaplaimk.setText("");
    }

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

        jPanel1.setBackground(new java.awt.Color(0, 204, 255));

        btn_change.setText("Đổi mật khẩu");
        btn_change.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_changeActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Mật khẩu cũ :");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Mật khẩu mới :");

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nhập lại mật khẩu mới :");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Đổi mật khẩu");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txt_mkmoi, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_mkcu, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_nhaplaimk, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(127, 127, 127))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_change)
                        .addGap(210, 210, 210))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel4)
                .addGap(33, 33, 33)
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
                .addGap(59, 59, 59)
                .addComponent(btn_change)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(244, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(179, 179, 179))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(63, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_changeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_changeActionPerformed
        changePassword();
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

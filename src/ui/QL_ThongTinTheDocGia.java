/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import DAO.DocGiaDAO;
import Entity.DocGia;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Font;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import raven.drawer.TabbedForm;
import java.sql.Date;

public class QL_ThongTinTheDocGia extends TabbedForm {

    private static int maTaiKhoan = 0;

    public static int getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public static void setMaTaiKhoan(int maTaiKhoan) {
        QL_ThongTinTheDocGia.maTaiKhoan = maTaiKhoan;
    }        
    public QL_ThongTinTheDocGia() {
        initComponents();
        guidocgia();
        clearForm();
    }

    private void guidocgia() {
        FlatLightLaf.setup();
        UIManager.put("Component.arc", 20); // Bo góc toàn bộ UI
        customizeTextField(txtHovaTen, "Nhập họ và tên");
        customizeTextField(txtDiachi, "Nhập địa chỉ");
        customizeTextField(txtSdt, "Nhập số điện thoại");

    }

    private void customizeTextField(JTextField txtField, String placeholder) {
        txtField.putClientProperty("JComponent.roundRect", true);
        txtField.putClientProperty("JTextField.placeholderText", placeholder);
        txtField.putClientProperty("JTextField.showClearButton", true);
        txtField.setFont(new Font("SansSerif", Font.PLAIN, 14));

    }

    private void clearForm() {
        txtHovaTen.setText("");
        txtDiachi.setText("");
        txtSdt.setText("");
        rdoNam.setSelected(true); // Mặc định chọn "Nam"
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        roundPanel1 = new swing.RoundPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtHovaTen = new javax.swing.JTextField();
        txtDiachi = new javax.swing.JTextField();
        txtSdt = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();

        setPreferredSize(new java.awt.Dimension(945, 584));

        roundPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/reading.png"))); // NOI18N
        jLabel1.setText("Thẻ độc giả");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/telephone.png"))); // NOI18N
        jLabel2.setText("Số điện thoại");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/fullname.png"))); // NOI18N
        jLabel3.setText("Họ và tên");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/equality.png"))); // NOI18N
        jLabel4.setText("Giới tính");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/location.png"))); // NOI18N
        jLabel5.setText("Địa chỉ");

        btnSave.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/write.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoNam);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundPanel1Layout.createSequentialGroup()
                        .addGap(159, 159, 159)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txtSdt))
                            .addGroup(roundPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(62, 62, 62)
                                .addComponent(txtDiachi))
                            .addGroup(roundPanel1Layout.createSequentialGroup()
                                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(42, 42, 42)
                                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtHovaTen, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(roundPanel1Layout.createSequentialGroup()
                                        .addComponent(rdoNam)
                                        .addGap(38, 38, 38)
                                        .addComponent(rdoNu)))
                                .addGap(3, 3, 3))))
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(29, 29, 29)
                        .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtHovaTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(rdoNam)
                            .addComponent(rdoNu))
                        .addGap(41, 41, 41)
                        .addComponent(jLabel5))
                    .addComponent(txtDiachi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(btnSave)
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(235, Short.MAX_VALUE)
                .addComponent(roundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(258, 258, 258))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(roundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(76, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
//        try {
//            int maTaiKhoan = QL_ThongTinTheDocGia.getMaTaiKhoan();
//            if (maTaiKhoan <= 0) {
//                JOptionPane.showMessageDialog(null, "Lỗi: Không tìm thấy tài khoản!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            String hoTen = txtHovaTen.getText().trim();
//            String diaChi = txtDiachi.getText().trim();
//            String soDienThoai = txtSdt.getText().trim();
//            if (hoTen.isEmpty() || diaChi.isEmpty() || soDienThoai.isEmpty()) {
//                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.WARNING_MESSAGE);
//                return;
//            }
//
//            if (!rdoNam.isSelected() && !rdoNu.isSelected()) {
//                JOptionPane.showMessageDialog(null, "Vui lòng chọn giới tính!", "Lỗi", JOptionPane.WARNING_MESSAGE);
//                return;
//            }
//            String gioiTinhStr = rdoNam.isSelected() ? "Nam" : "Nữ";
//
//            if (!soDienThoai.matches("\\d{10}")) {
//                JOptionPane.showMessageDialog(null, "Số điện thoại phải có 10 chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            // Lấy ngày đăng ký tự động
//            LocalDate today = LocalDate.now();
//            Date ngayDangKy = Date.valueOf(today);
//
//            DocGiaDAO dao = new DocGiaDAO();
//            if (dao.kiemTraTheDocGia(maTaiKhoan)) {
//                JOptionPane.showMessageDialog(null, "Tài khoản này đã có thẻ độc giả!", "Lỗi", JOptionPane.WARNING_MESSAGE);
//                return;
//            }
//
//            DocGia docGia = new DocGia(hoTen, gioiTinhStr, soDienThoai, diaChi);
//            if (dao.themTheDocGia(docGia)) {
//                JOptionPane.showMessageDialog(null, "Đăng ký thẻ độc giả thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
//                clearForm();
//            } else {
//                JOptionPane.showMessageDialog(null, "Đăng ký thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            }
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(null, "Lỗi hệ thống: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
    }//GEN-LAST:event_btnSaveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private swing.RoundPanel roundPanel1;
    private javax.swing.JTextField txtDiachi;
    private javax.swing.JTextField txtHovaTen;
    private javax.swing.JTextField txtSdt;
    // End of variables declaration//GEN-END:variables
}

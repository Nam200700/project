/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import DAO.DocGiaDAO1;
import Entity.DocGia;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import raven.drawer.TabbedForm;
import util.jdbchelper;

/**
 *
 * @author ACER
 */
public class QL_docgia extends TabbedForm {
    String[] gioiTinh = {"Nam", "Nữ"};
    JTextField txtMa = new JTextField(15);
    JTextField txtTen = new JTextField(15);
    JComboBox<String> cbGioiTinh = new JComboBox<>(gioiTinh);
    JTextField txtSoDienThoai = new JTextField(15);
    JTextField txtNgayDangKi = new JTextField(15);
    JTextField txtMaTaiKhoan = new JTextField(15);
    /**
     * Creates new form QL_
     */
    public QL_docgia() {
         initComponents();
        JDialog formCon = new JDialog((Frame) SwingUtilities.getWindowAncestor(null), "Thêm Độc Giả", true);
        formCon.setSize(350, 250);
        formCon.setLayout(new BorderLayout());

        // Panel chính chứa các thành phần
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        panel.add(new JLabel("Mã độc giả:"), gbc);
        gbc.gridx = 1;
        panel.add(txtMa, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Tên độc giả:"), gbc);
        gbc.gridx = 1;
        panel.add(txtTen, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Giới tính:"), gbc);
        gbc.gridx = 1;
        panel.add(cbGioiTinh, gbc);

        // Tạo panel chứa các nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnLuu = new JButton("Lưu");
        JButton btnHuy = new JButton("Hủy");

        btnLuu.addActionListener(e -> {
            JOptionPane.showMessageDialog(formCon, "Lưu thành công!");
            formCon.dispose();
        });

        btnHuy.addActionListener(e -> formCon.dispose());

        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);

        // Thêm vào form con
        formCon.add(panel, BorderLayout.CENTER);
        formCon.add(buttonPanel, BorderLayout.SOUTH);

        // Hiển thị form con
        formCon.setLocationRelativeTo(null);
        formCon.setVisible(true);
    }

    private void loadDataToTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ trước khi tải mới

        try (Connection conn = jdbchelper.getconnection(); PreparedStatement stmt = conn.prepareStatement("SELECT * FROM DocGia"); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("MaDocGia"), // Mã độc giả
                    rs.getString("HoTen"), // Họ tên
                    rs.getString("GioiTinh"), // Giới tính
                    rs.getString("SoDienThoai"),// Số điện thoại
                    rs.getDate("NgayDangKy"), // Ngày đăng ký
                    rs.getInt("MaTaiKhoan") // Mã tài khoản
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu độc giả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // tạo form con để thêm thông tin độc giả 
//    public void openFormCon() {
//        JDialog formCon = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm Độc Giả", true);
//        formCon.setSize(350, 250);
//        formCon.setLayout(new BorderLayout());
//
//        // Panel chính chứa các thành phần
//        JPanel panel = new JPanel(new GridBagLayout());
//        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Tạo khoảng cách viền
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(5, 5, 5, 5); // Tạo khoảng cách giữa các dòng
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//
//        panel.add(new JLabel("Mã độc giả:"), gbc);
//        gbc.gridx = 1;
//        // dặt kí hiệu cho Mã độc giả
//        JTextField txtMa = new JTextField(15);
//        panel.add(txtMa, gbc);
//
//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        panel.add(new JLabel("Tên độc giả:"), gbc);
//        gbc.gridx = 1;
//        JTextField txtTen = new JTextField(15);
//        panel.add(txtTen, gbc);
//
//        gbc.gridx = 0;
//        gbc.gridy = 2;
//        panel.add(new JLabel("Giới tính:"), gbc);
//        gbc.gridx = 1;
//        String[] gioiTinh = {"Nam", "Nữ"};
//        JComboBox<String> cbGioiTinh = new JComboBox<>(gioiTinh);
//        panel.add(cbGioiTinh, gbc);
//
//        gbc.gridx = 0;
//        gbc.gridy = 3;
//        panel.add(new JLabel("Địa chỉ:"), gbc);
//        gbc.gridx = 1;
//        JTextField txtDiaChi = new JTextField(15);
//        panel.add(txtDiaChi, gbc);
//
//        gbc.gridx = 0;
//        gbc.gridy = 4;
//        panel.add(new JLabel("Ghi chú:"), gbc);
//        gbc.gridx = 1;
//        JTextField txtGhiChu = new JTextField(15);
//        panel.add(txtGhiChu, gbc);
//
//        // Tạo panel chứa các nút
//        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
//        JButton btnLuu = new JButton("Lưu");
//        JButton btnHuy = new JButton("Hủy");
//
//        btnLuu.addActionListener(e -> {
//            JOptionPane.showMessageDialog(formCon, "Lưu thành công!");
//            formCon.dispose(); // Đóng form
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_them = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();

        btn_them.setText("Thêm");
        btn_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themActionPerformed(evt);
            }
        });

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã độc giả", "Tên độc giả", "Giới tính", "Địa chỉ", "Số điện thoại", "Email", "Ngày đăng kí"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton4.setText("Tìm kiếm");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(311, 311, 311)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jButton4))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_them)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnSua)
                                    .addComponent(btnXoa))
                                .addGap(85, 85, 85)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(122, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_them)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSua)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(57, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
//        openFormCon();
    }//GEN-LAST:event_btn_themActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // Lấy dữ liệu từ form
        String maDG = txtMa.getText().trim();
        String hoTen = txtTen.getText().trim();
        String gioiTinh = cbGioiTinh.getSelectedItem().toString().trim();
        String soDienThoai = txtSoDienThoai.getText().trim();
        String ngayDangKyStr = txtNgayDangKi.getText().trim();
        String maTaiKhoanStr = txtMaTaiKhoan.getText().trim();

// Kiểm tra xem các trường có bị trống không
        if (maDG.isEmpty() || hoTen.isEmpty() || gioiTinh.isEmpty() || soDienThoai.isEmpty() || ngayDangKyStr.isEmpty() || maTaiKhoanStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

// Kiểm tra mã độc giả & mã tài khoản có phải là số không
        int maDocGia, maTaiKhoan;
        try {
            maDocGia = Integer.parseInt(maDG);
            maTaiKhoan = Integer.parseInt(maTaiKhoanStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã độc giả và mã tài khoản phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

// Chuyển đổi ngày đăng ký sang kiểu Date
        Date ngayDangKy;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            ngayDangKy = dateFormat.parse(ngayDangKyStr);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ! Vui lòng nhập theo yyyy-MM-dd.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

// Tạo đối tượng DocGia
        DocGia docGia = new DocGia(maDocGia, hoTen, gioiTinh, soDienThoai, ngayDangKy, maTaiKhoan);
        DocGiaDAO1 docGiaDAO = new DocGiaDAO1();

// Gọi phương thức cập nhật từ DAO
        if (docGiaDAO.capNhatDocGia(docGia)) {
            JOptionPane.showMessageDialog(this, "Cập nhật độc giả thành công!");
            loadDataToTable(); // Cập nhật lại bảng dữ liệu
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật độc giả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed

        int index = jTable1.getSelectedRow(); // Lấy dòng được chọn

        if (index == -1) { // Kiểm tra xem có dòng nào được chọn không
            JOptionPane.showMessageDialog(this, "Chưa chọn độc giả để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int maDocGia = (int) jTable1.getValueAt(index, 0); // Lấy mã độc giả từ bảng

        // Xóa độc giả khỏi cơ sở dữ liệu
        if (DocGiaDAO1.xoaDocGia(maDocGia)) {
            JOptionPane.showMessageDialog(this, "Xóa độc giả thành công!");
            loadDataToTable(); // Cập nhật lại bảng
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa độc giả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnXoaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btn_them;
    private javax.swing.JButton jButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}

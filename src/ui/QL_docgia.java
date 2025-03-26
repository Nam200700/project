/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import javax.swing.JOptionPane;
import DAO.DocGiaDAO;
import Entity.DocGia;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.text.ParseException;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import raven.drawer.TabbedForm;
import util.jdbchelper;

/**
 *
 * @author ACER
 */
public class QL_docgia extends TabbedForm {
    List<DocGia> docgialist = new ArrayList<DocGia>();
      private JTextField nameField, phoneField, dateField, addressField, accountField;
    private JRadioButton maleRadio, femaleRadio;
    private JTable table;
    /**
     * Creates new form QL_docgia
     */
    public QL_docgia() {
        initComponents();
//        fillTable();
       
    }
  
  
//    public void updateDocGia() {
//        // Lấy dòng được chọn trong bảng
//        int index = tbl_docgia.getSelectedRow();
//        if (index == -1 || index >= docgialist.size()) {
//            JOptionPane.showMessageDialog(this, "Chưa chọn hàng nào để cập nhật!", "Thông báo", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//        // Kiểm tra dữ liệu đầu vào
//        if (txttendocgia.getText().trim().isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên độc giả!", "Lỗi", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//
//        if (cbbgioitinh.getSelectedItem() == null) {
//            JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính!", "Lỗi", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//
//        if (txtsodienthoai.getText().trim().isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!", "Lỗi", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//
//        if (!txtsodienthoai.getText().matches("\\d{10,11}")) {
//            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//
//        if (txtdiachi.getText().trim().isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Vui lòng nhập dia chi!", "Lỗi", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//
//
//        if (txtngaydangki.getText().trim().isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày đăng ký!", "Lỗi", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//
//        if (txtmataikhoan.getText().trim().isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã tài khoản!", "Lỗi", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//
//        // Chuyển đổi ngày đăng ký từ String sang java.sql.Date
//        String ngayDangKyStr = txtngaydangki.getText().trim();
//        Date ngayDangKy;
//        try {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//            java.util.Date utilDate = dateFormat.parse(ngayDangKyStr);
//            ngayDangKy = new Date(utilDate.getTime());
//        } catch (ParseException e) {
//            JOptionPane.showMessageDialog(this, "Ngày đăng ký không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // Chuyển đổi mã tài khoản từ String sang int
//        int maTaiKhoan;
//        try {
//            maTaiKhoan = Integer.parseInt(txtmataikhoan.getText().trim());
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(this, "Mã tài khoản phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // Cập nhật thông tin độc giả trong danh sách
//        DocGia docgia = docgialist.get(index);
//        docgia.setHoTen(txttendocgia.getText().trim());
//        docgia.setGioiTinh(cbbgioitinh.getSelectedItem().toString());
//        docgia.setSoDienThoai(txtsodienthoai.getText().trim());
//        docgia.setDiaChi(txtdiachi.getText().trim());
//        docgia.setNgayDangKy(ngayDangKy);
//        docgia.setMaTaiKhoan(maTaiKhoan);
//        // Cập nhật vào cơ sở dữ liệu
//        try {
//            DocGiaDAO.update(docgia);
//            fillTable();
//            JOptionPane.showMessageDialog(this, "Cập nhật độc giả thành công!");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật độc giả: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
//    }
////
////    public void removeDocGia() {
////        int[] selectedRows = tbl_docgia.getSelectedRows();
////        if (selectedRows.length == 0) {
////            JOptionPane.showMessageDialog(this, "Chưa chọn độc giả nào để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
////            return;
////        }
////
////        int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa độc giả đã chọn?", "Xác nhận", JOptionPane.YES_NO_OPTION);
////        if (choice == JOptionPane.YES_OPTION) {
////            for (int i = selectedRows.length - 1; i >= 0; i--) {
////                int index = selectedRows[i];
////                int maDocGia = (int) tbl_docgia.getValueAt(index, 0);
////                boolean isDeleted = DocGiaDAO.delete(maDocGia);
////                if (isDeleted) {
////                    docgialist.removeIf(dg -> dg.getMaDocGia() == maDocGia);
////                } else {
////                    JOptionPane.showMessageDialog(this, "Không thể xóa độc giả có mã " + maDocGia + " do ràng buộc dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
////                    return;
////                }
////            }
////            fillTable();
////            JOptionPane.showMessageDialog(this, "Xóa độc giả thành công!");
////            if (tbl_docgia.getRowCount() > 0) {
////                int newIndex = Math.min(selectedRows[0], tbl_docgia.getRowCount() - 1);
////                tbl_docgia.setRowSelectionInterval(newIndex, newIndex);
////                loadRowIndexField(newIndex);
////            } else {
////                clean();
////            }
////        }
//    }
////
////    public void fillTable() {
////        List<DocGia> docGiaList = DocGiaDAO.getAll();
////
////        docgialist.clear();
////        docgialist.addAll(docGiaList);
////
////        DefaultTableModel model = (DefaultTableModel) tbl_docgia.getModel();
////        model.setRowCount(0);
////
////        for (DocGia dg : docgialist) {
////            Object[] row = new Object[]{ dg.getHoTen(), dg.getGioiTinh(), dg.getSoDienThoai(), dg.getDiaChi(), dg.getNgayDangKy(), dg.getMaTaiKhoan()};
////            model.addRow(row);
////        }
////    }
////
////    public void loadRowIndexField(int rowIndex) {
//////        int maDocGia = (int) tbl_docgia.getValueAt(rowIndex, 0);
////        String hoTen = (String) tbl_docgia.getValueAt(rowIndex, 1);
////        String gioiTinh = (String) tbl_docgia.getValueAt(rowIndex, 2);
////        String soDienThoai = (String) tbl_docgia.getValueAt(rowIndex, 3);
////        String diachi = (String) tbl_docgia.getValueAt(rowIndex, 4);
////        Date ngayDangKy = (Date) tbl_docgia.getValueAt(rowIndex, 5);
////        int maTaiKhoan = (int) tbl_docgia.getValueAt(rowIndex, 6);
////
////        txttendocgia.setText(hoTen);
////        cbbgioitinh.setSelectedItem(gioiTinh);
////        txtsodienthoai.setText(soDienThoai);
////        txtdiachi.setText(diachi);
////        txtmataikhoan.setText(String.valueOf(maTaiKhoan));
////    }
////
////    public void clean() {
////        txttendocgia.setText("");
////        cbbgioitinh.setSelectedIndex(0);
////        txtsodienthoai.setText("");
////        txtdiachi.setText("");
////        txtmataikhoan.setText("");
////        txtngaydangki.setText("");
////    }
////
//////    public void clickDocgiachitiet() {
//////        // Lấy chỉ mục của hàng được chọn trong bảng
//////        int selectedRow = tbl_docgia.getSelectedRow();
//////
//////        // Kiểm tra nếu không có hàng nào được chọn
//////        if (selectedRow == -1) {
//////            // Hiển thị hộp thoại cảnh báo cho người dùng
//////            JOptionPane.showMessageDialog(this, "Vui lòng chọn một độc giả!", "Thông báo", JOptionPane.WARNING_MESSAGE);
//////            return; // Dừng phương thức nếu không có hàng nào được chọn
//////        }
//////
//////        String maTaiKhoan = tbl_docgia.getValueAt(selectedRow, 0).toString();
//////        String ten = tbl_docgia.getValueAt(selectedRow, 1).toString();
//////        String gioiTinh = tbl_docgia.getValueAt(selectedRow, 2).toString();
//////        String sdt = tbl_docgia.getValueAt(selectedRow, 3).toString();
//////        String email = tbl_docgia.getValueAt(selectedRow, 4).toString();
//////        String ngayDK = tbl_docgia.getValueAt(selectedRow, 5).toString();
//////
//////        // Tạo cửa sổ hiển thị chi tiết
//////        JDialog detailDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chi tiết độc giả", true);
//////        detailDialog.setSize(250, 300);
//////        detailDialog.setLocationRelativeTo(this);
//////
//////        // Panel chứa thông tin
//////        JPanel panel = new JPanel(new GridLayout(6, 1, 5, 5));
//////        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//////
//////        panel.add(new JLabel("Mã tài khoản: " + maTaiKhoan, JLabel.LEFT));
//////        panel.add(new JLabel("Tên: " + ten, JLabel.LEFT));
//////        panel.add(new JLabel("Giới tính: " + gioiTinh, JLabel.LEFT));
//////        panel.add(new JLabel("SĐT: " + sdt, JLabel.LEFT));
//////        panel.add(new JLabel("Email: " + email, JLabel.LEFT));
//////        panel.add(new JLabel("Ngày ĐK: " + ngayDK, JLabel.LEFT));
//////
//////        // Nút đóng
//////        JButton btnClose = new JButton("Đóng");
//////        btnClose.addActionListener(e -> detailDialog.dispose());
//////
//////        // Panel cho nút
//////        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//////        btnPanel.add(btnClose);
//////
//////        // Thêm các panel vào dialog
//////        detailDialog.setLayout(new BorderLayout());
//////        detailDialog.add(panel, BorderLayout.CENTER);
//////        detailDialog.add(btnPanel, BorderLayout.SOUTH);
//////
//////        detailDialog.setVisible(true);
//////    }
//////    public void clickDocGia() {
//////        // Kiểm tra xem bảng có dữ liệu hay không
//////        if (tbl_docgia.getRowCount() == 0) {
//////            JOptionPane.showMessageDialog(this, "Không có dữ liệu trong bảng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
//////            return;
//////        }
//////
//////        // Lấy chỉ số dòng được chọn
//////        int row = tbl_docgia.getSelectedRow();
//////
//////        // Kiểm tra xem có dòng nào được chọn không
//////        if (row != -1) {
//////            // Lấy dữ liệu từ bảng và điền vào các trường nhập liệu
//////            String hoTen = tbl_docgia.getValueAt(row, 1).toString();     // Họ tên
//////            String gioiTinh = tbl_docgia.getValueAt(row, 2).toString();  // Giới tính
//////            String soDienThoai = tbl_docgia.getValueAt(row, 3).toString(); // Số điện thoại
//////            String diachi = tbl_docgia.getValueAt(row, 4).toString();     // Email
//////            String ngayDangKy = tbl_docgia.getValueAt(row, 5).toString(); // Ngày đăng ký
//////            String maTaiKhoan = tbl_docgia.getValueAt(row, 6).toString(); // Mã tài khoản
//////
//////            // Cập nhật các trường nhập liệu
//////            txttendocgia.setText(hoTen);
//////            cbbgioitinh.setSelectedItem(gioiTinh);
//////            txtsodienthoai.setText(soDienThoai);
//////            txtdiachi.setText(diachi);
//////            txtngaydangki.setText(ngayDangKy);
//////            txtmataikhoan.setText(maTaiKhoan);
//////        } else {
//////            // Nếu không có dòng nào được chọn
//////            JOptionPane.showMessageDialog(this, "Chưa chọn dòng nào!", "Thông báo", JOptionPane.WARNING_MESSAGE);
//////        }
//////    }
////
////    /**
////     * This method is called from within the constructor to initialize the form.
////     * WARNING: Do NOT modify this code. The content of this method is always
////     * regenerated by the Form Editor.
////     */
////    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1107, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 549, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import javax.swing.JOptionPane;
import DAO.DocGiaDAO;
import Entity.DocGia;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.text.ParseException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import raven.drawer.TabbedForm;
import swing.RoundTableDocgia;


/**
 *
 * @author ACER
 */
public class QL_docgia extends TabbedForm {

    List<DocGia> docgialist = new ArrayList<DocGia>();

    /**
     * Creates new form QL_docgia
     */
    public QL_docgia() {
        initComponents();
        fillTable();
        guitrasach();
    }

    public void guitrasach() {
        // Áp dụng FlatLaf
        FlatLightLaf.setup();
        UIManager.put("Component.arc", 20); // Bo góc toàn bộ UI

        // Kiểm tra nếu `tbltheloai` chưa khởi tạo
        DefaultTableModel model = (DefaultTableModel) tbl_docgia.getModel();
        tbl_docgia = new RoundTableDocgia(model); // Tạo lại với model cũ

        tbl_docgia.setPreferredScrollableViewportSize(new Dimension(500, 200)); // Kích thước mặc định

        // Đảm bảo jScrollPane1 đang chứa `tbltacgia`
        jScrollPane1.setViewportView(tbl_docgia); // Cập nhật bảng vào ScrollPane
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder()); // Xóa viền
        jScrollPane1.getViewport().setOpaque(false); // Nền trong suốt

        // Bo góc cho JTextField
        
        txttendocgia.putClientProperty("JComponent.roundRect", true);
        txttendocgia.putClientProperty("JTextField.placeholderText", "Nhập tên độc giả...");
        txttendocgia.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtdiachi.putClientProperty("JComponent.roundRect", true);
        txtdiachi.putClientProperty("JTextField.placeholderText", "Nhập địa chỉ...");
        txtdiachi.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtsodienthoai.putClientProperty("JComponent.roundRect", true);
        txtsodienthoai.putClientProperty("JTextField.placeholderText", "Nhập số điện thoại...");
        txtsodienthoai.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txt_timkiem.putClientProperty("JTextField.placeholderText", "Vui lòng nhập tên hoặc mã để tìm kiếm...");
        // Cập nhật lại JButton với bo góc
        btn_them.setIcon(new ImageIcon(getClass().getResource("/image/add.png")));
        btn_capnhat.setIcon(new ImageIcon(getClass().getResource("/image/edit.png")));
        btn_xoa.setIcon(new ImageIcon(getClass().getResource("/image/delete.png")));

        // Refresh UI
        SwingUtilities.updateComponentTreeUI(jScrollPane1);
    }

    public void addDocGia() {
        
        // Kiểm tra dữ liệu đầu vào
        if (txttendocgia.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên độc giả!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (cbbgioitinh.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (txtsodienthoai.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!txtsodienthoai.getText().matches("\\d{10,11}")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (txtdiachi.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập địa chỉ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Tạo đối tượng độc giả
        DocGia docgia = new DocGia();
        docgia.setHoTen(txttendocgia.getText().trim());
        docgia.setGioiTinh(cbbgioitinh.getSelectedItem().toString());
        docgia.setSoDienThoai(txtsodienthoai.getText().trim());
        docgia.setDiaChi(txtdiachi.getText().trim());

        // Thêm vào cơ sở dữ liệu
        try {
            boolean isSuccess = DocGiaDAO.themTheDocGia(docgia);
            if (isSuccess) {
                // Cập nhật danh sách độc giả
                docgialist.add(docgia);
                fillTable();
                clean();
                JOptionPane.showMessageDialog(this, "Thêm độc giả thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Thêm độc giả thất bại! Kiểm tra lại dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm độc giả: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateDocGia() {
        // Lấy dòng được chọn trong bảng
        int index = tbl_docgia.getSelectedRow();
        if (index == -1 || index >= docgialist.size()) {
            JOptionPane.showMessageDialog(this, "Chưa chọn hàng nào để cập nhật!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Kiểm tra dữ liệu đầu vào
        if (txttendocgia.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên độc giả!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (cbbgioitinh.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (txtsodienthoai.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!txtsodienthoai.getText().matches("\\d{10,11}")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (txtdiachi.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập địa chỉ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Cập nhật thông tin độc giả trong danh sách
        DocGia docgia = docgialist.get(index);
        docgia.setHoTen(txttendocgia.getText().trim());
        docgia.setGioiTinh(cbbgioitinh.getSelectedItem().toString());
        docgia.setSoDienThoai(txtsodienthoai.getText().trim());
        docgia.setDiaChi(txtdiachi.getText().trim());
        // Cập nhật vào cơ sở dữ liệu
        try {
            DocGiaDAO.update(docgia);
            fillTable();
            JOptionPane.showMessageDialog(this, "Cập nhật độc giả thành công!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật độc giả: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void removeDocGia() {
        int[] selectedRows = tbl_docgia.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Chưa chọn độc giả nào để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa độc giả đã chọn?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                int index = selectedRows[i];
                String maDocGia = (String) tbl_docgia.getValueAt(index, 0);
                boolean isDeleted = DocGiaDAO.delete(maDocGia);
                if (isDeleted) {
                    docgialist.removeIf(dg -> dg.getMaDocGia().equals(maDocGia));
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể xóa độc giả có mã " + maDocGia + " do ràng buộc dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            fillTable();
            JOptionPane.showMessageDialog(this, "Xóa độc giả thành công!");
            if (tbl_docgia.getRowCount() > 0) {
                int newIndex = Math.min(selectedRows[0], tbl_docgia.getRowCount() - 1);
                tbl_docgia.setRowSelectionInterval(newIndex, newIndex);
                loadRowIndexField(newIndex);
            } else {
                clean();
            }
        }
    }

    public void fillTable() {
        List<DocGia> docGiaList = DocGiaDAO.getAll();

        docgialist.clear();
        docgialist.addAll(docGiaList);

        DefaultTableModel model = (DefaultTableModel) tbl_docgia.getModel();
        model.setRowCount(0);

        for (DocGia dg : docgialist) {
            Object[] row = new Object[]{dg.getMaDocGia(), dg.getHoTen(), dg.getGioiTinh(), dg.getSoDienThoai(), dg.getDiaChi()};
            model.addRow(row);
        }
    }

    public void loadRowIndexField(int rowIndex) {
        String hoTen = (String) tbl_docgia.getValueAt(rowIndex, 1);
        String gioiTinh = (String) tbl_docgia.getValueAt(rowIndex, 2);
        String soDienThoai = (String) tbl_docgia.getValueAt(rowIndex, 3);
        String diaChi = (String) tbl_docgia.getValueAt(rowIndex, 4);

        txttendocgia.setText(hoTen);
        cbbgioitinh.setSelectedItem(gioiTinh);
        txtsodienthoai.setText(soDienThoai);
        txtdiachi.setText(diaChi);
    }

    public void clean() {
        txttendocgia.setText("");
        cbbgioitinh.setSelectedIndex(0);
        txtsodienthoai.setText("");
        txtdiachi.setText("");

    }
//    public void clickDocgiachitiet() {
//        // Lấy chỉ mục của hàng được chọn trong bảng
//        int selectedRow = tbl_docgia.getSelectedRow();
//
//        // Kiểm tra nếu không có hàng nào được chọn
//        if (selectedRow == -1) {
//            // Hiển thị hộp thoại cảnh báo cho người dùng
//            JOptionPane.showMessageDialog(this, "Vui lòng chọn một độc giả!", "Thông báo", JOptionPane.WARNING_MESSAGE);
//            return; // Dừng phương thức nếu không có hàng nào được chọn
//        }
//
//        String maTaiKhoan = tbl_docgia.getValueAt(selectedRow, 0).toString();
//        String ten = tbl_docgia.getValueAt(selectedRow, 1).toString();
//        String gioiTinh = tbl_docgia.getValueAt(selectedRow, 2).toString();
//        String sdt = tbl_docgia.getValueAt(selectedRow, 3).toString();
//        String email = tbl_docgia.getValueAt(selectedRow, 4).toString();
//        String ngayDK = tbl_docgia.getValueAt(selectedRow, 5).toString();
//
//        // Tạo cửa sổ hiển thị chi tiết
//        JDialog detailDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chi tiết độc giả", true);
//        detailDialog.setSize(250, 300);
//        detailDialog.setLocationRelativeTo(this);
//
//        // Panel chứa thông tin
//        JPanel panel = new JPanel(new GridLayout(6, 1, 5, 5));
//        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//
//        panel.add(new JLabel("Mã tài khoản: " + maTaiKhoan, JLabel.LEFT));
//        panel.add(new JLabel("Tên: " + ten, JLabel.LEFT));
//        panel.add(new JLabel("Giới tính: " + gioiTinh, JLabel.LEFT));
//        panel.add(new JLabel("SĐT: " + sdt, JLabel.LEFT));
//        panel.add(new JLabel("Email: " + email, JLabel.LEFT));
//        panel.add(new JLabel("Ngày ĐK: " + ngayDK, JLabel.LEFT));
//
//        // Nút đóng
//        JButton btnClose = new JButton("Đóng");
//        btnClose.addActionListener(e -> detailDialog.dispose());
//
//        // Panel cho nút
//        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        btnPanel.add(btnClose);
//
//        // Thêm các panel vào dialog
//        detailDialog.setLayout(new BorderLayout());
//        detailDialog.add(panel, BorderLayout.CENTER);
//        detailDialog.add(btnPanel, BorderLayout.SOUTH);
//
//        detailDialog.setVisible(true);
//    }

    public void clickDocGia() {
        // Kiểm tra xem bảng có dữ liệu hay không
        if (tbl_docgia.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu trong bảng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy chỉ số dòng được chọn
        int row = tbl_docgia.getSelectedRow();

        // Kiểm tra xem có dòng nào được chọn không
        if (row != -1) {
            // Lấy dữ liệu từ bảng và điền vào các trường nhập liệu
            String hoTen = tbl_docgia.getValueAt(row, 1).toString();     // Họ tên
            String gioiTinh = tbl_docgia.getValueAt(row, 2).toString();  // Giới tính
            String soDienThoai = tbl_docgia.getValueAt(row, 3).toString(); // Số điện thoại
            String diachi = tbl_docgia.getValueAt(row, 4).toString();     // Email

            // Cập nhật các trường nhập liệu
            txttendocgia.setText(hoTen);
            cbbgioitinh.setSelectedItem(gioiTinh);
            txtsodienthoai.setText(soDienThoai);
            txtdiachi.setText(diachi);

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

        roundedPanel1 = new swing.RoundedPanel();
        txttendocgia = new javax.swing.JTextField();
        cbbgioitinh = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_docgia = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtsodienthoai = new javax.swing.JTextField();
        txtdiachi = new javax.swing.JTextField();
        txt_timkiem = new javax.swing.JTextField();
        btn_timkiem = new javax.swing.JButton();
        btn_capnhat = new javax.swing.JButton();
        btn_them = new javax.swing.JButton();
        btn_xoa = new javax.swing.JButton();

        setOpaque(false);

        cbbgioitinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));

        jLabel1.setText("Tên độc giả");

        jLabel2.setText("Giới tính");

        tbl_docgia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã độc giả", "Tên độc giả", "Giới tính", "Số điện thoại", "Địa chỉ"
            }
        ));
        tbl_docgia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_docgiaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_docgia);

        jLabel4.setText("Địa chỉ");

        jLabel3.setText("Số điện thoại");

        btn_timkiem.setText("Tìm kiếm");
        btn_timkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_timkiemActionPerformed(evt);
            }
        });

        btn_capnhat.setText("Cập nhật độc giả");
        btn_capnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_capnhatActionPerformed(evt);
            }
        });

        btn_them.setText("Thêm độc giả");
        btn_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themActionPerformed(evt);
            }
        });

        btn_xoa.setText("Xóa độc giả");
        btn_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(btn_them, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(btn_capnhat, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(btn_xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addComponent(btn_timkiem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbgioitinh, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txttendocgia, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(84, 84, 84)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtsodienthoai)
                            .addComponent(txtdiachi, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttendocgia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(txtsodienthoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbbgioitinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtdiachi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_timkiem, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_them)
                    .addComponent(btn_capnhat)
                    .addComponent(btn_xoa))
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(83, Short.MAX_VALUE)
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
        addDocGia();
    }//GEN-LAST:event_btn_themActionPerformed

    private void btn_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaActionPerformed
        removeDocGia();
    }//GEN-LAST:event_btn_xoaActionPerformed

    private void tbl_docgiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_docgiaMouseClicked
        clickDocGia();
    }//GEN-LAST:event_tbl_docgiaMouseClicked

    private void btn_capnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_capnhatActionPerformed
        updateDocGia();
    }//GEN-LAST:event_btn_capnhatActionPerformed

    private void btn_timkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_timkiemActionPerformed
        
    }//GEN-LAST:event_btn_timkiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_capnhat;
    private javax.swing.JButton btn_them;
    private javax.swing.JButton btn_timkiem;
    private javax.swing.JButton btn_xoa;
    private javax.swing.JComboBox<String> cbbgioitinh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private swing.RoundedPanel roundedPanel1;
    private javax.swing.JTable tbl_docgia;
    private javax.swing.JTextField txt_timkiem;
    private javax.swing.JTextField txtdiachi;
    private javax.swing.JTextField txtsodienthoai;
    private javax.swing.JTextField txttendocgia;
    // End of variables declaration//GEN-END:variables
}

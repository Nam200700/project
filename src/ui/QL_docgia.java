/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import javax.swing.JOptionPane;
import DAO.DocGiaDAO;
import Entity.DocGia;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.text.ParseException;
import raven.drawer.TabbedForm;
import util.jdbchelper;

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
    }

    public void addDocGia() {
        // Kiểm tra từng trường nhập liệu
        if (txttendocgia.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên độc giả!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return ;
        }

        if (cbbgioitinh.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return ;
        }

        if (txtsodienthoai.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return ;
        }

        if (!txtsodienthoai.getText().matches("\\d{10,11}")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return ;
        }

        if (txtemail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập email!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return ;
        }

        if (!txtemail.getText().matches("^[\\w-]+(\\.[\\w-]+)*@[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*(\\.[a-zA-Z]{2,})$")) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return ;
        }

        if (txtngaydangki.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày đăng ký!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return ;
        }

        if (txtmataikhoan.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã tài khoản!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return ;
        }

        // Kiểm tra email đã tồn tại hay chưa
        String checkEmailSQL = "SELECT * FROM taikhoan WHERE Email = ?";
        try (ResultSet rsEmail = jdbchelper.executeQuery(checkEmailSQL, txtemail.getText())) {
            if (rsEmail != null && rsEmail.next()) {
                JOptionPane.showMessageDialog(null, "Email đã tồn tại, vui lòng sử dụng email khác!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return ;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kiểm tra email: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return ;
        }

        // Chuyển đổi ngày đăng ký từ chuỗi sang java.sql.Date
        String ngayDangKyStr = txtngaydangki.getText().trim();
        Date ngayDangKy;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date utilDate = dateFormat.parse(ngayDangKyStr);
            ngayDangKy = new Date(utilDate.getTime());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Ngày đăng ký không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return ;
        }

        // Chuyển đổi mã tài khoản từ String sang int
        int maTaiKhoan;
        try {
            maTaiKhoan = Integer.parseInt(txtmataikhoan.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã tài khoản phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return ;
        }

        // Tạo đối tượng độc giả
        DocGia dg = new DocGia();
        dg.setHoTen(txttendocgia.getText().trim());
        dg.setGioiTinh(cbbgioitinh.getSelectedItem().toString());
        dg.setSoDienThoai(txtsodienthoai.getText().trim());
        dg.setEmail(txtemail.getText().trim());
        dg.setNgayDangKy(ngayDangKy);
        dg.setMaTaiKhoan(maTaiKhoan);

        // Thêm vào danh sách và cập nhật giao diện
        try {
            DocGiaDAO.insert(dg);
            docgialist.add(dg);
            fillTable();
            clean();
            return ;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm độc giả: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return ;
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

        if (txtemail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập email!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!txtemail.getText().matches("^[\\w-]+(\\.[\\w-]+)*@[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*(\\.[a-zA-Z]{2,})$")) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (txtngaydangki.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày đăng ký!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (txtmataikhoan.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã tài khoản!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Chuyển đổi ngày đăng ký từ String sang java.sql.Date
        String ngayDangKyStr = txtngaydangki.getText().trim();
        Date ngayDangKy;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date utilDate = dateFormat.parse(ngayDangKyStr);
            ngayDangKy = new Date(utilDate.getTime());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Ngày đăng ký không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Chuyển đổi mã tài khoản từ String sang int
        int maTaiKhoan;
        try {
            maTaiKhoan = Integer.parseInt(txtmataikhoan.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã tài khoản phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Cập nhật thông tin độc giả trong danh sách
        DocGia docgia = docgialist.get(index);
        docgia.setHoTen(txttendocgia.getText().trim());
        docgia.setGioiTinh(cbbgioitinh.getSelectedItem().toString());
        docgia.setSoDienThoai(txtsodienthoai.getText().trim());
        docgia.setEmail(txtemail.getText().trim());
        docgia.setNgayDangKy(ngayDangKy);
        docgia.setMaTaiKhoan(maTaiKhoan);
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
                int maDocGia = (int) tbl_docgia.getValueAt(index, 0);
                boolean isDeleted = DocGiaDAO.delete(maDocGia);
                if (isDeleted) {
                    docgialist.removeIf(dg -> dg.getMaDocGia() == maDocGia);
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
            Object[] row = new Object[]{dg.getMaDocGia(), dg.getHoTen(), dg.getGioiTinh(), dg.getSoDienThoai(), dg.getEmail(), dg.getNgayDangKy(), dg.getMaTaiKhoan()};
            model.addRow(row);
        }
    }

    public void loadRowIndexField(int rowIndex) {
        int maDocGia = (int) tbl_docgia.getValueAt(rowIndex, 0);
        String hoTen = (String) tbl_docgia.getValueAt(rowIndex, 1);
        String gioiTinh = (String) tbl_docgia.getValueAt(rowIndex, 2);
        String soDienThoai = (String) tbl_docgia.getValueAt(rowIndex, 3);
        String email = (String) tbl_docgia.getValueAt(rowIndex, 4);
        Date ngayDangKy = (Date) tbl_docgia.getValueAt(rowIndex, 5);
        int maTaiKhoan = (int) tbl_docgia.getValueAt(rowIndex, 6);

        txttendocgia.setText(hoTen);
        cbbgioitinh.setSelectedItem(gioiTinh);
        txtsodienthoai.setText(soDienThoai);
        txtemail.setText(email);
        txtmataikhoan.setText(String.valueOf(maTaiKhoan));
    }

    public void clean() {
        txttendocgia.setText("");
        cbbgioitinh.setSelectedIndex(0);
        txtsodienthoai.setText("");
        txtemail.setText("");
        txtmataikhoan.setText("");
        txtngaydangki.setText("");
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
            String email = tbl_docgia.getValueAt(row, 4).toString();     // Email
            String ngayDangKy = tbl_docgia.getValueAt(row, 5).toString(); // Ngày đăng ký
            String maTaiKhoan = tbl_docgia.getValueAt(row, 6).toString(); // Mã tài khoản

            // Cập nhật các trường nhập liệu
            txttendocgia.setText(hoTen);
            cbbgioitinh.setSelectedItem(gioiTinh);
            txtsodienthoai.setText(soDienThoai);
            txtemail.setText(email);
            txtngaydangki.setText(ngayDangKy);
            txtmataikhoan.setText(maTaiKhoan);
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
        tbl_docgia = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        txttendocgia = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtsodienthoai = new javax.swing.JTextField();
        txtemail = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtngaydangki = new javax.swing.JTextField();
        txtmataikhoan = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cbbgioitinh = new javax.swing.JComboBox<>();

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

        tbl_docgia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã độc giả", "Tên độc giả", "Giới tính", "Số điện thoại", "Email", "Ngày đăng kí", "Mã tài khoản"
            }
        ));
        tbl_docgia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_docgiaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_docgia);

        jButton4.setText("Tìm kiếm");

        jLabel1.setText("Tên độc giả");

        jLabel2.setText("Giới tính");

        jLabel3.setText("Số điện thoại");

        jLabel4.setText("Email");

        jLabel5.setText("Ngày đăng kí");

        jLabel6.setText("Mã tài khoản");

        cbbgioitinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(194, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 784, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_capnhat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_them, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_xoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jButton4))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbgioitinh, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txttendocgia, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(70, 70, 70)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtsodienthoai)
                            .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtngaydangki)
                            .addComponent(txtmataikhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(33, 33, 33))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttendocgia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(txtsodienthoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtngaydangki, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtmataikhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(cbbgioitinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_them)
                        .addGap(18, 18, 18)
                        .addComponent(btn_capnhat)
                        .addGap(18, 18, 18)
                        .addComponent(btn_xoa)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addContainerGap(9, Short.MAX_VALUE))
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_capnhat;
    private javax.swing.JButton btn_them;
    private javax.swing.JButton btn_xoa;
    private javax.swing.JComboBox<String> cbbgioitinh;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tbl_docgia;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txtmataikhoan;
    private javax.swing.JTextField txtngaydangki;
    private javax.swing.JTextField txtsodienthoai;
    private javax.swing.JTextField txttendocgia;
    // End of variables declaration//GEN-END:variables
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import DAO.KhuVucSachDao;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import raven.drawer.TabbedForm;
import swing.RoundTablekhuvucsach;
import java.sql.Connection;
import util.jdbchelper;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author ACER
 */
public class QL_khuvucsach extends TabbedForm {

    public Connection conn;

    /**
     * Creates new form QL_khuvucsach
     */
    public QL_khuvucsach()  {
        initComponents();
        guimuonsach();
        try {
            loadComboBoxData();
        } catch (SQLException ex) {
        }
        loadDataToTable();
    }

    public void guimuonsach() {
        // Áp dụng FlatLaf
        FlatLightLaf.setup();
        UIManager.put("Component.arc", 20); // Bo góc toàn bộ UI

        // Kiểm tra nếu `tbltheloai` chưa khởi tạo
        DefaultTableModel model = (DefaultTableModel) tbl_khuvucsach.getModel();
        tbl_khuvucsach = new RoundTablekhuvucsach(model); // Tạo lại với model cũ

        tbl_khuvucsach.setPreferredScrollableViewportSize(new Dimension(500, 200)); // Kích thước mặc định

        // Đảm bảo jScrollPane1 đang chứa `tbltacgia`
        jScrollPane1.setViewportView(tbl_khuvucsach); // Cập nhật bảng vào ScrollPane
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder()); // Xóa viền
        jScrollPane1.getViewport().setOpaque(false); // Nền trong suốt

        // Bo góc cho JTextField
        txt_tenkhuvuc.putClientProperty("JComponent.roundRect", true);
        txt_tenkhuvuc.putClientProperty("JTextField.placeholderText", "Nhập tên khu vực...");
        txt_tenkhuvuc.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txt_ke.putClientProperty("JComponent.roundRect", true);
        txt_ke.putClientProperty("JTextField.placeholderText", "Nhập số kệ...");
        txt_ke.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txt_tang.putClientProperty("JComponent.roundRect", true);
        txt_tang.putClientProperty("JTextField.placeholderText", "Nhập tầng...");
        txt_tang.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txt_vitri.putClientProperty("JComponent.roundRect", true);
        txt_vitri.putClientProperty("JTextField.placeholderText", "Nhập vị trí...");
        txt_vitri.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cbo_MaSach.putClientProperty("JComponent.roundRect", true);
        cbo_MaSach.putClientProperty("JTextField.placeholderText", "Nhập mã sách...");
        cbo_MaSach.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txt_soluong.putClientProperty("JComponent.roundRect", true);
        txt_soluong.putClientProperty("JTextField.placeholderText", "Nhập số lượng...");
        txt_soluong.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Cập nhật lại JButton với bo góc
        btn_them.setIcon(new ImageIcon(getClass().getResource("/image/add.png")));
        btn_sua.setIcon(new ImageIcon(getClass().getResource("/image/edit.png")));
        btn_xoa.setIcon(new ImageIcon(getClass().getResource("/image/delete.png")));

        // Refresh UI
        SwingUtilities.updateComponentTreeUI(jScrollPane1);
    }

//     Phương thức lấy tên sách và thêm vào ComboBox
    public void loadComboBoxData() throws SQLException {
        // Kết nối cơ sở dữ liệu
        Connection con = jdbchelper.getconnection();
        String sql = "SELECT MaSach FROM SACH"; // Truy vấn lấy tên sách
        try (PreparedStatement pst = con.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {

            // Xóa dữ liệu cũ trong ComboBox
            cbo_MaSach.removeAllItems();

            // Thêm tên sách vào ComboBox
            while (rs.next()) {
                cbo_MaSach.addItem(rs.getString("MaSach"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void loadDataToTable() {
        // Tạo mô hình bảng
        DefaultTableModel model = (DefaultTableModel) tbl_khuvucsach.getModel();

        // Làm sạch bảng trước khi thêm dữ liệu mới
        model.setRowCount(0);

        // Kết nối cơ sở dữ liệu và truy vấn dữ liệu
        try (Connection conn = jdbchelper.getconnection()) {
            String sql = "SELECT * FROM KhuVucSach"; // Truy vấn tất cả dữ liệu từ bảng KhuVucSach
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // Duyệt qua các dòng dữ liệu từ ResultSet và thêm vào JTable
            while (rs.next()) {
                String maKhuVuc = rs.getString("MaKhuVuc");
                String tenKhuVuc = rs.getString("TenKhuVuc");
                String tang = rs.getString("Tang");
                String ke = rs.getString("Ke");
                String vitri = rs.getString("Vitri");
                String maSach = rs.getString("MaSach");
                String soLuong = rs.getString("SoLuong");

                // Thêm dòng vào model bảng
                model.addRow(new Object[]{maKhuVuc, tenKhuVuc, tang, ke, vitri, maSach, soLuong});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy dữ liệu từ cơ sở dữ liệu.");
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
        btn_them = new javax.swing.JButton();
        btn_xoa = new javax.swing.JButton();
        btn_sua = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_khuvucsach = new javax.swing.JTable();
        txt_TìmKiem = new javax.swing.JTextField();
        btn_TimKiem = new javax.swing.JButton();
        txt_tenkhuvuc = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_tang = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_ke = new javax.swing.JTextField();
        txt_vitri = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_soluong = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cbo_MaSach = new javax.swing.JComboBox<>();

        setOpaque(false);

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

        tbl_khuvucsach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã khu vực", "Tên khu vực", "Tầng", "Dãy kệ", "Vị trí", "Mã sách", "Số lượng"
            }
        ));
        tbl_khuvucsach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_khuvucsachMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_khuvucsach);

        btn_TimKiem.setText("Tìm kiếm");
        btn_TimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TimKiemActionPerformed(evt);
            }
        });

        jLabel1.setText("Tên khu vực");

        jLabel2.setText("Tầng");

        jLabel3.setText("Kệ");

        jLabel4.setText("Vị trí");

        jLabel5.setText("Mã sách ");

        jLabel6.setText("Số lượng");

        cbo_MaSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_MaSachActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txt_tenkhuvuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addComponent(txt_tang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txt_ke, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(57, 57, 57)
                                .addComponent(jLabel3)))
                        .addGap(18, 18, 18)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(56, 56, 56)
                                .addComponent(jLabel5))
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addComponent(txt_vitri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbo_MaSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(16, 16, 16)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txt_soluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addComponent(btn_TimKiem)
                        .addGap(18, 18, 18)
                        .addComponent(txt_TìmKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_sua)
                    .addComponent(btn_xoa)
                    .addComponent(btn_them))
                .addGap(77, 77, 77))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 826, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_tenkhuvuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_tang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_ke, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_vitri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_soluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbo_MaSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_TìmKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_TimKiem)))
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addComponent(btn_sua)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_xoa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_them)))
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(120, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbo_MaSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_MaSachActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cbo_MaSachActionPerformed

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
        // Lấy giá trị từ các trường nhập liệu
        String tenKhuVuc = txt_tenkhuvuc.getText().trim();
        String tang = txt_tang.getText().trim();
        String ke = txt_ke.getText().trim();
        String vitri = txt_vitri.getText().trim();
        String maSach = cbo_MaSach.getSelectedItem().toString();
        String soLuong = txt_soluong.getText().trim();

        // Kiểm tra nếu có trường nào rỗng
        if (tenKhuVuc.isEmpty() || tang.isEmpty() || ke.isEmpty() || vitri.isEmpty() || soLuong.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Câu lệnh SQL để thêm dữ liệu vào bảng KhuVucSach
        String sql = "INSERT INTO KhuVucSach (TenKhuVuc, Tang, Ke, ViTri, MaSach, SoLuong) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = jdbchelper.getconnection(); PreparedStatement pst = con.prepareStatement(sql)) {

            // Thiết lập các tham số cho câu lệnh SQL
            pst.setString(1, tenKhuVuc);
            pst.setString(2, tang);
            pst.setString(3, ke);
            pst.setString(4, vitri);
            pst.setString(5, maSach);
            pst.setString(6, soLuong);

            // Thực thi câu lệnh
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Thêm khu vực sách thành công.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadDataToTable();  // Cập nhật bảng sau khi thêm
            } else {
                JOptionPane.showMessageDialog(this, "Thêm khu vực sách thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm khu vực sách.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btn_themActionPerformed

    private void btn_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaActionPerformed
        // TODO add your handling code here:
         int row = tbl_khuvucsach.getSelectedRow(); // Lấy dòng được chọn từ bảng
    if (row != -1) {  // Kiểm tra nếu có dòng được chọn
        String maKhuVuc = tbl_khuvucsach.getValueAt(row, 0).toString(); // Lấy mã khu vực từ dòng được chọn

        // Xóa dữ liệu trong cơ sở dữ liệu
        try (Connection conn = jdbchelper.getconnection()) {
            String sql = "DELETE FROM KhuVucSach WHERE MaKhuVuc = ?"; // Truy vấn xóa
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maKhuVuc);
            int rowsDeleted = stmt.executeUpdate(); // Thực hiện xóa
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Xóa thành công!");
                loadDataToTable(); // Làm mới bảng
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy khu vực để xóa!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xóa khu vực!");
        }
    } else {
        JOptionPane.showMessageDialog(null, "Vui lòng chọn khu vực để xóa!");
    }
    }//GEN-LAST:event_btn_xoaActionPerformed

    private void btn_suaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaActionPerformed
        // TODO add your handling code here:
        // Lấy dữ liệu từ các trường nhập liệu
    String tenKhuVuc = txt_tenkhuvuc.getText();
    String tang = txt_tang.getText();
    String ke = txt_ke.getText();
    String vitri = txt_vitri.getText();
    String maSach = cbo_MaSach.getSelectedItem().toString();
    String soLuong = txt_soluong.getText();

    // Kiểm tra nếu có trường nào chưa điền thông tin
    if (tenKhuVuc.isEmpty() || tang.isEmpty() || ke.isEmpty() || vitri.isEmpty() || soLuong.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!");
        return;
    }

    // Lấy mã khu vực từ bảng để cập nhật
    int row = tbl_khuvucsach.getSelectedRow();
    if (row != -1) {
        String maKhuVuc = tbl_khuvucsach.getValueAt(row, 0).toString();

        // Cập nhật dữ liệu trong cơ sở dữ liệu
        try (Connection conn = jdbchelper.getconnection()) {
            String sql = "UPDATE KhuVucSach SET TenKhuVuc = ?, Tang = ?, Ke = ?, Vitri = ?, MaSach = ?, SoLuong = ? WHERE MaKhuVuc = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, tenKhuVuc);
            stmt.setString(2, tang);
            stmt.setString(3, ke);
            stmt.setString(4, vitri);
            stmt.setString(5, maSach);
            stmt.setString(6, soLuong);
            stmt.setString(7, maKhuVuc);
            
            int rowsUpdated = stmt.executeUpdate(); // Thực hiện cập nhật
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
                loadDataToTable(); // Làm mới bảng
            } else {
                JOptionPane.showMessageDialog(null, "Cập nhật không thành công!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật khu vực!");
        }
    } else {
        JOptionPane.showMessageDialog(null, "Vui lòng chọn khu vực để sửa!");
    }
    }//GEN-LAST:event_btn_suaActionPerformed

    private void tbl_khuvucsachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_khuvucsachMouseClicked
        // TODO add your handling code here:
          // Lấy dòng được chọn trong bảng
    int row = tbl_khuvucsach.getSelectedRow();
    if (row != -1) {
        // Điền dữ liệu vào các trường nhập liệu
        txt_tenkhuvuc.setText(tbl_khuvucsach.getValueAt(row, 1).toString());
        txt_tang.setText(tbl_khuvucsach.getValueAt(row, 2).toString());
        txt_ke.setText(tbl_khuvucsach.getValueAt(row, 3).toString());
        txt_vitri.setText(tbl_khuvucsach.getValueAt(row, 4).toString());
        cbo_MaSach.setSelectedItem(tbl_khuvucsach.getValueAt(row, 5).toString());
        txt_soluong.setText(tbl_khuvucsach.getValueAt(row, 6).toString());
    }
    }//GEN-LAST:event_tbl_khuvucsachMouseClicked

    private void btn_TimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TimKiemActionPerformed
        // TODO add your handling code here:
         String keyword = txt_TìmKiem.getText().trim(); // Lấy từ khóa tìm kiếm từ JTextField
    
    if (keyword.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Vui lòng nhập từ khóa tìm kiếm!");
        return;
    }

    // Thực hiện tìm kiếm trong cơ sở dữ liệu
    try (Connection conn = jdbchelper.getconnection()) {
        String sql = "SELECT * FROM KhuVucSach WHERE TenKhuVuc LIKE ? OR Tang LIKE ? OR Ke LIKE ? OR Vitri LIKE ? OR MaSach LIKE ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        // Dùng dấu "%" để tìm kiếm một phần của chuỗi
        String searchTerm = "%" + keyword + "%";
        stmt.setString(1, searchTerm);
        stmt.setString(2, searchTerm);
        stmt.setString(3, searchTerm);
        stmt.setString(4, searchTerm);
        stmt.setString(5, searchTerm);
        
        ResultSet rs = stmt.executeQuery();
        DefaultTableModel model = (DefaultTableModel) tbl_khuvucsach.getModel();
        model.setRowCount(0); // Xóa các dòng hiện tại trong bảng

        while (rs.next()) {
            Object[] row = new Object[7];
            row[0] = rs.getString("MaKhuVuc");
            row[1] = rs.getString("TenKhuVuc");
            row[2] = rs.getString("Tang");
            row[3] = rs.getString("Ke");
            row[4] = rs.getString("Vitri");
            row[5] = rs.getString("MaSach");
            row[6] = rs.getString("SoLuong");
            model.addRow(row); // Thêm dòng vào bảng
        }

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy kết quả nào!");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm!");
    }
    }//GEN-LAST:event_btn_TimKiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_TimKiem;
    private javax.swing.JButton btn_sua;
    private javax.swing.JButton btn_them;
    private javax.swing.JButton btn_xoa;
    private javax.swing.JComboBox<String> cbo_MaSach;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private swing.RoundedPanel roundedPanel1;
    private javax.swing.JTable tbl_khuvucsach;
    private javax.swing.JTextField txt_TìmKiem;
    private javax.swing.JTextField txt_ke;
    private javax.swing.JTextField txt_soluong;
    private javax.swing.JTextField txt_tang;
    private javax.swing.JTextField txt_tenkhuvuc;
    private javax.swing.JTextField txt_vitri;
    // End of variables declaration//GEN-END:variables
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import Entity.TacGia;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import util.jdbchelper;
import java.sql.*;
import DAO.TacGiaDAO;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import raven.drawer.TabbedForm;
import swing.RoundButton;
import swing.ImageHeaderRenderer;
import swing.RoundTable;

/**
 *
 * @author ACER
 */
public class QL_tacgia extends TabbedForm {

    List<TacGia> tacgiasach = new ArrayList<>();

    /**
     * Creates new form QL_tacgia
     */
    public QL_tacgia() {
        initComponents();
        loadDataToTable();
        clearFields();
        guitacgia();
    }
    public void guitacgia() {
        // Áp dụng FlatLaf
        FlatLightLaf.setup();
        UIManager.put("Component.arc", 20); // Bo góc toàn bộ UI

        // Kiểm tra nếu `tbltacgia` chưa khởi tạo
        if (tbltacgia == null) {
            DefaultTableModel model = new DefaultTableModel(
                    new Object[][]{},
                    new String[]{"Mã TG", "Tên tác giả", "Năm sinh"}
            );
            tbltacgia = new RoundTable(model);
        } else {
            DefaultTableModel model = (DefaultTableModel) tbltacgia.getModel();
            tbltacgia = new RoundTable(model); // Tạo lại với model cũ
        }

        tbltacgia.setPreferredScrollableViewportSize(new Dimension(500, 200)); // Kích thước mặc định

        // Đảm bảo jScrollPane1 đang chứa `tbltacgia`
        jScrollPane1.setViewportView(tbltacgia); // Cập nhật bảng vào ScrollPane
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder()); // Xóa viền
        jScrollPane1.getViewport().setOpaque(false); // Nền trong suốt

        // Bo góc cho JTextField
        txtTenTacGia.putClientProperty("JComponent.roundRect", true);
        txtTenTacGia.putClientProperty("JTextField.placeholderText", "Nhập tên tác giả...");
        txtTenTacGia.putClientProperty("JTextField.showClearButton", true);
        txtTenTacGia.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Cập nhật lại JButton với bo góc
        btnThem.setIcon(new ImageIcon(getClass().getResource("/image/add.png")));
        btnCapNhat.setIcon(new ImageIcon(getClass().getResource("/image/edit.png")));
        btnXoa.setIcon(new ImageIcon(getClass().getResource("/image/delete.png")));

        // Refresh UI
        SwingUtilities.updateComponentTreeUI(jScrollPane1);
    }
    public void addTacGia() {
        // Kiểm tra các trường nhập liệu
        if (txtTenTacGia.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập tên tác giả", "Error", JOptionPane.WARNING_MESSAGE);
            JOptionPane.showMessageDialog(this, "Thêm thất bại", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Tạo đối tượng tac gia
        TacGia tg = new TacGia();

        tg.setTenTacGia(txtTenTacGia.getText());

        // Thêm ngành học vào danh sách và cập nhật giao diện
        tacgiasach.add(tg);
        // Lưu tac gia vào cơ sở dữ liệu
        TacGiaDAO.insert(tg);
        fillTable();
        clean();
    }

    public void updateTacGia() {
        // Kiểm tra xem có dòng nào được chọn không
        int index = tbltacgia.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Chưa chọn hàng nào để cập nhật!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Kiểm tra danh sách có dữ liệu hợp lệ không
        if (index >= tacgiasach.size()) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lấy thông tin tác giả từ danh sách
        TacGia tacgia = tacgiasach.get(index);
        String tenTacGia = txtTenTacGia.getText().trim();

        // Kiểm tra tên tác giả có bị rỗng không
        if (tenTacGia.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên tác giả không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Cập nhật thông tin tác giả
        tacgia.setTenTacGia(tenTacGia);

        // Cập nhật vào cơ sở dữ liệu
        try {
            TacGiaDAO.update(tacgia);
            fillTable();
            JOptionPane.showMessageDialog(this, "Cập nhật tác giả thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật tác giả: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void removetacgia() {
        // Lấy danh sách các dòng được chọn trong bảng
        int[] selectedRows = tbltacgia.getSelectedRows();

        // Kiểm tra nếu không có dòng nào được chọn
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Chưa chọn tác giả nào để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Xác nhận trước khi xóa
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa tác giả đã chọn?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            try {
                // Duyệt qua từng dòng được chọn và xóa khỏi cơ sở dữ liệu
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    int index = selectedRows[i];
                    String tentacgia = (String) tbltacgia.getValueAt(index, 0); // Lấy tên tac gia

                    // Xóa khỏi cơ sở dữ liệu
                    boolean isDeleted = TacGiaDAO.delete(tentacgia);

                    if (isDeleted) {
                        // Xóa khỏi danh sách tacgia
                        tacgiasach.removeIf(tg -> tg.getTenTacGia().equals(tentacgia));
                    } else {
                        JOptionPane.showMessageDialog(this, "Không thể xóa tác giả" + tentacgia + "' do ràng buộc dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Cập nhật lại bảng sau khi xóa
                fillTable();
                JOptionPane.showMessageDialog(this, "Xóa tác giả thành công!");

                // Nếu còn dữ liệu trong bảng, chọn lại một dòng
                if (tbltacgia.getRowCount() > 0) {
                    int newIndex = Math.min(selectedRows[0], tbltacgia.getRowCount() - 1);
                    tbltacgia.setRowSelectionInterval(newIndex, newIndex);
                    loadRowindexfield(newIndex);
                } else {
                    clean(); // Nếu không còn dữ liệu, làm sạch form
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa tác giả: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void fillTable() {
        // Lấy dữ liệu từ cơ sở dữ liệu
        List<TacGia> tacgia1 = TacGiaDAO.getAll();

        tacgiasach.clear(); // Xóa danh sách cũ
        tacgiasach.addAll(tacgia1); // Cập nhật danh sách mới

        // Cập nhật JTable
        DefaultTableModel model = (DefaultTableModel) tbltacgia.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ trong bảng

        for (TacGia tg : tacgiasach) {
            Object[] row = new Object[]{tg.getMaTacGia(), tg.getTenTacGia()};
            model.addRow(row); // Thêm dữ liệu vào bảng
        }
    }

    public void loadRowindexfield(int newrowintdex) {
        String matacgia = (String) tbltacgia.getValueAt(newrowintdex, 0);
        String tentacgia = (String) tbltacgia.getValueAt(newrowintdex, 1);

        txtTenTacGia.setText(tentacgia);

    }

    public void clean() {
        txtTenTacGia.setText("");
    }

    public void clickTacGia() {
        // Kiểm tra xem bảng có dữ liệu hay không
        if (tbltacgia.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu trong bảng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy chỉ số dòng được chọn
        int row = tbltacgia.getSelectedRow();

        // Kiểm tra xem có dòng nào được chọn không
        if (row != -1) {
            // Lấy dữ liệu từ bảng và điền vào các trường nhập liệu
            String hoTen = tbltacgia.getValueAt(row, 1).toString();     // Họ tên

            // Cập nhật các trường nhập liệu
            txtTenTacGia.setText(hoTen);

        } else {
            // Nếu không có dòng nào được chọn
            JOptionPane.showMessageDialog(this, "Chưa chọn dòng nào!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void loadDataToTable() {
        DefaultTableModel model = (DefaultTableModel) tbltacgia.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        try (Connection conn = jdbchelper.getconnection(); PreparedStatement stmt = conn.prepareStatement("SELECT * FROM TacGia"); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("MaTacGia"),
                    rs.getString("TenTacGia")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        txtTenTacGia.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundPanel1 = new swing.RoundPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbltacgia = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtTenTacGia = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(939, 570));

        roundPanel1.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel1.setPreferredSize(new java.awt.Dimension(831, 483));

        tbltacgia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã tác giả", "Tên tác giả"
            }
        ));
        tbltacgia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbltacgiaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbltacgia);

        jLabel1.setText("Tên tác giả");

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnCapNhat.setText("Cập Nhật");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 759, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabel1)
                        .addGap(27, 27, 27)
                        .addComponent(txtTenTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(207, 207, 207)
                        .addComponent(btnThem)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa)
                        .addGap(18, 18, 18)
                        .addComponent(btnCapNhat)))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel1Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTenTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem)
                    .addComponent(btnXoa)
                    .addComponent(btnCapNhat))
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(56, Short.MAX_VALUE)
                .addComponent(roundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 852, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addComponent(roundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        addTacGia();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        updateTacGia();
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void tbltacgiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbltacgiaMouseClicked
        clickTacGia();
    }//GEN-LAST:event_tbltacgiaMouseClicked

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        removetacgia();
    }//GEN-LAST:event_btnXoaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private swing.RoundPanel roundPanel1;
    private javax.swing.JTable tbltacgia;
    private javax.swing.JTextField txtTenTacGia;
    // End of variables declaration//GEN-END:variables
}

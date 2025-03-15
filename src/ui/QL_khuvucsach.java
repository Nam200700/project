/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author ACER
 */
public class QL_khuvucsach extends javax.swing.JPanel {

    /**
     * Creates new form QL_khuvucsach
     */
    public QL_khuvucsach() {
        initComponents();
    }

    private void openFormCon() {
        JDialog formCon = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm khu vực", true);
        formCon.setSize(400, 300);
        formCon.setLayout(new BorderLayout());

        // Panel chứa form nhập liệu
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10)); // 4 hàng, 2 cột
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Tạo khoảng cách với viền

        panel.add(new JLabel("Mã khu vực:"));
        JTextField txtMaKhuVuc = new JTextField();
        panel.add(txtMaKhuVuc);

        panel.add(new JLabel("Tên khu vực:"));
        JTextField txtTenKhuVuc = new JTextField();
        panel.add(txtTenKhuVuc);

        panel.add(new JLabel("Dãy kệ:"));
        JTextField txtDayKe = new JTextField();
        panel.add(txtDayKe);

        panel.add(new JLabel("Số kệ:"));
        JTextField txtSoKe = new JTextField();
        panel.add(txtSoKe);

        // Panel chứa nút Lưu & Hủy
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
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
        formCon.setLocationRelativeTo(this);
        formCon.setVisible(true);
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
        btn_sua = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_khuvucsach = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();

        btn_them.setText("Thêm");

        btn_xoa.setText("Xóa");

        btn_sua.setText("Sửa");

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
        jScrollPane1.setViewportView(tbl_khuvucsach);

        jButton2.setText("Tìm kiếm");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btn_xoa)
                        .addComponent(btn_them))
                    .addComponent(btn_sua))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(jButton2)
                        .addGap(58, 58, 58))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 678, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(btn_them)
                        .addGap(28, 28, 28)
                        .addComponent(btn_xoa)
                        .addGap(27, 27, 27)
                        .addComponent(btn_sua))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(48, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_sua;
    private javax.swing.JButton btn_them;
    private javax.swing.JButton btn_xoa;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tbl_khuvucsach;
    // End of variables declaration//GEN-END:variables
}

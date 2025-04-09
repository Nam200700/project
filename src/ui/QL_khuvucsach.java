/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;
import DAO.KhuVucSachDao; // Nếu DAO nằm trong package Entity
import static DAO.SachDAO.getAll;
import Entity.KhuVucSach;
import Entity.Sach;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
/**
 *
 * @author ACER
 */
public class QL_khuvucsach extends TabbedForm {
     public Connection conn;
    private KhuVucSachDao dao;  // Khai báo đối tượng dao
    /**
     * Creates new form QL_khuvucsach
     */
    public QL_khuvucsach() {
        initComponents();
    try {
        this.dao = new KhuVucSachDao(jdbchelper.getconnection()); // Khởi tạo dao tại đây
        loadKhuVucToTable();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }

        guimuonsach();
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
      
        // Cập nhật lại JButton với bo góc
        btn_them.setIcon(new ImageIcon(getClass().getResource("/image/add.png")));
        btn_sua.setIcon(new ImageIcon(getClass().getResource("/image/edit.png")));
        btn_xoa.setIcon(new ImageIcon(getClass().getResource("/image/delete.png")));

        // Refresh UI
        SwingUtilities.updateComponentTreeUI(jScrollPane1);
    }
    
     public void loadKhuVucToTable() throws SQLException {
        String[] columnNames = {"Mã khu vực", "Tên khu vực", "Tầng", "Dãy kệ", "Vị trí", "Số lượng"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        KhuVucSachDao dao = new KhuVucSachDao(jdbchelper.getconnection());
        List<KhuVucSach> list = dao.getAllKhuVucWithSoLuong();

        for (KhuVucSach kv : list) {
            Object[] row = {
                kv.getMaKhuVuc(),
                kv.getTenKhuVuc(),
                kv.getTang(),
                kv.getKe(),
                kv.getViTri(),
                kv.getSoLuong()
            };
            model.addRow(row);
        }

        tbl_khuvucsach.setModel(model);
    }

    private void clearForm() {
        // Xóa nội dung các trường nhập liệu
        txt_tenkhuvuc.setText("");
        txt_tang.setText("");
        txt_ke.setText("");
        txt_vitri.setText("");

        // Đặt lại trạng thái của các trường (nếu có) về mặc định
        // Ví dụ: nếu có các combobox, checkbox, hay radio button, bạn cũng có thể reset lại trạng thái của chúng ở đây
    }
    
    private void xemThongTin() {
    int selectedRow = tbl_khuvucsach.getSelectedRow();
if (selectedRow != -1) {
    int maKhuVuc = Integer.parseInt(tbl_khuvucsach.getValueAt(selectedRow, 0).toString());
    String tenKhuVuc = tbl_khuvucsach.getValueAt(selectedRow, 1).toString();
    hienThiThongTinKhuVucSach(maKhuVuc, tenKhuVuc);
} else {
    JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng trước!");
}

}
       
    public static List<Sach> getSachByKhuVuc(int maKhuVuc) {
    List<Sach> dsTheoKhuVuc = new ArrayList<>();
    for (Sach sach : getAll()) {
        if (sach.getMaKhuVuc() == maKhuVuc) {
            dsTheoKhuVuc.add(sach);
        }
    }
    return dsTheoKhuVuc;
}

private void hienThiThongTinKhuVucSach(int maKhuVuc, String tenKhuVuc) {
    JFrame frame = new JFrame("Thông tin khu vực sách: " + tenKhuVuc);
    frame.setSize(400, 300);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setLayout(new BorderLayout());

    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Tên sách");
    model.addColumn("Số lượng");

    List<Sach> danhSach = getSachByKhuVuc(maKhuVuc);
    for (Sach s : danhSach) {
        model.addRow(new Object[]{s.getTenSach(), s.getSoLuong()});
    }

    JTable table = new JTable(model);
    frame.add(new JScrollPane(table), BorderLayout.CENTER);

    frame.setVisible(true);
}

public void hienDataToTxt(){
     // TODO add your handling code here:
        
          int selectedRow = tbl_khuvucsach.getSelectedRow();
        
        if (selectedRow != -1) {
            // Lấy giá trị từ bảng và hiển thị lên các ô txt
            txt_tenkhuvuc.setText(tbl_khuvucsach.getValueAt(selectedRow, 1).toString()); // Giả sử cột 1 là tên khu vực
            txt_tang.setText(tbl_khuvucsach.getValueAt(selectedRow, 2).toString()); // Cột 2 là tầng
            txt_ke.setText(tbl_khuvucsach.getValueAt(selectedRow, 3).toString()); // Cột 3 là kệ
            txt_vitri.setText(tbl_khuvucsach.getValueAt(selectedRow, 4).toString()); // Cột 4 là vị trí
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

        btn_Xemthongtin = new javax.swing.JButton();
        roundedPanel1 = new swing.RoundedPanel();
        btn_them = new javax.swing.JButton();
        btn_xoa = new javax.swing.JButton();
        btn_sua = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_khuvucsach = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        btn_Timkiem = new javax.swing.JButton();
        txt_tenkhuvuc = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_tang = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_ke = new javax.swing.JTextField();
        txt_vitri = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btn_XemThongTin = new javax.swing.JButton();

        btn_Xemthongtin.setText("Xem thông tin");
        btn_Xemthongtin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XemthongtinActionPerformed(evt);
            }
        });

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
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã khu vực", "Tên khu vực", "Tầng", " Kệ", "Vị trí", "Số lượng sách"
            }
        ));
        tbl_khuvucsach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_khuvucsachMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_khuvucsach);

        btn_Timkiem.setText("Tìm kiếm");
        btn_Timkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TimkiemActionPerformed(evt);
            }
        });

        jLabel1.setText("Tên khu vực");

        jLabel2.setText("Tầng");

        jLabel3.setText("Kệ");

        jLabel4.setText("Vị trí");

        btn_XemThongTin.setText("Xem thông tin");
        btn_XemThongTin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XemThongTinActionPerformed(evt);
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
                            .addComponent(txt_vitri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addComponent(btn_Timkiem)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_sua)
                    .addComponent(btn_xoa)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addComponent(btn_XemThongTin)
                        .addGap(68, 68, 68)
                        .addComponent(btn_them)))
                .addGap(77, 77, 77))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
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
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_tenkhuvuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_tang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_ke, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_vitri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_Timkiem)))
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addComponent(btn_sua)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_xoa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_them)
                            .addComponent(btn_XemThongTin))))
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(140, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_XemthongtinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XemthongtinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_XemthongtinActionPerformed

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
        // Lấy dữ liệu từ các ô nhập
    String tenKhuVuc = txt_tenkhuvuc.getText().trim();
    String tang = txt_tang.getText().trim();
    String ke = txt_ke.getText().trim();
    String viTri = txt_vitri.getText().trim();

    // --- KIỂM TRA DỮ LIỆU NHẬP ---
    if (tenKhuVuc.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Tên khu vực không được để trống!");
        return;
    }
    if (!tenKhuVuc.matches("^[^\\d]+$")) {
        JOptionPane.showMessageDialog(this, "Tên khu vực không được chứa số!");
        return;
    }

    if (tang.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Tầng không được để trống!");
        return;
    }
    if (!tang.matches("\\d+")) {
        JOptionPane.showMessageDialog(this, "Tầng phải là số!");
        return;
    }

    if (ke.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Kệ không được để trống!");
        return;
    }
    if (!ke.matches("\\d+")) {
        JOptionPane.showMessageDialog(this, "Kệ phải là số!");
        return;
    }

    if (viTri.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vị trí không được để trống!");
        return;
    }
    if (!viTri.matches("\\d+")) {
        JOptionPane.showMessageDialog(this, "Vị trí phải là số!");
        return;
    }

    // Chuyển chuỗi sang số
    int tangInt = Integer.parseInt(tang);
    int keInt = Integer.parseInt(ke);
    int viTriInt = Integer.parseInt(viTri);

    // Tạo đối tượng KhuVucSach
    KhuVucSach khuVucSach = new KhuVucSach();
    khuVucSach.setTenKhuVuc(tenKhuVuc);
    khuVucSach.setTang(tangInt);
    khuVucSach.setKe(keInt);
    khuVucSach.setViTri(viTriInt);

    // Gọi DAO để thêm
    KhuVucSachDao dao = null;
    try {
        dao = new KhuVucSachDao(jdbchelper.getconnection());
        boolean isSuccess = dao.themKhuVuc(khuVucSach);
        if (isSuccess) {
            JOptionPane.showMessageDialog(this, "Thêm khu vực sách thành công!");
            loadKhuVucToTable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm khu vực sách.");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
    }
    }//GEN-LAST:event_btn_themActionPerformed

    private void btn_XemThongTinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XemThongTinActionPerformed
        // TODO add your handling code here:
        xemThongTin();
    }//GEN-LAST:event_btn_XemThongTinActionPerformed

    private void btn_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaActionPerformed
        // TODO add your handling code here:
         int selectedRow = tbl_khuvucsach.getSelectedRow();
        if (selectedRow != -1) {
            String maKhuVuc = tbl_khuvucsach.getValueAt(selectedRow, 0).toString(); // cột 0 là Mã khu vực
            Connection conn = null;
            try {
                conn = jdbchelper.getconnection(); // Lấy connection
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu!");
                return;
            }

            KhuVucSachDao dao = new KhuVucSachDao(conn);

            // 🔍 Kiểm tra nếu khu vực đang chứa sách
            if (dao.kiemTraKhuVucCoSach(maKhuVuc)) {
                JOptionPane.showMessageDialog(null, "Không thể xóa! Khu vực vẫn còn sách.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = dao.xoa(maKhuVuc);

                if (success) {
                    DefaultTableModel model = (DefaultTableModel) tbl_khuvucsach.getModel();
                    model.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(null, "Xóa thành công!");
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa thất bại!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng cần xóa!");
        }
    }//GEN-LAST:event_btn_xoaActionPerformed

    private void btn_suaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaActionPerformed
        // TODO add your handling code here:
         try {
        // Lấy dữ liệu từ các ô txt
        String tenKhuVuc = txt_tenkhuvuc.getText().trim();
        String tangStr = txt_tang.getText().trim();
        String keStr = txt_ke.getText().trim();
        String viTriStr = txt_vitri.getText().trim();

        // --- KIỂM TRA DỮ LIỆU NHẬP ---
        if (tenKhuVuc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên khu vực không được để trống!");
            return;
        }
        if (!tenKhuVuc.matches("^[^\\d]+$")) {
            JOptionPane.showMessageDialog(this, "Tên khu vực không được chứa số!");
            return;
        }

        if (tangStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tầng không được để trống!");
            return;
        }
        if (!tangStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Tầng phải là số!");
            return;
        }

        if (keStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Kệ không được để trống!");
            return;
        }
        if (!keStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Kệ phải là số!");
            return;
        }

        if (viTriStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vị trí không được để trống!");
            return;
        }
        if (!viTriStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Vị trí phải là số!");
            return;
        }

        int tang = Integer.parseInt(tangStr);
        int ke = Integer.parseInt(keStr);
        int viTri = Integer.parseInt(viTriStr);

        // Lấy mã khu vực từ bảng
        int selectedRow = tbl_khuvucsach.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khu vực cần sửa.");
            return;
        }

        int maKhuVuc = (int) tbl_khuvucsach.getValueAt(selectedRow, 0); // Giả sử cột 0 là mã khu vực

        // Tạo đối tượng và gọi DAO để cập nhật
        KhuVucSach khuVucSach = new KhuVucSach();
        khuVucSach.setMaKhuVuc(maKhuVuc);
        khuVucSach.setTenKhuVuc(tenKhuVuc);
        khuVucSach.setTang(tang);
        khuVucSach.setKe(ke);
        khuVucSach.setViTri(viTri);

        boolean result = dao.updateKhuVuc(khuVucSach);

        if (result) {
            JOptionPane.showMessageDialog(this, "Cập nhật khu vực thành công!");
            loadKhuVucToTable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại. Kiểm tra lại thông tin.");
        }

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho tầng, kệ, vị trí.");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        e.printStackTrace();
    }
    }//GEN-LAST:event_btn_suaActionPerformed

    private void tbl_khuvucsachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_khuvucsachMouseClicked
        // TODO add your handling code here:
       hienDataToTxt();
    }//GEN-LAST:event_tbl_khuvucsachMouseClicked

    private void btn_TimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TimkiemActionPerformed
        // TODO add your handling code here:
             // TODO add your handling code here:
String searchText = btn_Timkiem.getText().trim();  // Lấy giá trị từ ô tìm kiếm

if (searchText.isEmpty()) {
    JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin để tìm kiếm.");
    return;
}

// Chuẩn bị câu lệnh SQL với điều kiện tìm kiếm
String query = "SELECT KhuVuc.MaKhuVuc, KhuVuc.TenKhuVuc, KhuVuc.Tang, KhuVuc.Ke, KhuVuc.ViTri, "
        + "COUNT(Sach.MaSach) AS SoLuongSach "
        + "FROM KhuVuc "
        + "LEFT JOIN Sach ON KhuVuc.MaKhuVuc = Sach.MaKhuVuc "
        + "WHERE KhuVuc.TenKhuVuc LIKE ? OR KhuVuc.Tang LIKE ? OR KhuVuc.Ke LIKE ? OR KhuVuc.ViTri LIKE ? "
        + "GROUP BY KhuVuc.MaKhuVuc, KhuVuc.TenKhuVuc, KhuVuc.Tang, KhuVuc.Ke, KhuVuc.ViTri";

try (Connection conn = jdbchelper.getconnection();  // Kết nối CSDL
     PreparedStatement stmt = conn.prepareStatement(query)) {

    // Thêm điều kiện tìm kiếm vào câu lệnh SQL (Sử dụng ký tự % để tìm kiếm theo chuỗi con)
    String searchPattern = "%" + searchText + "%";  // Tạo pattern tìm kiếm (bao gồm dấu %)
    stmt.setString(1, searchPattern);  // Tìm theo tên khu vực
    stmt.setString(2, searchPattern);  // Tìm theo tầng
    stmt.setString(3, searchPattern);  // Tìm theo kệ
    stmt.setString(4, searchPattern);  // Tìm theo vị trí

    ResultSet rs = stmt.executeQuery();  // Thực thi truy vấn tìm kiếm

    // Xử lý kết quả tìm kiếm và hiển thị lên bảng
    DefaultTableModel model = (DefaultTableModel) tbl_khuvucsach.getModel();
    model.setRowCount(0);  // Xóa dữ liệu hiện tại trong bảng

    while (rs.next()) {
        // Lấy dữ liệu từ ResultSet và thêm vào bảng
        Object[] row = {
            rs.getInt("MaKhuVuc"),
            rs.getString("TenKhuVuc"),
            rs.getInt("Tang"),
            rs.getInt("Ke"),
            rs.getString("ViTri"),
            rs.getInt("SoLuongSach")  // Số lượng sách
        };
        model.addRow(row);
    }
} catch (SQLException ex) {
    JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + ex.getMessage());
    ex.printStackTrace();
}

    }//GEN-LAST:event_btn_TimkiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Timkiem;
    private javax.swing.JButton btn_XemThongTin;
    private javax.swing.JButton btn_Xemthongtin;
    private javax.swing.JButton btn_sua;
    private javax.swing.JButton btn_them;
    private javax.swing.JButton btn_xoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private swing.RoundedPanel roundedPanel1;
    private javax.swing.JTable tbl_khuvucsach;
    private javax.swing.JTextField txt_ke;
    private javax.swing.JTextField txt_tang;
    private javax.swing.JTextField txt_tenkhuvuc;
    private javax.swing.JTextField txt_vitri;
    // End of variables declaration//GEN-END:variables
}

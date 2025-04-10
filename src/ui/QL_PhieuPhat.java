/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import DAO.PhieuPhatDAO;
import Entity.PhieuPhat;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.table.DefaultTableModel;
import raven.drawer.TabbedForm;
import swing.RoundTablePhieuPhat;
import java.sql.*;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import util.jdbchelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;




/**
 *
 * @author Acer
 */
public class QL_PhieuPhat extends TabbedForm {


    public QL_PhieuPhat() {
        initComponents();
        loadMaPhieuTra();
        loadTable();
        initComboBox();

        
    }
//     public static void main(String[] args) {
//    try {
//        UIManager.setLookAndFeel(new FlatLightLaf());
//    } catch (Exception e) {
//        System.err.println("Không thể thiết lập giao diện FlatLaf");
//    }
//
//    SwingUtilities.invokeLater(() -> {
//        // Tạo JFrame và nhúng form QL_PhieuPhat vào
//        JFrame frame = new JFrame("Quản lý phiếu phạt");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(900, 600);
//        frame.setLocationRelativeTo(null);
//
//        QL_PhieuPhat form = new QL_PhieuPhat();
//        frame.setContentPane(form);  // Nếu QL_PhieuPhat kế thừa JPanel
//        frame.setVisible(true);
//    });
//}

    
     private void loadMaPhieuTra() {
    try {
        Connection conn = jdbchelper.getConnection(); // hoặc dùng kết nối riêng của bạn
        String sql = "SELECT MaPhieuTra FROM PhieuTra";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        cboMaPhieuTra.removeAllItems(); // Xóa dữ liệu cũ
        cboMaPhieuTra.addItem(""); // Thêm lựa chọn trống đầu tiên

        while (rs.next()) {
            String maPhieuTra = rs.getString("MaPhieuTra");
            cboMaPhieuTra.addItem(maPhieuTra);
        }

        rs.close();
        stmt.close();
        conn.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi khi load mã phiếu trả: " + e.getMessage());
    }
}


      private void themPhieuPhat() {
    try {
    int maPhieuTra = Integer.parseInt(cboMaPhieuTra.getSelectedItem().toString());
    double soTienPhat = Double.parseDouble(txtSoTienPhat.getText());
    String lyDo = txtLyDo.getText();
    String trangThai = cboTrangThaiThanhToan.getSelectedItem() != null ? cboTrangThaiThanhToan.getSelectedItem().toString() : "";

    // Kiểm tra trạng thái đã chọn chưa
    if (trangThai == null || trangThai.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn trạng thái thanh toán!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return; // Dừng thêm phiếu nếu chưa chọn trạng thái
    }

    PhieuPhatDAO phieuPhatDAO = new PhieuPhatDAO();
    phieuPhatDAO.addPhieuPhat(maPhieuTra, soTienPhat, lyDo, trangThai);
    
    JOptionPane.showMessageDialog(this, "Thêm phiếu phạt thành công!");
    loadTable();  // Cập nhật bảng
} catch (Exception e) {
    JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
}

}
    private void capNhatPhieuPhat() {
    try {
        int selectedRow = tblPhieuPhat.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu phạt để cập nhật!");
            return;
        }

        int maPhieuPhat = Integer.parseInt(tblPhieuPhat.getValueAt(selectedRow, 1).toString());
        int maPhieuTra = Integer.parseInt(cboMaPhieuTra.getSelectedItem().toString());
        double soTienPhat = Double.parseDouble(txtSoTienPhat.getText());
        String lyDo = txtLyDo.getText();
        String trangThai = cboTrangThaiThanhToan.getSelectedItem().toString();

        PhieuPhatDAO phieuPhatDAO = new PhieuPhatDAO();
        phieuPhatDAO.updatePhieuPhat(maPhieuPhat, maPhieuTra, soTienPhat, lyDo, trangThai);
        
        JOptionPane.showMessageDialog(this, "Cập nhật phiếu phạt thành công!");
        loadTable();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }
}


      private void xoaPhieuPhat() {
    try {
        int selectedRow = tblPhieuPhat.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu phạt để xóa!");
            return;
        }

        int maPhieuPhat = Integer.parseInt(tblPhieuPhat.getValueAt(selectedRow, 1).toString());

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            PhieuPhatDAO phieuPhatDAO = new PhieuPhatDAO();
            phieuPhatDAO.deletePhieuPhat(maPhieuPhat);
            
            JOptionPane.showMessageDialog(this, "Xóa phiếu phạt thành công!");
            loadTable();
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }
}

   private void loadTable() {
    DefaultTableModel model = (DefaultTableModel) tblPhieuPhat.getModel();
    model.setRowCount(0);
    
    PhieuPhatDAO phieuPhatDAO = new PhieuPhatDAO();
    List<PhieuPhat> list = phieuPhatDAO.getAllPhieuPhat();
    
    for (PhieuPhat p : list) {
        model.addRow(new Object[]{
            p.getMaPhieuTra(),
            p.getMaPhieuPhat(),
            p.getSoTienPhat(),
            p.getLyDo(),
            p.getTrangThaiThanhToan()
        });
    }
}

     private void initComboBox() {
    cboTrangThaiThanhToan.removeAllItems(); // Xóa các mục có sẵn
    cboTrangThaiThanhToan.addItem(""); // Mục trống mặc định
    cboTrangThaiThanhToan.addItem("Chưa thanh toán");
    cboTrangThaiThanhToan.addItem("Đã thanh toán");
  
}
     // Sự kiện click vào bảng để hiển thị dữ liệu lên các ô nhập liệu


    
  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundedPanel1 = new swing.RoundedPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPhieuPhat = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        cboMaPhieuTra = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cboTrangThaiThanhToan = new javax.swing.JComboBox<>();
        btn_taophieu = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtLyDo = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        txtSoTienPhat = new javax.swing.JTextField();
        btn_chinhsuaphieu = new javax.swing.JButton();
        btn_xoaphieu = new javax.swing.JButton();

        tblPhieuPhat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã Phiếu Trả", "Mã Phiếu Phạt", "Số Tiền Phạt", "Lý Do", "Trạng Thái Thanh Toán"
            }
        ));
        tblPhieuPhat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPhieuPhatMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPhieuPhat);

        jLabel1.setText("Mã Phiếu Trả");

        cboMaPhieuTra.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Trạng Thái Thanh Toán");

        cboTrangThaiThanhToan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboTrangThaiThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTrangThaiThanhToanActionPerformed(evt);
            }
        });

        btn_taophieu.setText("Tạo Phiếu Phạt");
        btn_taophieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_taophieuActionPerformed(evt);
            }
        });

        jLabel4.setText("Lý do :");

        txtLyDo.setColumns(20);
        txtLyDo.setRows(5);
        jScrollPane2.setViewportView(txtLyDo);

        jLabel5.setText("Số tiền phạt :");

        txtSoTienPhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoTienPhatActionPerformed(evt);
            }
        });

        btn_chinhsuaphieu.setText("Cập Nhật Phiếu");
        btn_chinhsuaphieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_chinhsuaphieuActionPerformed(evt);
            }
        });

        btn_xoaphieu.setText("Xóa Phiếu Phạt");
        btn_xoaphieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoaphieuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addContainerGap(64, Short.MAX_VALUE)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18))
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(70, 70, 70)))
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboMaPhieuTra, 0, 115, Short.MAX_VALUE)
                    .addComponent(cboTrangThaiThanhToan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(43, 43, 43)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoTienPhat, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_taophieu, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_chinhsuaphieu, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_xoaphieu, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(cboMaPhieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addGap(15, 15, 15))
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addComponent(btn_xoaphieu)
                                .addGap(18, 18, 18)))
                        .addComponent(btn_chinhsuaphieu))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)))
                .addGap(12, 12, 12)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cboTrangThaiThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoTienPhat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(btn_taophieu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboTrangThaiThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTrangThaiThanhToanActionPerformed
         
    }//GEN-LAST:event_cboTrangThaiThanhToanActionPerformed

    private void txtSoTienPhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoTienPhatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoTienPhatActionPerformed

    private void btn_taophieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_taophieuActionPerformed
    themPhieuPhat();
    }//GEN-LAST:event_btn_taophieuActionPerformed

    private void btn_chinhsuaphieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_chinhsuaphieuActionPerformed
 capNhatPhieuPhat();    }//GEN-LAST:event_btn_chinhsuaphieuActionPerformed
    
    private void btn_xoaphieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaphieuActionPerformed
  xoaPhieuPhat();    }//GEN-LAST:event_btn_xoaphieuActionPerformed

    private void tblPhieuPhatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuPhatMouseClicked
        int selectedRow = tblPhieuPhat.getSelectedRow();
    if (selectedRow != -1) {
        // Gán dữ liệu vào ComboBox mã phiếu trả
        String maPhieuTra = tblPhieuPhat.getValueAt(selectedRow, 0).toString();
        cboMaPhieuTra.setSelectedItem(maPhieuTra);

        // Gán dữ liệu vào các ô nhập còn lại
        txtSoTienPhat.setText(tblPhieuPhat.getValueAt(selectedRow, 2).toString());
        txtLyDo.setText(tblPhieuPhat.getValueAt(selectedRow, 3).toString());

        // Gán trạng thái thanh toán
        String trangThai = tblPhieuPhat.getValueAt(selectedRow, 4).toString();
        cboTrangThaiThanhToan.setSelectedItem(trangThai);
    }
    }//GEN-LAST:event_tblPhieuPhatMouseClicked
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_chinhsuaphieu;
    private javax.swing.JButton btn_taophieu;
    private javax.swing.JButton btn_xoaphieu;
    private javax.swing.JComboBox<String> cboMaPhieuTra;
    private javax.swing.JComboBox<String> cboTrangThaiThanhToan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private swing.RoundedPanel roundedPanel1;
    private javax.swing.JTable tblPhieuPhat;
    private javax.swing.JTextArea txtLyDo;
    private javax.swing.JTextField txtSoTienPhat;
    // End of variables declaration//GEN-END:variables
}

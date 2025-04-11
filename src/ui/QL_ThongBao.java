package ui;

import DAO.ThongBaoDAO;
import Entity.ThongBao;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import raven.drawer.TabbedForm;

public class QL_ThongBao extends TabbedForm {

    public QL_ThongBao() {
        initComponents();
        fillTable();
    }

    public void guiThongBao() {
        if (txtTieuDe.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập tiêu đề", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (txtNoiDung.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập nội dung", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Tạo đối tượng ThongBao
        ThongBao tb = new ThongBao();
        tb.setTieuDe(txtTieuDe.getText().trim());
        tb.setNoiDung(txtNoiDung.getText().trim());

        // Gửi (insert vào DB)
        ThongBaoDAO tbDAO = new ThongBaoDAO();
        if (tbDAO.insertForAllThuThu(tb)) {
            JOptionPane.showMessageDialog(this, "Gửi thông báo thành công!");
            clearForm(); // nếu bạn muốn xóa trắng sau khi gửi
            fillTable(); // nếu có bảng danh sách thông báo
        } else {
            JOptionPane.showMessageDialog(this, "Gửi thông báo thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        txtTieuDe.setText("");
        txtNoiDung.setText("");
    }

    public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblThongBao.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        List<ThongBao> list = new ThongBaoDAO().getAll();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (ThongBao tb : list) {
            model.addRow(new Object[]{
                tb.getMaTB(),
                tb.getTieuDe(),
                tb.getNoiDung(),
                tb.getNgayTao() != null ? sdf.format(tb.getNgayTao()) : ""
            });
        }
    }

    public void xoaThongBao() {
        int selectedRow = tblThongBao.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thông báo để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa thông báo này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int maTB = (int) tblThongBao.getValueAt(selectedRow, 0); // Cột 0 là MaTB
        ThongBaoDAO dao = new ThongBaoDAO();

        if (dao.delete(maTB)) {
            JOptionPane.showMessageDialog(this, "Xóa thông báo thành công!");
            fillTable(); // Cập nhật lại bảng
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundPanel2 = new swing.RoundPanel();
        jLabel1 = new javax.swing.JLabel();
        label = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtNoiDung = new javax.swing.JTextArea();
        txtTieuDe = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblThongBao = new javax.swing.JTable();
        btnGui = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();

        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(1100, 700));

        roundPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Tiêu đề");

        label.setText("Nội dung");

        txtNoiDung.setColumns(20);
        txtNoiDung.setRows(5);
        jScrollPane1.setViewportView(txtNoiDung);

        tblThongBao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "MaTB", "Tiêu đề", "Nội dung", "Ngày tạo"
            }
        ));
        jScrollPane3.setViewportView(tblThongBao);

        btnGui.setText("Gửi thông báo");
        btnGui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuiActionPerformed(evt);
            }
        });

        btnXoa.setText("Xóa thông báo");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel2Layout = new javax.swing.GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 834, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTieuDe, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(43, 43, 43)
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label)
                            .addGroup(roundPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnGui)
                                .addGap(48, 48, 48)
                                .addComponent(btnXoa)
                                .addGap(13, 13, 13)))))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        roundPanel2Layout.setVerticalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(label)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createSequentialGroup()
                                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnGui)
                                    .addComponent(btnXoa))
                                .addGap(34, 34, 34))))
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTieuDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(roundPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(135, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(roundPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuiActionPerformed
        guiThongBao();
    }//GEN-LAST:event_btnGuiActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        xoaThongBao();
    }//GEN-LAST:event_btnXoaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGui;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel label;
    private swing.RoundPanel roundPanel2;
    private javax.swing.JTable tblThongBao;
    private javax.swing.JTextArea txtNoiDung;
    private javax.swing.JTextField txtTieuDe;
    // End of variables declaration//GEN-END:variables
}

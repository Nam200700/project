/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import Entity.DocGia;
import Entity.Session;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import DAO.DocGiaDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import raven.drawer.TabbedForm;
import javax.swing.JFrame;

/**
 *
 * @author ACER
 */
public class QL_ThongTinTheDocGia extends TabbedForm {

   private JTextField txtHoTen, txtDiaChi, txtSoDienThoai;
    private JRadioButton rdoNam, rdoNu;
    private JButton btnDangKy;

    /**
     * Creates new form QL_ThongTinTheDocGia
     */
    public QL_ThongTinTheDocGia() {

        initComponents();
        initUI();
        clearForm();
    }
    private void clearForm() {
    txtHoTen.setText("");
    txtDiaChi.setText("");
    txtSoDienThoai.setText("");
    rdoNam.setSelected(true); // Mặc định chọn "Nam"
}
    


  private void initUI() {
    JFrame frame = new JFrame(); // Tạo một JFrame
    frame.setLayout(new BorderLayout());

    setLayout(new BorderLayout());
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBackground(new Color(240, 248, 255));
    panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.WEST;

    JLabel lblTitle = new JLabel("THẺ ĐỘC GIẢ", SwingConstants.CENTER);
    lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
    lblTitle.setForeground(new Color(25, 25, 112));

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    panel.add(lblTitle, gbc);

    gbc.gridwidth = 1;
    gbc.gridy++;
    panel.add(new JLabel("Họ và tên:"), gbc);
    txtHoTen = new JTextField(20);
    gbc.gridx = 1;
    panel.add(txtHoTen, gbc);

    gbc.gridx = 0;
    gbc.gridy++;
    panel.add(new JLabel("Giới tính:"), gbc);
    JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
    genderPanel.setOpaque(false);
    rdoNam = new JRadioButton("Nam");
    rdoNu = new JRadioButton("Nữ");
    ButtonGroup bg = new ButtonGroup();
    bg.add(rdoNam);
    bg.add(rdoNu);
    genderPanel.add(rdoNam);
    genderPanel.add(rdoNu);
    gbc.gridx = 1;
    panel.add(genderPanel, gbc);

    gbc.gridx = 0;
    gbc.gridy++;
    panel.add(new JLabel("Địa chỉ:"), gbc);
    txtDiaChi = new JTextField(20);
    gbc.gridx = 1;
    panel.add(txtDiaChi, gbc);

    gbc.gridx = 0;
    gbc.gridy++;
    panel.add(new JLabel("Số điện thoại:"), gbc);
    txtSoDienThoai = new JTextField(20);
    gbc.gridx = 1;
    panel.add(txtSoDienThoai, gbc);

    gbc.gridx = 0;
    gbc.gridy++;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    btnDangKy = new JButton("Đăng ký");
    btnDangKy.setFont(new Font("Arial", Font.BOLD, 16));
    btnDangKy.setBackground(new Color(70, 130, 180));
    btnDangKy.setForeground(Color.WHITE);
    btnDangKy.setPreferredSize(new Dimension(150, 40));

    btnDangKy.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int maTaiKhoan = Session.getMaTaiKhoan();
                if (maTaiKhoan <= 0) {
                    JOptionPane.showMessageDialog(null, "Lỗi: Không tìm thấy tài khoản!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String hoTen = txtHoTen.getText().trim();
                String diaChi = txtDiaChi.getText().trim();
                String soDienThoai = txtSoDienThoai.getText().trim();
                if (hoTen.isEmpty() || diaChi.isEmpty() || soDienThoai.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!rdoNam.isSelected() && !rdoNu.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn giới tính!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String gioiTinhStr = rdoNam.isSelected() ? "Nam" : "Nữ";

                if (!soDienThoai.matches("\\d{10}")) {
                    JOptionPane.showMessageDialog(null, "Số điện thoại phải có 10 chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Lấy ngày đăng ký tự động
                LocalDate today = LocalDate.now();
                Date ngayDangKy = Date.valueOf(today);

                DocGiaDAO dao = new DocGiaDAO();
                if (dao.kiemTraTheDocGia(maTaiKhoan)) {
                    JOptionPane.showMessageDialog(null, "Tài khoản này đã có thẻ độc giả!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                DocGia docGia = new DocGia(hoTen, gioiTinhStr, soDienThoai, diaChi, ngayDangKy, maTaiKhoan);
                if (dao.themTheDocGia(docGia)) {
                    JOptionPane.showMessageDialog(null, "Đăng ký thẻ độc giả thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(null, "Đăng ký thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi hệ thống: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    });

    panel.add(btnDangKy, gbc);
    
    // Thêm panel vào frame
    frame.add(panel, BorderLayout.CENTER);

    // Căn giữa cửa sổ JFrame
    frame.setSize(500, 400); // Thiết lập kích thước
    frame.setLocationRelativeTo(null); // Căn giữa cửa sổ
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Đóng cửa sổ khi nhấn X
    frame.setVisible(true); // Hiển thị cửa sổ
}


    private void customizeTextField(JTextField txtField, String placeholder) {
        txtField.putClientProperty("JComponent.roundRect", true);
        txtField.putClientProperty("JTextField.placeholderText", placeholder);
        txtField.putClientProperty("JTextField.showClearButton", true);
        txtField.setFont(new Font("SansSerif", Font.PLAIN, 14));

    }



//   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Giới tính:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Số điện thoại:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 514, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 418, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}

package ui;

import DAO.SachDAO;
import Entity.Sach;
import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.*;
import raven.drawer.TabbedForm;

public class QRScan extends TabbedForm {

    private Webcam webcam;
    private WebcamPanel panel;
    private boolean scanning = false;

    public QRScan() {
        initComponents();

    }

    private void initWebcam() {
        // Chọn độ phân giải webcam
        Dimension size = WebcamResolution.QVGA.getSize();  // Chọn độ phân giải QVGA (320x240)

        // Lấy webcam đầu tiên trong danh sách
        webcam = Webcam.getWebcams().get(0);  // Lấy webcam đầu tiên

        // Thiết lập độ phân giải cho webcam
        webcam.setViewSize(size);

        // Tạo WebcamPanel để hiển thị video từ webcam
        panel = new WebcamPanel(webcam);
        panel.setPreferredSize(size);  // Thiết lập kích thước cho panel
        panel.setFPSDisplayed(true);   // Hiển thị FPS

        // Đảm bảo roundPanel2 có layout để chứa WebcamPanel
        roundPanel2.setLayout(new BorderLayout());  // Đảm bảo roundPanel2 có layout để chứa panel
        roundPanel2.add(panel, BorderLayout.CENTER);  // Thêm WebcamPanel vào center của roundPanel2

        // Đảm bảo vẽ lại roundPanel2
        roundPanel2.revalidate();
        roundPanel2.repaint();
    }

    private void startQRScan() {
        if (scanning) {
            return;
        }
        scanning = true;

        // Gọi phương thức initWebcam để khởi tạo webcam và hiển thị trong roundPanel2
        initWebcam();

        // Lấy webcam mặc định
        webcam = Webcam.getDefault();
        if (webcam == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy webcam!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            scanning = false;
            return;
        }

        // Thiết lập độ phân giải của webcam
        webcam.setViewSize(new Dimension(640, 480)); // Tăng độ phân giải
        webcam.open();

        // Sử dụng một SwingWorker để quét và cập nhật giao diện
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                while (scanning) {
                    BufferedImage image = webcam.getImage();
                    if (image != null) {
                        // Quét mã QR
                        String result = decodeQRCode(image);
                        if (result != null) {
                            SwingUtilities.invokeLater(() -> {
                                // Hiển thị mã sách vào JTextField
                                String maSachQuet = result;
                                // Lấy tất cả thông tin sách từ cơ sở dữ liệu
                                List<Sach> dsSach = SachDAO.QRScangetall();
                                for (Sach sach : dsSach) {
                                    if (String.valueOf(sach.getMaSach()).equals(maSachQuet)) {
                                        // Hiển thị thông tin sách vào các JTextField
                                        txtTenSach.setText(sach.getTenSach());
                                        txtMaDauSach.setText(sach.getMaDauSach());
                                        txtNamXuatBan.setText(String.valueOf(sach.getNamXuatBan()));
                                        txtLanTaiBan.setText(String.valueOf(sach.getLanTaiBan()));
                                        txtNgonNgu.setText(sach.getNgonNgu());
                                        txtNhaXuatBan.setText(String.valueOf(sach.getTenNhaXuatBan()));
                                        txtSoLuong.setText(String.valueOf(sach.getSoLuong()));
                                        txtTheLoai.setText(String.valueOf(sach.getTenTheLoai()));
                                        txtTacgia.setText(String.valueOf(sach.getTenTacGia()));
                                        break; // Dừng vòng lặp khi tìm thấy sách
                                    }
                                }
                            });
                            stopQRScan();
                            return null;
                        }
                    }
                }
                return null;
            }
        };
        worker.execute();  // Chạy SwingWorker
    }

    private String decodeQRCode(BufferedImage image) {
        try {
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Result result = new MultiFormatReader().decode(bitmap);
            System.out.println("QR Code decoded: " + result.getText());

            return result.getText();
        } catch (NotFoundException e) {
            return null; // Không tìm thấy mã QR
        }
    }

    private void stopQRScan() {
        scanning = false;
        if (webcam != null) {
            webcam.close();
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

        roundPanel1 = new swing.RoundPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtTenSach = new javax.swing.JTextField();
        txtTheLoai = new javax.swing.JTextField();
        txtNhaXuatBan = new javax.swing.JTextField();
        txtMaDauSach = new javax.swing.JTextField();
        txtNamXuatBan = new javax.swing.JTextField();
        txtLanTaiBan = new javax.swing.JTextField();
        txtNgonNgu = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        roundPanel2 = new swing.RoundPanel();
        btnScan = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtTacgia = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(939, 570));

        roundPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Tên sách");

        jLabel2.setText("Thể loại");

        jLabel3.setText("Nhà xuất bản");

        jLabel4.setText("Mã đầu sách");

        jLabel5.setText("Năm xuất bản");

        jLabel6.setText("Lần tái bản");

        jLabel7.setText("Ngôn ngữ");

        jLabel8.setText("Số lượng");

        roundPanel2.setBackground(new java.awt.Color(176, 176, 176));

        javax.swing.GroupLayout roundPanel2Layout = new javax.swing.GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 357, Short.MAX_VALUE)
        );
        roundPanel2Layout.setVerticalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 364, Short.MAX_VALUE)
        );

        btnScan.setText("Scan QR");
        btnScan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnScanActionPerformed(evt);
            }
        });

        btnClose.setText("Close Scan");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        jLabel9.setText("Tên tác giả");

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTenSach, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNgonNgu)
                            .addComponent(txtNamXuatBan)
                            .addComponent(txtNhaXuatBan, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel7)
                            .addComponent(txtTacgia, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                        .addGap(38, 38, 38)
                        .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(roundPanel1Layout.createSequentialGroup()
                                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel4)
                                    .addComponent(txtTheLoai)
                                    .addComponent(txtMaDauSach)
                                    .addComponent(txtLanTaiBan)
                                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnScan, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnClose)))))
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addComponent(roundPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(23, 23, 23)
                        .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNhaXuatBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaDauSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnClose))
                        .addGap(21, 21, 21)
                        .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(20, 20, 20)
                        .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNamXuatBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLanTaiBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnScan))
                        .addGap(18, 18, 18)
                        .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(26, 26, 26)
                        .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNgonNgu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTacgia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(125, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(roundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnScanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnScanActionPerformed
        startQRScan();
    }//GEN-LAST:event_btnScanActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        stopQRScan();
    }//GEN-LAST:event_btnCloseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnScan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private swing.RoundPanel roundPanel1;
    private swing.RoundPanel roundPanel2;
    private javax.swing.JTextField txtLanTaiBan;
    private javax.swing.JTextField txtMaDauSach;
    private javax.swing.JTextField txtNamXuatBan;
    private javax.swing.JTextField txtNgonNgu;
    private javax.swing.JTextField txtNhaXuatBan;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTacgia;
    private javax.swing.JTextField txtTenSach;
    private javax.swing.JTextField txtTheLoai;
    // End of variables declaration//GEN-END:variables
}

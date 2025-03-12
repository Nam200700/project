/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main;

import java.awt.Color;
import java.security.Key;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Properties;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

/**
 *
 * @author ACER
 */
public class forgot extends javax.swing.JFrame {

    /**
     * Creates new form forgot
     */
    public forgot() {
        initComponents();
        this.setLocationRelativeTo(this);
        setBackground(new Color(0, 0, 0, 0));
        fadeInEffect();
        
    }
    private void fadeInEffect() {
        new Thread(() -> {
            for (double i = 0.0; i <= 1.0; i += 0.1) {
                String s = i + "";
                float f = Float.valueOf(s);
                this.setOpacity(f);
                try {
                    Thread.sleep(50);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private static final String url = "jdbc:mysql://localhost:3306/***";
    private static final String user = "root";
    private static final String password = "***";

    // Đăng ký driver MySQL khi class được nạp
    static {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to register MySQL driver", e);
        }
    }

    public static Connection getconnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private static boolean verifyUser(String email, String username) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT COUNT(*) FROM users WHERE email = ? AND full_name = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            pstmt.setString(2, username);

            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String generateOTP() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

    private static void sendEmail(String recipient, String otp) {
        final String senderEmail = "nghiatttv00104@fpt.edu.vn";
        final String senderPassword = "qrzd hmib axkr ydlw"; // app password của mail bạn

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail, "Hệ Thống OTP"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject("Mã OTP của bạn");
            message.setText("Mã OTP của bạn là: " + otp);
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean updatePasswordInDB(String email, String newPassword) {
//        String url = "jdbc:mysql://localhost:3306/assjava3";
//        String user = "root";
//        String password = "18102007";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String hashedPassword = encrypt(newPassword);
            String query = "UPDATE users SET password = ? WHERE email = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, hashedPassword);
            pstmt.setString(2, email);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private static final String SECRET_KEY = "MySecretKey12345";  // Khóa bí mật cho AES phải có độ dài 16 ký tự

// Hàm mã hóa AES
    public static String encrypt(String input) {
        try {
            // Tạo đối tượng khóa AES từ chuỗi khóa bí mật
            Key key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

            // Khởi tạo đối tượng Cipher với thuật toán AES
            Cipher cipher = Cipher.getInstance("AES");

            // Thiết lập Cipher để mã hóa (ENCRYPT_MODE)
            cipher.init(Cipher.ENCRYPT_MODE, key);

            // Mã hóa dữ liệu đầu vào
            byte[] encryptedBytes = cipher.doFinal(input.getBytes());

            // Trả về dữ liệu đã mã hóa dưới dạng chuỗi Base64
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            // Nếu có lỗi trong quá trình mã hóa, ném ra một ngoại lệ với thông báo lỗi
            throw new RuntimeException("Error during AES encryption", e);
        }
    }
    public static String OTP;
    public static String userEmail;
    public static String userUsername;

    public void forget() {
        userEmail = txt_email.getText().trim();
        userUsername = txt_name.getText().trim();

        // Kiểm tra nếu email hoặc tên đăng nhập bị trống
        if (userEmail.isEmpty() || userUsername.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ Email và Tên đăng nhập!");
            return;
        } else if (verifyUser(userEmail, userUsername)) {
            OTP = generateOTP();
            sendEmail(userEmail, OTP);
            JOptionPane.showMessageDialog(this, "Mã OTP đã được gửi tới " + userEmail);
            getcodeOtp code = new getcodeOtp();
            code.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Email hoặc tên đăng nhập không hợp lệ!");
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

        backgroundinlogin1 = new login.Backgroundinlogin();
        jPanel1 = new javax.swing.JPanel();
        txt_email = new swing.TextField();
        txt_name = new swing.TextField();
        btn_sendOTP = new swing.Button();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btn_back = new swing.Button();
        btn_exit = new swing.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        backgroundinlogin1.setBlur(jPanel1);
        backgroundinlogin1.setOpaque(true);

        jPanel1.setOpaque(false);

        txt_email.setHint("Email");

        txt_name.setHint("Username");

        btn_sendOTP.setForeground(new java.awt.Color(255, 255, 255));
        btn_sendOTP.setText("Send OTP");
        btn_sendOTP.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btn_sendOTP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_sendOTPMouseClicked(evt);
            }
        });
        btn_sendOTP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sendOTPActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Forgot ");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Email");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Name");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                        .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(82, 82, 82))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(btn_sendOTP, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel2)
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(35, 35, 35)
                .addComponent(btn_sendOTP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        backgroundinlogin1.add(jPanel1);
        jPanel1.setBounds(80, 20, 350, 290);

        btn_back.setForeground(new java.awt.Color(255, 255, 255));
        btn_back.setText("Back");
        btn_back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_backMouseClicked(evt);
            }
        });
        backgroundinlogin1.add(btn_back);
        btn_back.setBounds(412, 310, 60, 32);

        btn_exit.setForeground(new java.awt.Color(255, 255, 255));
        btn_exit.setText("X");
        btn_exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_exitMouseClicked(evt);
            }
        });
        backgroundinlogin1.add(btn_exit);
        btn_exit.setBounds(430, 10, 40, 32);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(backgroundinlogin1, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(backgroundinlogin1, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_exitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_btn_exitMouseClicked

    private void btn_sendOTPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_sendOTPMouseClicked
        
    }//GEN-LAST:event_btn_sendOTPMouseClicked

    private void btn_sendOTPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sendOTPActionPerformed
       forget();
    }//GEN-LAST:event_btn_sendOTPActionPerformed

    private void btn_backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_backMouseClicked
        main mn = new main();
        mn.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_backMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private login.Backgroundinlogin backgroundinlogin1;
    private swing.Button btn_back;
    private swing.Button btn_exit;
    private swing.Button btn_sendOTP;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private swing.TextField txt_email;
    private swing.TextField txt_name;
    // End of variables declaration//GEN-END:variables
}

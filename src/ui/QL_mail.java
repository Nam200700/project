/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import net.miginfocom.swing.MigLayout;
import swing.RoundedScrollPane;

/**
 *
 * @author ACER
 */


public class QL_mail extends javax.swing.JPanel {

    /**
     * Creates new form QL_mail
     */
    private JTextField recipientField;
    private JTextField subjectField;
    private JTextArea noteArea;
    private JButton sendButton;
    public QL_mail() {
        setLayout(new MigLayout("wrap 2", "[][grow]", "[][][grow][]"));
        setBackground(new Color(255, 250, 240)); // Màu nền sáng
         // Tiêu đề
        JLabel titleLabel = new JLabel("Gửi mail");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 34));
        add(titleLabel, "span, center, gapbottom 20");
        
        add(new JLabel("Người nhận:"), "align right");
        recipientField = createRoundedTextField(40);
        add(recipientField, "growx");
        
        add(new JLabel("Chủ đề:"), "align right");
        subjectField = createRoundedTextField(40);
        add(subjectField, "growx");
        
        add(createStyledLabel("Ghi chú:"), "align right, top");
        noteArea = createRoundedTextArea(8, 40);
        noteArea = createRoundedTextArea(8, 40);
        RoundedScrollPane scrollPane = new RoundedScrollPane(noteArea);
        add(scrollPane, "grow");
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Color.BLACK, 1), // Viền đen 2px
        BorderFactory.createEmptyBorder(5, 5, 5, 5)     // Bo góc
        )); // Bo góc textarea
        add(scrollPane, "grow");
        
        sendButton = new JButton("Gửi");
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sendButton.setBackground(new Color(100, 149, 237)); // Màu xanh dương nhạt
        sendButton.setForeground(Color.WHITE);
        sendButton = createRoundedButton("Gửi"); // Bo góc nút
        add(sendButton, "span, center, gaptop 20");

    }
    private JTextField createRoundedTextField(int columns) {
    JTextField field = new JTextField(columns) {
        @Override
        protected void paintComponent(Graphics g) {
            if (!isOpaque()) {
                int width = getWidth();
                int height = getHeight();
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Vẽ nền bo góc
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, width - 1, height - 1, 10, 10);
                super.paintComponent(g2);

                g2.dispose();
            } else {
                super.paintComponent(g);
            }
        }

        @Override
        protected void paintBorder(Graphics g) {
            int width = getWidth();
            int height = getHeight();
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Vẽ viền bo góc màu đen
            g2.setColor(Color.BLACK);
            g2.drawRoundRect(0, 0, width - 1, height - 1, 10, 10);
            g2.dispose();
        }

        @Override
        public void updateUI() {
            super.updateUI();
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Tạo padding bên trong
        }
    };
        field.setPreferredSize(new Dimension(300, 35)); // Đảm bảo chiều cao chuẩn

    return field;
}


     private JTextArea createRoundedTextArea(int rows, int columns) {
        JTextArea area = new JTextArea(rows, columns) {
            @Override
            protected void paintComponent(Graphics g) {
                if (!isOpaque()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                    super.paintComponent(g2);
                    g2.dispose();
                } else {
                    super.paintComponent(g);
                }
            }

            @Override
            public void updateUI() {
                super.updateUI();
                setOpaque(false);
                setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
            }
        };
        area.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        return area;
    }
     
    // Bo tròn JButton với kích thước dài hơn
private JButton createRoundedButton(String text) {
    JButton button = new JButton(text) {
        @Override
        protected void paintComponent(Graphics g) {
            if (!isOpaque()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g2);
                g2.dispose();
            } else {
                super.paintComponent(g);
            }
        }

        @Override
        public void updateUI() {
            super.updateUI();
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(12, 100, 12, 100)); // Tăng độ dài nút
        }
    };
    button.setFont(new Font("Segoe UI", Font.BOLD, 18));
    button.setBackground(new Color(100, 149, 237)); // Màu xanh dương nhạt
    button.setForeground(Color.WHITE);
    return button;
}

    // Tạo JLabel với font style
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Tăng cỡ chữ
        return label;
    }
     
     

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

       SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gửi Mail");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 600);
            frame.setLocationRelativeTo(null);

            frame.add(new QL_mail());
            frame.setVisible(true);
        });
    }

    
    // nếu không muốn hiện lên thì chỉ cần gán thẳng vô là được 
    String chude = "Kính gửi độc giả thân mến tôi gửi từ thư viên Thủ Dầu Một!";
    String labfrom1 = "nghiatttv00104@fpt.edu.vn";
    String note ="Thời gian bạn mượn sách hiện tại sắp hết vui lòng đăng kí lại và xin hãy trả sách đúng hạn ạ!!!!";
    private void sendmail() {
        String toEmail = txt_nguoinhan.getText();
        String fromEmail = lablfrom.getText(); // lấy cái mail mặc định từ lable đã set ở trên 
        String fromEmailPassword = "qrzd hmib axkr ydlw"; // Mật khẩu ứng dụng email vì google nó ngăn cản phần mềm thứ 3 nên mình phải sài bằng mật khẩu ứng dụng mail nha 
        String subject = txt_chude.getText();
        String mess = txt_ghichu.getText();
        // Kiểm tra từng điều kiện riêng biệt
        if (toEmail.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập email người nhận!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (fromEmail.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy email người gửi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (subject.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập chủ đề email!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (mess.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập nội dung email!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Thiết lập các thuộc tính cho phiên gửi email
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Tạo một phiên gửi email
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, fromEmailPassword);
            }
        });

        try {
            // Tạo một đối tượng MimeMessage
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail)); // Địa chỉ email người gửi
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail)); // Địa chỉ email người nhận
            message.setSubject(subject); // Tiêu đề email
            message.setText(txt_ghichu.getText());
            // Gửi email
            Transport.send(message);
            JOptionPane.showMessageDialog(this, "Email đã được gửi thành công!");
            System.out.println("Email đã được gửi thành công!");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Gửi email không thành công!");
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

        jLabel2 = new javax.swing.JLabel();
        labelGhiChu = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_ghichu = new javax.swing.JTextArea();
        txt_chude = new javax.swing.JTextField();
        txt_nguoinhan = new javax.swing.JTextField();
        lablfrom = new javax.swing.JLabel();
        btnSend = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        headerLabel = new javax.swing.JLabel();

        jLabel2.setText("Người nhận :");

        labelGhiChu.setText("Ghi chú");

        jLabel4.setText("Chủ đề :");

        txt_ghichu.setColumns(20);
        txt_ghichu.setRows(5);
        jScrollPane1.setViewportView(txt_ghichu);

        lablfrom.setText("mail");

        btnSend.setText("Gửi");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        headerLabel.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        headerLabel.setText("Gửi mail");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelGhiChu))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_chude, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nguoinhan, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(headerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lablfrom)))
                .addGap(140, 140, 140))
            .addGroup(layout.createSequentialGroup()
                .addGap(222, 222, 222)
                .addComponent(btnSend)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(123, 123, 123)
                .addComponent(lablfrom)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(headerLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_nguoinhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_chude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelGhiChu)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSend)
                .addGap(0, 12, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        sendmail();
    }//GEN-LAST:event_btnSendActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSend;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelGhiChu;
    private javax.swing.JLabel lablfrom;
    private javax.swing.JTextField txt_chude;
    private javax.swing.JTextArea txt_ghichu;
    private javax.swing.JTextField txt_nguoinhan;
    // End of variables declaration//GEN-END:variables
}

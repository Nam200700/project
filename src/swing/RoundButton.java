/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class RoundButton extends JButton {
    public RoundButton(String text) {
        super(text);
        initButton();
    }

    public RoundButton(String text, Icon icon) {
        super(text, resizeIcon(icon, 18, 18));
        initButton();
        setHorizontalTextPosition(SwingConstants.RIGHT);
        setVerticalTextPosition(SwingConstants.CENTER);
        setIconTextGap(10);
    }

    private void initButton() {
        setContentAreaFilled(false); // Bỏ nền mặc định của JButton
        setFocusPainted(false);      // Bỏ viền khi nhấn nút
        setBorderPainted(false);     // Bỏ viền button
        setOpaque(false);            // Loại bỏ nền mờ
        setForeground(Color.WHITE);
        setFont(new Font("SansSerif", Font.BOLD, 14));
        setBackground(new Color(63, 81, 181));
        setMargin(new Insets(5, 10, 5, 10));
    }

    // Resize icon để không bị mờ và bỏ nền trắng của icon
    private static Icon resizeIcon(Icon icon, int width, int height) {
        if (icon instanceof ImageIcon) {
            Image img = ((ImageIcon) icon).getImage();
            img = makeTransparent(img); // Xử lý trong suốt viền
            img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        }
        return icon;
    }

    // Hàm loại bỏ viền trắng của icon (nếu có)
    private static Image makeTransparent(Image img) {
        ImageIcon icon = new ImageIcon(img);
        int w = icon.getIconWidth();
        int h = icon.getIconHeight();
        BufferedImage bimg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bimg.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.drawImage(img, 0, 0, null);
        g2.dispose();
        return bimg;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Không vẽ border
    }
}

package swing;

import javax.swing.*;
import java.awt.*;

// Lớp tạo JScrollPane bo góc
public class RoundedScrollPane extends JScrollPane {
    public RoundedScrollPane(JTextArea textArea) {
        super(textArea);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ viền bo góc
        g2.setColor(Color.BLACK);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

        // Vẽ nền
        g2.setColor(new Color(255, 250, 240));
        g2.fillRoundRect(2, 2, getWidth() - 5, getHeight() - 5, 18, 18);

        g2.dispose();
        super.paintComponent(g);
    }
}

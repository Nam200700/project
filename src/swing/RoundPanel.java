package swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class RoundPanel extends JPanel {

    private int shadowSize = 10; // Kích thước bóng
    private int arcSize = 15; // Độ cong góc

    public RoundPanel() {
        setOpaque(false); // Để hiển thị bóng
    }

    @Override
    public void paint(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Vẽ bóng mờ màu xám nhạt
        g2.setColor(new Color(150, 150, 150, 80)); // Xám nhạt với độ trong suốt 80
        g2.fillRoundRect(shadowSize, shadowSize, width - shadowSize, height - shadowSize, arcSize, arcSize);

        // Vẽ panel chính
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width - shadowSize, height - shadowSize, arcSize, arcSize);

        g2.dispose();
        super.paint(grphcs);
    }
}

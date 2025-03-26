/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ImageHeaderRenderer extends DefaultTableCellRenderer {
    private final ImageIcon icon;
    private final String text;

    public ImageHeaderRenderer(String imagePath, String text) {
        this.icon = new ImageIcon(getClass().getClassLoader().getResource(imagePath));
        this.text = text;
        setHorizontalAlignment(CENTER);
        setHorizontalTextPosition(RIGHT);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = new JLabel(text, icon, JLabel.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setForeground(Color.BLACK);
        label.setHorizontalTextPosition(SwingConstants.RIGHT);
        return label;
    }
}



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import com.formdev.flatlaf.ui.FlatTableHeaderUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author ACER
 */
public class RoundTablenhaxuatban extends JTable{
    public RoundTablenhaxuatban(DefaultTableModel model) {
        super(model);
        setRowHeight(40);
        setFont(new Font("SansSerif", Font.PLAIN, 14));
        setSelectionBackground(new Color(255, 203, 164, 180));
        setSelectionForeground(Color.BLACK);
        setGridColor(new Color(220, 220, 220, 100));
        setShowGrid(false);
        setIntercellSpacing(new Dimension(0, 0));
        setOpaque(false);

        // 🌟 Header với icon
        JTableHeader header = getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 45));
        header.setFont(new Font("SansSerif", Font.BOLD, 15));
        header.setForeground(Color.BLACK);
        header.setBackground(new Color(255, 228, 196));
        header.setUI(new FlatTableHeaderUI());
        header.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        // Cập nhật tiêu đề cột với icon 📌
        TableColumnModel columnModel = getColumnModel();
        if (columnModel.getColumnCount() > 0) {
            columnModel.getColumn(0).setHeaderRenderer(new ImageHeaderRenderer("image/application.png", "Mã nhà xuất bản"));
            columnModel.getColumn(1).setHeaderRenderer(new ImageHeaderRenderer("image/publisher.png", "Tên nhà xuất bản"));

        }

        // Căn giữa nội dung bảng
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < getColumnCount(); i++) {
            getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 🎨 SVG/Nền bảng
        g2.setColor(new Color(255, 239, 219, 200));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

        // 🔥 Hiệu ứng SVG nhỏ (ví dụ: hình watermark)
        g2.setColor(new Color(255, 180, 150, 80));
        g2.fillOval(getWidth() - 50, getHeight() - 50, 40, 40); // Watermark tròn nhỏ

        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component comp = super.prepareRenderer(renderer, row, column);

        if (!isRowSelected(row)) {
            comp.setBackground(row % 2 == 0 ? new Color(255, 245, 230) : new Color(255, 255, 245));
            comp.setForeground(Color.BLACK);
        } else {
            comp.setBackground(new Color(255, 203, 164));
            comp.setForeground(Color.BLACK);
            comp.setFont(new Font("SansSerif", Font.BOLD, 14));
        }

        return comp;
    }
}

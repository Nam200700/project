package ui;

import DAO.ThongKeDAO;
import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import java.awt.*;
import java.util.Random;
import raven.chart.pie.PieChart;
import raven.chart.data.pie.DefaultPieDataset;
import raven.drawer.TabbedForm;

public class QL_thongkevabaocao extends TabbedForm {

    /**
     * Creates new form QL_thongkevabaocao
     */
    public QL_thongkevabaocao() {
        initComponents();
        roundPanel1.setLayout(new BorderLayout()); // cần set layout để không bị rỗng
        roundPanel1.add(createPieChartPanel(), BorderLayout.CENTER);
        roundPanel1.revalidate();
        roundPanel1.repaint();
    }

    private JPanel createPieChartPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 10));
        panel.setOpaque(false);

        // Biểu đồ 1
        PieChart pieChart1 = new PieChart();
        JLabel header1 = new JLabel("Thu Nhập Sản Phẩm");
        header1.putClientProperty(FlatClientProperties.STYLE, "font:+1");
        pieChart1.setHeader(header1);
        pieChart1.getChartColor().addColor(
                Color.decode("#f87171"), Color.decode("#fb923c"), Color.decode("#fbbf24"),
                Color.decode("#a3e635"), Color.decode("#34d399"), Color.decode("#22d3ee"),
                Color.decode("#818cf8"), Color.decode("#c084fc"));
        pieChart1.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
        pieChart1.setDataset(ThongKeDAO.createPieData());

        // Biểu đồ 2
        PieChart pieChart2 = new PieChart();
        JLabel header2 = new JLabel("Chi Phí Sản Phẩm");
        header2.putClientProperty(FlatClientProperties.STYLE, "font:+1");
        pieChart2.setHeader(header2);
        pieChart2.getChartColor().addColor(
                Color.decode("#f87171"), Color.decode("#fb923c"), Color.decode("#fbbf24"),
                Color.decode("#a3e635"), Color.decode("#34d399"), Color.decode("#22d3ee"),
                Color.decode("#818cf8"), Color.decode("#c084fc"));
        pieChart2.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
        pieChart2.setDataset(createPieData());

        // Biểu đồ 3
        PieChart pieChart3 = new PieChart();
        JLabel header3 = new JLabel("Lợi Nhuận Sản Phẩm");
        header3.putClientProperty(FlatClientProperties.STYLE, "font:+1");
        pieChart3.setHeader(header3);
        pieChart3.getChartColor().addColor(
                Color.decode("#f87171"), Color.decode("#fb923c"), Color.decode("#fbbf24"),
                Color.decode("#a3e635"), Color.decode("#34d399"), Color.decode("#22d3ee"),
                Color.decode("#818cf8"), Color.decode("#c084fc"));
        pieChart3.setChartType(PieChart.ChartType.DONUT_CHART);
        pieChart3.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
        pieChart3.setDataset(createPieData());

        // Chỉ add vào panel này
        panel.add(pieChart1);
        panel.add(pieChart2);
        panel.add(pieChart3);

        return panel;
    }

    private DefaultPieDataset createPieData() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        Random random = new Random();
        dataset.addValue("Túi xách", random.nextInt(100) + 50);
        dataset.addValue("Mũ", random.nextInt(100) + 50);
        dataset.addValue("Kính", random.nextInt(100) + 50);
        dataset.addValue("Đồng hồ", random.nextInt(100) + 50);
        dataset.addValue("Trang sức", random.nextInt(100) + 50);

        return dataset;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundPanel1 = new swing.RoundPanel();

        setPreferredSize(new java.awt.Dimension(1098, 659));

        roundPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1037, Short.MAX_VALUE)
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 623, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(roundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(roundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.RoundPanel roundPanel1;
    // End of variables declaration//GEN-END:variables
}

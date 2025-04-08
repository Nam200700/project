package ui;

import net.miginfocom.swing.MigLayout;
import raven.drawer.TabbedForm;
import DAO.ThongKeDAO;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Random;
import raven.chart.pie.PieChart;
import raven.chart.data.pie.DefaultPieDataset;
import net.miginfocom.swing.MigLayout;
import raven.chart.bar.HorizontalBarChart;
import raven.chart.line.LineChart;

public class DashboardForm extends TabbedForm {

    private LineChart lineChart;
    private HorizontalBarChart barChart1;
    private HorizontalBarChart barChart2;

    public DashboardForm() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("wrap,fill,gap 5", "fill"));
        createPieChart();
        createBarChart();
        createPieChart1();
    }

    private void createPieChart() {
        PieChart pieChart1 = new PieChart();
        JLabel header1 = new JLabel("Tỷ lệ sách theo khu vực");
        header1.putClientProperty(FlatClientProperties.STYLE, "font:+1");
        pieChart1.setHeader(header1);
        pieChart1.getChartColor().addColor(Color.decode("#f87171"), Color.decode("#fb923c"), Color.decode("#fbbf24"), Color.decode("#a3e635"), Color.decode("#34d399"), Color.decode("#22d3ee"), Color.decode("#818cf8"), Color.decode("#c084fc"));
        pieChart1.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
        pieChart1.setDataset(ThongKeDAO.createPieData());
        add(pieChart1, "split 3, height 200");

        PieChart pieChart2 = new PieChart();
        JLabel header2 = new JLabel("Tỉ lệ ngôn ngữ sách");
        header2.putClientProperty(FlatClientProperties.STYLE, "font:+1");
        pieChart2.setHeader(header2);
        pieChart2.getChartColor().addColor(Color.decode("#f87171"), Color.decode("#fb923c"), Color.decode("#fbbf24"), Color.decode("#a3e635"), Color.decode("#34d399"), Color.decode("#22d3ee"), Color.decode("#818cf8"), Color.decode("#c084fc"));
        pieChart2.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
        pieChart2.setDataset(ThongKeDAO.createPieDataByNgonNgu());
        add(pieChart2, "height 200");

        PieChart pieChart3 = new PieChart();
        JLabel header3 = new JLabel("Tỉ lệ thể loại sách");
        header3.putClientProperty(FlatClientProperties.STYLE, "font:+1");
        pieChart3.setHeader(header3);
        pieChart3.getChartColor().addColor(Color.decode("#f87171"), Color.decode("#fb923c"), Color.decode("#fbbf24"), Color.decode("#a3e635"), Color.decode("#34d399"), Color.decode("#22d3ee"), Color.decode("#818cf8"), Color.decode("#c084fc"));
        pieChart3.setChartType(PieChart.ChartType.DONUT_CHART);  // Biểu đồ donut
        pieChart3.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
        pieChart3.setDataset(ThongKeDAO.createPieDataByTheLoai());
        add(pieChart3, "height 200");
    }

    private void createBarChart() {
        // BarChart 1
        barChart1 = new HorizontalBarChart();
        JLabel header1 = new JLabel("Số lượng sách mượn theo từng thể loại");
        header1.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:+1;"
                + "border:0,0,5,0");
        barChart1.setHeader(header1);
        barChart1.setBarColor(Color.decode("#f97316"));
        barChart1.setDataset(ThongKeDAO.createBarDataByTheLoaiSoluong());
        JPanel panel1 = new JPanel(new BorderLayout());
        panel1.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5,$Component.borderColor,,20");
        panel1.add(barChart1);
        add(panel1, "split 2,gap 0 20");
        barChart1.setValuesFormat(new DecimalFormat("#,#0"));

        // BarChart 2
        barChart2 = new HorizontalBarChart();
        JLabel header2 = new JLabel("Số lượng mượn sách theo tháng");
        header2.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:+1;"
                + "border:0,0,5,0");
        barChart2.setHeader(header2);
        barChart2.setBarColor(Color.decode("#10b981"));
        barChart2.setDataset(ThongKeDAO.createBarDataByMonth());
        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5,$Component.borderColor,,20");
        panel2.add(barChart2);
        add(panel2);
        barChart2.setValuesFormat(new DecimalFormat("#,#0"));
    }

    private void createPieChart1() {
        PieChart pieChart1 = new PieChart();
        JLabel header1 = new JLabel("Tỉ lệ sách đến từ các nhà xuất bản");
        header1.putClientProperty(FlatClientProperties.STYLE, "font:+1");
        pieChart1.setHeader(header1);
        pieChart1.getChartColor().addColor(Color.decode("#f87171"), Color.decode("#fb923c"), Color.decode("#fbbf24"), Color.decode("#a3e635"), Color.decode("#34d399"), Color.decode("#22d3ee"), Color.decode("#818cf8"), Color.decode("#c084fc"));
        pieChart1.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
        pieChart1.setDataset(ThongKeDAO.createPieDataByNXB());
        add(pieChart1, "split 2, height 200");

        PieChart pieChart2 = new PieChart();
        JLabel header2 = new JLabel("Tỉ lệ tình trạng trả sách");
        header2.putClientProperty(FlatClientProperties.STYLE, "font:+1");
        pieChart2.setHeader(header2);
        pieChart2.getChartColor().addColor(Color.decode("#f87171"), Color.decode("#fb923c"), Color.decode("#fbbf24"), Color.decode("#a3e635"), Color.decode("#34d399"), Color.decode("#22d3ee"), Color.decode("#818cf8"), Color.decode("#c084fc"));
        pieChart2.putClientProperty(FlatClientProperties.STYLE, "border:5,5,5,5,$Component.borderColor,,20");
        pieChart2.setDataset(ThongKeDAO.createPieDataByTrangThaiPM());
        add(pieChart2, "height 200");

    }

}

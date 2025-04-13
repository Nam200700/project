package print;

import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import print_model.ParameterReportMuon;
import print_model.ParameterReportTra;
//import print.model.ParameterReportInvoice;
//import print.model.ParameterReportMuon;
//import print.model.ParameterReportPayment;
//import print_model.ParameterReportMuon;

public class ReportManager {

    private static ReportManager instance;

    private JasperReport reportPay;
    private JasperReport reportTra;
    private JasperReport reportMuon; // 💡 Thêm báo cáo phiếu mượn

    public static ReportManager getInstance() {
        if (instance == null) {
            instance = new ReportManager();
        }
        return instance;
    }

    private ReportManager() {
    }

    // phải dẫn đến 
    public void compileReport() throws JRException {
        // Load tất cả báo cáo
//        reportPay = JasperCompileManager.compileReport(getClass()
//                .getResourceAsStream("/print/report_pay.jrxml"));
        reportTra = JasperCompileManager.compileReport(getClass()
                .getResourceAsStream("/print/report_phieutra.jrxml"));
        reportMuon = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/print/report_phieumuon.jrxml")
        );

    }

//    public void printReportPayment(ParameterReportPayment data) throws JRException {
//        Map<String, Object> para = new HashMap<>();
//        para.put("staff", data.getStaff());
//        para.put("customer", data.getCustomer());
//        para.put("total", data.getTotal());
//        para.put("qrcode", data.getQrcode());
//        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data.getFields());
//        JasperPrint print = JasperFillManager.fillReport(reportPay, para, dataSource);
//        view(print);
//    }
//
//    public void printReportInvoice(ParameterReportInvoice data) throws JRException {
//        Map<String, Object> para = new HashMap<>();
//        para.put("totalQty", data.getTotalQty());
//        para.put("totalAmount", data.getTotalAmount());
//        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data.getFields());
//        JasperPrint print = JasperFillManager.fillReport(reportInvoice, para, dataSource);
//        view(print);
//    }
    // 🆕 Thêm hàm in báo cáo phiếu mượn
    // ✅ Đã thêm đủ các tham số mà JasperReport yêu cầu
//    public void printReportMuon(ParameterReportMuon data) throws JRException {
//        Map<String, Object> para = new HashMap<>();
//
//        para.put("maphieumuon", data.getMaphieumuon());
//        para.put("madocgia", data.getMadocgia());
//        para.put("ngaymuon", data.getNgaymuon());
//        para.put("hantra", data.getHantra());
//
//        JasperPrint print = JasperFillManager.fillReport(reportMuon, para, new JREmptyDataSource());
//        view(print);
//    }
    public void printReportMuon(ParameterReportMuon data) throws JRException {
        Map<String, Object> para = new HashMap<>();

        // Truyền tham số vào báo cáo Jasper
        para.put("maphieu", data.getMaphieu());
        para.put("madocgia", data.getMadocgia());
        para.put("hoten", data.getHoten());
        para.put("ngaymuon", data.getNgaymuon());
        para.put("hantra", data.getHantra());
        para.put("trangthai", data.getTrangthai());

        // Tạo datasource từ danh sách FieldReportMuon
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data.getDanhSachPhieu());

        // In báo cáo
        JasperPrint print = JasperFillManager.fillReport(reportMuon, para, dataSource);
        view(print);
    }

    public void printReportTra(ParameterReportTra data) throws JRException {
        Map<String, Object> para = new HashMap<>();

        // Truyền tham số vào báo cáo Jasper
        para.put("maphieutra", data.getMaphieutra());
        para.put("madocgia", data.getMadocgia());
        para.put("hoten", data.getHoten());
        para.put("ngaytra", data.getNgaytra());

        // Tạo datasource từ danh sách FieldReportMuon
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data.getDanhSachPhieu());

        // In báo cáo
        JasperPrint print = JasperFillManager.fillReport(reportTra, para, dataSource);
        view(print);
    }

    private void view(JasperPrint print) throws JRException {
        JasperViewer.viewReport(print, false);
    }
}

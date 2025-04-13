/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author ACER
 */
public class url {

    public static void main(String[] args) {
        try {
            String reportPath = "D:\\MonHoc\\Do_an_tot_Nghiep\\newproject\\project\\src\\print\\report_phieutra.jrxml";
            JasperReport report = JasperCompileManager.compileReport(reportPath);

            if (report != null) {
                System.out.println("✅ Compile báo cáo thành công!");
            } else {
                System.out.println("❌ Compile thất bại, báo cáo NULL.");
            }
        } catch (JRException e) {
            e.printStackTrace();
            System.out.println("❌ Lỗi compile báo cáo!");
        }
    }
}

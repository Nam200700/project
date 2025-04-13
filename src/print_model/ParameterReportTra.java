/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package print_model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author ACER
 */
public class ParameterReportTra {

    private String maphieutra;
    private String madocgia;
    private String hoten;
    private Date ngaytra;
    private List<FieldReportTra> danhSachPhieu;

    public ParameterReportTra(String maphieutra, String madocgia, String hoten, Date ngaytra, List<FieldReportTra> danhSachPhieu) {
        this.maphieutra = maphieutra;
        this.madocgia = madocgia;
        this.hoten = hoten;
        this.ngaytra = ngaytra;
        this.danhSachPhieu = danhSachPhieu;
    }

    public String getMaphieutra() {
        return maphieutra;
    }

    public void setMaphieutra(String maphieutra) {
        this.maphieutra = maphieutra;
    }

    public String getMadocgia() {
        return madocgia;
    }

    public void setMadocgia(String madocgia) {
        this.madocgia = madocgia;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public Date getNgaytra() {
        return ngaytra;
    }

    public void setNgaytra(Date ngaytra) {
        this.ngaytra = ngaytra;
    }

    public List<FieldReportTra> getDanhSachPhieu() {
        return danhSachPhieu;
    }

    public void setDanhSachPhieu(List<FieldReportTra> danhSachPhieu) {
        this.danhSachPhieu = danhSachPhieu;
    }

}

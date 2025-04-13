package print_model;

import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ParameterReportMuon {

    private String maphieu;
    private String madocgia;
    private String hoten;
    private Date ngaymuon;
    private Date hantra;
    private String trangthai;
    private List<FieldReportMuon> danhSachPhieu;

    public ParameterReportMuon(String maphieu, String madocgia, String hoten, Date ngaymuon, Date hantra, String trangthai, List<FieldReportMuon> danhSachPhieu) {
        this.maphieu = maphieu;
        this.madocgia = madocgia;
        this.hoten = hoten;
        this.ngaymuon = ngaymuon;
        this.hantra = hantra;
        this.trangthai = trangthai;
        this.danhSachPhieu = danhSachPhieu;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    // Getter và Setter
    public Date getNgaymuon() {
        return ngaymuon;
    }

    public void setNgaymuon(Date ngaymuon) {
        this.ngaymuon = ngaymuon;
    }

    public Date getHantra() {
        return hantra;
    }

    public void setHantra(Date hantra) {
        this.hantra = hantra;
    }

    public String getMaphieu() {
        return maphieu;
    }

    public void setMaphieu(String maphieu) {
        this.maphieu = maphieu;
    }

    public String getMadocgia() {
        return madocgia;
    }

    public void setMadocgia(String madocgia) {
        this.madocgia = madocgia;
    }

    public List<FieldReportMuon> getDanhSachPhieu() {
        return danhSachPhieu;
    }

    public void setDanhSachPhieu(List<FieldReportMuon> danhSachPhieu) {
        this.danhSachPhieu = danhSachPhieu;
    }
}

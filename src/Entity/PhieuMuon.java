/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.Date;

/**
 *
 * @author Acer
 */
public class PhieuMuon {

    private String maPhieuMuon;
    private String maDocGia;
    private Date ngayMuon;
    private Date hanTra;
    private String trangthai;

    public PhieuMuon() {

    }

    public PhieuMuon(String maPhieuMuon, String maDocGia, String maSach, Date ngayMuon, Date hanTra, String trangthai) {
        this.maPhieuMuon = maPhieuMuon;
        this.maDocGia = maDocGia;
        this.ngayMuon = ngayMuon;
        this.hanTra = hanTra;
        this.trangthai = trangthai;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getMaPhieuMuon() {
        return maPhieuMuon;
    }

    public void setMaPhieuMuon(String maPhieuMuon) {
        this.maPhieuMuon = maPhieuMuon;
    }

    public String getMaDocGia() {
        return maDocGia;
    }

    public void setMaDocGia(String maDocGia) {
        this.maDocGia = maDocGia;
    }

    public Date getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(Date ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public Date getHanTra() {
        return hanTra;
    }

    public void setHanTra(Date hanTra) {
        this.hanTra = hanTra;
    }

}

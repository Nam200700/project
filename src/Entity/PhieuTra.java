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
public class PhieuTra {
    private String maPhieuTra;
    private String maPhieuMuon;
    private Date ngayTra;
    private double tienPhat;

    public PhieuTra(String maPhieuTra, String maPhieuMuon, Date ngayTra, double tienPhat) {
        this.maPhieuTra = maPhieuTra;
        this.maPhieuMuon = maPhieuMuon;
        this.ngayTra = ngayTra;
        this.tienPhat = tienPhat;
    }

    public String getMaPhieuTra() {
        return maPhieuTra;
    }

    public void setMaPhieuTra(String maPhieuTra) {
        this.maPhieuTra = maPhieuTra;
    }

    public String getMaPhieuMuon() {
        return maPhieuMuon;
    }

    public void setMaPhieuMuon(String maPhieuMuon) {
        this.maPhieuMuon = maPhieuMuon;
    }

    public Date getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(Date ngayTra) {
        this.ngayTra = ngayTra;
    }

    public double getTienPhat() {
        return tienPhat;
    }

    public void setTienPhat(double tienPhat) {
        this.tienPhat = tienPhat;
    }
}

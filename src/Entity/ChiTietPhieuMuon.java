/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author Acer
 */
public class ChiTietPhieuMuon {
    private int maChiTiet;
    private PhieuMuon phieuMuon;
    private Sach sach;
    private int soLuong;

    public ChiTietPhieuMuon(int maChiTiet, PhieuMuon phieuMuon, Sach sach, int soLuong) {
        this.maChiTiet = maChiTiet;
        this.phieuMuon = phieuMuon;
        this.sach = sach;
        this.soLuong = soLuong;
    }

    public int getMaChiTiet() {
        return maChiTiet;
    }

    public void setMaChiTiet(int maChiTiet) {
        this.maChiTiet = maChiTiet;
    }

    public PhieuMuon getPhieuMuon() {
        return phieuMuon;
    }

    public void setPhieuMuon(PhieuMuon phieuMuon) {
        this.phieuMuon = phieuMuon;
    }

    public Sach getSach() {
        return sach;
    }

    public void setSach(Sach sach) {
        this.sach = sach;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.logging.Logger;

/**
 *
 * @author Acer
 */
public class ChiTietPhieuMuon {

    private String maphieuMuon;
    private String maSach;
    private int soLuong;
    private String tenSach;

    public ChiTietPhieuMuon() {
    }

    public ChiTietPhieuMuon(String maphieuMuon, String maSach, int soLuong, String tenSach) {
        this.maphieuMuon = maphieuMuon;
        this.maSach = maSach;
        this.soLuong = soLuong;
        this.tenSach = tenSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    

    public String getMaphieuMuon() {
        return maphieuMuon;
    }

    public void setMaphieuMuon(String maphieuMuon) {
        this.maphieuMuon = maphieuMuon;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

}

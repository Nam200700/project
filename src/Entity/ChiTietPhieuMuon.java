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

    public ChiTietPhieuMuon() {
    }

    public ChiTietPhieuMuon(String maphieuMuon, String maSach, int soLuong) {
        this.maphieuMuon = maphieuMuon;
        this.maSach = maSach;
        this.soLuong = soLuong;
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

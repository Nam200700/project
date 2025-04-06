/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author ACER
 */
public class ChiTietPhieuTra {

    private String maPhieuTra;
    private String maSach;
    private int soluong;
    private String tinhTrangSach;
    private String tenSach;

    public ChiTietPhieuTra(String maPhieuTra, String maSach, int soluong, String tinhTrangSach, String tenSach) {
        this.maPhieuTra = maPhieuTra;
        this.maSach = maSach;
        this.soluong = soluong;
        this.tinhTrangSach = tinhTrangSach;
        this.tenSach = tenSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public ChiTietPhieuTra() {
    }

    public String getMaPhieuTra() {
        return maPhieuTra;
    }

    public void setMaPhieuTra(String maPhieuTra) {
        this.maPhieuTra = maPhieuTra;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getTinhTrangSach() {
        return tinhTrangSach;
    }

    public void setTinhTrangSach(String tinhTrangSach) {
        this.tinhTrangSach = tinhTrangSach;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author Acer
 */
public class PhieuPhat {
    private int maPhieuPhat;
    private int maPhieuTra;
    private double soTienPhat;
    private String lyDo;
    private String trangThaiThanhToan;

    public int getMaPhieuPhat() {
        return maPhieuPhat;
    }

    public void setMaPhieuPhat(int maPhieuPhat) {
        this.maPhieuPhat = maPhieuPhat;
    }

    public int getMaPhieuTra() {
        return maPhieuTra;
    }

    public void setMaPhieuTra(int maPhieuTra) {
        this.maPhieuTra = maPhieuTra;
    }

    public double getSoTienPhat() {
        return soTienPhat;
    }

    public void setSoTienPhat(double soTienPhat) {
        this.soTienPhat = soTienPhat;
    }

    public String getLyDo() {
        return lyDo;
    }

    public void setLyDo(String lyDo) {
        this.lyDo = lyDo;
    }

    public String getTrangThaiThanhToan() {
        return trangThaiThanhToan;
    }

    public void setTrangThaiThanhToan(String trangThaiThanhToan) {
        this.trangThaiThanhToan = trangThaiThanhToan;
    }

    // Constructor đúng với dữ liệu truy vấn
    public PhieuPhat(int maPhieuPhat, int maPhieuTra, double soTienPhat, String lyDo, String trangThaiThanhToan) {
        this.maPhieuPhat = maPhieuPhat;
        this.maPhieuTra = maPhieuTra;
        this.soTienPhat = soTienPhat;
        this.lyDo = lyDo;
        this.trangThaiThanhToan = trangThaiThanhToan;
    }

    // Getter và Setter (nếu cần)


    
}

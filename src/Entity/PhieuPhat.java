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
    private String maPhieuPhat;
    private String maDocGia;
    private double soTienPhat;
    private String lyDo;

    public PhieuPhat(String maPhieuPhat, String maDocGia, double soTienPhat, String lyDo) {
        this.maPhieuPhat = maPhieuPhat;
        this.maDocGia = maDocGia;
        this.soTienPhat = soTienPhat;
        this.lyDo = lyDo;
    }

    public String getMaPhieuPhat() {
        return maPhieuPhat;
    }

    public void setMaPhieuPhat(String maPhieuPhat) {
        this.maPhieuPhat = maPhieuPhat;
    }

    public String getMaDocGia() {
        return maDocGia;
    }

    public void setMaDocGia(String maDocGia) {
        this.maDocGia = maDocGia;
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
}

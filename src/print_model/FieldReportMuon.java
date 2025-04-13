/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package print_model;

/**
 *
 * @author ACER
 */
public class FieldReportMuon {

    private String mpm;      // mã phiếu mượn
    private String ms;       // mã sách
    private String ten;      // tên sách
    private int soluong;     // số lượng

    public FieldReportMuon(String mpm, String ms, String ten, int soluong) {
        this.mpm = mpm;
        this.ms = ms;
        this.ten = ten;
        this.soluong = soluong;
    }

    public String getMpm() {
        return mpm;
    }

    public void setMpm(String mpm) {
        this.mpm = mpm;
    }

    public String getMs() {
        return ms;
    }

    public void setMs(String ms) {
        this.ms = ms;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}

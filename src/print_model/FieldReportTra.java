/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package print_model;

/**
 *
 * @author ACER
 */
public class FieldReportTra {

    private String mpt;      // mã phiếu trả
    private String ms;       // mã sách
    private String ten;      // tên sách
    private int soluong;     // số lượng
    private String tinhtrangsach;

    public FieldReportTra(String mpt, String ms, String ten, int soluong, String tinhtrangsach) {
        this.mpt = mpt;
        this.ms = ms;
        this.ten = ten;
        this.soluong = soluong;
        this.tinhtrangsach = tinhtrangsach;
    }

    public String getMpt() {
        return mpt;
    }

    public void setMpt(String mpt) {
        this.mpt = mpt;
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

    public String getTinhtrangsach() {
        return tinhtrangsach;
    }

    public void setTinhtrangsach(String tinhtrangsach) {
        this.tinhtrangsach = tinhtrangsach;
    }

}

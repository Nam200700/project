/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author Acer
 */
public class Sach {
    public String tenSach;
    public String theLoai;
    public String tacGia;
    public String nhaXuatBan;
    public int namXuatBan;
    public String ngonNgu;
    public int soLuong;
    public int lanTaiBan;

    public Sach(String tenSach, String theLoai, String tacGia, String nhaXuatBan, int namXuatBan, String ngonNgu, int soLuong, int lanTaiBan) {
        this.tenSach = tenSach;
        this.theLoai = theLoai;
        this.tacGia = tacGia;
        this.nhaXuatBan = nhaXuatBan;
        this.namXuatBan = namXuatBan;
        this.ngonNgu = ngonNgu;
        this.soLuong = soLuong;
        this.lanTaiBan = lanTaiBan;
    }
    
    public Sach(){
        
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getNhaXuatBan() {
        return nhaXuatBan;
    }

    public void setNhaXuatBan(String nhaXuatBan) {
        this.nhaXuatBan = nhaXuatBan;
    }

    public int getNamXuatBan() {
        return namXuatBan;
    }

    public void setNamXuatBan(int namXuatBan) {
        this.namXuatBan = namXuatBan;
    }

    public String getNgonNgu() {
        return ngonNgu;
    }

    public void setNgonNgu(String ngonNgu) {
        this.ngonNgu = ngonNgu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getLanTaiBan() {
        return lanTaiBan;
    }

    public void setLanTaiBan(int lanTaiBan) {
        this.lanTaiBan = lanTaiBan;
    }
    
        
    
    }

    
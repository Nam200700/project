/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author Acer
 */
public class KhuVucSach {
    private int maKhuVuc;
    private String tenKhuVuc;
    private int tang;
    private int ke;
    private int viTri;
    private int soLuong; // không cần lưu vào DB


    public KhuVucSach(int maKhuVuc, String tenKhuVuc, int tang, int ke, int viTri) {
        this.maKhuVuc = maKhuVuc;
        this.tenKhuVuc = tenKhuVuc;
        this.tang = tang;
        this.ke = ke;
        this.viTri = viTri;
        this.soLuong = soLuong;
    }
    
    public KhuVucSach(){
        
    }

    public int getMaKhuVuc() {
        return maKhuVuc;
    }

    public void setMaKhuVuc(int maKhuVuc) {
        this.maKhuVuc = maKhuVuc;
    }

    public String getTenKhuVuc() {
        return tenKhuVuc;
    }

    public void setTenKhuVuc(String tenKhuVuc) {
        this.tenKhuVuc = tenKhuVuc;
    }

    public int getTang() {
        return tang;
    }

    public void setTang(int tang) {
        this.tang = tang;
    }

    public int getKe() {
        return ke;
    }

    public void setKe(int ke) {
        this.ke = ke;
    }

    public int getViTri() {
        return viTri;
    }

    public void setViTri(int viTri) {
        this.viTri = viTri;
    }
    
    public int getSoLuong() {
    return soLuong;
}

public void setSoLuong(int soLuong) {
    this.soLuong = soLuong;
}
    
    
}

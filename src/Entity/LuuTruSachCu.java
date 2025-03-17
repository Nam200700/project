/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.Date;

/**
 *
 * @author Acer
 */
public class LuuTruSachCu {
    private String maSachCu;
    private String maSach;
    private Date ngayLuuTru;

    public LuuTruSachCu(String maSachCu, String maSach, Date ngayLuuTru) {
        this.maSachCu = maSachCu;
        this.maSach = maSach;
        this.ngayLuuTru = ngayLuuTru;
    }

    public String getMaSachCu() {
        return maSachCu;
    }

    public void setMaSachCu(String maSachCu) {
        this.maSachCu = maSachCu;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public Date getNgayLuuTru() {
        return ngayLuuTru;
    }

    public void setNgayLuuTru(Date ngayLuuTru) {
        this.ngayLuuTru = ngayLuuTru;
    }
}

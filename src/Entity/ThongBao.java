package Entity;

import java.util.Date;

public class ThongBao {
    public int MaTB;
    public String TieuDe;
    public String NoiDung;
    public Date NgayTao;

    public ThongBao() {
    }

    public ThongBao(int MaTB, String TieuDe, String NoiDung) {
        this.MaTB = MaTB;
        this.TieuDe = TieuDe;
        this.NoiDung = NoiDung;
    }

    public int getMaTB() {
        return MaTB;
    }

    public void setMaTB(int MaTB) {
        this.MaTB = MaTB;
    }

    public String getTieuDe() {
        return TieuDe;
    }

    public void setTieuDe(String TieuDe) {
        this.TieuDe = TieuDe;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String NoiDung) {
        this.NoiDung = NoiDung;
    }

    public Date getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(Date NgayTao) {
        this.NgayTao = NgayTao;
    }
    
    
}

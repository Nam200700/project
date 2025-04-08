package Entity;

public class ThongKeSach {
    private String maSach;
    private String tenSach;
    private int soLanMuon;

    public ThongKeSach(String maSach, String tenSach, int soLanMuon) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.soLanMuon = soLanMuon;
    }

    public String getMaSach() {
        return maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public int getSoLanMuon() {
        return soLanMuon;
    }
}

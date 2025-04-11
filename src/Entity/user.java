package Entity;

public class user {

    public int maTaiKhoan;
    public String fullname;
    public String Email;
    public String password;
    public int maQuyen;
    public String TenQuyen;
    
    public user() {
    }

    public user(int maTaiKhoan, String fullname, String Email, String password, int maQuyen) {
        this.maTaiKhoan = maTaiKhoan;
        this.fullname = fullname;
        this.Email = Email;
        this.password = password;
        this.maQuyen = maQuyen;
    }

    public int getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public void setMaTaiKhoan(int maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaQuyen() {
        return maQuyen;
    }

    public void setMaQuyen(int maQuyen) {
        this.maQuyen = maQuyen;
    }

    public String getTenQuyen() {
        return TenQuyen;
    }

    public void setTenQuyen(String TenQuyen) {
        this.TenQuyen = TenQuyen;
    }

    
    
}

package models;

import java.sql.*;

/**
 *
 * @author ADMIN
 */
public class NguoiDungModel {
    
    private String manguoidung;
    private String taikhoan;
    private String matkhau;
    private String loaiuser;
    private String hoten; // Bổ sung thuộc tính Họ Tên
    private String sodienthoai;
    
    // Khởi tạo không tham số
    public NguoiDungModel(){}
    
    // Khởi tạo có tham số
    public NguoiDungModel(String manguoidung, String taikhoan, String matkhau, String loaiuser) {
        this.manguoidung = manguoidung;
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
        this.loaiuser = loaiuser;
    }

    public NguoiDungModel(String manguoidung, String taikhoan, String matkhau, String loaiuser, String hoten, String sodienthoai) {
        this.manguoidung = manguoidung;
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
        this.loaiuser = loaiuser;
        this.hoten = hoten;
        this.sodienthoai = sodienthoai;
    }

    
    
     //khoi tao nhanh khi lam viec voi giao dien
    public NguoiDungModel(ResultSet rs) throws SQLException{
        this.manguoidung = rs.getString("id_user");
        this.taikhoan = rs.getString("taikhoan");
        this.matkhau = rs.getString("matkhau");
        this.loaiuser = rs.getString("loaiuser");
        this.hoten = rs.getString("hoten");
    }
   

    // Getter và Setter
    
    
    public void setManguoidung(String manguoidung) {
        this.manguoidung = manguoidung;
    }

    public String getManguoidung() {
        return manguoidung;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = hashPassword(matkhau);  // Giả sử bạn mã hóa mật khẩu trước khi set
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }
    
    
    
    public String getLoaiuser() {
        return loaiuser;
    }

    public void setLoaiuser(String loaiuser) {
        this.loaiuser = loaiuser;
    }

    // Giả sử hàm hashPassword thực hiện mã hóa mật khẩu
    private String hashPassword(String password) {
        return password; // Trả về mật khẩu đã được mã hóa (ở đây là ví dụ, bạn cần thực hiện mã hóa thực tế)
    }

    // So sánh mật khẩu đã mã hóa
    public boolean checkPassword(String inputPassword) {
        return this.matkhau.equals(hashPassword(inputPassword));  // So sánh mật khẩu nhập với mật khẩu đã mã hóa
    }
}

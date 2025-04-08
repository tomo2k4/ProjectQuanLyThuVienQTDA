package controllers;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.DocGiaModel;
import models.NguoiDungModel;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class NguoiDungController {
    Connection conn;
    public NguoiDungController() throws SQLException{
        conn = new DBConnect().connectSQL();
    }
    /*
    // Phương thức mã hóa mật khẩu với SHA-256
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();  // Trả về mật khẩu đã mã hóa dưới dạng chuỗi hex
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Lỗi mã hóa mật khẩu!", e);
        }        
    }
    */
    // Khóa bí mật cho AES (16 ký tự)
    private static final String SECRET_KEY = "0123456789abcdef";

    /**
     * Mã hóa mật khẩu bằng thuật toán AES.
     *
     * @param plainPassword Mật khẩu gốc (bản rõ)
     * @return Chuỗi mã hóa mật khẩu
     */
    public static String encryptPassword(String plainPassword) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(plainPassword.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            throw new RuntimeException("Lỗi mã hóa mật khẩu!", e);
        }
    }

    /**
     * Giải mã mật khẩu đã mã hóa bằng thuật toán AES.
     *
     * @param encryptedPassword Chuỗi mật khẩu đã mã hóa
     * @return Mật khẩu gốc (bản rõ)
     */
    public static String decryptPassword(String encryptedPassword) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassword);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            throw new RuntimeException("Lỗi giải mã mật khẩu!", e);
        }
    }
    
    // Phương thức kiểm tra đăng nhập
    public String kiemTraDangNhap(String taikhoan, String matkhau) throws SQLException {
        String role = null;
        // Mã hóa mật khẩu người dùng nhập vào
        String matkhauEncrypt = encryptPassword(matkhau);

        // Câu lệnh SQL lấy dữ liệu người dùng theo tài khoản và loại người dùng
        String sql = "SELECT matkhau, loaiuser FROM tbl_NguoiDung WHERE taikhoan = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, taikhoan);
            ResultSet rs = stmt.executeQuery();

            // Kiểm tra nếu có dữ liệu người dùng
            if (rs.next()) {
                String matkhauDB = rs.getString("matkhau"); // Lấy mật khẩu từ CSDL
                if (matkhauEncrypt.equals(matkhauDB)) {
                    role = rs.getString("loaiuser"); // Lấy quyền (user/admin)
                }
            }
        return role; // Trả về quyền nếu đăng nhập thành công, null nếu thất bại
    }
    
    //----------------------------------------------------------------------------------------------------

    public boolean kiemTraTaiKhoanTonTai(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM tbl_nguoidung WHERE taikhoan = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Nếu số lượng > 0 nghĩa là đã tồn tại
                }
            }
        }
        return false;
    }

    public boolean dangKyTaiKhoan(String username, String password, String hoTen, String soDT) throws SQLException {
        conn.setAutoCommit(false); // Bắt đầu transaction
        try {
            // 1. Mã hóa mật khẩu
            String EncryptPassword = encryptPassword(password);

            // 2. Thêm vào bảng tbl_nguoidung
            String insertNguoiDungSQL = "INSERT INTO tbl_nguoidung (taikhoan, matkhau, loaiuser) VALUES (?, ?, 'user')";
            try (PreparedStatement stmtNguoiDung = conn.prepareStatement(insertNguoiDungSQL, Statement.RETURN_GENERATED_KEYS)) {
                stmtNguoiDung.setString(1, username);
                stmtNguoiDung.setString(2, EncryptPassword);
                stmtNguoiDung.executeUpdate();

                // Lấy id_user vừa tạo
                try (ResultSet rs = stmtNguoiDung.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idUser = rs.getInt(1);

                        // 3. Thêm vào bảng tbl_docgia
                        String insertDocGiaSQL = "INSERT INTO tbl_docgia (hoten, sodt, id_user) VALUES (?, ?, ?)";
                        try (PreparedStatement stmtDocGia = conn.prepareStatement(insertDocGiaSQL)) {
                            stmtDocGia.setString(1, hoTen);
                            stmtDocGia.setString(2, soDT);
                            stmtDocGia.setInt(3, idUser);
                            stmtDocGia.executeUpdate();
                        }
                    }
                }
            }

            conn.commit(); // Xác nhận transaction
            return true;
        } catch (SQLException ex) {
            conn.rollback(); // Hoàn tác nếu xảy ra lỗi
            throw ex;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    //Truy van tat ca du lieu trong Table LoaiSP 
    /*
    public List<NguoiDungModel> getdsNguoidung() throws SQLException{  
        // Truy vấn đọc dữ liệu  
        List<NguoiDungModel> dsSach = new ArrayList<>();
        String query = "SELECT id_docgia, hoten, email, sodt, gioitinh, anhhoso FROM tbl_docgia;";
        
        try {
            try (Statement stmt = conn.createStatement(); 
                ResultSet rs = stmt.executeQuery(query)) {
                
                // Lấy dữ liệu từ ResultSet và thêm vào danh sách
                while (rs.next()) {
                    NguoiDungModel sach = new NguoiDungModel(rs); 
                    dsSach.add(sach);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy dữ liệu  bang nguoi dung: " + e.getMessage());
        }
        return dsSach;
    }*/

    public List<NguoiDungModel> getdsNguoidung() throws SQLException {
        // Danh sách lưu thông tin người dùng
        List<NguoiDungModel> dsNguoiDung = new ArrayList<>();

        // Câu truy vấn kết hợp hai bảng
        String query = "SELECT nguoidung.id_user AS Ma, docgia.hoten AS HoTen, docgia.sodt AS SoDT, " +
                       "nguoidung.taikhoan AS TaiKhoan, nguoidung.matkhau AS MatKhau, nguoidung.loaiuser AS LoaiUser " +
                       "FROM tbl_NguoiDung nguoidung " +
                       "INNER JOIN tbl_docgia docgia ON nguoidung.id_user = docgia.id_user";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            // Lặp qua từng bản ghi và thêm vào danh sách
            while (rs.next()) {
                NguoiDungModel nguoiDung = new NguoiDungModel();
                nguoiDung.setManguoidung(rs.getString("Ma"));       // Mã người dùng
                nguoiDung.setTaikhoan(rs.getString("TaiKhoan"));   // Tài khoản
                nguoiDung.setMatkhau(rs.getString("MatKhau"));     // Mật khẩu
                nguoiDung.setLoaiuser(rs.getString("LoaiUser"));   // Loại user

                // Kết hợp Họ Tên và Số điện thoại từ bảng độc giả
                DocGiaModel docGia = new DocGiaModel();
                docGia.setHoten(rs.getString("HoTen"));            // Họ tên
                docGia.setSodienthoai(rs.getString("SoDT"));       // Số điện thoại

                nguoiDung.setHoten(docGia.getHoten());             // Gán Họ tên vào NguoiDungModel
                nguoiDung.setSodienthoai(docGia.getSodienthoai()); // Gán số điện thoại vào NguoiDungModel

                dsNguoiDung.add(nguoiDung);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy dữ liệu người dùng: " + e.getMessage());
        }

        return dsNguoiDung;
    }

    
    
    
    public List<NguoiDungModel> timKiemNguoiDung(String keyword) throws SQLException {
        List<NguoiDungModel> dsNguoiDung = new ArrayList<>();

        // Câu truy vấn tìm kiếm
        String query = "SELECT nguoidung.id_user AS Ma, docgia.hoten AS HoTen, nguoidung.taikhoan AS TaiKhoan, " +
                       "nguoidung.matkhau AS MatKhau, nguoidung.loaiuser AS LoaiUser, docgia.sodt AS SoDienThoai " +
                       "FROM tbl_NguoiDung nguoidung " +
                       "INNER JOIN tbl_docgia docgia ON nguoidung.id_user = docgia.id_user " +
                       "WHERE docgia.id_docgia LIKE ? OR docgia.hoten LIKE ? OR docgia.sodt LIKE ? OR nguoidung.taikhoan LIKE ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Gán tham số cho câu truy vấn
            //pstmt.setString(1, "%" + keyword + "%"); // Tìm theo ID độc giả
            pstmt.setString(2, "%" + keyword + "%"); // Tìm theo Họ tên
            pstmt.setString(3, "%" + keyword + "%"); // Tìm theo Số điện thoại
            pstmt.setString(4, "%" + keyword + "%"); // Tìm theo Tài khoản

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Tạo đối tượng NguoiDungModel và gán dữ liệu
                    NguoiDungModel nguoiDung = new NguoiDungModel();
                    nguoiDung.setManguoidung(rs.getString("Ma"));          // Mã người dùng
                    nguoiDung.setHoten(rs.getString("HoTen"));            // Họ tên
                    nguoiDung.setTaikhoan(rs.getString("TaiKhoan"));      // Tài khoản
                    nguoiDung.setMatkhau(rs.getString("MatKhau"));        // Mật khẩu
                    nguoiDung.setLoaiuser(rs.getString("LoaiUser"));      // Loại user
                    nguoiDung.setSodienthoai(rs.getString("SoDienThoai"));// Số điện thoại

                    dsNguoiDung.add(nguoiDung);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi tìm kiếm người dùng: " + e.getMessage());
        }

        return dsNguoiDung;
    }

    
    public boolean addUser(String username, String password, String hoTen, String soDT, String loaiUser) throws SQLException {
        conn.setAutoCommit(false); // Bắt đầu transaction
        try {
            // 1. Mã hóa mật khẩu
            String EncryptPassword = encryptPassword(password);

            // 2. Thêm vào bảng tbl_nguoidung
            String insertNguoiDungSQL = "INSERT INTO tbl_nguoidung (taikhoan, matkhau, loaiuser) VALUES (?, ?, ?)";
            try (PreparedStatement stmtNguoiDung = conn.prepareStatement(insertNguoiDungSQL, Statement.RETURN_GENERATED_KEYS)) {
                stmtNguoiDung.setString(1, username);
                stmtNguoiDung.setString(2, EncryptPassword);
                stmtNguoiDung.setString(3, loaiUser);
                stmtNguoiDung.executeUpdate();

                // Lấy id_user vừa tạo
                try (ResultSet rs = stmtNguoiDung.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idUser = rs.getInt(1);

                        // 3. Thêm vào bảng tbl_docgia
                        String insertDocGiaSQL = "INSERT INTO tbl_docgia (hoten, sodt, id_user) VALUES (?, ?, ?)";
                        try (PreparedStatement stmtDocGia = conn.prepareStatement(insertDocGiaSQL)) {
                            stmtDocGia.setString(1, hoTen);
                            stmtDocGia.setString(2, soDT);
                            stmtDocGia.setInt(3, idUser);
                            stmtDocGia.executeUpdate();
                        }
                    }
                }
            }

            conn.commit(); // Xác nhận transaction
            return true;
        } catch (SQLException ex) {
            conn.rollback(); // Hoàn tác nếu xảy ra lỗi
            throw ex;
        } finally {
            conn.setAutoCommit(true);
        }
    }   

    
    public boolean updateUserField(String userId, String fieldName, String value) throws SQLException {
        String tableName;
        String sql;

        // Xác định bảng và cột cần cập nhật
        switch (fieldName) {
            case "hoten":
            case "sodt":
                tableName = "tbl_docgia";
                sql = "UPDATE " + tableName + " SET " + fieldName + " = ? WHERE id_user = ?";
                break;
            case "taikhoan":
            case "matkhau":
            case "loaiuser": // Thêm trường hợp cho loaiUser
                tableName = "tbl_nguoidung";
                sql = "UPDATE " + tableName + " SET " + fieldName + " = ? WHERE id_user = ?";
                break;
            default:
                throw new IllegalArgumentException("Cột không hợp lệ: " + fieldName);
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, value);
            pstmt.setString(2, userId);
            return pstmt.executeUpdate() > 0;
        }
    }
    
    public boolean checkIfUserHasUnreturnedBooks(int idUser) throws SQLException {
        String sql = "SELECT COUNT(*) FROM tbl_sachmuon WHERE id_docgia = ? AND trangthai != 'Đã hoàn'";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idUser);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;  // Nếu có phiếu mượn chưa trả, trả về true
            }
        }

        return false;  // Nếu không có phiếu mượn chưa trả
    }
    
    public boolean deleteNguoiDung(int idUser) throws SQLException {
        String sql = "DELETE FROM tbl_NguoiDung WHERE id_user = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idUser);
            return pstmt.executeUpdate() > 0;
        }
    }

    
    
    

    /*
    public boolean deleteDocGia(int idDocGia) throws SQLException {
        String sqlCheckBorrowedBooks = "SELECT COUNT(*) FROM tbl_sachmuon WHERE id_docgia = ?";
        String sqlDeleteDocGia = "DELETE FROM tbl_docgia WHERE id_docgia = ?";

        try (PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheckBorrowedBooks);
             PreparedStatement pstmtDelete = conn.prepareStatement(sqlDeleteDocGia)) {

            // Kiểm tra nếu độc giả còn sách mượn
            pstmtCheck.setInt(1, idDocGia);
            ResultSet rs = pstmtCheck.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // Độc giả vẫn còn sách mượn
                return false; // Không thể xóa
            }

            // Xóa độc giả
            pstmtDelete.setInt(1, idDocGia);
            return pstmtDelete.executeUpdate() > 0;
        }
    }
    */

    
    
    
    
    
    
    
/*
    public boolean updateUser(String userId, String hoTen, String taiKhoan, String matKhau, String soDienThoai) throws SQLException {
        String updateNguoiDungSQL = "UPDATE tbl_nguoidung SET taikhoan = ?, matkhau = ? WHERE id_user = ?";
        String updateDocGiaSQL = "UPDATE tbl_docgia SET hoten = ?, sodt = ? WHERE id_user = ?";

        conn.setAutoCommit(false); // Bắt đầu transaction
        try {
            // Cập nhật bảng tbl_nguoidung
            try (PreparedStatement stmtNguoiDung = conn.prepareStatement(updateNguoiDungSQL)) {
                stmtNguoiDung.setString(1, taiKhoan);
                stmtNguoiDung.setString(2, matKhau);
                stmtNguoiDung.setString(3, userId);
                stmtNguoiDung.executeUpdate();
            }

            // Cập nhật bảng tbl_docgia
            try (PreparedStatement stmtDocGia = conn.prepareStatement(updateDocGiaSQL)) {
                stmtDocGia.setString(1, hoTen);
                stmtDocGia.setString(2, soDienThoai);
                stmtDocGia.setString(3, userId);
                stmtDocGia.executeUpdate();
            }

            conn.commit(); // Xác nhận transaction
            return true;
        } catch (SQLException e) {
            conn.rollback(); // Hoàn tác nếu xảy ra lỗi
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }


*/



    
    
    
    
    
    
    
    
    

     // Thêm mới người dùng vào cơ sở dữ liệu
    public boolean InsertData(NguoiDungModel user) {
         String sql = "INSERT INTO tbl_NguoiDung (taikhoan, matkhau, loaiuser) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getTaikhoan());
            pstmt.setString(2, encryptPassword(user.getMatkhau())); // Mã hóa mật khẩu trước khi lưu
            pstmt.setString(3, user.getLoaiuser());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            return false;
        }
    }
    
    
    
    
    
    
}

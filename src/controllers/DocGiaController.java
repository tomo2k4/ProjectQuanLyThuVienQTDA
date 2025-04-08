/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.DocGiaModel;

/**
 *
 * @author ADMIN
 */
public class DocGiaController {
    Connection conn;
    public DocGiaController() throws SQLException{
        conn = new DBConnect().connectSQL();
    }

    //Truy van tat ca du lieu trong Table LoaiSP 
    public List<DocGiaModel> getdsDocGia() throws SQLException{  
        // Truy vấn đọc dữ liệu  
        List<DocGiaModel> dsSach = new ArrayList<>();
        String query = "SELECT id_docgia, hoten, email, sodt, gioitinh, anhhoso FROM tbl_docgia;";
        
        try {
            try (Statement stmt = conn.createStatement(); 
                ResultSet rs = stmt.executeQuery(query)) {
                
                // Lấy dữ liệu từ ResultSet và thêm vào danh sách
                while (rs.next()) {
                    DocGiaModel sach = new DocGiaModel(rs); 
                    dsSach.add(sach);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy dữ liệu  bang doc gia: " + e.getMessage());
        }
        return dsSach;
    }
    //Them moi 1 dong du lieu vao table 
    public boolean InsertDocGia(DocGiaModel obj) throws SQLException {
        try {
            // Nếu id_tacgia và id_theloai hợp lệ, thực hiện câu lệnh INSERT
            String insertQuery = "INSERT INTO tbl_docgia (hoten, email, sodt, gioitinh, anhhoso) VALUES(?, ?, ?, ?, ?)";
            try (PreparedStatement pstmtInsert = conn.prepareStatement(insertQuery)) {
                pstmtInsert.setString(1, obj.getHoten());
                pstmtInsert.setString(2, obj.getSodienthoai());
                pstmtInsert.setString(3, obj.getEmail());
                pstmtInsert.setString(4, obj.getGioitinh());        
                pstmtInsert.setBytes(5, obj.getAnhhoso());

                int rowsAffected = pstmtInsert.executeUpdate();
                return rowsAffected > 0; // Trả về true nếu dữ liệu được chèn thành công
            }
        } catch (SQLException ex) {
            // In lỗi nếu có exception
            System.err.println("Lỗi khi thêm dữ liệu: " + ex.getMessage());
            throw ex; // Ném lại lỗi để xử lý bên ngoài nếu cần
        }
    }
    
     //Dieu chinh 1 dong du lieu vao table LoaiSP 
    public boolean EditData(DocGiaModel obj) throws SQLException {
        String updateQuery = "UPDATE tbl_docgia SET hoten = ?, email = ?, sodt = ?, gioitinh = ?, anhhoso = ? WHERE id_docgia = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            // Gán giá trị cho các tham số
            pstmt.setString(1, obj.getHoten());
            pstmt.setString(2, obj.getEmail());
            pstmt.setString(3, obj.getSodienthoai());
            pstmt.setString(4, obj.getGioitinh());
            pstmt.setBytes(5, obj.getAnhhoso());
            pstmt.setString(6, obj.getMadocgia()); 

            // Thực thi lệnh cập nhật
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean DeleteData(String idDocGia) {
        String deleteQuery = "DELETE FROM tbl_docgia WHERE id_docgia = ?";

        try {
            // Bắt đầu transaction
            conn.setAutoCommit(false);

            // Chuẩn bị và thực thi câu lệnh DELETE
            try (PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
                pstmt.setString(1, idDocGia); // Dùng String nếu idDocGia là kiểu String
                int rowsAffected = pstmt.executeUpdate();

                // Nếu xóa thành công, commit transaction
                if (rowsAffected > 0) {
                    conn.commit();
                    return true;
                } else {
                    // Nếu không có dòng nào bị xóa, rollback
                    conn.rollback();
                    return false;
                }
            } catch (SQLException ex) {
                // Rollback nếu có lỗi trong quá trình thực thi câu lệnh
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Lỗi khi rollback: " + rollbackEx.getMessage());
                }
                System.err.println("Lỗi khi xóa độc giả: " + ex.getMessage());
                return false;
            }
        } catch (SQLException ex) {
            // Xử lý lỗi nếu không thể bắt đầu transaction
            System.err.println("Lỗi khi bắt đầu transaction: " + ex.getMessage());
            return false;
        } finally {
            // Khôi phục chế độ tự động commit sau khi thao tác xong
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                System.err.println("Lỗi khi đặt lại auto-commit: " + ex.getMessage());
            }
        }
    }


    
    
}

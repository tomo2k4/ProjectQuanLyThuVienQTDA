/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import models.TheLoaiModel;

/**
 *
 * @author ADMIN
 */
public class TheLoaiController {
     
    Connection conn;
    public TheLoaiController() throws SQLException{
        conn = new DBConnect().connectSQL();
    }
    
    //Truy van tat ca du lieu trong Table LoaiSP 
    public List<TheLoaiModel> getTheloaiSach() throws SQLException{  
        // Truy vấn đọc dữ liệu  
        List<TheLoaiModel> dsTheloai = new ArrayList<>();
        String query = "SELECT * FROM tbl_theloai";
        
        try {
            try (Statement stmt = conn.createStatement(); 
                ResultSet rs = stmt.executeQuery(query)) {
                
                // Lấy dữ liệu từ ResultSet và thêm vào danh sách
                while (rs.next()) {
                    TheLoaiModel theloai = new TheLoaiModel(rs); // Sử dụng constructor của LoaiSanPham
                    dsTheloai.add(theloai);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy dữ liệu Loại Sản Phẩm: " + e.getMessage());
        }
        return dsTheloai;
    } 
    
    //Them moi 1 dong du lieu vao table LoaiSP 
    public boolean InsertData(TheLoaiModel obj) throws SQLException{ 
        String insertQuery = "INSERT INTO tbl_theloai (tentheloai) VALUES (?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            pstmt.setString(1, obj.getTenTheLoai());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            System.err.println("Lỗi khi thêm dữ liệu: " + ex.getMessage());
            throw ex; 
        }
    }
    
     //Dieu chinh 1 dong du lieu vao table LoaiSP 
    public boolean EditData(TheLoaiModel obj) throws SQLException{  
        String updateQuery = "UPDATE tbl_theloai SET tentheloai = ? WHERE id_theloai = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setString(1, obj.getTenTheLoai());
            pstmt.setInt(2, obj.getMaTheLoai());
            return pstmt.executeUpdate() > 0;
        }
    } 
    
    // Hàm kiểm tra trùng tên thể loại
    public boolean IsDuplicate(String tenTheLoai) throws SQLException {
        String checkQuery = "SELECT COUNT(*) FROM tbl_theloai WHERE tentheloai = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(checkQuery)) {
            pstmt.setString(1, tenTheLoai);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Trả về true nếu tồn tại
                }
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi kiểm tra trùng lặp: " + ex.getMessage());
            throw ex;
        }
        return false;
    }


    public boolean DeleteData(String matl) {
        String deleteQueryLoai = "DELETE FROM tbl_theloai WHERE id_theloai = ?";

        try {
            // Start transaction
            conn.setAutoCommit(false);

            // Prepare and execute the delete statement
            try (PreparedStatement pstmtLoai = conn.prepareStatement(deleteQueryLoai)) {
                pstmtLoai.setString(1, matl);
                int rowsAffected = pstmtLoai.executeUpdate();

                // Commit the transaction if successful
                conn.commit();
                return rowsAffected > 0;
            }

        } catch (SQLException ex) {
            // Rollback transaction in case of an error
            try {
                conn.rollback();
                System.err.println("Truy vấn loi Reset: " + ex.getMessage());
            } catch (SQLException rollbackEx) {
                System.err.println("Lỗi: " + rollbackEx.getMessage());
            }
        } finally {
            // Restore the auto-commit mode
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                System.err.println("Lỗi : " + ex.getMessage());
            }
        }
        return false; // Return false if deletion failed
    }

    public HashMap<String, Integer> GetTheloaiMap (){
        HashMap<String, Integer> map = new HashMap<>();
        String query = "SELECT * FROM tbl_theloai";
        
        try {
            try (Statement stmt = conn.createStatement(); 
                ResultSet rs = stmt.executeQuery(query)) {
                
                // Lấy dữ liệu từ ResultSet và thêm vào danh sách
                while (rs.next()) {
                    TheLoaiModel theloai = new TheLoaiModel(rs); // Sử dụng constructor của LoaiSanPham
                    map.put(theloai.getTenTheLoai(), theloai.getMaTheLoai());
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy dữ liệu the loai: " + e.getMessage());
        }
        return map;
    }
    
 
}

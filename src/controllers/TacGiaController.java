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
import models.TacGiaModel;

/**
 *
 * @author ADMIN
 */
public class TacGiaController {
    Connection conn;
    public TacGiaController() throws SQLException{
        conn = new DBConnect().connectSQL();
    }
    
    //Truy van tat ca du lieu trong Table LoaiSP 
    public List<TacGiaModel> getdsTacGia() throws SQLException{  
        // Truy vấn đọc dữ liệu  
        List<TacGiaModel> dsTacGia = new ArrayList<>();
        String query = "SELECT * FROM tbl_tacgia";
        
        try {
            try (Statement stmt = conn.createStatement(); 
                ResultSet rs = stmt.executeQuery(query)) {
                
                // Lấy dữ liệu từ ResultSet và thêm vào danh sách
                while (rs.next()) {
                    TacGiaModel tacgia = new TacGiaModel(rs); 
                    dsTacGia.add(tacgia);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy dữ liệu  bang tac gia: " + e.getMessage());
        }
        return dsTacGia;
    }
    
    //Them moi 1 dong du lieu vao table LoaiSP 
    public boolean InsertData(TacGiaModel obj) throws SQLException{ 
        String insertQuery = "INSERT INTO tbl_tacgia (tentacgia, linhvuc, gioithieu) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            pstmt.setString(1, obj.getTenTacgia());
            pstmt.setString(2, obj.getChuyenmon()); // Lĩnh vực
            pstmt.setString(3, obj.getGioithieu());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            System.err.println("Lỗi khi thêm dữ liệu: " + ex.getMessage());
            throw ex; 
        }
    }
    
     //Dieu chinh 1 dong du lieu vao table LoaiSP 
    public boolean EditData(TacGiaModel obj) throws SQLException{  
        String updateQuery = "UPDATE tbl_tacgia SET tentacgia = ?, linhvuc = ?, gioithieu = ? WHERE id_tacgia = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setString(1, obj.getTenTacgia());
            pstmt.setString(2, obj.getChuyenmon()); // Lĩnh vực
            pstmt.setString(3, obj.getGioithieu());
            pstmt.setString(4, obj.getMaTacgia()); // ID tác giả
            
            return pstmt.executeUpdate() > 0;
        }
    } 
    
    
    // Hàm kiểm tra trùng tên 
    public boolean IsDuplicate(String tenTacgia) throws SQLException {
        String checkQuery = "SELECT COUNT(*) FROM tbl_tacgia WHERE tentacgia = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(checkQuery)) {
            pstmt.setString(1, tenTacgia);

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

    
    public boolean DeleteData(String matg) {
        String deleteQuery = "DELETE FROM tbl_tacgia WHERE id_tacgia = ?";

        try {
            // Start transaction
            conn.setAutoCommit(false);

            // Prepare and execute the delete statement
            try (PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
                pstmt.setString(1, matg);
                int rowsAffected = pstmt.executeUpdate();

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
    
    public HashMap<String, String> getTacGiaMap() {
        HashMap<String, String> map = new HashMap<>();
        String query = "SELECT id_tacgia, tentacgia FROM tbl_tacgia";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String maTacGia = rs.getString("id_tacgia");
                String tenTacGia = rs.getString("tentacgia");
                map.put(maTacGia, tenTacGia);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy dữ liệu tác giả: " + e.getMessage());
        }
        return map;
    }

    
    
    
    
    
}

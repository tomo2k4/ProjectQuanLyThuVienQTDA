/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import models.SachMuonModel;

/**
 *
 * @author admin
 */
public class SachMuonController {
    Connection conn;
    public SachMuonController() throws SQLException{
        conn = new DBConnect().connectSQL();
    }
    
    public SachMuonModel getSachById(int idSach) throws SQLException {
        String query = """
            SELECT s.id_sach, s.tensach, tg.tentacgia, s.id_tacgia, s.soluong
            FROM tbl_sach s
            INNER JOIN tbl_tacgia tg ON s.id_tacgia = tg.id_tacgia
            WHERE s.id_sach = ?;
        """;

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idSach);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new SachMuonModel(rs, false);
        }

        return null;
    }

    
    public SachMuonModel getSachByTen(String tenSach) throws SQLException {
        String query = """
            SELECT s.id_sach, s.tensach, tg.tentacgia, s.id_tacgia, s.soluong
            FROM tbl_sach s
            INNER JOIN tbl_tacgia tg ON s.id_tacgia = tg.id_tacgia
            WHERE s.tensach LIKE ?;
        """;

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, "%" + tenSach + "%"); // Tìm kiếm gần đúng
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new SachMuonModel(rs, false);
            }
        }

        return null; // Không tìm thấy sách
    }


    public SachMuonModel getDocGiaById(int idDocGia) throws SQLException {
        String query = """
            SELECT id_docgia, hoten, email, sodt
            FROM tbl_docgia
            WHERE id_docgia = ?;
        """;

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idDocGia);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new SachMuonModel(rs, true);
        }

        return null;
    }


    public SachMuonModel getDocGiaByHoTen(String hoTen) throws SQLException {
        String query = """
            SELECT id_docgia, hoten, email, sodt
            FROM tbl_docgia
            WHERE hoten LIKE ?;
        """;

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, "%" + hoTen + "%"); // Tìm kiếm gần đúng
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new SachMuonModel(rs, true);
            }
        }

        return null; // Không tìm thấy độc giả
    }


    public boolean isSachAvailable(int idSach) throws SQLException {
        String query = "SELECT soluong FROM tbl_sach WHERE id_sach = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idSach);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("soluong") > 0; // Trả về true nếu sách còn
            }
        }
        return false; // Sách không còn
    }

    public boolean updateSoLuongSach(int idSach, int soLuongMuon) throws SQLException {
        String query = "UPDATE tbl_sach SET soluong = soluong - ? WHERE id_sach = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, soLuongMuon);
            ps.setInt(2, idSach);
            return ps.executeUpdate() > 0; // Trả về true nếu cập nhật thành công
        }
    }

    
    public boolean addPhieuMuon(int idSach, String tenSach, int idDocGia, String hoTenDocGia, String ngayMuon, String ngayTra) throws SQLException {
        if (!isSachAvailable(idSach)) {
            System.out.println("Sách không đủ số lượng để mượn!");
            return false; // Không thêm phiếu nếu sách hết
        }

        String query = """
            INSERT INTO tbl_sachmuon 
            (id_sach, tensach, id_docgia, hoten, trangthai, ngaymuon, ngaytra, status) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?);
        """;

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idSach);
            ps.setString(2, tenSach);
            ps.setInt(3, idDocGia);
            ps.setString(4, hoTenDocGia);
            ps.setString(5, "Đang mượn");
            ps.setString(6, ngayMuon);
            ps.setString(7, ngayTra);
            ps.setString(8, "Active");

            if (ps.executeUpdate() > 0) {
                // Cập nhật số lượng sách sau khi thêm phiếu
                return updateSoLuongSach(idSach, 1);
            }
        }
        return false; // Thêm phiếu thất bại
    }

    //-------------------------------------------TRẢ SÁCH-----------------------
    
    public List<SachMuonModel> getdsSachMuon() throws SQLException {
        List<SachMuonModel> dsSach = new ArrayList<>();
        String query = """
            SELECT s.id_sach, 
                   s.tensach, 
                   dg.id_docgia, 
                   dg.hoten, 
                   pm.ngaymuon, 
                   pm.ngaytra, 
                   pm.trangthai, 
                   pm.status
            FROM tbl_sachmuon pm
            INNER JOIN tbl_sach s ON pm.id_sach = s.id_sach
            INNER JOIN tbl_docgia dg ON pm.id_docgia = dg.id_docgia;
        """;

        try (Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                // Chuyển đổi ngày sang String (nếu null thì để chuỗi rỗng)
                String ngayMuon = rs.getDate("ngaymuon") != null ? rs.getDate("ngaymuon").toString() : "";
                String ngayTra = rs.getDate("ngaytra") != null ? rs.getDate("ngaytra").toString() : "";

                // Tạo đối tượng SachMuonModel từ ResultSet
                SachMuonModel sach = new SachMuonModel(
                    rs.getString("id_sach"),      // idSach
                    rs.getString("tensach"),     // tenSach
                    null,                        // idTacGia (không có trong query)
                    null,                        // tenTacGia (không có trong query)
                    null,                        // soLuong (không có trong query)
                    rs.getString("id_docgia"),   // madocgia
                    rs.getString("hoten"),       // hoten
                    null,                        // email (không có trong query)
                    null,                        // sodienthoai (không có trong query)
                    ngayMuon,                    // ngaymuon
                    ngayTra,                     // ngaytra
                    rs.getString("trangthai"),   // trangthai
                    rs.getString("status")         // mota
                );

                dsSach.add(sach);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy dữ liệu từ bảng phiếu mượn: " + e.getMessage());
        }
        return dsSach;
    }
    
    
    public boolean returnPhieuMuon(int idSach, int idDocGia) throws SQLException {
            String checkQuery = """
            SELECT trangthai FROM tbl_sachmuon 
            WHERE id_sach = ? AND id_docgia = ? AND status = 'Active';
        """;

        String updatePhieuQuery = """
            UPDATE tbl_sachmuon 
            SET trangthai = 'Đã hoàn', status = 'Inactive' 
            WHERE id_sach = ? AND id_docgia = ? AND trangthai = 'Đang mượn';
        """;

        String updateSoLuongQuery = """
            UPDATE tbl_sach 
            SET soluong = soluong + 1 
            WHERE id_sach = ?;
        """;

        try (PreparedStatement checkPs = conn.prepareStatement(checkQuery);
             PreparedStatement updatePhieuPs = conn.prepareStatement(updatePhieuQuery);
             PreparedStatement updateSoLuongPs = conn.prepareStatement(updateSoLuongQuery)) {

            // Kiểm tra trạng thái phiếu mượn
            checkPs.setInt(1, idSach);
            checkPs.setInt(2, idDocGia);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                String trangThai = rs.getString("trangthai");
                if (!"Đang mượn".equalsIgnoreCase(trangThai)) {
                    JOptionPane.showMessageDialog(null, "Phiếu mượn đã được trả hoặc không hợp lệ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy phiếu mượn!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            // Cập nhật trạng thái phiếu
            updatePhieuPs.setInt(1, idSach);
            updatePhieuPs.setInt(2, idDocGia);
            int rowsUpdated = updatePhieuPs.executeUpdate();

            if (rowsUpdated > 0) {
                // Cập nhật lại số lượng sách
                updateSoLuongPs.setInt(1, idSach);
                return updateSoLuongPs.executeUpdate() > 0;
            }
        }

        return false; // Trả phiếu thất bại
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /*
    public SachMuonModel getSachByMa(int maSach) throws SQLException {
        String query = "SELECT s.tensach, dg.hoten, pm.ngaymuon, pm.ngaytra, pm.mota " +
                       "FROM tbl_sachmuon pm " +
                       "INNER JOIN tbl_sach s ON pm.id_sach = s.id_sach " +
                       "INNER JOIN tbl_docgia dg ON pm.id_docgia = dg.id_docgia " +
                       "WHERE s.id_sach = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, maSach);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new SachMuonModel(
                            null,                   //id sach
                        rs.getString("tensach"),    //ten sach
                            null,                   //id tac gia
                            null,                   //ten tac gia
                            null,                   //so luong
                            null,                   //id doc gia
                        rs.getString("hoten"),
                            null,                   //email
                            null,                   //sdt
                        rs.getString("ngaymuon"),   //ngay muon
                        rs.getString("ngaytra"),    //ngay tra
                        rs.getString("trangthai"),   // trangthai
                        rs.getString("status")       // mota
                    );
                }
            }
        }
        return null;
    }

    public List<SachMuonModel> getSachByTen(String tenSach) throws SQLException {
        List<SachMuonModel> dsSach = new ArrayList<>();
        String query = "SELECT s.tensach, dg.hoten, pm.ngaymuon, pm.ngaytra, pm.mota " +
                       "FROM tbl_sachmuon pm " +
                       "INNER JOIN tbl_sach s ON pm.id_sach = s.id_sach " +
                       "INNER JOIN tbl_docgia dg ON pm.id_docgia = dg.id_docgia " +
                       "WHERE s.tensach LIKE ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + tenSach + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    dsSach.add(new SachMuonModel(
                            null,                   //id sach
                        rs.getString("tensach"),    //ten sach
                            null,                   //id tac gia
                            null,                   //ten tac gia
                            null,                   //so luong
                            null,                   //id doc gia
                        rs.getString("hoten"),
                            null,                   //email
                            null,                   //sdt
                        rs.getString("ngaymuon"),   //ngay muon
                        rs.getString("ngaytra"),    //ngay tra
                        rs.getString("trangthai"),   // trangthai
                        rs.getString("status")       // mota
                    ));
                }
            }
        }
        return dsSach;
    }

    public SachMuonModel getDocGiaByMa(int maDocGia) throws SQLException {
    String query = "SELECT s.tensach, dg.hoten, pm.ngaymuon, pm.ngaytra, pm.mota " +
                   "FROM tbl_sachmuon pm " +
                   "INNER JOIN tbl_sach s ON pm.id_sach = s.id_sach " +
                   "INNER JOIN tbl_docgia dg ON pm.id_docgia = dg.id_docgia " +
                   "WHERE dg.id_docgia = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, maDocGia);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return new SachMuonModel(
                            null,                   //id sach
                        rs.getString("tensach"),    //ten sach
                            null,                   //id tac gia
                            null,                   //ten tac gia
                            null,                   //so luong
                            null,                   //id doc gia
                        rs.getString("hoten"),
                            null,                   //email
                            null,                   //sdt
                        rs.getString("ngaymuon"),   //ngay muon
                        rs.getString("ngaytra"),    //ngay tra
                        rs.getString("trangthai"),   // trangthai
                        rs.getString("status")       // mota
                );
            }
        }
    }
    return null;
}

    public List<SachMuonModel> getDocGiaByHoTen(String hoTen) throws SQLException {
        List<SachMuonModel> dsDocGia = new ArrayList<>();
        String query = "SELECT s.tensach, dg.hoten, pm.ngaymuon, pm.ngaytra, pm.mota " +
                       "FROM tbl_sachmuon pm " +
                       "INNER JOIN tbl_sach s ON pm.id_sach = s.id_sach " +
                       "INNER JOIN tbl_docgia dg ON pm.id_docgia = dg.id_docgia " +
                       "WHERE dg.hoten LIKE ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + hoTen + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    dsDocGia.add(new SachMuonModel(
                            null,                   //id sach
                        rs.getString("tensach"),    //ten sach
                            null,                   //id tac gia
                            null,                   //ten tac gia
                            null,                   //so luong
                            null,                   //id doc gia
                        rs.getString("hoten"),
                            null,                   //email
                            null,                   //sdt
                        rs.getString("ngaymuon"),   //ngay muon
                        rs.getString("ngaytra"),    //ngay tra
                        rs.getString("trangthai"),   // trangthai
                        rs.getString("status")       // mota
                    ));
                }
            }
        }
        return dsDocGia;
    }
    */
    

    
    
    
    
    
}

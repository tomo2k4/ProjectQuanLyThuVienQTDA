/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ADMIN
 */
public class SachModel {
    private String idSach;
    private String tenSach;
    private Integer idTacGia;
    private Integer idTheLoai;
    private String nhaXuatBan;
    private double giaSach;
    private Integer soLuong;
    private String ngayNhan;
    private String moTa;
    private byte[] anhSach;

    public SachModel() {
    }

    public SachModel(String idSach, String tenSach, Integer idTacGia, Integer idTheLoai, String nhaXuatBan, 
            double giaSach, Integer soLuong, String ngayNhan, String moTa, byte[] anhSach) {
        this.idSach = idSach;
        this.tenSach = tenSach;
        this.idTacGia = idTacGia;
        this.idTheLoai = idTheLoai;
        this.nhaXuatBan = nhaXuatBan;
        this.giaSach = giaSach;
        this.soLuong = soLuong;
        this.ngayNhan = ngayNhan;
        this.moTa = moTa;
        this.anhSach = anhSach;
    }

    

      //khoi tao nhanh khi lam viec voi giao dien
    public SachModel(ResultSet rs) throws SQLException{
        this.idSach = rs.getString("id_sach");
        this.tenSach = rs.getString("tensach");
        this.idTacGia = rs.getInt("id_tacgia");
        this.idTheLoai = rs.getInt("id_theloai");
        this.nhaXuatBan = rs.getString("nhaxuatban");
        this.giaSach = rs.getDouble("giasach");
        this.soLuong = rs.getInt("soluong");
        this.ngayNhan = rs.getString("ngaynhan");
        this.moTa = rs.getString("mota");
        this.anhSach = rs.getBytes("anhsach");
        
    }

    public String getIdSach() {
        return idSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public Integer getIdTacGia() {
        return idTacGia;
    }

    public Integer getIdTheLoai() {
        return idTheLoai;
    }

    public String getNhaXuatBan() {
        return nhaXuatBan;
    }

    public double getGiaSach() {
        return giaSach;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public String getNgayNhan() {
        return ngayNhan;
    }

    public String getMoTa() {
        return moTa;
    }

    public byte[] getAnhSach() {
        return anhSach;
    }

    public void setIdSach(String idSach) {
        this.idSach = idSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public void setIdTacGia(Integer idTacGia) {
        this.idTacGia = idTacGia;
    }

    public void setIdTheLoai(Integer idTheLoai) {
        this.idTheLoai = idTheLoai;
    }

    public void setNhaXuatBan(String nhaXuatBan) {
        this.nhaXuatBan = nhaXuatBan;
    }

    public void setGiaSach(double giaSach) {
        this.giaSach = giaSach;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public void setNgayNhan(String ngayNhan) {
        this.ngayNhan = ngayNhan;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public void setAnhSach(byte[] anhSach) {
        this.anhSach = anhSach;
    }
    
    
}

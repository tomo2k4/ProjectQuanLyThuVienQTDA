/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.*;

/**
 *
 * @author ADMIN
 */
public class DocGiaModel {
    private String madocgia;
    private String hoten;
    private String email;
    private String sodienthoai;
    private String gioitinh;
    private byte[] anhhoso;
    
    // Khởi tạo không tham số

    public DocGiaModel() {
    }

    public DocGiaModel(String madocgia, String hoten, String email, String sodienthoai, String gioitinh, byte[] anhhoso) {
        this.madocgia = madocgia;
        this.hoten = hoten;
        this.email = email;
        this.sodienthoai = sodienthoai;
        this.gioitinh = gioitinh;
        this.anhhoso = anhhoso;
    }
    
     //khoi tao nhanh khi lam viec voi giao dien
    public DocGiaModel(ResultSet rs) throws SQLException{
        this.madocgia = rs.getString("id_docgia");
        this.hoten = rs.getString("hoten");
        this.email = rs.getString("email");
        this.sodienthoai = rs.getString("sodt");
        this.gioitinh = rs.getString("gioitinh");
        this.anhhoso = rs.getBytes("anhhoso");
    }

    public String getMadocgia() {
        return madocgia;
    }

    public String getHoten() {
        return hoten;
    }

    public String getEmail() {
        return email;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public byte[] getAnhhoso() {
        return anhhoso;
    }

    public void setMadocgia(String madocgia) {
        this.madocgia = madocgia;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public void setAnhhoso(byte[] anhhoso) {
        this.anhhoso = anhhoso;
    }
    
}

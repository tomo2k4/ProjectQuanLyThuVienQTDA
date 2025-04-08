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
public class TacGiaModel {
    private String maTacgia;
    private String tenTacgia;
    private String Chuyenmon;
    private String Gioithieu;

    public TacGiaModel() {
    }

    public TacGiaModel(String maTacgia, String tenTacgia, String Chuyenmon, String Gioithieu) {
        this.maTacgia = maTacgia;
        this.tenTacgia = tenTacgia;
        this.Chuyenmon = Chuyenmon;
        this.Gioithieu = Gioithieu;
    }

      //khoi tao nhanh khi lam viec voi giao dien
    public TacGiaModel(ResultSet rs) throws SQLException{
        this.maTacgia= rs.getString("id_tacgia");
        this.tenTacgia = rs.getString("tentacgia");
        this.Chuyenmon = rs.getString("linhvuc");
        this.Gioithieu = rs.getString("gioithieu");
    }
    
    public String getMaTacgia() {
        return maTacgia;
    }

    public String getTenTacgia() {
        return tenTacgia;
    }

    public String getChuyenmon() {
        return Chuyenmon;
    }

    public String getGioithieu() {
        return Gioithieu;
    }

    public void setMaTacgia(String maTacgia) {
        this.maTacgia = maTacgia;
    }

    public void setTenTacgia(String tenTacgia) {
        this.tenTacgia = tenTacgia;
    }

    public void setChuyenmon(String Chuyenmon) {
        this.Chuyenmon = Chuyenmon;
    }

    public void setGioithieu(String Gioithieu) {
        this.Gioithieu = Gioithieu;
    }
    
    
    
    
}

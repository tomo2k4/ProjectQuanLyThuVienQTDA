/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author ADMIN
 */
public class DBConnect {
    public Connection conn = null; 
    //Phuong thuc thuc hien ket noi CSDL 
    public Connection connectSQL() throws SQLException { 
        try { 
            String userName ="sa"; 
            String password = "tomo1912"; 
            String url = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyThuVienSach;encrypt=false;"; 
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
            conn = java.sql.DriverManager.getConnection(url,userName,password); 
            System.out.println("Ket noi thanh cong toi database");
            return conn; 
        } catch (ClassNotFoundException e) { 
            JOptionPane.showMessageDialog(null,"Ket noi CSDL that bai","Thong bao",1); 
        } 
        return null; 
    } 
}

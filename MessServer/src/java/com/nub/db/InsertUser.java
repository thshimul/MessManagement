package com.nub.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class InsertUser {
    static    Connection conn = null;
    static   PreparedStatement pst = null;
    static   ResultSet rs = null;
    public static String insert(String name,String phone,String pass){
        
        conn = DBConnection.connect();
        try {
            pst = conn.prepareStatement("INSERT INTO member(name, phone, password) VALUES(?,?,?)");
            pst.setString(1, name);
            pst.setString(2, phone);
            pst.setString(3, pass);
            
            int a = pst.executeUpdate();
            
            if(a>0){
                return "success";
            }else{
                return "Faild";
            }
        } catch (SQLException ex) {
            Logger.getLogger(InsertUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "faild";
    }
    
    public static void main(String[] args) {
        System.out.println(insert("a","b","c"));
    }
}

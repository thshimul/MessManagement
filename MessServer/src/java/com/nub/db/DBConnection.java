package com.nub.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    static Connection conn;
    
    public static Connection connect(){
        
        try{
            
            Class.forName("com.mysql.jdbc.Driver");
            conn =  DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/messmanage","root","");
            System.out.println("Success");
            
        }catch(Exception e){
            e.printStackTrace();
        }
   
    return conn;
    }
     public static Connection customConnect(String dbName){
        
        try{
            
            Class.forName("com.mysql.jdbc.Driver");
            conn =  DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/"+dbName,"root","");
            System.out.println("Success");
            
        }catch(Exception e){
            e.printStackTrace();
        }
   
    return conn;
    }
    
    
    public static void main(String[] args) {
        Connection con = connect();
        try {
            PreparedStatement pst = con.prepareStatement("create table test (id int not null auto_increment,name varchar(25),primary key(id))");
            pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

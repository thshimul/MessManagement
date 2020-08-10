package com.nub.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateDB {

    public static boolean create(String databaseName) {

        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/?user=root&password=");

            String sql = "CREATE DATABASE " + databaseName;
            
            System.out.println(sql);
            
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;   
        }

        return true;
    }
    
    public static void main(String[] args) {
        create("ruhulpagla2");
    }

}

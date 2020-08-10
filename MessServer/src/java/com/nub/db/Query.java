package com.nub.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;

public class Query {

    static Connection conn = null;
    static PreparedStatement pst = null;
    static ResultSet rs = null;
    static ArrayList<memberModel> memberData;
    static String[] mnth = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    static Date date = new Date();

    public static boolean messRegister(String dbName, String phoneNumber, String passWord) {

        conn = DBConnection.customConnect(dbName);

        String sql = "create table messAuth ("
                + "id int not null auto_increment,phone varchar(25),password varchar(50),"
                + "primary key(id))";
        System.out.println(sql);
        try {
            pst = conn.prepareStatement(sql);
            int a = pst.executeUpdate();

            sql = "insert into messauth (phone,password)values('" + phoneNumber + "','" + passWord + "')";
            System.out.println(sql);

            pst = conn.prepareStatement(sql);
            a = pst.executeUpdate();
            System.out.println("value is :" + a);
            if (a > 0) {
                System.out.println("Done........");
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return true;
    }

    public static boolean messAuthentication(String dbName, String phoneNumber, String passWord) {
        boolean con = true;
        try {
            conn = DBConnection.customConnect(dbName);

            String sql = "select password from messauth where phone = '" + phoneNumber + "'";
            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();

            while (rs.next()) {
                String pass = rs.getString("password");
                if (pass.equals(passWord)) {
                    con = true;
                } else {
                    con = false;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }

        return con;
    }

    public static boolean manageMess(String db, ArrayList<memberModel> memberData) {

        Query.memberData = memberData;
        try {
            createUser(db);
            createMeal(db);
            createBazar(db);
            createPayment(db);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private static boolean createUser(String dbName) {

        conn = DBConnection.customConnect(dbName);

        String sql = "create table user ("
                + "id int not null auto_increment,name varchar(25),phone varchar(25),password varchar(50),type varchar(25),"
                + "primary key(id))";
        System.out.println(sql);
        try {
            pst = conn.prepareStatement(sql);
            int a = pst.executeUpdate();

            for (int i = 0; i < memberData.size(); i++) {

                sql = "INSERT INTO user(name, phone, password, type) VALUES('" + memberData.get(i).getName().toString() + "','" + memberData.get(i).getNumber() + "','" + "1234','member')";
                System.out.println(sql);

                pst = conn.prepareStatement(sql);
                a = pst.executeUpdate();
                System.out.println("value is :" + a);
                if (a > 0) {
                    System.out.println("CreateUser Table Created and Data Inserted........");
                } else {
                    System.out.println("Faild number " + i);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    private static boolean createMeal(String dbName) {

        conn = DBConnection.customConnect(dbName);
        String temp = null;
        for (int i = 0; i < memberData.size(); i++) {
            if (i == 0) {
                temp = memberData.get(i).getName() + " varchar(25),";
            } else {
                if (i == (memberData.size() - 1)) {
                    temp += memberData.get(i).getName() + " varchar(25)";
                    System.out.println(temp);
                } else {
                    temp += memberData.get(i).getName() + " varchar(25),";
                    System.out.println(temp);
                }
            }
        }
        String sql = "create table Meal_" + mnth[date.getMonth()] + "_" + (1900 + date.getYear())
                + "(date int," + temp + ")";

        System.out.println(sql);
        try {
            pst = conn.prepareStatement(sql);
            int a = pst.executeUpdate();

            String query = "";
            String values = "";
            for (int i = 0; i < memberData.size(); i++) {

                if (i == memberData.size() - 1) {
                    values += "'0+0'";
                    query += memberData.get(i).getName();
                } else {
                    values += "'0+0',";
                    query += memberData.get(i).getName() + ",";
                }
            }
            sql = "insert into  Meal_" + mnth[date.getMonth()] + "_" + (1900 + date.getYear()) + " (date," + query + ") values (" + date.getDate() + "," + values + ")";
            System.out.println(sql);

            pst = conn.prepareStatement(sql);
            a = pst.executeUpdate();

            System.out.println("createMeal table created........");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    private static boolean createBazar(String dbName) {

        conn = DBConnection.customConnect(dbName);

        String sql = "create table bazar_" + mnth[date.getMonth()] + "_" + (1900 + date.getYear())
                + "(date int,name varchar(25),bazartk varchar(5),description varchar(255))";
        System.out.println(sql);
        try {
            pst = conn.prepareStatement(sql);
            int a = pst.executeUpdate();
            if (a == 0) {
                System.out.println("createBazar table created........"); 
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return true;
    }

    private static boolean createPayment(String dbName) {

        conn = DBConnection.customConnect(dbName);

        String sql = "create table payment_" + mnth[date.getMonth()] + "_" + (1900 + date.getYear())
                + "(id int not null auto_increment,name varchar(25),house_rent int,meal_tk int,"
                + "primary key(id))";
        System.out.println(sql);
        try {
            pst = conn.prepareStatement(sql);
            int a = pst.executeUpdate();
            System.out.println("value is :" + a);
            if (a == 0) {
                System.out.println("Done........");
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return true;
    }

    static String login(String type, String dbName, String phone, String pass) {
        String name = null;
        try {
            conn = DBConnection.customConnect(dbName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = "SELECT name FROM user WHERE phone = '" + phone + "'and password='" + pass + "'";
        System.out.println(sql);
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                String newname = rs.getString("name");
                System.out.println(dbName + " -->Your name is :" + newname);
                if (newname != null) {
                    name = newname;
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return name;
    }

    static void addMeal(String db, String user, String daymeal, String nightmeal) {
        date = new Date();
        String meal = daymeal + "+" + nightmeal;

        String table = "Meal_" + mnth[date.getMonth()] + "_" + (1900 + date.getYear());

        try {
            conn = DBConnection.customConnect(db);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String checkDate = "select date from " + table;
        System.out.println(checkDate);

        try {
            pst = conn.prepareStatement(checkDate);
            rs = pst.executeQuery();
            int condition =0;
            if (rs.first()) {
                System.out.println("database date = "+rs.getString("date").trim() );
                // if (rs.getString("date").trim().equals(String.valueOf(date.getDate()))){
                        //condition = 1;
                   // }
                 if(rs.first()){
                    System.out.println("compare "+rs.getString("date")+" "+date.getDate());
                    if (rs.getString("date").trim().contains(String.valueOf(date.getDate()))){
                        condition = 1;
                    }
                }
                while(rs.next()){
                    System.out.println("compare "+rs.getString("date")+" "+date.getDate());
                    if (rs.getString("date").trim().contains(String.valueOf(date.getDate()))){
                        condition = 1;
                    }
                }
                System.out.println("Data inserted before");
                rs.first();
                if (condition == 1) {

                    System.out.println("Date matched");
                    String sql = "update " + table + " set " + user + "= ('" + meal + "')where date = " + date.getDate();
                    System.out.println(sql);
                    pst = conn.prepareStatement(sql);
                    int a = pst.executeUpdate();

                    if (a > 0) {
                        System.out.println("Update successfull.........");
                    }

                } else {
                    System.out.println("Date doesn't match");
                    String newdate = rs.getString("date");
                    System.out.println("Today is "+newdate);
                    String sql = "insert into " + table + "(date," + user + ")values(" + date.getDate() + ",'" + meal + "')";
                    System.out.println(sql);
                    pst = conn.prepareStatement(sql);
                    pst.executeUpdate();

                    System.out.println("Update successfull.........");
                }
            } else {
                String sql = "insert into " + table + "(date," + user + ")values(" + date.getDate() + ",'" + meal + "')";
                System.out.println(sql);
                pst = conn.prepareStatement(sql);
                pst.executeUpdate();

                System.out.println("Update successfull.........");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    static void addBazar(String db, String bazardate, String user, String bazartk, String description) {

        String table = "bazar_" + mnth[date.getMonth()] + "_" + (1900 + date.getYear());

        try {
            conn = DBConnection.customConnect(db);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String checkDate = "select date from " + table;
        System.out.println(checkDate);

        try {
            pst = conn.prepareStatement(checkDate);
            rs = pst.executeQuery();
            if (rs.next()) {
                if (rs.getString("date").contains(bazardate)) {
                    String sql = "update " + table + " set bazartk = " + bazartk + " where name = " + user + " and date = " + bazardate;
                    System.out.println(sql);
                    pst = conn.prepareStatement(sql);
                    int a = pst.executeUpdate();

                    if (a > 0) {
                        System.out.println("Update successfull.........");
                    }

                } else {
                    String sql = "insert into " + table + " (date,name,bazartk)values(" + bazardate + ",'" + user + "','" + bazartk + "')";
                    System.out.println(sql);
                    pst = conn.prepareStatement(sql);
                    pst.executeUpdate();
                }
            } else {
                String sql = "insert into " + table + " (date,name,bazartk)values(" + bazardate + ",'" + user + "','" + bazartk + "')";
                System.out.println(sql);
                pst = conn.prepareStatement(sql);
                pst.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        String table = "Meal_" + mnth[date.getMonth()] + "_" + (1900 + date.getYear());
        String meal = "1+1";
        String user = "solaiman";
        System.out.println("Meal_" + mnth[date.getMonth()] + "_" + (1900 + date.getYear()));
        System.out.println(date.getDate());
        System.out.println("update " + table + " set " + user + "= ('" + meal + "')where date = " + date.getDate());
        System.out.println("insert into " + table + "(date," + user + ")values(" + date.getDate() + ",'" + meal + "')");
    }

    static String getAllMeal(String dbName) throws JSONException {
        System.out.println(dbName);
        String name = "";
        String mealdate[] = null;
        int c = 0;
        try {
            conn = DBConnection.customConnect(dbName);
            System.out.println("Success");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = "select * from Meal_" + mnth[date.getMonth()] + "_" + (1900 + date.getYear());
        System.out.println(sql);
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int rowCount = metaData.getColumnCount();
                rs.first();
                System.out.println("\n\n");
                for (int i = 0; i < rowCount; i++) {
                    if (i == 0) {
                        name = metaData.getColumnName(i + 1) + "--";
                    } else {
                        name += metaData.getColumnName(i + 1) + "--";
                    }
                }
                name += "@";

                for (int i = 0; i < rowCount; i++) {
                    if (rs.getString(metaData.getColumnName(i + 1)) == null) {
                        name += "0+0,";
                    } else {
                        name += rs.getString(metaData.getColumnName(i + 1)) + ",";
                    }
                }
                name += "-";
                while (rs.next()) {
                    for (int i = 0; i < rowCount; i++) {
                        if (rs.getString(metaData.getColumnName(i + 1)) == null) {
                            name += "0+0,";
                        } else {
                            name += rs.getString(metaData.getColumnName(i + 1)) + ",";
                        }
                    }
                    name += "-";
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return name;
    }

    static void addPayment(String db, String user, String houserent, String payment) {

        String table = "payment_" + mnth[date.getMonth()] + "_" + (1900 + date.getYear());
        System.out.println(table);
        try {
            conn = DBConnection.customConnect(db);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = "insert into " + table + " (name,house_rent,meal_tk)values('" + user + "'," +houserent+ "," + payment + ")";
        System.out.println(sql);
        try {
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }

    static String getAllMember(String dbName) {
        String names="";
        try {
            conn = DBConnection.customConnect(dbName);
        } catch (Exception e) {
            e.printStackTrace();
        }
         String sql="select name from user";
         System.out.println(sql);
         try {
            pst = conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while(rs.next()){
             names+=rs.getString("name")+"\n";
         }
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
         
      return names;
    }
    
    
    
      static String [] getAllBazar(String dbName){
          String Samount=null;
          String names[]=null;
          
          String table = "bazar_" + mnth[date.getMonth()] + "_" + (1900 + date.getYear());
           try {
            conn = DBConnection.customConnect(dbName);
        } catch (Exception e) {
            e.printStackTrace();
        }
          String sql="select name from "+table;
          try{
              
              pst = conn.prepareStatement(sql);
              rs = pst.executeQuery();
              int len=0;
              String tempName=null;
              while(rs.next()){
                  if(tempName==null){
                      tempName = rs.getString("name");
                      len++;
                  }else{
                      if(tempName.contains(rs.getString("name"))){
                          continue;
                      }else{
                          tempName=rs.getString("name");
                          len++;
                      }
                  }
                  
              }
              System.out.println(len);
              names = new String[len];
              rs.first();
              int i=0;
              if(rs.first()){
                  names[i]=rs.getString("name");
              }
              System.out.println("names added");
              while(rs.next()){
                  if(names[i].contains(rs.getString("name"))){
                      continue;
                  }else{
                      i++;
                      names[i]=rs.getString("name");
                  }
              }
              System.out.println("array created");
              i=0;
              for(String n:names){
                  System.out.println(n);
                  sql = "select bazartk from "+table+" where name ='"+n+"'";
                  System.out.println(sql);
                  pst = conn.prepareStatement(sql);
                  
                  rs = pst.executeQuery();
                  
                  int amount = 0;
                  
                  while(rs.next()){
                      amount+=Integer.parseInt(rs.getString("bazartk").toString().trim());
                  }
                  System.out.println("Total taka ="+amount);
                  Samount = n+":"+String.valueOf(amount)+",";
                  names[i]=Samount;
                  i++;
              }
              
          }catch(Exception e){
              e.printStackTrace();
          }
          
      return names;
      }
      
       static String [] getAllPayment(String dbName){
          String Samount=null;
          String names[]=null;
          
          String table = "payment_" + mnth[date.getMonth()] + "_" + (1900 + date.getYear());
           try {
            conn = DBConnection.customConnect(dbName);
        } catch (Exception e) {
            e.printStackTrace();
        }
          String sql="select name from "+table;
          try{
              
              pst = conn.prepareStatement(sql);
              rs = pst.executeQuery();
              int len=0;
              String tempName=null;
              while(rs.next()){
                  if(tempName==null){
                      System.out.println("name null found");
                      tempName = rs.getString("name");
                      System.out.println(tempName);
                      len++;
                  }else{
                      if(tempName.contains(rs.getString("name"))){
                          continue;
                      }else{
                          tempName=rs.getString("name");
                          len++;
                      }
                  }
                  
              }
              System.out.println(len);
              names = new String[len];
              rs.first();
              int i=0;
              if(rs.first()){
                  names[i]=rs.getString("name");
                  System.out.println("array name :"+names[i]);
              }
              System.out.println("names added");
              while(rs.next()){
                  if(names[i].contains(rs.getString("name"))){
                      continue;
                  }else{
                      i++;
                      names[i]=rs.getString("name");
                  }
              }
              System.out.println("array created");
              i=0;
              for(String n:names){
                  System.out.println("Your name is "+n);
                  sql = "select meal_tk from "+table+" where name ='"+n+"'";
                  System.out.println(sql);
                  pst = conn.prepareStatement(sql);
                  
                  rs = pst.executeQuery();
                  
                  int amount = 0;
                  
                  while(rs.next()){
                      amount+=Integer.parseInt(rs.getString("meal_tk").toString().trim());
                  }
                  System.out.println("Total Payment ="+amount);
                  Samount = n+":"+String.valueOf(amount)+"\n";
                  names[i]=Samount;
                  i++;
              }
              
          }catch(Exception e){
              e.printStackTrace();
          }
          
      return names;
       }

   
}

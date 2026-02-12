package com.wipro.book.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    public static Connection getDBConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
            String username = "system";
            String password = "system";
            Connection con = DriverManager.getConnection(url, username, password); 
            return con;
        } catch (ClassNotFoundException e) {    
            e.printStackTrace();
        } catch (SQLException e) {   
            e.printStackTrace();
        }
       return null;
    }
}
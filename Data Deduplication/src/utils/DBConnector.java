package utils;

import java.sql.*;
public class DBConnector {
    public static Connection getConnection() 
    {
        Connection conn = null;
        String driver = CommonProperties.sqlDriver;
        String secret_URL_string_of_Database = CommonProperties.sqlDbUrl;
        String secret_ID_of_Database = CommonProperties.sqlUser;
        String secret_Password_of_Database = CommonProperties.sqlPassword;
        try 
        {
            Class.forName(driver);
            conn = DriverManager.getConnection(secret_URL_string_of_Database,secret_ID_of_Database,secret_Password_of_Database);
        } 
        catch (ClassNotFoundException | SQLException e) 
        {
            System.out.println(e.toString());
        }
        return conn;
    }
}
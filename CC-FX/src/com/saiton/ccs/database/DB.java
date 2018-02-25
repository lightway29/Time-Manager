package com.saiton.ccs.database;

import java.sql.*;

public class DB {

    public static Connection connect(String username, String password,
            String hostname, String data, String port) {

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://" + hostname + ":" + port + "/" + data
                    + ""; //database name
            Connection connnection = DriverManager.getConnection(url, username,
                    password); //username + password

            if (connnection.isValid(5)) {
                return connnection;
            }
        } catch (SQLException | ClassNotFoundException |
                InstantiationException | IllegalAccessException e) {

        }
        return null;
    }

    public static boolean check(String username, String password,
            String hostname, String data, String port) {
        
        boolean success = false;
        try {
            Connection con = connect(username, password, hostname, data, port);

            if (con != null) {
                success = true;
                con.close();
            }

        } catch (SQLException ex) {

        }
        
        return success;
    }
}

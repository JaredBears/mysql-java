package projects.dao;

import java.sql.*;

import projects.exception.DbException;

public class DbConnection {
    
    private static String HOST = "localhost";
    private static String PASSWORD = "projects1";
    private static int PORT = 3306;
    private static String SCHEMA = "projects";
    private static String USER = "projects";
    
    public static java.sql.Connection getConnection() {
        String uri = String.format("jdbc:mysql://%s:%d/%s?user=%s&password=%s", HOST, PORT, SCHEMA, USER, PASSWORD);
        try {
            Connection conn = DriverManager.getConnection(uri);
            System.out.println("Connection to " + SCHEMA + " is successful");
            return conn;
        } catch(SQLException e) {
            System.out.println("Unable to connect to " + SCHEMA);
            throw new DbException ("Unable to connect to " + SCHEMA);
        }
    }

}
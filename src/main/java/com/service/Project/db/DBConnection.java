package com.service.Project.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;
    private Connection connection;

    private DBConnection() throws SQLException {
        String URL = "jdbc:mysql://localhost:3306/supermarketfx?createDatabaseIfNotExist=true";
        String USER = "root";
        String PASSWORD = "Ijse@1234";
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }
    public static DBConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }
    public Connection getConnection() {
        return connection;
    }
}

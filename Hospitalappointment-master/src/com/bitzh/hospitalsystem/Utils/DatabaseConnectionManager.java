package com.bitzh.hospitalsystem.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Xhospital";
    private static final String USER = "root";
    private static final String PASS = "xzy040520";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MySQL JDBC 驱动程序未找到", e);
        }
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}

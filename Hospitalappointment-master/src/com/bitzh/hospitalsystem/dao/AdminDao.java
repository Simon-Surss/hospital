package com.bitzh.hospitalsystem.dao;

import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDao {
    private Connection conn;

    public AdminDao(Connection conn) throws SQLException {
        this.conn = DatabaseConnectionManager.getConnection();
    }

    public boolean login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM admins WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
    }
}

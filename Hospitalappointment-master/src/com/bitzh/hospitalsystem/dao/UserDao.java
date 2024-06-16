package com.bitzh.hospitalsystem.dao;

import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;
import com.bitzh.hospitalsystem.model.Appointment;
import com.bitzh.hospitalsystem.model.Doctor;
import com.bitzh.hospitalsystem.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private final Connection conn;

    public UserDao(Connection conn) throws SQLException {
        this.conn = DatabaseConnectionManager.getConnection();
    }

    public User login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setContactInfo(rs.getString("contact_info"));
                user.setUserType(rs.getString("user_type"));
                return user;
            }
        }
        return null;
    }

    public String getUserType(String username, String password) throws SQLException {
        String sql = "SELECT user_type FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("user_type");
            }
        }
        return null;
    }

    public boolean register(User user) throws SQLException {
        if (usernameExists(user.getUsername())) {
            return false; // 用户名已存在
        }
        String sql = "INSERT INTO users (username, password, contact_info, user_type) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getContactInfo());
            pstmt.setString(4, user.getUserType() != null ? user.getUserType() : "user"); // 默认值为 'user'
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean registerDoctor(User user) throws SQLException {
        if (usernameExists(user.getUsername())) {
            return false; // 用户名已存在
        }
        String sql = "INSERT INTO users (username, password, contact_info, user_type) VALUES (?, ?, ?, 'doctor')";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getContactInfo());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean usernameExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setContactInfo(rs.getString("contact_info"));
                user.setUserType(rs.getString("user_type"));
                users.add(user);
            }
        }
        return users;
    }

    public void addUser(String username, String password) throws SQLException {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
        }
    }

    public void updateUserInfo(int id, String username, String password, String contactInfo) throws SQLException {
        if (usernameExistsExceptId(username, id)) {
            throw new SQLException("用户名已存在");
        }
        String sql = "UPDATE users SET username = ?, password = ?, contact_info = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, contactInfo);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        }
    }

    public void updateUserInfo1(int id, String username, String password) throws SQLException {
        if (usernameExistsExceptId(username, id)) {
            throw new SQLException("用户名已存在");
        }
        String sql = "UPDATE users SET username = ?, password = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        }
    }

    private boolean usernameExistsExceptId(String username, int id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND id != ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setInt(2, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public void deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public List<Appointment> viewAppointments(int userId) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(rs.getInt("id"));
                appointment.setUserId(rs.getInt("user_id"));
                appointment.setDoctorId(rs.getInt("doctor_id"));
                appointment.setAppointmentTime(Timestamp.valueOf(rs.getString("appointment_time")));
                appointments.add(appointment);
            }
        }
        return appointments;
    }

    //获取已预约的医生
    public List<Doctor> getAppointedDoctors(int userId) throws SQLException {
        String sql = "SELECT doctors.id, doctors.name FROM doctors JOIN appointments ON doctors.id = appointments.doctor_id WHERE appointments.user_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        List<Doctor> doctors = new ArrayList<>();
        while (rs.next()) {
            Doctor doctor = new Doctor();
            doctor.setId(rs.getInt("id"));
            doctor.setName(rs.getString("name"));
            doctors.add(doctor);
        }
        return doctors;
    }
}
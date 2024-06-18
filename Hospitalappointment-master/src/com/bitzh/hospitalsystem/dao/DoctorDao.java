package com.bitzh.hospitalsystem.dao;

import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;
import com.bitzh.hospitalsystem.model.Doctor;
import com.bitzh.hospitalsystem.model.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDao {
    private Connection conn;

    public DoctorDao(Connection conn) throws SQLException {
        this.conn = conn;
    }

    public Doctor Login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM doctors WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setId(rs.getInt("id"));
                doctor.setName(rs.getString("name"));
                doctor.setUsername(rs.getString("username"));
                doctor.setPassword(rs.getString("password"));
                doctor.setSpecialty(rs.getString("specialty"));
                doctor.setAvailable_time(rs.getString("available_time"));
                return doctor;
            }
        }
        return null;
    }
    public String getDoctorNameById(int id) throws SQLException {
        String sql = "SELECT name FROM doctors WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getString("name");
        } else {
            return null;
        }
    }
    public List<Doctor> getAllDoctors() throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctors";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setId(rs.getInt("id"));
                doctor.setName(rs.getString("name"));
                doctor.setSpecialty(rs.getString("specialty"));
                doctor.setAvailable_time(rs.getString("available_time"));
                doctor.setUsername(rs.getString("username"));
                doctor.setPassword(rs.getString("password"));
                doctors.add(doctor);
            }
        }
        return doctors;
    }

    public void addDoctor(String name, String specialty, String availableTime, String username, String password) throws SQLException {
        String sql = "INSERT INTO doctors (name, specialty, available_time, username, password,user_type ) VALUES (?, ?, ?, ?, ?, 'doctor')";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, specialty);
            pstmt.setString(3, availableTime);
            pstmt.setString(4, username);
            pstmt.setString(5, password);
            pstmt.executeUpdate();
        }
    }

    public void updateDoctorInfo(int id, String name, String specialty, String availableTime, String username, String password) throws SQLException {
        String sql = "UPDATE doctors SET name = ?, specialty = ?, available_time = ?, username = ?, password = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, specialty);
            pstmt.setString(3, availableTime);
            pstmt.setString(4, username);
            pstmt.setString(5, password);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
        }
    }

    public void deleteDoctor(int id) throws SQLException {
        String sql = "DELETE FROM doctors WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public List<Appointment> getDoctorAppointments(int doctorId) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE doctor_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, doctorId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(rs.getInt("id"));
                appointment.setUserId(rs.getInt("user_id"));
                appointment.setDoctorId(rs.getInt("doctor_id"));
                appointment.setAppointmentTime(rs.getTimestamp("appointment_time"));
                appointments.add(appointment);
            }
        }
        return appointments;
    }

    public boolean usernameExists(String username) throws SQLException {
        String sql = "SELECT * FROM doctors WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
    }

    // 新增方法：保存聊天信息
    public void saveMessage(String sender, String receiver, String message) throws SQLException {
        String sql = "INSERT INTO chat_messages (sender, receiver, message, timestamp) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, sender);
            pstmt.setString(2, receiver);
            pstmt.setString(3, message);
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
        }
    }

    // 新增方法：获取聊天历史记录
    public List<String> getChatHistory(String user1, String user2) throws SQLException {
        List<String> messages = new ArrayList<>();
        String sql = "SELECT sender, message, timestamp FROM chat_messages " +
                "WHERE (sender = ? AND receiver = ?) OR (sender = ? AND receiver = ?) " +
                "ORDER BY timestamp";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user1);
            pstmt.setString(2, user2);
            pstmt.setString(3, user2);
            pstmt.setString(4, user1);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String sender = rs.getString("sender");
                    String message = rs.getString("message");
                    Timestamp timestamp = rs.getTimestamp("timestamp");
                    messages.add("[" + timestamp + "] " + sender + ": " + message);
                }
            }
        }
        return messages;
    }

}

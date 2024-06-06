package com.bitzh.hospitalsystem.dao;

import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;
import com.bitzh.hospitalsystem.model.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDao {
    private Connection conn;

    public AppointmentDao(Connection conn) throws SQLException {
        this.conn = DatabaseConnectionManager.getConnection();
    }

    public List<Appointment> getAllAppointments() throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
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

    public void updateAppointmentTime(int appointmentId, String newTime) throws SQLException {
        String sql = "UPDATE appointments SET appointment_time = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newTime);
            stmt.setInt(2, appointmentId);
            stmt.executeUpdate();
        }
    }

    public void deleteAppointment(int appointmentId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, appointmentId);
            stmt.executeUpdate();
        }
    }

    public void bookAppointment(int userId, int doctorId, String appointmentTime) throws SQLException {
        String sql = "INSERT INTO appointments (user_id, doctor_id, appointment_time) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, doctorId);
            stmt.setString(3, appointmentTime);
            stmt.executeUpdate();
        }
    }
}
package com.bitzh.hospitalsystem.model;

import java.sql.Timestamp;

public class Appointment {
    private int id;
    private int userId;
    private int doctorId;
    private Timestamp appointmentTime;

    // 无参数构造方法
    public Appointment() {
    }

    // 有参数构造方法
    public Appointment(int id, int userId, int doctorId, Timestamp appointmentTime) {
        this.id = id;
        this.userId = userId;
        this.doctorId = doctorId;
        this.appointmentTime = appointmentTime;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public Timestamp getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Timestamp appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
}

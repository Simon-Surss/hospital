package com.bitzh.hospitalsystem.view;

import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;
import com.bitzh.hospitalsystem.dao.*;
import com.bitzh.hospitalsystem.model.*;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class DoctorFrame extends JFrame {
    private Doctor doctor;

    public DoctorFrame(Doctor doctor) {
        this.doctor = doctor;

        setTitle("医生界面");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JButton viewAppointmentsButton = new JButton("查看预约");
        JButton updateInfoButton = new JButton("修改个人信息");

        panel.add(viewAppointmentsButton);
        panel.add(updateInfoButton);

        add(panel);

        viewAppointmentsButton.addActionListener(e -> viewAppointments());
        updateInfoButton.addActionListener(e -> updateInfo());

        setVisible(true);
    }

    private void viewAppointments() {
        try {
            DoctorDao doctorDao = new DoctorDao(DatabaseConnectionManager.getConnection());
            List<Appointment> appointments = doctorDao.getDoctorAppointments(doctor.getId());
            StringBuilder info = new StringBuilder();
            for (Appointment appointment : appointments) {
                info.append("预约ID: ").append(appointment.getId())
                        .append(", 用户ID: ").append(appointment.getUserId())
                        .append(", 预约时间: ").append(appointment.getAppointmentTime())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(this, info.toString(), "预约信息", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateInfo() {
        String newName = JOptionPane.showInputDialog(this, "输入新姓名:");
        String newPassword = JOptionPane.showInputDialog(this, "输入新密码:");

        if (newName != null && newPassword != null) {
            try {
                DoctorDao doctorDao = new DoctorDao(DatabaseConnectionManager.getConnection());
                // 获取当前医生的其余信息
                String currentSpecialty = doctor.getSpecialty();
                String currentAvailableTime = doctor.getAvailable_time();
                String currentUsername = doctor.getUsername();
                // 调用 updateDoctorInfo 方法
                doctorDao.updateDoctorInfo(doctor.getId(), newName, currentSpecialty, currentAvailableTime, currentUsername, newPassword);
                // 更新 doctor 对象的信息
                doctor.setName(newName);
                doctor.setPassword(newPassword);
                JOptionPane.showMessageDialog(this, "信息更新成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}

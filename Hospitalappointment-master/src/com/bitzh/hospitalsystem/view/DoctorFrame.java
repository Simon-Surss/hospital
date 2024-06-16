package com.bitzh.hospitalsystem.view;

import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;
import com.bitzh.hospitalsystem.dao.DoctorDao;
import com.bitzh.hospitalsystem.model.Appointment;
import com.bitzh.hospitalsystem.model.Doctor;

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
        panel.setLayout(new GridLayout(4, 1));

        JButton viewAppointmentsButton = new JButton("查看预约");
        JButton updateInfoButton = new JButton("修改个人信息");
        JButton logoutButton = new JButton("退出");

        panel.add(viewAppointmentsButton);
        panel.add(updateInfoButton);
        panel.add(logoutButton);

        add(panel);

        viewAppointmentsButton.addActionListener(e -> viewAppointments());
        updateInfoButton.addActionListener(e -> updateInfo());
        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true); // 重新显示登录界面
            dispose(); // 关闭当前窗口
        });

        setVisible(true);
    }

    private void viewAppointments() {
        try {
            DoctorDao doctorDao = new DoctorDao(DatabaseConnectionManager.getConnection());
            List<Appointment> appointments = doctorDao.getDoctorAppointments(doctor.getId());
            new AppointmentsFrame(appointments);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateInfo() {
        new UpdateInfoFrame(doctor);
    }
}

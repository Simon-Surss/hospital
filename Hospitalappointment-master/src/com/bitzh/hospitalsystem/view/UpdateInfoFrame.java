package com.bitzh.hospitalsystem.view;


import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;
import com.bitzh.hospitalsystem.dao.DoctorDao;
import com.bitzh.hospitalsystem.model.Doctor;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

// 创建一个新的 JFrame 类，用于修改个人信息
public class UpdateInfoFrame extends JFrame {
    private Doctor doctor;

    public UpdateInfoFrame(Doctor doctor) {
        this.doctor = doctor;

        setTitle("修改个人信息");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel nameLabel = new JLabel("新姓名:");
        JLabel passwordLabel = new JLabel("新密码:");
        JTextField nameField = new JTextField();
        JTextField passwordField = new JTextField();
        JButton updateButton = new JButton("更新信息");

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(updateButton);

        add(panel);

        updateButton.addActionListener(e -> {
            String newName = nameField.getText();
            String newPassword = passwordField.getText();
            if (newName != null && newPassword != null) {
                try {
                    DoctorDao doctorDao = new DoctorDao(DatabaseConnectionManager.getConnection());
                    String currentSpecialty = doctor.getSpecialty();
                    String currentAvailableTime = doctor.getAvailable_time();
                    String currentUsername = doctor.getUsername();
                    doctorDao.updateDoctorInfo(doctor.getId(), newName, currentSpecialty, currentAvailableTime, currentUsername, newPassword);
                    doctor.setName(newName);
                    doctor.setPassword(newPassword);
                    JOptionPane.showMessageDialog(this, "信息更新成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        setVisible(true);
    }
}

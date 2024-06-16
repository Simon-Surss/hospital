package com.bitzh.hospitalsystem.view;

import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;
import com.bitzh.hospitalsystem.dao.DoctorDao;
import com.bitzh.hospitalsystem.model.Doctor;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ManageDoctorsFrame extends JFrame {
    public ManageDoctorsFrame() {
        setTitle("医生管理");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextArea textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        try {
            DoctorDao doctorDao = new DoctorDao(DatabaseConnectionManager.getConnection());
            List<Doctor> doctors = doctorDao.getAllDoctors();
            StringBuilder info = new StringBuilder();
            for (Doctor doctor : doctors) {
                info.append("ID: ").append(doctor.getId())
                        .append(", 姓名: ").append(doctor.getName())
                        .append("\n");
            }
            textArea.setText(info.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JPanel panel = new JPanel();
        add(panel, BorderLayout.SOUTH);

        JButton addButton = new JButton("添加");
        JButton updateButton = new JButton("修改");
        JButton deleteButton = new JButton("删除");

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);

        addButton.addActionListener(e -> {
            String newName = JOptionPane.showInputDialog(this, "输入新医生姓名:");
            String specialty = JOptionPane.showInputDialog(this, "输入医生专长:");
            String availableTime = JOptionPane.showInputDialog(this, "输入医生可用时间:");
            String username = JOptionPane.showInputDialog(this, "输入医生用户名:");
            String password = JOptionPane.showInputDialog(this, "输入医生密码:");
            if (newName != null && specialty != null && availableTime != null && username != null && password != null) {
                try {
                    DoctorDao doctorDao = new DoctorDao(DatabaseConnectionManager.getConnection());
                    if (doctorDao.usernameExists(username)) {
                        JOptionPane.showMessageDialog(this, "用户名已存在，请选择其他用户名", "错误", JOptionPane.ERROR_MESSAGE);
                    } else {
                        doctorDao.addDoctor(newName, specialty, availableTime, username, password);
                        JOptionPane.showMessageDialog(this, "医生添加成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "操作失败，请重试", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        updateButton.addActionListener(e -> {
            String doctorIdToUpdate = JOptionPane.showInputDialog(this, "输入要修改的医生ID:");
            String updatedName = JOptionPane.showInputDialog(this, "输入新姓名:");
            String updatedSpecialty = JOptionPane.showInputDialog(this, "输入新专长:");
            String updatedAvailableTime = JOptionPane.showInputDialog(this, "输入新可用时间:");
            String updatedUsername = JOptionPane.showInputDialog(this, "输入新用户名:");
            String updatedPassword = JOptionPane.showInputDialog(this, "输入新密码:");
            if (doctorIdToUpdate != null && updatedName != null && updatedSpecialty != null && updatedAvailableTime != null && updatedUsername != null && updatedPassword != null) {
                try {
                    DoctorDao doctorDao = new DoctorDao(DatabaseConnectionManager.getConnection());
                    doctorDao.updateDoctorInfo(Integer.parseInt(doctorIdToUpdate), updatedName, updatedSpecialty, updatedAvailableTime, updatedUsername, updatedPassword);
                    JOptionPane.showMessageDialog(this, "医生信息更新成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "用户名已存在，请选择其他用户名", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(e -> {
            String doctorIdToDelete = JOptionPane.showInputDialog(this, "输入要删除的医生ID:");
            if (doctorIdToDelete != null) {
                try {
                    DoctorDao doctorDao = new DoctorDao(DatabaseConnectionManager.getConnection());
                    doctorDao.deleteDoctor(Integer.parseInt(doctorIdToDelete));
                    JOptionPane.showMessageDialog(this, "医生删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "操作失败，请重试", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }
}

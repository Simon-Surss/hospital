package com.bitzh.hospitalsystem.view;

import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;
import com.bitzh.hospitalsystem.dao.AppointmentDao;
import com.bitzh.hospitalsystem.model.Appointment;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ManageAppointmentsFrame extends JFrame {
    public ManageAppointmentsFrame() {
        setTitle("预约管理");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextArea textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        try {
            AppointmentDao appointmentDao = new AppointmentDao(DatabaseConnectionManager.getConnection());
            List<Appointment> appointments = appointmentDao.getAllAppointments();
            StringBuilder info = new StringBuilder();
            for (Appointment appointment : appointments) {
                info.append("预约ID: ").append(appointment.getId())
                        .append(", 用户ID: ").append(appointment.getUserId())
                        .append(", 医生ID: ").append(appointment.getDoctorId())
                        .append(", 预约时间: ").append(appointment.getAppointmentTime())
                        .append("\n");
            }
            textArea.setText(info.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JPanel panel = new JPanel();
        add(panel, BorderLayout.SOUTH);

        JButton updateButton = new JButton("修改");
        JButton deleteButton = new JButton("删除");

        panel.add(updateButton);
        panel.add(deleteButton);

        updateButton.addActionListener(e -> {
            String appointmentIdToUpdate = JOptionPane.showInputDialog(this, "输入要修改的预约ID:");
            String updatedTime = JOptionPane.showInputDialog(this, "输入新预约时间 (格式: yyyy-MM-dd HH:mm):");
            if (appointmentIdToUpdate != null && updatedTime != null) {
                try {
                    AppointmentDao appointmentDao = new AppointmentDao(DatabaseConnectionManager.getConnection());
                    appointmentDao.updateAppointmentTime(Integer.parseInt(appointmentIdToUpdate), updatedTime);
                    JOptionPane.showMessageDialog(this, "预约信息更新成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "操作失败，请重试", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(e -> {
            String appointmentIdToDelete = JOptionPane.showInputDialog(this, "输入要删除的预约ID:");
            if (appointmentIdToDelete != null) {
                try {
                    AppointmentDao appointmentDao = new AppointmentDao(DatabaseConnectionManager.getConnection());
                    appointmentDao.deleteAppointment(Integer.parseInt(appointmentIdToDelete));
                    JOptionPane.showMessageDialog(this, "预约删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "操作失败，请重试", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }
}

package com.bitzh.hospitalsystem.view;

import com.bitzh.hospitalsystem.model.Appointment;

import javax.swing.*;
import java.util.List;

// 创建一个新的 JFrame 类，用于显示预约信息
public class AppointmentsFrame extends JFrame {
    private List<Appointment> appointments;

    public AppointmentsFrame(List<Appointment> appointments) {
        this.appointments = appointments;

        setTitle("预约信息");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextArea textArea = new JTextArea();
        for (Appointment appointment : appointments) {
            textArea.append("预约ID: " + appointment.getId()
                    + ", 用户ID: " + appointment.getUserId()
                    + ", 预约时间: " + appointment.getAppointmentTime() + "\n");
        }

        add(new JScrollPane(textArea));

        setVisible(true);
    }
}


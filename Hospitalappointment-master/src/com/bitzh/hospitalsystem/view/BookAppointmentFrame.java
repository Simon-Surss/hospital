package com.bitzh.hospitalsystem.view;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;
import com.bitzh.hospitalsystem.dao.AppointmentDao;
import com.bitzh.hospitalsystem.model.*;
import java.util.List;
// 用户点击预约医生跳转到的界面
public class BookAppointmentFrame extends JFrame {
    private User user;

    public BookAppointmentFrame(User user, List<Doctor> doctors) {
        this.user = user;

        setTitle("预约医生");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel doctorLabel = new JLabel("选择医生:");
        JComboBox<String> doctorComboBox = new JComboBox<>();
        for (Doctor doctor : doctors) {
            doctorComboBox.addItem(doctor.getName());
        }

        JLabel timeLabel = new JLabel("输入预约时间 (格式: yyyy-MM-dd HH:mm):");
        JTextField timeTextField = new JTextField();

        JButton bookButton = new JButton("预约");
        bookButton.addActionListener(e -> {
            String selectedDoctorName = (String) doctorComboBox.getSelectedItem();
            String appointmentTime = timeTextField.getText();
            for (Doctor doctor : doctors) {
                if (doctor.getName().equals(selectedDoctorName)) {
                    try {
                        AppointmentDao appointmentDao = new AppointmentDao(DatabaseConnectionManager.getConnection());
                        appointmentDao.bookAppointment(user.getId(), doctor.getId(), appointmentTime);
                        JOptionPane.showMessageDialog(this, "预约成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        panel.add(doctorLabel);
        panel.add(doctorComboBox);
        panel.add(timeLabel);
        panel.add(timeTextField);
        panel.add(bookButton);

        add(panel);

        setVisible(true);
    }
}

package com.bitzh.hospitalsystem.view;

import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;
import com.bitzh.hospitalsystem.dao.*;
import com.bitzh.hospitalsystem.model.*;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class UserFrame extends JFrame {
    private User user;

    public UserFrame(User user) {
        this.user = user;

        setTitle("用户界面");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));

        JButton viewDoctorsButton = new JButton("查看医生信息");
        JButton bookAppointmentButton = new JButton("预约医生");
        JButton viewAppointmentsButton = new JButton("查看预约");
        JButton rateDoctorButton = new JButton("评价医生");
        JButton updateInfoButton = new JButton("修改个人信息");

        panel.add(viewDoctorsButton);
        panel.add(bookAppointmentButton);
        panel.add(viewAppointmentsButton);
        panel.add(rateDoctorButton);
        panel.add(updateInfoButton);

        add(panel);

        viewDoctorsButton.addActionListener(e -> viewDoctors());
        bookAppointmentButton.addActionListener(e -> bookAppointment());
        viewAppointmentsButton.addActionListener(e -> viewAppointments());
        rateDoctorButton.addActionListener(e -> rateDoctor());
        updateInfoButton.addActionListener(e -> updateInfo());

        setVisible(true);
    }

    private void viewDoctors() {
        try {
            DoctorDao doctorDao = new DoctorDao(DatabaseConnectionManager.getConnection());
            List<Doctor> doctors = doctorDao.getAllDoctors();
            StringBuilder info = new StringBuilder();
            for (Doctor doctor : doctors) {
                info.append("ID: ").append(doctor.getId())
                        .append(", 姓名: ").append(doctor.getName())
                        .append(", 专长: ").append(doctor.getSpecialty())
                        .append(", 可用时间: ").append(doctor.getAvailable_time())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(this, info.toString(), "医生信息", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void bookAppointment() {
        try {
            DoctorDao doctorDao = new DoctorDao(DatabaseConnectionManager.getConnection());
            List<Doctor> doctors = doctorDao.getAllDoctors();
            String[] doctorNames = new String[doctors.size()];
            for (int i = 0; i < doctors.size(); i++) {
                doctorNames[i] = doctors.get(i).getName();
            }

            String selectedDoctorName = (String) JOptionPane.showInputDialog(this, "选择医生", "预约医生",
                    JOptionPane.QUESTION_MESSAGE, null, doctorNames, doctorNames[0]);

            if (selectedDoctorName != null) {
                for (Doctor doctor : doctors) {
                    if (doctor.getName().equals(selectedDoctorName)) {
                        String appointmentTime = JOptionPane.showInputDialog(this, "输入预约时间 (格式: yyyy-MM-dd HH:mm):");
                        if (appointmentTime != null) {
                            AppointmentDao appointmentDao = new AppointmentDao(DatabaseConnectionManager.getConnection());
                            appointmentDao.bookAppointment(user.getId(), doctor.getId(), appointmentTime);
                            JOptionPane.showMessageDialog(this, "预约成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void viewAppointments() {
        try {
            UserDao userDao = new UserDao(DatabaseConnectionManager.getConnection());
            List<Appointment> appointments = userDao.viewAppointments(user.getId());
            StringBuilder info = new StringBuilder();
            for (Appointment appointment : appointments) {
                info.append("预约ID: ").append(appointment.getId())
                        .append(", 医生ID: ").append(appointment.getDoctorId())
                        .append(", 预约时间: ").append(appointment.getAppointmentTime())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(this, info.toString(), "预约信息", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void rateDoctor() {
        try {
            DoctorDao doctorDao = new DoctorDao(DatabaseConnectionManager.getConnection());
            List<Doctor> doctors = doctorDao.getAllDoctors();
            String[] doctorNames = new String[doctors.size()];
            for (int i = 0; i < doctors.size(); i++) {
                doctorNames[i] = doctors.get(i).getName();
            }

            String selectedDoctorName = (String) JOptionPane.showInputDialog(this, "选择医生", "评价医生",
                    JOptionPane.QUESTION_MESSAGE, null, doctorNames, doctorNames[0]);

            if (selectedDoctorName != null) {
                for (Doctor doctor : doctors) {
                    if (doctor.getName().equals(selectedDoctorName)) {
                        String rating = JOptionPane.showInputDialog(this, "输入评分 (1-5):");
                        String review = JOptionPane.showInputDialog(this, "输入评价:");

                        if (rating != null && review != null) {
                            ReviewDao reviewDao = new ReviewDao(DatabaseConnectionManager.getConnection());
                            reviewDao.addReview(user.getId(), doctor.getId(), Integer.parseInt(rating), review);
                            JOptionPane.showMessageDialog(this, "评价成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateInfo() {
        String newName = JOptionPane.showInputDialog(this, "输入新姓名:");
        String newPassword = JOptionPane.showInputDialog(this, "输入新密码:");
        String newContactInfo = JOptionPane.showInputDialog(this, "输入新联系方式:");

        if (newName != null && newPassword != null && newContactInfo != null) {
            try {
                UserDao userDao = new UserDao(DatabaseConnectionManager.getConnection());
                userDao.updateUserInfo(user.getId(), newName, newPassword, newContactInfo);
                JOptionPane.showMessageDialog(this, "信息更新成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}

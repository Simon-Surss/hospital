package com.bitzh.hospitalsystem.view;

import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;
import com.bitzh.hospitalsystem.dao.*;
import com.bitzh.hospitalsystem.model.*;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class AdminFrame extends JFrame {
    public AdminFrame() {
        setTitle("管理员界面");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));

        JButton manageUsersButton = new JButton("用户管理");
        JButton manageDoctorsButton = new JButton("医生管理");
        JButton manageAppointmentsButton = new JButton("预约管理");
        JButton manageReviewsButton = new JButton("评价管理");

        panel.add(manageUsersButton);
        panel.add(manageDoctorsButton);
        panel.add(manageAppointmentsButton);
        panel.add(manageReviewsButton);

        add(panel);

        manageUsersButton.addActionListener(e -> manageUsers());
        manageDoctorsButton.addActionListener(e -> manageDoctors());
        manageAppointmentsButton.addActionListener(e -> manageAppointments());
        manageReviewsButton.addActionListener(e -> manageReviews());

        setVisible(true);
    }

    private void manageUsers() {
        try {
            UserDao userDao = new UserDao(DatabaseConnectionManager.getConnection());
            List<User> users = userDao.getAllUsers();
            StringBuilder info = new StringBuilder();
            for (User user : users) {
                info.append("ID: ").append(user.getId())
                        .append(", 姓名: ").append(user.getUsername()) // 修改这一行
                        .append("\n");
            }
            JOptionPane.showMessageDialog(this, info.toString(), "用户信息", JOptionPane.INFORMATION_MESSAGE);

            String[] options = {"添加", "修改", "删除"};
            int choice = JOptionPane.showOptionDialog(this, "选择操作", "用户管理",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    String newName = JOptionPane.showInputDialog(this, "输入新用户姓名:");
                    String newPassword = JOptionPane.showInputDialog(this, "输入新用户密码:");
                    if (newName != null && newPassword != null) {
                        userDao.addUser(newName, newPassword);
                        JOptionPane.showMessageDialog(this, "用户添加成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                case 1:
                    String userIdToUpdate = JOptionPane.showInputDialog(this, "输入要修改的用户ID:");
                    String updatedName = JOptionPane.showInputDialog(this, "输入新姓名:");
                    String updatedPassword = JOptionPane.showInputDialog(this, "输入新密码:");
                    if (userIdToUpdate != null && updatedName != null && updatedPassword != null) {
                        userDao.updateUserInfo(Integer.parseInt(userIdToUpdate), updatedName, updatedPassword);
                        JOptionPane.showMessageDialog(this, "用户信息更新成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                case 2:
                    String userIdToDelete = JOptionPane.showInputDialog(this, "输入要删除的用户ID:");
                    if (userIdToDelete != null) {
                        userDao.deleteUser(Integer.parseInt(userIdToDelete));
                        JOptionPane.showMessageDialog(this, "用户删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void manageDoctors() {
        try {
            DoctorDao doctorDao = new DoctorDao(DatabaseConnectionManager.getConnection());
            List<Doctor> doctors = doctorDao.getAllDoctors();
            StringBuilder info = new StringBuilder();
            for (Doctor doctor : doctors) {
                info.append("ID: ").append(doctor.getId())
                        .append(", 姓名: ").append(doctor.getName())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(this, info.toString(), "医生信息", JOptionPane.INFORMATION_MESSAGE);

            String[] options = {"添加", "修改", "删除"};
            int choice = JOptionPane.showOptionDialog(this, "选择操作", "医生管理",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    String newName = JOptionPane.showInputDialog(this, "输入新医生姓名:");
                    String specialty = JOptionPane.showInputDialog(this, "输入医生专长:");
                    String availableTime = JOptionPane.showInputDialog(this, "输入医生可用时间:");
                    String username = JOptionPane.showInputDialog(this, "输入医生用户名:");
                    String password = JOptionPane.showInputDialog(this, "输入医生密码:");
                    if (newName != null && specialty != null && availableTime != null && username != null && password != null) {
                        doctorDao.addDoctor(newName, specialty, availableTime, username, password);
                        JOptionPane.showMessageDialog(this, "医生添加成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                case 1:
                    String doctorIdToUpdate = JOptionPane.showInputDialog(this, "输入要修改的医生ID:");
                    String updatedName = JOptionPane.showInputDialog(this, "输入新姓名:");
                    String updatedSpecialty = JOptionPane.showInputDialog(this, "输入新专长:");
                    String updatedAvailableTime = JOptionPane.showInputDialog(this, "输入新可用时间:");
                    String updatedUsername = JOptionPane.showInputDialog(this, "输入新用户名:");
                    String updatedPassword = JOptionPane.showInputDialog(this, "输入新密码:");
                    if (doctorIdToUpdate != null && updatedName != null && updatedSpecialty != null && updatedAvailableTime != null && updatedUsername != null && updatedPassword != null) {
                        doctorDao.updateDoctorInfo(Integer.parseInt(doctorIdToUpdate), updatedName, updatedSpecialty, updatedAvailableTime, updatedUsername, updatedPassword);
                        JOptionPane.showMessageDialog(this, "医生信息更新成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                case 2:
                    String doctorIdToDelete = JOptionPane.showInputDialog(this, "输入要删除的医生ID:");
                    if (doctorIdToDelete != null) {
                        doctorDao.deleteDoctor(Integer.parseInt(doctorIdToDelete));
                        JOptionPane.showMessageDialog(this, "医生删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void manageAppointments() {
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
            JOptionPane.showMessageDialog(this, info.toString(), "预约信息", JOptionPane.INFORMATION_MESSAGE);

            String[] options = {"修改", "删除"};
            int choice = JOptionPane.showOptionDialog(this, "选择操作", "预约管理",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    String appointmentIdToUpdate = JOptionPane.showInputDialog(this, "输入要修改的预约ID:");
                    String updatedTime = JOptionPane.showInputDialog(this, "输入新预约时间 (格式: yyyy-MM-dd HH:mm):");
                    if (appointmentIdToUpdate != null && updatedTime != null) {
                        appointmentDao.updateAppointmentTime(Integer.parseInt(appointmentIdToUpdate), updatedTime);
                        JOptionPane.showMessageDialog(this, "预约信息更新成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                case 1:
                    String appointmentIdToDelete = JOptionPane.showInputDialog(this, "输入要删除的预约ID:");
                    if (appointmentIdToDelete != null) {
                        appointmentDao.deleteAppointment(Integer.parseInt(appointmentIdToDelete));
                        JOptionPane.showMessageDialog(this, "预约删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void manageReviews() {
        try {
            ReviewDao reviewDao = new ReviewDao(DatabaseConnectionManager.getConnection());
            List<Review> reviews = reviewDao.getAllReviews();
            StringBuilder info = new StringBuilder();
            for (Review review : reviews) {
                info.append("评价ID: ").append(review.getId())
                        .append(", 用户ID: ").append(review.getUserId())
                        .append(", 医生ID: ").append(review.getDoctorId())
                        .append(", 评分: ").append(review.getRating())
                        .append(", 评价内容: ").append(review.getReviewContent())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(this, info.toString(), "评价信息", JOptionPane.INFORMATION_MESSAGE);

            String reviewIdToDelete = JOptionPane.showInputDialog(this, "输入要删除的评价ID:");
            if (reviewIdToDelete != null) {
                reviewDao.deleteReview(Integer.parseInt(reviewIdToDelete));
                JOptionPane.showMessageDialog(this, "评价删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

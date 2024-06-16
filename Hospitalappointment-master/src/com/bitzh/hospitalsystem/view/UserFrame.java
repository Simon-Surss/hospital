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
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));

        JButton viewDoctorsButton = new JButton("查看医生信息");
        JButton bookAppointmentButton = new JButton("预约医生");
        JButton viewAppointmentsButton = new JButton("查看预约");
        JButton rateDoctorButton = new JButton("评价医生");
        JButton updateInfoButton = new JButton("修改个人信息");
        JButton logoutButton = new JButton("退出登录");

        panel.add(viewDoctorsButton);
        panel.add(bookAppointmentButton);
        panel.add(viewAppointmentsButton);
        panel.add(rateDoctorButton);
        panel.add(updateInfoButton);
        panel.add(logoutButton);

        add(panel);

        viewDoctorsButton.addActionListener(e -> viewDoctors());
        bookAppointmentButton.addActionListener(e -> bookAppointment());
        viewAppointmentsButton.addActionListener(e -> viewAppointments());
        rateDoctorButton.addActionListener(e -> rateDoctor());
        updateInfoButton.addActionListener(e -> updateInfo());
        logoutButton.addActionListener(e -> logout());

        setVisible(true);
    }
    //用户查看医生信息
    private void viewDoctors() {
        try {
            DoctorDao doctorDao = new DoctorDao(DatabaseConnectionManager.getConnection());
            List<Doctor> doctors = doctorDao.getAllDoctors();
            new DoctorInfoFrame(doctors);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //用户预约医生的方法
    private void bookAppointment() {
        try {
            DoctorDao doctorDao = new DoctorDao(DatabaseConnectionManager.getConnection());
            List<Doctor> doctors = doctorDao.getAllDoctors();
            new BookAppointmentFrame(user, doctors);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void viewAppointments() {
        new ViewAppointmentsFrame(user);
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
        String newName = JOptionPane.showInputDialog(this, "输入新用户名:");
        String newPassword = JOptionPane.showInputDialog(this, "输入新密码:");
        String newContactInfo = JOptionPane.showInputDialog(this, "输入新联系方式:");

        if (newName != null && newPassword != null && newContactInfo != null) {
            try {
                UserDao userDao = new UserDao(DatabaseConnectionManager.getConnection());

                // 检查新用户名是否已存在
                if (userDao.usernameExists(newName)) {
                    JOptionPane.showMessageDialog(this, "用户名已存在，请重新输入用户名", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                userDao.updateUserInfo(user.getId(), newName, newPassword, newContactInfo);
                JOptionPane.showMessageDialog(this, "信息更新成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private void logout() {
        new LoginFrame().setVisible(true);
        this.dispose();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            User user = new User();
            user.setId(1);
            user.setUsername("testUser");
            user.setPassword("password");
            user.setContactInfo("123456789");
            user.setUserType("user");

            new UserFrame(user);
        });


    }
}

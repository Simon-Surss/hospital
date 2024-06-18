package com.bitzh.hospitalsystem.view;

import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;
import com.bitzh.hospitalsystem.dao.DoctorDao;
import com.bitzh.hospitalsystem.dao.ReviewDao;
import com.bitzh.hospitalsystem.dao.UserDao;
import com.bitzh.hospitalsystem.model.Doctor;
import com.bitzh.hospitalsystem.model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

// 用户界面窗口类
public class UserFrame extends JFrame {
    private User user;  // 当前用户对象

    // 构造方法，初始化用户界面窗口
    public UserFrame(User user) {
        this.user = user;  // 设置当前用户

        setTitle("用户界面");  // 设置窗口标题
        setSize(500, 400);  // 设置窗口大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 设置窗口关闭操作为退出程序
        setLocationRelativeTo(null);  // 将窗口位置设置为屏幕中央

        JPanel panel = new JPanel();  // 创建面板
        panel.setLayout(new GridLayout(7, 1));  // 设置面板布局为7行1列的网格布局

        // 创建按钮并添加到面板
        JButton viewDoctorsButton = new JButton("查看医生信息");
        JButton bookAppointmentButton = new JButton("预约医生");
        JButton viewAppointmentsButton = new JButton("查看预约");
        JButton rateDoctorButton = new JButton("评价医生");
        JButton updateInfoButton = new JButton("修改个人信息");
        JButton chatButton = new JButton("实时沟通");
        JButton logoutButton = new JButton("退出登录");

        panel.add(viewDoctorsButton);
        panel.add(bookAppointmentButton);
        panel.add(viewAppointmentsButton);
        panel.add(rateDoctorButton);
        panel.add(updateInfoButton);
        panel.add(chatButton);
        panel.add(logoutButton);

        add(panel);  // 将面板添加到窗口

        // 按钮动作监听器
        viewDoctorsButton.addActionListener(e -> viewDoctors());
        bookAppointmentButton.addActionListener(e -> bookAppointment());
        viewAppointmentsButton.addActionListener(e -> viewAppointments());
        rateDoctorButton.addActionListener(e -> rateDoctor());
        updateInfoButton.addActionListener(e -> updateInfo());
        chatButton.addActionListener(e -> startChat());
        logoutButton.addActionListener(e -> logout());

        setVisible(true);  // 设置窗口可见
    }

    // 查看医生信息方法
    private void viewDoctors() {
        try {
            DoctorDao doctorDao = new DoctorDao(DatabaseConnectionManager.getConnection());  // 创建DoctorDao对象
            List<Doctor> doctors = doctorDao.getAllDoctors();  // 获取所有医生信息
            new DoctorInfoFrame(doctors);  // 打开医生信息窗口显示医生信息
        } catch (SQLException e) {
            e.printStackTrace();  // 捕获SQL异常并打印异常信息
        }
    }

    // 预约医生方法
    private void bookAppointment() {
        try {
            DoctorDao doctorDao = new DoctorDao(DatabaseConnectionManager.getConnection());  // 创建DoctorDao对象
            List<Doctor> doctors = doctorDao.getAllDoctors();  // 获取所有医生信息
            new BookAppointmentFrame(user, doctors);  // 打开预约窗口，并传入当前用户和医生列表
        } catch (SQLException e) {
            e.printStackTrace();  // 捕获SQL异常并打印异常信息
        }
    }

    // 查看预约方法
    private void viewAppointments() {
        new ViewAppointmentsFrame(user);  // 打开查看预约窗口，并传入当前用户信息
    }

    // 评价医生方法
    private void rateDoctor() {
        try {
            UserDao userDao = new UserDao(DatabaseConnectionManager.getConnection());  // 创建UserDao对象
            List<Doctor> doctors = userDao.getAppointedDoctors(user.getId());  // 获取用户已经预约过的医生列表

            String[] doctorNames = new String[doctors.size()];  // 创建医生姓名数组
            for (int i = 0; i < doctors.size(); i++) {
                doctorNames[i] = doctors.get(i).getName();  // 将医生姓名添加到数组
            }

            // 弹出对话框，让用户根据预约选择要评价的医生
            String selectedDoctorName = (String) JOptionPane.showInputDialog(this, "选择医生", "评价医生",
                    JOptionPane.QUESTION_MESSAGE, null, doctorNames, doctorNames[0]);

            if (selectedDoctorName != null) {
                for (Doctor doctor : doctors) {
                    if (doctor.getName().equals(selectedDoctorName)) {
                        String rating = JOptionPane.showInputDialog(this, "输入评分 (1-5):");  // 弹出输入评分对话框
                        String review = JOptionPane.showInputDialog(this, "输入评价:");  // 弹出输入评价对话框

                        if (rating != null && review != null) {
                            ReviewDao reviewDao = new ReviewDao(DatabaseConnectionManager.getConnection());  // 创建ReviewDao对象
                            reviewDao.addReview(user.getId(), doctor.getId(), Integer.parseInt(rating), review);  // 添加评价到数据库
                            JOptionPane.showMessageDialog(this, "评价成功", "提示", JOptionPane.INFORMATION_MESSAGE);  // 弹出评价成功消息框
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // 捕获SQL异常并打印异常信息
        }
    }

    // 修改个人信息方法
    private void updateInfo() {
        String newName = JOptionPane.showInputDialog(this, "输入新用户名:");  // 弹出输入新用户名对话框
        String newPassword = JOptionPane.showInputDialog(this, "输入新密码:");  // 弹出输入新密码对话框
        String newContactInfo = JOptionPane.showInputDialog(this, "输入新联系方式:");  // 弹出输入新联系方式对话框

        if (newName != null && newPassword != null && newContactInfo != null) {
            try {
                UserDao userDao = new UserDao(DatabaseConnectionManager.getConnection());  // 创建UserDao对象

                // 检查新用户名是否已存在
                if (userDao.usernameExists(newName)) {
                    JOptionPane.showMessageDialog(this, "用户名已存在，请重新输入用户名", "错误", JOptionPane.ERROR_MESSAGE);  // 弹出用户名已存在错误消息框
                    return;
                }

                userDao.updateUserInfo(user.getId(), newName, newPassword, newContactInfo);  // 更新用户信息到数据库
                JOptionPane.showMessageDialog(this, "信息更新成功", "提示", JOptionPane.INFORMATION_MESSAGE);  // 弹出信息更新成功消息框
            } catch (SQLException e) {
                e.printStackTrace();  // 捕获SQL异常并打印异常信息
            }
        }
    }

    // 退出登录方法
    private void logout() {
        new LoginFrame().setVisible(true);  // 打开登录窗口
        this.dispose();  // 关闭当前用户界面窗口
    }

    private void startChat() {
        String doctorID = JOptionPane.showInputDialog(this, "输入医生的ID:");
        if (doctorID!= null && !doctorID.isEmpty()) {
            ChatFrame chatFrame = new ChatFrame("用户(ID:" + user.getUserID() + ")", "医生(ID:" + doctorID + ")");
            chatFrame.setVisible(true);
        }
    }

    // 主方法，用于测试界面
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 创建一个测试用户对象
            User user = new User();
            user.setId(1);
            user.setUsername("testUser");
            user.setPassword("password");
            user.setContactInfo("123456789");
            user.setUserType("user");

            new UserFrame(user);  // 创建用户界面窗口，并显示
        });
    }
}

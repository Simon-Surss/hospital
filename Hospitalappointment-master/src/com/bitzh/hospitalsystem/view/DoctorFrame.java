package com.bitzh.hospitalsystem.view;

import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;
import com.bitzh.hospitalsystem.dao.DoctorDao;
import com.bitzh.hospitalsystem.model.Appointment;
import com.bitzh.hospitalsystem.model.Doctor;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

// 创建一个新的 JFrame 类，用于显示医生界面
public class DoctorFrame extends JFrame {
    private Doctor doctor; // 声明一个 Doctor 对象，用于存储当前医生信息

    // 构造函数，接收一个 Doctor 对象作为参数
    public DoctorFrame(Doctor doctor) {
        this.doctor = doctor; // 初始化 doctor 对象

        setTitle("医生界面"); // 设置窗口标题
        setSize(500, 300); // 设置窗口大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置默认关闭操作，关闭窗口时退出程序
        setLocationRelativeTo(null); // 设置窗口居中显示

        // 创建主面板，并设置网格布局
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1)); // 使用 4 行 1 列的网格布局

        // 创建按钮组件
        JButton viewAppointmentsButton = new JButton("查看预约");
        JButton updateInfoButton = new JButton("修改个人信息");
        JButton chatButton = new JButton("实时沟通");
        JButton logoutButton = new JButton("退出");

        // 将按钮添加到面板
        panel.add(viewAppointmentsButton);
        panel.add(updateInfoButton);
        panel.add(chatButton);
        panel.add(logoutButton);

        add(panel); // 将面板添加到窗口

        viewAppointmentsButton.addActionListener(e -> viewAppointments());
        updateInfoButton.addActionListener(e -> updateInfo());
        chatButton.addActionListener(e -> startChat());
        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true); // 重新显示登录界面
            dispose(); // 关闭当前窗口
        });

        setVisible(true);

    }

    // 查看预约的事件处理方法
    private void viewAppointments() {
        try {
            DoctorDao doctorDao = new DoctorDao(DatabaseConnectionManager.getConnection()); // 创建 DoctorDao 对象
            List<Appointment> appointments = doctorDao.getDoctorAppointments(doctor.getId()); // 获取医生的预约信息
            new AppointmentsFrame(appointments); // 显示预约信息
        } catch (SQLException e) { // 捕获并处理 SQL 异常
            e.printStackTrace();
        }
    }
    private void startChat() {
        String userID = JOptionPane.showInputDialog(this, "输入用户的ID:");
        if (userID != null && !userID.isEmpty()) {
            ChatFrame chatFrame = new ChatFrame("医生(ID:" + doctor.getUserID() + ")", "用户(ID:" + userID + ")");
            chatFrame.setVisible(true);
        }
    }


    // 修改个人信息的事件处理方法
    private void updateInfo() {
        new UpdateInfoFrame(doctor); // 显示修改个人信息的界面
    }
}

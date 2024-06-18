package com.bitzh.hospitalsystem.view;

import javax.swing.*;
import java.awt.*;

public class AdminFrame extends JFrame {
    // 构造函数，初始化管理员界面
    public AdminFrame() {
        setTitle("管理员界面"); // 设置窗口标题
        setSize(400, 300); // 设置窗口大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置默认关闭操作
        setLocationRelativeTo(null); // 设置窗口居中显示

        // 创建一个面板，使用网格布局
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1)); // 设置网格布局，6行1列

        // 创建各个功能按钮
        JButton manageUsersButton = new JButton("用户管理");
        JButton manageDoctorsButton = new JButton("医生管理");
        JButton manageAppointmentsButton = new JButton("预约管理");
        JButton manageReviewsButton = new JButton("评价管理");
        JButton logoutButton = new JButton("退出");

        // 将按钮添加到面板中
        panel.add(manageUsersButton);
        panel.add(manageDoctorsButton);
        panel.add(manageAppointmentsButton);
        panel.add(manageReviewsButton);
        panel.add(logoutButton);

        add(panel); // 将面板添加到窗口

        // 设置用户管理按钮的事件监听，点击后打开用户管理界面
        manageUsersButton.addActionListener(e -> new ManageUsersFrame());
        // 设置医生管理按钮的事件监听，点击后打开医生管理界面
        manageDoctorsButton.addActionListener(e -> new ManageDoctorsFrame());
        // 设置预约管理按钮的事件监听，点击后打开预约管理界面
        manageAppointmentsButton.addActionListener(e -> new ManageAppointmentsFrame());
        // 设置评价管理按钮的事件监听，点击后打开评价管理界面
        manageReviewsButton.addActionListener(e -> new ManageReviewsFrame());

        // 设置退出按钮的事件监听，点击后返回登录界面并关闭当前窗口
        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true); // 重新显示登录界面
            dispose(); // 关闭当前窗口
        });

        setVisible(true); // 设置窗口可见
    }
}

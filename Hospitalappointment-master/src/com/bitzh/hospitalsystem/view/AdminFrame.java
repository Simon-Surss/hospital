package com.bitzh.hospitalsystem.view;

import javax.swing.*;
import java.awt.*;

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
        JButton logoutButton = new JButton("退出");

        panel.add(manageUsersButton);
        panel.add(manageDoctorsButton);
        panel.add(manageAppointmentsButton);
        panel.add(manageReviewsButton);
        panel.add(logoutButton);

        add(panel);

        manageUsersButton.addActionListener(e -> new ManageUsersFrame());
        manageDoctorsButton.addActionListener(e -> new ManageDoctorsFrame());
        manageAppointmentsButton.addActionListener(e -> new ManageAppointmentsFrame());
        manageReviewsButton.addActionListener(e -> new ManageReviewsFrame());

        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true); // 重新显示登录界面
            dispose(); // 关闭当前窗口
        });

        setVisible(true);
    }
}

package com.bitzh.hospitalsystem.view;

import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;
import com.bitzh.hospitalsystem.model.Doctor;
import com.bitzh.hospitalsystem.dao.ReviewDao;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class DoctorDetailFrame extends JFrame {

    public DoctorDetailFrame(Doctor doctor) {
        setTitle("医生详情");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 关闭该窗口不会退出程序
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        JLabel nameLabel = new JLabel("姓名：" + doctor.getName());
        JLabel specialtyLabel = new JLabel("特长：" + doctor.getSpecialty());
        JLabel availableTimeLabel = new JLabel("预约时间段：" + doctor.getAvailable_time());

        panel.add(nameLabel);
        panel.add(specialtyLabel);
        panel.add(availableTimeLabel);

        // 获取医生的评价信息并显示
        try {
            ReviewDao reviewDao = new ReviewDao(DatabaseConnectionManager.getConnection());
            String reviews = reviewDao.getDoctorReviews(doctor.getId()).toString();
            JLabel reviewsLabel = new JLabel("评价：" + reviews);
            panel.add(reviewsLabel);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane);

        setVisible(true);
    }
}

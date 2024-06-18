package com.bitzh.hospitalsystem.view;

import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;
import com.bitzh.hospitalsystem.dao.DoctorDao;
import com.bitzh.hospitalsystem.model.Doctor;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

// 创建一个新的 JFrame 类，用于修改个人信息
public class UpdateInfoFrame extends JFrame {
    private Doctor doctor;  // 医生对象

    public UpdateInfoFrame(Doctor doctor) {
        this.doctor = doctor;  // 初始化医生对象

        setTitle("修改个人信息");  // 设置窗口标题
        setSize(400, 300);  // 设置窗口大小
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // 设置窗口关闭操作为释放资源
        setLocationRelativeTo(null);  // 将窗口位置设置为屏幕中央

        JPanel panel = new JPanel();  // 创建面板
        panel.setLayout(new GridLayout(3, 2));  // 设置面板布局为网格布局，3行2列

        JLabel nameLabel = new JLabel("新姓名:");  // 创建标签“新姓名”
        JLabel passwordLabel = new JLabel("新密码:");  // 创建标签“新密码”
        JTextField nameField = new JTextField();  // 创建文本框用于输入新姓名
        JTextField passwordField = new JTextField();  // 创建文本框用于输入新密码
        JButton updateButton = new JButton("更新信息");  // 创建更新信息按钮

        panel.add(nameLabel);  // 将“新姓名”标签添加到面板
        panel.add(nameField);  // 将新姓名文本框添加到面板
        panel.add(passwordLabel);  // 将“新密码”标签添加到面板
        panel.add(passwordField);  // 将新密码文本框添加到面板
        panel.add(updateButton);  // 将更新信息按钮添加到面板

        add(panel);  // 将面板添加到窗口

        updateButton.addActionListener(e -> {  // 注册更新按钮的动作监听器
            String newName = nameField.getText();  // 获取新姓名文本框中的内容
            String newPassword = passwordField.getText();  // 获取新密码文本框中的内容
            if (newName != null && newPassword != null) {  // 确保新姓名和新密码均不为空
                try {
                    DoctorDao doctorDao = new DoctorDao(DatabaseConnectionManager.getConnection());  // 创建DoctorDao对象
                    String currentSpecialty = doctor.getSpecialty();  // 获取当前医生的专长
                    String currentAvailableTime = doctor.getAvailable_time();  // 获取当前医生的可用时间
                    String currentUsername = doctor.getUsername();  // 获取当前医生的用户名
                    doctorDao.updateDoctorInfo(doctor.getId(), newName, currentSpecialty, currentAvailableTime, currentUsername, newPassword);  // 调用DoctorDao更新医生信息
                    doctor.setName(newName);  // 更新医生对象中的姓名
                    doctor.setPassword(newPassword);  // 更新医生对象中的密码
                    JOptionPane.showMessageDialog(this, "信息更新成功", "提示", JOptionPane.INFORMATION_MESSAGE);  // 弹出消息框提示信息更新成功
                } catch (SQLException ex) {
                    ex.printStackTrace();  // 打印SQL异常信息
                }
            }
        });

        setVisible(true);  // 设置窗口可见
    }
}

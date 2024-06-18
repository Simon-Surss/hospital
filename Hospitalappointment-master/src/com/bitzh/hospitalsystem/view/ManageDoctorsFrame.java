package com.bitzh.hospitalsystem.view;

import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;
import com.bitzh.hospitalsystem.dao.DoctorDao;
import com.bitzh.hospitalsystem.model.Doctor;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ManageDoctorsFrame extends JFrame {
    public ManageDoctorsFrame() {
        setTitle("医生管理");  // 设置窗口标题
        setSize(400, 300);  // 设置窗口大小
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // 设置窗口关闭操作为释放资源
        setLocationRelativeTo(null);  // 将窗口位置设置为屏幕中央

        JTextArea textArea = new JTextArea();  // 创建文本区域组件
        add(new JScrollPane(textArea), BorderLayout.CENTER);  // 添加带滚动条的文本区域到窗口中央位置

        try {
            DoctorDao doctorDao = new DoctorDao(DatabaseConnectionManager.getConnection());  // 创建DoctorDao对象
            List<Doctor> doctors = doctorDao.getAllDoctors();  // 调用DoctorDao的getAllDoctors方法获取所有医生信息
            StringBuilder info = new StringBuilder();  // 创建字符串构建器用于存储医生信息的字符串
            for (Doctor doctor : doctors) {  // 遍历医生列表
                // 构建每个医生信息的字符串，并添加到字符串构建器中
                info.append("ID: ").append(doctor.getId())
                        .append(", 姓名: ").append(doctor.getName())
                        .append("\n");
            }
            textArea.setText(info.toString());  // 在文本区域中显示医生信息字符串
        } catch (SQLException e) {
            e.printStackTrace();  // 打印SQL异常信息
        }

        JPanel panel = new JPanel();  // 创建面板
        add(panel, BorderLayout.SOUTH);  // 将面板添加到窗口南侧位置

        JButton addButton = new JButton("添加");  // 创建添加按钮
        JButton updateButton = new JButton("修改");  // 创建修改按钮
        JButton deleteButton = new JButton("删除");  // 创建删除按钮

        panel.add(addButton);  // 将添加按钮添加到面板中
        panel.add(updateButton);  // 将修改按钮添加到面板中
        panel.add(deleteButton);  // 将删除按钮添加到面板中

        // 添加添加按钮的动作监听器
        addButton.addActionListener(e -> {
            String newName = JOptionPane.showInputDialog(this, "输入新医生姓名:");  // 弹出输入对话框获取新医生姓名
            String specialty = JOptionPane.showInputDialog(this, "输入医生专长:");  // 弹出输入对话框获取医生专长
            String availableTime = JOptionPane.showInputDialog(this, "输入医生可用时间:");  // 弹出输入对话框获取医生可用时间
            String username = JOptionPane.showInputDialog(this, "输入医生用户名:");  // 弹出输入对话框获取医生用户名
            String password = JOptionPane.showInputDialog(this, "输入医生密码:");  // 弹出输入对话框获取医生密码
            if (newName != null && specialty != null && availableTime != null && username != null && password != null) {  // 如果所有信息都不为空
                try {
                    DoctorDao doctorDao = new DoctorDao(DatabaseConnectionManager.getConnection());  // 创建DoctorDao对象
                    if (doctorDao.usernameExists(username)) {  // 检查用户名是否已存在
                        JOptionPane.showMessageDialog(this, "用户名已存在，请选择其他用户名", "错误", JOptionPane.ERROR_MESSAGE);  // 弹出消息框提示用户名已存在
                    } else {
                        doctorDao.addDoctor(newName, specialty, availableTime, username, password);  // 调用DoctorDao的addDoctor方法添加医生
                        JOptionPane.showMessageDialog(this, "医生添加成功", "提示", JOptionPane.INFORMATION_MESSAGE);  // 弹出消息框提示医生添加成功
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "操作失败，请重试", "错误", JOptionPane.ERROR_MESSAGE);  // 弹出消息框提示操作失败
                }
            }
        });

        // 添加修改按钮的动作监听器
        updateButton.addActionListener(e -> {
            String doctorIdToUpdate = JOptionPane.showInputDialog(this, "输入要修改的医生ID:");  // 弹出输入对话框获取要修改的医生ID
            String updatedName = JOptionPane.showInputDialog(this, "输入新姓名:");  // 弹出输入对话框获取新的医生姓名
            String updatedSpecialty = JOptionPane.showInputDialog(this, "输入新专长:");  // 弹出输入对话框获取新的医生专长
            String updatedAvailableTime = JOptionPane.showInputDialog(this, "输入新可用时间:");  // 弹出输入对话框获取新的医生可用时间
            String updatedUsername = JOptionPane.showInputDialog(this, "输入新用户名:");  // 弹出输入对话框获取新的医生用户名
            String updatedPassword = JOptionPane.showInputDialog(this, "输入新密码:");  // 弹出输入对话框获取新的医生密码
            if (doctorIdToUpdate != null && updatedName != null && updatedSpecialty != null && updatedAvailableTime != null && updatedUsername != null && updatedPassword != null) {  // 如果所有信息都不为空
                try {
                    DoctorDao doctorDao = new DoctorDao(DatabaseConnectionManager.getConnection());  // 创建DoctorDao对象
                    doctorDao.updateDoctorInfo(Integer.parseInt(doctorIdToUpdate), updatedName, updatedSpecialty, updatedAvailableTime, updatedUsername, updatedPassword);  // 调用DoctorDao的updateDoctorInfo方法更新医生信息
                    JOptionPane.showMessageDialog(this, "医生信息更新成功", "提示", JOptionPane.INFORMATION_MESSAGE);  // 弹出消息框提示医生信息更新成功
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "用户名已存在，请选择其他用户名", "错误", JOptionPane.ERROR_MESSAGE);  // 弹出消息框提示操作失败
                }
            }
        });

        // 添加删除按钮的动作监听器
        deleteButton.addActionListener(e -> {
            String doctorIdToDelete = JOptionPane.showInputDialog(this, "输入要删除的医生ID:");  // 弹出输入对话框获取要删除的医生ID
            if (doctorIdToDelete != null) {  // 如果医生ID不为空
                try {
                    DoctorDao doctorDao = new DoctorDao(DatabaseConnectionManager.getConnection());  // 创建DoctorDao对象
                    doctorDao.deleteDoctor(Integer.parseInt(doctorIdToDelete));  // 调用DoctorDao的deleteDoctor方法删除医生
                    JOptionPane.showMessageDialog(this, "医生删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);  // 弹出消息框提示医生删除成功
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "操作失败，请重试", "错误", JOptionPane.ERROR_MESSAGE);  // 弹出消息框提示操作失败
                }
            }
        });

        setVisible(true);  // 设置窗口可见
    }
}

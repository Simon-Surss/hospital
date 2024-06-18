package com.bitzh.hospitalsystem.view;

import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;
import com.bitzh.hospitalsystem.dao.AppointmentDao;
import com.bitzh.hospitalsystem.model.Appointment;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ManageAppointmentsFrame extends JFrame {
    public ManageAppointmentsFrame() {
        setTitle("预约管理");  // 设置窗口标题
        setSize(400, 300);  // 设置窗口大小
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // 设置窗口关闭操作为释放资源
        setLocationRelativeTo(null);  // 将窗口位置设置为屏幕中央

        JTextArea textArea = new JTextArea();  // 创建文本区域组件
        add(new JScrollPane(textArea), BorderLayout.CENTER);  // 添加带滚动条的文本区域到窗口中央位置

        try {
            AppointmentDao appointmentDao = new AppointmentDao(DatabaseConnectionManager.getConnection());  // 创建AppointmentDao对象
            List<Appointment> appointments = appointmentDao.getAllAppointments();  // 调用AppointmentDao的getAllAppointments方法获取所有预约信息
            StringBuilder info = new StringBuilder();  // 创建字符串构建器用于存储预约信息的字符串
            for (Appointment appointment : appointments) {  // 遍历预约列表
                // 构建每个预约信息的字符串，并添加到字符串构建器中
                info.append("预约ID: ").append(appointment.getId())
                        .append(", 用户ID: ").append(appointment.getUserId())
                        .append(", 医生ID: ").append(appointment.getDoctorId())
                        .append(", 预约时间: ").append(appointment.getAppointmentTime())
                        .append("\n");
            }
            textArea.setText(info.toString());  // 在文本区域中显示预约信息字符串
        } catch (SQLException e) {
            e.printStackTrace();  // 打印SQL异常信息
        }

        JPanel panel = new JPanel();  // 创建面板
        add(panel, BorderLayout.SOUTH);  // 将面板添加到窗口南侧位置

        JButton updateButton = new JButton("修改");  // 创建修改按钮
        JButton deleteButton = new JButton("删除");  // 创建删除按钮

        panel.add(updateButton);  // 将修改按钮添加到面板中
        panel.add(deleteButton);  // 将删除按钮添加到面板中

        // 添加修改按钮的动作监听器
        updateButton.addActionListener(e -> {
            String appointmentIdToUpdate = JOptionPane.showInputDialog(this, "输入要修改的预约ID:");  // 弹出输入对话框获取要修改的预约ID
            String updatedTime = JOptionPane.showInputDialog(this, "输入新预约时间 (格式: yyyy-MM-dd HH:mm):");  // 弹出输入对话框获取新的预约时间
            if (appointmentIdToUpdate != null && updatedTime != null) {  // 如果预约ID和新预约时间都不为空
                try {
                    AppointmentDao appointmentDao = new AppointmentDao(DatabaseConnectionManager.getConnection());  // 创建AppointmentDao对象
                    appointmentDao.updateAppointmentTime(Integer.parseInt(appointmentIdToUpdate), updatedTime);  // 调用AppointmentDao的updateAppointmentTime方法更新预约时间
                    JOptionPane.showMessageDialog(this, "预约信息更新成功", "提示", JOptionPane.INFORMATION_MESSAGE);  // 弹出消息框提示预约信息更新成功
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "操作失败，请重试", "错误", JOptionPane.ERROR_MESSAGE);  // 弹出消息框提示操作失败
                }
            }
        });

        // 添加删除按钮的动作监听器
        deleteButton.addActionListener(e -> {
            String appointmentIdToDelete = JOptionPane.showInputDialog(this, "输入要删除的预约ID:");  // 弹出输入对话框获取要删除的预约ID
            if (appointmentIdToDelete != null) {  // 如果预约ID不为空
                try {
                    AppointmentDao appointmentDao = new AppointmentDao(DatabaseConnectionManager.getConnection());  // 创建AppointmentDao对象
                    appointmentDao.deleteAppointment(Integer.parseInt(appointmentIdToDelete));  // 调用AppointmentDao的deleteAppointment方法删除预约
                    JOptionPane.showMessageDialog(this, "预约删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);  // 弹出消息框提示预约删除成功
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "操作失败，请重试", "错误", JOptionPane.ERROR_MESSAGE);  // 弹出消息框提示操作失败
                }
            }
        });

        setVisible(true);  // 设置窗口可见
    }
}

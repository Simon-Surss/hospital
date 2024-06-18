package com.bitzh.hospitalsystem.view;

import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;
import com.bitzh.hospitalsystem.dao.DoctorDao;
import com.bitzh.hospitalsystem.dao.UserDao;
import com.bitzh.hospitalsystem.model.User;
import com.bitzh.hospitalsystem.model.Appointment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

// 用户查看已有预约窗口类
public class ViewAppointmentsFrame extends JFrame {
    private User user;  // 当前用户对象

    // 构造方法，初始化查看预约窗口
    public ViewAppointmentsFrame(User user) {
        this.user = user;  // 设置当前用户

        setTitle("查看预约");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // 设置窗口关闭操作为关闭窗口
        setLocationRelativeTo(null);  // 将窗口位置设置为屏幕中央

        String[] columnNames = {"预约ID", "医生姓名", "预约时间"};  // 表格列名数组
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);  // 创建表格模型
        JTable table = new JTable(model);  // 创建表格并使用模型

        try {
            UserDao userDao = new UserDao(DatabaseConnectionManager.getConnection());  // 创建UserDao对象
            DoctorDao doctorDao = new DoctorDao(DatabaseConnectionManager.getConnection());  // 创建DoctorDao对象
            List<Appointment> appointments = userDao.viewAppointments(user.getId());  // 获取当前用户的所有预约信息
            for (Appointment appointment : appointments) {  // 遍历预约列表
                Object[] row = new Object[3];  // 创建行数据数组
                row[0] = appointment.getId();  // 设置预约ID
                row[1] = doctorDao.getDoctorNameById(appointment.getDoctorId());  // 根据医生ID获取医生姓名
                row[2] = appointment.getAppointmentTime();  // 设置预约时间
                model.addRow(row);  // 将行数据添加到表格模型
            }
        } catch (SQLException e) {
            e.printStackTrace();  // 捕获SQL异常并打印异常信息
        }

        add(new JScrollPane(table), BorderLayout.CENTER);  // 将带有滚动条的表格添加到窗口的中间位置
        setVisible(true);  // 设置窗口可见
    }
}

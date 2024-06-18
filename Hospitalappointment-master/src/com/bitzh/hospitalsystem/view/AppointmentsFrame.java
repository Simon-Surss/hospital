package com.bitzh.hospitalsystem.view;

import com.bitzh.hospitalsystem.model.Appointment;

import javax.swing.*;
import java.util.List;

// 创建一个新的 JFrame 类，用于显示预约信息
public class AppointmentsFrame extends JFrame {
    // 存储预约信息的列表
    private List<Appointment> appointments;

    // 构造函数，接收预约信息列表并初始化界面
    public AppointmentsFrame(List<Appointment> appointments) {
        this.appointments = appointments;

        setTitle("预约信息"); // 设置窗口标题
        setSize(400, 300); // 设置窗口大小
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 设置默认关闭操作，仅关闭当前窗口
        setLocationRelativeTo(null); // 设置窗口居中显示

        // 创建一个文本区域用于显示预约信息
        JTextArea textArea = new JTextArea();
        // 遍历预约信息列表，将每个预约信息添加到文本区域中
        for (Appointment appointment : appointments) {
            textArea.append("预约ID: " + appointment.getId()
                    + ", 用户ID: " + appointment.getUserId()
                    + ", 预约时间: " + appointment.getAppointmentTime() + "\n");
        }

        // 将文本区域添加到带滚动条的面板中
        add(new JScrollPane(textArea));

        setVisible(true); // 设置窗口可见
    }
}

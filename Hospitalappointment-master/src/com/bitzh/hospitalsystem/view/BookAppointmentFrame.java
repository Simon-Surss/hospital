package com.bitzh.hospitalsystem.view;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;
import com.bitzh.hospitalsystem.dao.AppointmentDao;
import com.bitzh.hospitalsystem.model.*;
import java.util.List;

// 用户点击预约医生跳转到的界面
public class BookAppointmentFrame extends JFrame {
    // 当前登录的用户
    private User user;

    // 构造函数，接收用户和医生列表作为参数
    public BookAppointmentFrame(User user, List<Doctor> doctors) {
        this.user = user;

        setTitle("预约医生"); // 设置窗口标题
        setSize(500, 400); // 设置窗口大小
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 设置默认关闭操作，仅关闭当前窗口
        setLocationRelativeTo(null); // 设置窗口居中显示

        // 创建主面板，并设置网格布局
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        // 创建标签和下拉框，用于选择医生
        JLabel doctorLabel = new JLabel("选择医生:");
        JComboBox<String> doctorComboBox = new JComboBox<>();
        for (Doctor doctor : doctors) { // 将医生列表中的每个医生名称添加到下拉框中
            doctorComboBox.addItem(doctor.getName());
        }

        // 创建标签和文本框，用于输入预约时间
        JLabel timeLabel = new JLabel("输入预约时间 (格式: yyyy-MM-dd HH:mm):");
        JTextField timeTextField = new JTextField();

        // 创建预约按钮，并添加点击事件监听器
        JButton bookButton = new JButton("预约");
        bookButton.addActionListener(e -> {
            // 获取用户选择的医生名称和输入的预约时间
            String selectedDoctorName = (String) doctorComboBox.getSelectedItem();
            String appointmentTime = timeTextField.getText();
            for (Doctor doctor : doctors) { // 遍历医生列表，找到用户选择的医生
                if (doctor.getName().equals(selectedDoctorName)) {
                    try {
                        // 创建预约数据访问对象，并调用预约方法
                        AppointmentDao appointmentDao = new AppointmentDao(DatabaseConnectionManager.getConnection());
                        appointmentDao.bookAppointment(user.getId(), doctor.getId(), appointmentTime);
                        // 显示预约成功提示框
                        JOptionPane.showMessageDialog(this, "预约成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) { // 捕获并处理SQL异常
                        ex.printStackTrace();
                    }
                }
            }
        });

        // 将组件添加到面板
        panel.add(doctorLabel);
        panel.add(doctorComboBox);
        panel.add(timeLabel);
        panel.add(timeTextField);
        panel.add(bookButton);

        // 将面板添加到窗口
        add(panel);

        setVisible(true); // 设置窗口可见
    }
}

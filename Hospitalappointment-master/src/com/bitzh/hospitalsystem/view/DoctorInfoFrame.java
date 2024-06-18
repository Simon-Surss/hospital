package com.bitzh.hospitalsystem.view;

import com.bitzh.hospitalsystem.model.Doctor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// 用户查看医生信息的界面
public class DoctorInfoFrame extends JFrame {
    // 构造函数，接收一个包含医生信息的 List 作为参数
    public DoctorInfoFrame(List<Doctor> doctors) {
        setTitle("医生信息"); // 设置窗口标题
        setSize(500, 400); // 设置窗口大小
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 设置关闭操作为仅关闭该窗口
        setLocationRelativeTo(null); // 设置窗口居中显示

        // 定义表格的列名
        String[] columnNames = {"ID", "姓名", "专长", "可用时间"};
        // 创建用于存储表格数据的二维数组
        Object[][] data = new Object[doctors.size()][4];

        // 将医生信息填充到数据数组中
        for (int i = 0; i < doctors.size(); i++) {
            Doctor doctor = doctors.get(i);
            data[i][0] = doctor.getId(); // 医生 ID
            data[i][1] = doctor.getName(); // 医生姓名
            data[i][2] = doctor.getSpecialty(); // 医生专长
            data[i][3] = doctor.getAvailableTime(); // 医生可用时间
        }

        // 创建表格模型并初始化 JTable
        JTable table = new JTable(new DefaultTableModel(data, columnNames));
        // 创建滚动面板并将表格添加到其中
        JScrollPane scrollPane = new JScrollPane(table);
        // 将滚动面板添加到窗口的中心区域
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true); // 设置窗口可见
    }
}

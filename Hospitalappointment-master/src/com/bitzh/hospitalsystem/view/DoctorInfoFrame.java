package com.bitzh.hospitalsystem.view;
import com.bitzh.hospitalsystem.model.Doctor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

//用户查看医生信息的界面
public class DoctorInfoFrame extends JFrame {
    public DoctorInfoFrame(List<Doctor> doctors) {
        setTitle("医生信息");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"ID", "姓名", "专长", "可用时间"};
        Object[][] data = new Object[doctors.size()][4];
        for (int i = 0; i < doctors.size(); i++) {
            Doctor doctor = doctors.get(i);
            data[i][0] = doctor.getId();
            data[i][1] = doctor.getName();
            data[i][2] = doctor.getSpecialty();
            data[i][3] = doctor.getAvailableTime();
        }

        JTable table = new JTable(new DefaultTableModel(data, columnNames));
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
}

package com.bitzh.hospitalsystem.view;

import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;
import com.bitzh.hospitalsystem.dao.DoctorDao;
import com.bitzh.hospitalsystem.model.Doctor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class DoctorListFrame extends JFrame {

    public DoctorListFrame() {
        setTitle("医生列表");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        try {
            DoctorDao doctorDao = new DoctorDao(DatabaseConnectionManager.getConnection());
            List<Doctor> doctors = doctorDao.getAllDoctors();

            for (Doctor doctor : doctors) {
                JButton button = new JButton(doctor.getName());
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // 当用户点击医生名字按钮时，打开医生详细信息界面
                        DoctorDetailFrame doctorDetailFrame = new DoctorDetailFrame(doctor);
                        doctorDetailFrame.setVisible(true);
                    }
                });
                panel.add(button);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane);

        setVisible(true);
    }
}

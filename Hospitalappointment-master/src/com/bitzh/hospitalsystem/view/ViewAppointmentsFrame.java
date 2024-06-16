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

//用户查看已有预约
// ViewAppointmentsFrame.java
public class ViewAppointmentsFrame extends JFrame {
    private User user;

    public ViewAppointmentsFrame(User user) {
        this.user = user;

        setTitle("查看预约");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"预约ID", "医生姓名", "预约时间"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        try {
            UserDao userDao = new UserDao(DatabaseConnectionManager.getConnection());
            DoctorDao doctorDao = new DoctorDao(DatabaseConnectionManager.getConnection());
            List<Appointment> appointments = userDao.viewAppointments(user.getId());
            for (Appointment appointment : appointments) {
                Object[] row = new Object[3]；
                row[0] = appointment.getId();
                row[1] = doctorDao.getDoctorNameById(appointment.getDoctorId());
                row[2] = appointment.getAppointmentTime();
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        add(new JScrollPane(table), BorderLayout.CENTER);
        setVisible(true);
    }
}

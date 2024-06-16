package com.bitzh.hospitalsystem.view;

import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;
import com.bitzh.hospitalsystem.dao.UserDao;
import com.bitzh.hospitalsystem.model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ManageUsersFrame extends JFrame {
    public ManageUsersFrame() {
        setTitle("用户管理");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextArea textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        try {
            UserDao userDao = new UserDao(DatabaseConnectionManager.getConnection());
            List<User> users = userDao.getAllUsers();
            StringBuilder info = new StringBuilder();
            for (User user : users) {
                info.append("ID: ").append(user.getId())
                        .append(", 姓名: ").append(user.getUsername())
                        .append("\n");
            }
            textArea.setText(info.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JPanel panel = new JPanel();
        add(panel, BorderLayout.SOUTH);

        JButton addButton = new JButton("添加");
        JButton updateButton = new JButton("修改");
        JButton deleteButton = new JButton("删除");

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);

        addButton.addActionListener(e -> {
            String newName = JOptionPane.showInputDialog(this, "输入新用户姓名:");
            String newPassword = JOptionPane.showInputDialog(this, "输入新用户密码:");
            if (newName != null && newPassword != null) {
                try {
                    UserDao userDao = new UserDao(DatabaseConnectionManager.getConnection());
                    if (userDao.usernameExists(newName)) {
                        JOptionPane.showMessageDialog(this, "用户名已存在，请选择其他用户名", "错误", JOptionPane.ERROR_MESSAGE);
                    } else {
                        userDao.addUser(newName, newPassword);
                        JOptionPane.showMessageDialog(this, "用户添加成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "操作失败，请重试", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        updateButton.addActionListener(e -> {
            String userIdToUpdate = JOptionPane.showInputDialog(this, "输入要修改的用户ID:");
            String updatedName = JOptionPane.showInputDialog(this, "输入新姓名:");
            String updatedPassword = JOptionPane.showInputDialog(this, "输入新密码:");
            if (userIdToUpdate != null && updatedName != null && updatedPassword != null) {
                try {
                    UserDao userDao = new UserDao(DatabaseConnectionManager.getConnection());
                    userDao.updateUserInfo1(Integer.parseInt(userIdToUpdate), updatedName, updatedPassword);
                    JOptionPane.showMessageDialog(this, "用户信息更新成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "用户名已存在，请选择其他用户名", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(e -> {
            String userIdToDelete = JOptionPane.showInputDialog(this, "输入要删除的用户ID:");
            if (userIdToDelete != null) {
                try {
                    UserDao userDao = new UserDao(DatabaseConnectionManager.getConnection());
                    userDao.deleteUser(Integer.parseInt(userIdToDelete));
                    JOptionPane.showMessageDialog(this, "用户删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "操作失败，请重试", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }
}

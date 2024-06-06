package com.bitzh.hospitalsystem.view;

import com.bitzh.hospitalsystem.dao.UserDao;
import com.bitzh.hospitalsystem.model.User;
import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class UpdateUserInfoFrame extends JFrame {
    private User user;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField contactInfoField;
    private Connection conn;

    public UpdateUserInfoFrame(User user) throws SQLException {
        this.user = user;
        this.conn = DatabaseConnectionManager.getConnection();

        setTitle("用户个人信息修改");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        panel.add(new JLabel("用户名:"));
        usernameField = new JTextField(user.getUsername(), 15);
        panel.add(usernameField);

        panel.add(new JLabel("密码:"));
        passwordField = new JPasswordField(15);
        panel.add(passwordField);

        panel.add(new JLabel("确认密码:"));
        confirmPasswordField = new JPasswordField(15);
        panel.add(confirmPasswordField);

        panel.add(new JLabel("联系方式:"));
        contactInfoField = new JTextField(user.getContactInfo(), 15);
        panel.add(contactInfoField);

        JButton updateButton = new JButton("更新信息");
        updateButton.addActionListener(e -> updateUserInfo());

        panel.add(new JLabel()); // empty cell
        panel.add(updateButton);

        add(panel);
    }

    private void updateUserInfo() {
        String username = usernameField.getText();
        String password1 = new String(passwordField.getPassword());
        String password2 = new String(confirmPasswordField.getPassword());
        String contactInfo = contactInfoField.getText();

        if (username.isEmpty() || password1.isEmpty() || password2.isEmpty() || contactInfo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "所有字段都不能为空！");
            return;
        }

        if (!password1.equals(password2)) {
            JOptionPane.showMessageDialog(this, "两次密码输入不一致，请重新输入！");
            return;
        }

        try {
            UserDao userDao = new UserDao(conn);
            userDao.updateUserInfo(user.getId(), username, password1, contactInfo);
            JOptionPane.showMessageDialog(this, "用户信息更新成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "用户信息更新失败！");
        }
    }
}

package com.bitzh.hospitalsystem.view;

import com.bitzh.hospitalsystem.dao.UserDao;
import com.bitzh.hospitalsystem.model.User;
import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class RegisterFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField contactInfoField;

    public RegisterFrame() {
        setTitle("用户注册");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        confirmPasswordField = new JPasswordField(15);
        contactInfoField = new JTextField(15);

        JButton registerButton = new JButton("注册");

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(new JLabel("用户名:"), gbc);

        gbc.gridx = 2;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(new JLabel("密码:"), gbc);

        gbc.gridx = 2;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("确认密码:"), gbc);

        gbc.gridx = 2;
        panel.add(confirmPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("联系方式:"), gbc);

        gbc.gridx = 2;
        panel.add(contactInfoField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(registerButton, gbc);

        add(panel);

        registerButton.addActionListener(e -> registerUser());
    }

    private void registerUser() {
        String username = usernameField.getText();
        String password1 = new String(passwordField.getPassword());
        String password2 = new String(confirmPasswordField.getPassword());
        String contactInfo = contactInfoField.getText();

        // 确保所有字段都有值
        if (username.isEmpty() || password1.isEmpty() || password2.isEmpty() || contactInfo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "所有字段都不能为空！");
            return;
        }

        // 确保两次密码输入一致
        if (!password1.equals(password2)) {
            JOptionPane.showMessageDialog(this, "两次密码输入不一致，请重新输入！");
            return;
        }

        try (Connection conn = DatabaseConnectionManager.getConnection()) {
            UserDao userDao = new UserDao(conn);

            if (userDao.usernameExists(username)) {
                JOptionPane.showMessageDialog(this, "用户名已存在，请选择其他用户名！");
                return;
            }

            User user = new User();
            user.setUsername(username);
            user.setPassword(password1);
            user.setContactInfo(contactInfo);
            user.setUserType("普通用户"); // 设置用户类型为普通用户

            if (userDao.register(user)) {
                JOptionPane.showMessageDialog(this, "用户注册成功！");
                this.dispose(); // 关闭注册窗口
            } else {
                JOptionPane.showMessageDialog(this, "用户注册失败！");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "数据库错误，请稍后再试！");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegisterFrame().setVisible(true));
    }
}

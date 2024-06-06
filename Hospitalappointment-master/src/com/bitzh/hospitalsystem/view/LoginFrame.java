package com.bitzh.hospitalsystem.view;

import com.bitzh.hospitalsystem.dao.AdminDao;
import com.bitzh.hospitalsystem.dao.DoctorDao;
import com.bitzh.hospitalsystem.dao.UserDao;
import com.bitzh.hospitalsystem.model.Doctor;
import com.bitzh.hospitalsystem.model.User;
import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("医疗预约系统");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);

        JButton userLoginButton = new JButton("用户登录");
        JButton doctorLoginButton = new JButton("医生登录");
        JButton adminLoginButton = new JButton("管理员登录");
        JButton registerButton = new JButton("用户注册");

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
        panel.add(new JLabel("密码:"), gbc);

        gbc.gridx = 2;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(userLoginButton, gbc);

        gbc.gridx = 1;
        panel.add(doctorLoginButton, gbc);

        gbc.gridx = 2;
        panel.add(adminLoginButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(registerButton, gbc);

        add(panel);

        userLoginButton.addActionListener(e -> userLogin());
        doctorLoginButton.addActionListener(e -> doctorLogin());
        adminLoginButton.addActionListener(e -> adminLogin());
        registerButton.addActionListener(e -> openRegisterFrame());
    }

    private void openRegisterFrame() {
        JFrame registerFrame = new JFrame("用户注册");
        registerFrame.setSize(300, 250);
        registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registerFrame.setLocationRelativeTo(null);

        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);
        JPasswordField confirmPasswordField = new JPasswordField(15);
        JTextField contactInfoField = new JTextField(15);

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

        registerFrame.add(panel);
        registerFrame.setVisible(true);

        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password1 = new String(passwordField.getPassword());
            String password2 = new String(confirmPasswordField.getPassword());
            String contactInfo = contactInfoField.getText();

            if (username.isEmpty() || password1.isEmpty() || password2.isEmpty() || contactInfo.isEmpty()) {
                JOptionPane.showMessageDialog(registerFrame, "所有字段都不能为空！");
                return;
            }

            if (!password1.equals(password2)) {
                JOptionPane.showMessageDialog(registerFrame, "两次密码输入不一致，请重新输入！");
                return;
            }

            try (Connection conn = DatabaseConnectionManager.getConnection()) {
                UserDao userDao = new UserDao(conn);
                User user = new User();
                user.setUsername(username);
                user.setPassword(password1);
                user.setContactInfo(contactInfo);
                user.setUserType("user");

                if (userDao.register(user)) {
                    JOptionPane.showMessageDialog(registerFrame, "用户注册成功！");
                    registerFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(registerFrame, "用户注册失败！");
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
    }

    private void userLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection conn = DatabaseConnectionManager.getConnection()) {
            UserDao userDao = new UserDao(conn);
            User user = userDao.login(username, password);
            if (user != null && "user".equals(user.getUserType())) {
                JOptionPane.showMessageDialog(this, "用户登录成功！");
                new UserFrame(user).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "用户名或密码错误，或用户类型不正确！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void doctorLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection conn = DatabaseConnectionManager.getConnection()) {
            UserDao userDao = new UserDao(conn);
            String userType = userDao.getUserType(username, password);
            if ("doctor".equals(userType)) {
                DoctorDao doctorDao = new DoctorDao(conn);
                Doctor doctor = doctorDao.Login(username, password);
                if (doctor != null) {
                    JOptionPane.showMessageDialog(this, "医生登录成功！");
                    // 进入医生界面
                    // new DoctorFrame(doctor).setVisible(true);
                    // this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "用户名或密码错误！");
                }
            } else {
                JOptionPane.showMessageDialog(this, "用户登录类型不正确！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void adminLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection conn = DatabaseConnectionManager.getConnection()) {
            UserDao userDao = new UserDao(conn);
            String userType = userDao.getUserType(username, password);
            if ("admin".equals(userType)) {
                AdminDao adminDao = new AdminDao(conn);
                if (adminDao.login(username, password)) {
                    JOptionPane.showMessageDialog(this, "管理员登录成功！");
                    // 进入管理员界面
                    // new AdminFrame().setVisible(true);
                    // this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "用户名或密码错误！");
                }
            } else {
                JOptionPane.showMessageDialog(this, "用户登录类型不正确！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}

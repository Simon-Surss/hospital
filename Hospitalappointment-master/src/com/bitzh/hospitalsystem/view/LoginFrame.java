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
    private JTextField usernameField;  // 用户名输入框
    private JPasswordField passwordField;  // 密码输入框

    public LoginFrame() {
        setTitle("医疗预约系统");  // 设置窗口标题
        setSize(500, 400);  // 设置窗口大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 设置窗口关闭操作
        setLocationRelativeTo(null);  // 将窗口位置设置为屏幕中央

        usernameField = new JTextField(15);  // 创建用户名输入框，设置长度为15个字符
        passwordField = new JPasswordField(15);  // 创建密码输入框，设置长度为15个字符

        JButton userLoginButton = new JButton("用户登录");  // 创建用户登录按钮
        JButton doctorLoginButton = new JButton("医生登录");  // 创建医生登录按钮
        JButton adminLoginButton = new JButton("管理员登录");  // 创建管理员登录按钮
        JButton registerButton = new JButton("用户注册");  // 创建用户注册按钮

        JPanel panel = new JPanel();  // 创建面板
        panel.setLayout(new GridBagLayout());  // 设置面板布局为网格包布局
        GridBagConstraints gbc = new GridBagConstraints();  // 创建网格包约束对象

        gbc.gridx = 0;  // 设置网格x坐标
        gbc.gridy = 0;  // 设置网格y坐标
        gbc.gridwidth = 2;  // 设置组件在网格中的宽度占比
        gbc.insets = new Insets(5, 5, 5, 5);  // 设置组件与边界的空白
        panel.add(new JLabel("用户名:"), gbc);  // 在面板中添加用户名标签

        gbc.gridx = 2;  // 更新网格x坐标
        panel.add(usernameField, gbc);  // 在面板中添加用户名输入框

        gbc.gridx = 0;  // 重置网格x坐标
        gbc.gridy = 1;  // 更新网格y坐标
        panel.add(new JLabel("密码:"), gbc);  // 在面板中添加密码标签

        gbc.gridx = 2;  // 更新网格x坐标
        panel.add(passwordField, gbc);  // 在面板中添加密码输入框

        gbc.gridx = 0;  // 重置网格x坐标
        gbc.gridy = 2;  // 更新网格y坐标
        gbc.gridwidth = 1;  // 重置组件在网格中的宽度占比
        panel.add(userLoginButton, gbc);  // 在面板中添加用户登录按钮

        gbc.gridx = 1;  // 更新网格x坐标
        panel.add(doctorLoginButton, gbc);  // 在面板中添加医生登录按钮

        gbc.gridx = 2;  // 更新网格x坐标
        panel.add(adminLoginButton, gbc);  // 在面板中添加管理员登录按钮

        gbc.gridx = 1;  // 更新网格x坐标
        gbc.gridy = 3;  // 更新网格y坐标
        panel.add(registerButton, gbc);  // 在面板中添加用户注册按钮

        add(panel);  // 将面板添加到窗口中

        // 添加用户登录按钮的动作监听器
        userLoginButton.addActionListener(e -> userLogin());
        // 添加医生登录按钮的动作监听器
        doctorLoginButton.addActionListener(e -> doctorLogin());
        // 添加管理员登录按钮的动作监听器
        adminLoginButton.addActionListener(e -> adminLogin());
        // 添加用户注册按钮的动作监听器
        registerButton.addActionListener(e -> openRegisterFrame());
    }

    // 打开用户注册窗口
    private void openRegisterFrame() {
        JFrame registerFrame = new JFrame("用户注册");  // 创建用户注册窗口
        registerFrame.setSize(300, 250);  // 设置窗口大小
        registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // 设置窗口关闭操作为释放资源
        registerFrame.setLocationRelativeTo(null);  // 将窗口位置设置为屏幕中央

        JTextField usernameField = new JTextField(15);  // 创建用户名输入框，设置长度为15个字符
        JPasswordField passwordField = new JPasswordField(15);  // 创建密码输入框，设置长度为15个字符
        JPasswordField confirmPasswordField = new JPasswordField(15);  // 创建确认密码输入框，设置长度为15个字符
        JTextField contactInfoField = new JTextField(15);  // 创建联系方式输入框，设置长度为15个字符

        JButton registerButton = new JButton("注册");  // 创建注册按钮

        JPanel panel = new JPanel();  // 创建面板
        panel.setLayout(new GridBagLayout());  // 设置面板布局为网格包布局
        GridBagConstraints gbc = new GridBagConstraints();  // 创建网格包约束对象

        gbc.gridx = 0;  // 设置网格x坐标
        gbc.gridy = 0;  // 设置网格y坐标
        gbc.gridwidth = 2;  // 设置组件在网格中的宽度占比
        gbc.insets = new Insets(5, 5, 5, 5);  // 设置组件与边界的空白
        panel.add(new JLabel("用户名:"), gbc);  // 在面板中添加用户名标签

        gbc.gridx = 2;  // 更新网格x坐标
        panel.add(usernameField, gbc);  // 在面板中添加用户名输入框

        gbc.gridx = 0;  // 重置网格x坐标
        gbc.gridy = 1;  // 更新网格y坐标
        panel.add(new JLabel("密码:"), gbc);  // 在面板中添加密码标签

        gbc.gridx = 2;  // 更新网格x坐标
        panel.add(passwordField, gbc);  // 在面板中添加密码输入框

        gbc.gridx = 0;  // 重置网格x坐标
        gbc.gridy = 2;  // 更新网格y坐标
        panel.add(new JLabel("确认密码:"), gbc);  // 在面板中添加确认密码标签

        gbc.gridx = 2;  // 更新网格x坐标
        panel.add(confirmPasswordField, gbc);  // 在面板中添加确认密码输入框

        gbc.gridx = 0;  // 重置网格x坐标
        gbc.gridy = 3;  // 更新网格y坐标
        panel.add(new JLabel("联系方式:"), gbc);  // 在面板中添加联系方式标签

        gbc.gridx = 2;  // 更新网格x坐标
        panel.add(contactInfoField, gbc);  // 在面板中添加联系方式输入框

        gbc.gridx = 1;  // 更新网格x坐标
        gbc.gridy = 4;  // 更新网格y坐标
        panel.add(registerButton, gbc);  // 在面板中添加注册按钮

        registerFrame.add(panel);  // 将面板添加到窗口中
        registerFrame.setVisible(true);  // 设置窗口可见

        // 添加注册按钮的动作监听器
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();  // 获取用户名输入框的文本
            String password1 = new String(passwordField.getPassword());  // 获取密码输入框的文本
            String password2 = new String(confirmPasswordField.getPassword());  // 获取确认密码输入框的文本
            String contactInfo = contactInfoField.getText();  // 获取联系方式输入框的文本

            // 检查输入是否为空
            if (username.isEmpty() || password1.isEmpty() || password2.isEmpty() || contactInfo.isEmpty()) {
                JOptionPane.showMessageDialog(registerFrame, "所有字段都不能为空！");  // 弹出消息框提示用户
                return;  // 返回，不继续执行后续代码
            }

            // 检查两次输入的密码是否一致
            if (!password1.equals(password2)) {
                JOptionPane.showMessageDialog(registerFrame, "两次密码输入不一致，请重新输入！");  // 弹出消息框提示用户
                return;  // 返回，不继续执行后续代码
            }

            // 建立数据库连接
            try (Connection conn = DatabaseConnectionManager.getConnection()) {
                UserDao userDao = new UserDao(conn);  // 创建UserDao对象
                User user = new User();  // 创建User对象
                user.setUsername(username);  // 设置用户对象的用户名
                user.setPassword(password1);  // 设置用户对象的密码
                user.setContactInfo(contactInfo);  // 设置用户对象的联系方式
                user.setUserType("user");  // 设置用户对象的类型为"user"

                // 调用UserDao的register方法进行用户注册
                if (userDao.register(user)) {
                    JOptionPane.showMessageDialog(registerFrame, "用户注册成功！");  // 弹出消息框提示用户注册成功
                    registerFrame.dispose();  // 关闭注册窗口
                } else {
                    JOptionPane.showMessageDialog(registerFrame, "用户注册失败！");  // 弹出消息框提示用户注册失败
                }
            } catch (SQLException e1) {
                e1.printStackTrace();  // 打印SQL异常信息
            }
        });
    }

    // 用户登录方法
    private void userLogin() {
        String username = usernameField.getText();  // 获取用户名输入框的文本
        String password = new String(passwordField.getPassword());  // 获取密码输入框的文本

        try (Connection conn = DatabaseConnectionManager.getConnection()) {
            UserDao userDao = new UserDao(conn);  // 创建UserDao对象
            User user = userDao.login(username, password);  // 调用UserDao的login方法进行用户登录

            // 判断用户对象是否为空且用户类型是否为"user"
            if (user != null && "user".equals(user.getUserType())) {
                JOptionPane.showMessageDialog(this, "用户登录成功！");  // 弹出消息框提示用户登录成功
                new UserFrame(user).setVisible(true);  // 创建并显示用户界面窗口
                this.dispose();  // 关闭当前登录窗口
            } else {
                JOptionPane.showMessageDialog(this, "用户名或密码错误，或用户类型不正确！");  // 弹出消息框提示用户名或密码错误
            }
        } catch (SQLException e) {
            e.printStackTrace();  // 打印SQL异常信息
        }
    }

    // 医生登录方法
    private void doctorLogin() {
        String username = usernameField.getText();  // 获取用户名输入框的文本
        String password = new String(passwordField.getPassword());  // 获取密码输入框的文本

        try (Connection conn = DatabaseConnectionManager.getConnection()) {
            DoctorDao doctorDao = new DoctorDao(conn);  // 创建DoctorDao对象
            Doctor doctor = doctorDao.Login(username, password);  // 调用DoctorDao的Login方法进行医生登录

            // 判断医生对象是否为空
            if (doctor != null) {
                JOptionPane.showMessageDialog(this, "医生登录成功！");  // 弹出消息框提示医生登录成功
                new DoctorFrame(doctor).setVisible(true);  // 创建并显示医生界面窗口
                this.dispose();  // 关闭当前登录窗口
            } else {
                JOptionPane.showMessageDialog(this, "用户名或密码错误，或用户类型不正确！");  // 弹出消息框提示用户名或密码错误
            }
        } catch (SQLException e) {
            e.printStackTrace();  // 打印SQL异常信息
        }
    }

    // 管理员登录方法
    private void adminLogin() {
        String username = usernameField.getText();  // 获取用户名输入框的文本
        String password = new String(passwordField.getPassword());  // 获取密码输入框的文本

        try (Connection conn = DatabaseConnectionManager.getConnection()) {
            AdminDao adminDao = new AdminDao(conn);  // 创建AdminDao对象

            // 调用AdminDao的login方法进行管理员登录
            if (adminDao.login(username, password)) {
                JOptionPane.showMessageDialog(this, "管理员登录成功！");  // 弹出消息框提示管理员登录成功
                new AdminFrame().setVisible(true);  // 创建并显示管理员界面窗口
                this.dispose();  // 关闭当前登录窗口
            } else {
                JOptionPane.showMessageDialog(this, "用户名或密码错误！");  // 弹出消息框提示用户名或密码错误
            }
        } catch (SQLException e) {
            e.printStackTrace();  // 打印SQL异常信息
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));  // 使用SwingUtilities在事件调度线程中创建并显示登录窗口
    }
}


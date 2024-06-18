package com.bitzh.hospitalsystem.view;

import com.bitzh.hospitalsystem.dao.UserDao;
import com.bitzh.hospitalsystem.model.User;
import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class RegisterFrame extends JFrame {
    private JTextField usernameField;  // 用户名文本框
    private JPasswordField passwordField;  // 密码文本框
    private JPasswordField confirmPasswordField;  // 确认密码文本框
    private JTextField contactInfoField;  // 联系方式文本框

    public RegisterFrame() {
        setTitle("用户注册");  // 设置窗口标题
        setSize(300, 250);  // 设置窗口大小
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // 设置窗口关闭操作为释放资源
        setLocationRelativeTo(null); // 将窗口位置设置为屏幕中央

        usernameField = new JTextField(15);  // 创建用户名文本框
        passwordField = new JPasswordField(15);  // 创建密码文本框
        confirmPasswordField = new JPasswordField(15);  // 创建确认密码文本框
        contactInfoField = new JTextField(15);  // 创建联系方式文本框

        JButton registerButton = new JButton("注册");  // 创建注册按钮

        JPanel panel = new JPanel();  // 创建面板
        panel.setLayout(new GridBagLayout());  // 设置面板布局为网格包布局
        GridBagConstraints gbc = new GridBagConstraints();  // 创建网格包约束对象

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 5);  // 设置组件间距
        panel.add(new JLabel("用户名:"), gbc);  // 添加用户名标签

        gbc.gridx = 2;
        panel.add(usernameField, gbc);  // 添加用户名文本框

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(new JLabel("密码:"), gbc);  // 添加密码标签

        gbc.gridx = 2;
        panel.add(passwordField, gbc);  // 添加密码文本框

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("确认密码:"), gbc);  // 添加确认密码标签

        gbc.gridx = 2;
        panel.add(confirmPasswordField, gbc);  // 添加确认密码文本框

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("联系方式:"), gbc);  // 添加联系方式标签

        gbc.gridx = 2;
        panel.add(contactInfoField, gbc);  // 添加联系方式文本框

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(registerButton, gbc);  // 添加注册按钮

        add(panel);  // 将面板添加到窗口

        registerButton.addActionListener(e -> registerUser());  // 注册按钮添加动作监听器，调用registerUser方法
    }

    private void registerUser() {
        String username = usernameField.getText();  // 获取用户名文本框的内容
        String password1 = new String(passwordField.getPassword());  // 获取密码文本框的内容
        String password2 = new String(confirmPasswordField.getPassword());  // 获取确认密码文本框的内容
        String contactInfo = contactInfoField.getText();  // 获取联系方式文本框的内容

        // 确保所有字段都有值
        if (username.isEmpty() || password1.isEmpty() || password2.isEmpty() || contactInfo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "所有字段都不能为空！");  // 弹出消息框提示所有字段不能为空
            return;
        }

        // 确保两次密码输入一致
        if (!password1.equals(password2)) {
            JOptionPane.showMessageDialog(this, "两次密码输入不一致，请重新输入！");  // 弹出消息框提示两次密码输入不一致
            return;
        }

        try (Connection conn = DatabaseConnectionManager.getConnection()) {  // 获取数据库连接
            UserDao userDao = new UserDao(conn);  // 创建UserDao对象

            if (userDao.usernameExists(username)) {  // 检查用户名是否已存在
                JOptionPane.showMessageDialog(this, "用户名已存在，请选择其他用户名！");  // 弹出消息框提示用户名已存在
                return;
            }

            User user = new User();  // 创建User对象
            user.setUsername(username);  // 设置用户名
            user.setPassword(password1);  // 设置密码
            user.setContactInfo(contactInfo);  // 设置联系方式
            user.setUserType("普通用户"); // 设置用户类型为普通用户

            if (userDao.register(user)) {  // 调用UserDao的register方法注册用户
                JOptionPane.showMessageDialog(this, "用户注册成功！");  // 弹出消息框提示用户注册成功
                this.dispose(); // 关闭注册窗口
            } else {
                JOptionPane.showMessageDialog(this, "用户注册失败！");  // 弹出消息框提示用户注册失败
            }

        } catch (SQLException e) {
            e.printStackTrace();  // 打印SQL异常信息
            JOptionPane.showMessageDialog(this, "数据库错误，请稍后再试！");  // 弹出消息框提示数据库错误
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegisterFrame().setVisible(true));  // 使用SwingUtilities.invokeLater确保在事件分发线程上创建窗口
    }
}

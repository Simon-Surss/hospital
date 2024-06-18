package com.bitzh.hospitalsystem.view;

import com.bitzh.hospitalsystem.dao.UserDao;
import com.bitzh.hospitalsystem.model.User;
import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class UpdateUserInfoFrame extends JFrame {
    private User user;  // 用户对象
    private JTextField usernameField;  // 用户名文本框
    private JPasswordField passwordField;  // 密码文本框
    private JPasswordField confirmPasswordField;  // 确认密码文本框
    private JTextField contactInfoField;  // 联系方式文本框
    private Connection conn;  // 数据库连接对象

    public UpdateUserInfoFrame(User user) throws SQLException {
        this.user = user;  // 初始化用户对象
        this.conn = DatabaseConnectionManager.getConnection();  // 获取数据库连接

        setTitle("用户个人信息修改");  // 设置窗口标题
        setSize(400, 300);  // 设置窗口大小
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // 设置窗口关闭操作为释放资源
        setLocationRelativeTo(null);  // 将窗口位置设置为屏幕中央

        JPanel panel = new JPanel();  // 创建面板
        panel.setLayout(new GridLayout(5, 2, 10, 10));  // 设置面板布局为5行2列的网格布局，行间距和列间距为10像素

        panel.add(new JLabel("用户名:"));  // 添加“用户名”标签到面板
        usernameField = new JTextField(user.getUsername(), 15);  // 创建用户名文本框，并设置初始值为用户当前的用户名
        panel.add(usernameField);  // 将用户名文本框添加到面板

        panel.add(new JLabel("密码:"));  // 添加“密码”标签到面板
        passwordField = new JPasswordField(15);  // 创建密码文本框
        panel.add(passwordField);  // 将密码文本框添加到面板

        panel.add(new JLabel("确认密码:"));  // 添加“确认密码”标签到面板
        confirmPasswordField = new JPasswordField(15);  // 创建确认密码文本框
        panel.add(confirmPasswordField);  // 将确认密码文本框添加到面板

        panel.add(new JLabel("联系方式:"));  // 添加“联系方式”标签到面板
        contactInfoField = new JTextField(user.getContactInfo(), 15);  // 创建联系方式文本框，并设置初始值为用户当前的联系方式
        panel.add(contactInfoField);  // 将联系方式文本框添加到面板

        JButton updateButton = new JButton("更新信息");  // 创建更新信息按钮
        updateButton.addActionListener(e -> updateUserInfo());  // 注册更新按钮的动作监听器

        panel.add(new JLabel());  // 添加一个空的标签占位
        panel.add(updateButton);  // 将更新信息按钮添加到面板

        add(panel);  // 将面板添加到窗口
    }

    private void updateUserInfo() {
        String username = usernameField.getText();  // 获取用户名文本框中的内容
        String password1 = new String(passwordField.getPassword());  // 获取密码文本框中的内容
        String password2 = new String(confirmPasswordField.getPassword());  // 获取确认密码文本框中的内容
        String contactInfo = contactInfoField.getText();  // 获取联系方式文本框中的内容

        if (username.isEmpty() || password1.isEmpty() || password2.isEmpty() || contactInfo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "所有字段都不能为空！");  // 如果有任何字段为空，弹出消息框提示
            return;
        }

        if (!password1.equals(password2)) {
            JOptionPane.showMessageDialog(this, "两次密码输入不一致，请重新输入！");  // 如果两次密码输入不一致，弹出消息框提示
            return;
        }

        try {
            UserDao userDao = new UserDao(conn);  // 创建UserDao对象，传入数据库连接
            userDao.updateUserInfo(user.getId(), username, password1, contactInfo);  // 调用UserDao的方法更新用户信息
            JOptionPane.showMessageDialog(this, "用户信息更新成功！");  // 弹出消息框提示用户信息更新成功
        } catch (SQLException e) {
            e.printStackTrace();  // 打印SQL异常信息
            JOptionPane.showMessageDialog(this, "用户信息更新失败！");  // 弹出消息框提示用户信息更新失败
        }
    }
}

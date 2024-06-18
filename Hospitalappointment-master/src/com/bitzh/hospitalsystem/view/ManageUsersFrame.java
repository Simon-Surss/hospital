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
        setTitle("用户管理");  // 设置窗口标题
        setSize(400, 300);  // 设置窗口大小
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // 设置窗口关闭操作为释放资源
        setLocationRelativeTo(null);  // 将窗口位置设置为屏幕中央

        JTextArea textArea = new JTextArea();  // 创建文本区域组件
        add(new JScrollPane(textArea), BorderLayout.CENTER);  // 添加带滚动条的文本区域到窗口中央

        try {
            UserDao userDao = new UserDao(DatabaseConnectionManager.getConnection());  // 创建UserDao对象
            List<User> users = userDao.getAllUsers();  // 调用UserDao的getAllUsers方法获取所有用户信息
            StringBuilder info = new StringBuilder();  // 创建字符串构建器用于存储用户信息的字符串
            for (User user : users) {  // 遍历用户列表
                // 构建每个用户信息的字符串，并添加到字符串构建器中
                info.append("ID: ").append(user.getId())
                        .append(", 姓名: ").append(user.getUsername())
                        .append("\n");
            }
            textArea.setText(info.toString());  // 在文本区域中显示用户信息字符串
        } catch (SQLException e) {
            e.printStackTrace();  // 打印SQL异常信息
        }

        JPanel panel = new JPanel();  // 创建面板
        add(panel, BorderLayout.SOUTH);  // 将面板添加到窗口南侧位置

        JButton addButton = new JButton("添加");  // 创建添加按钮
        JButton updateButton = new JButton("修改");  // 创建修改按钮
        JButton deleteButton = new JButton("删除");  // 创建删除按钮

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton); 

        // 添加添加按钮的动作监听器
        addButton.addActionListener(e -> {
            String newName = JOptionPane.showInputDialog(this, "输入新用户姓名:");  // 弹出输入对话框获取新用户名
            String newPassword = JOptionPane.showInputDialog(this, "输入新用户密码:");  // 弹出输入对话框获取新用户密码
            if (newName != null && newPassword != null) {  // 如果新用户名和密码不为空
                try {
                    UserDao userDao = new UserDao(DatabaseConnectionManager.getConnection());  // 创建UserDao对象
                    if (userDao.usernameExists(newName)) {  // 检查用户名是否已存在
                        JOptionPane.showMessageDialog(this, "用户名已存在，请选择其他用户名", "错误", JOptionPane.ERROR_MESSAGE);  // 弹出消息框提示用户名已存在
                    } else {
                        userDao.addUser(newName, newPassword);  // 调用UserDao的addUser方法添加新用户
                        JOptionPane.showMessageDialog(this, "用户添加成功", "提示", JOptionPane.INFORMATION_MESSAGE);  // 弹出消息框提示用户添加成功
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "操作失败，请重试", "错误", JOptionPane.ERROR_MESSAGE);  // 弹出消息框提示操作失败
                }
            }
        });

        // 添加修改按钮的动作监听器
        updateButton.addActionListener(e -> {
            String userIdToUpdate = JOptionPane.showInputDialog(this, "输入要修改的用户ID:");  // 弹出输入对话框获取要修改的用户ID
            String updatedName = JOptionPane.showInputDialog(this, "输入新姓名:");  // 弹出输入对话框获取新用户名
            String updatedPassword = JOptionPane.showInputDialog(this, "输入新密码:");  // 弹出输入对话框获取新密码
            if (userIdToUpdate != null && updatedName != null && updatedPassword != null) {  // 如果用户ID、新用户名和密码不为空
                try {
                    UserDao userDao = new UserDao(DatabaseConnectionManager.getConnection());  // 创建UserDao对象
                    userDao.updateUserInfo1(Integer.parseInt(userIdToUpdate), updatedName, updatedPassword);  // 调用UserDao的updateUserInfo1方法更新用户信息
                    JOptionPane.showMessageDialog(this, "用户信息更新成功", "提示", JOptionPane.INFORMATION_MESSAGE);  // 弹出消息框提示用户信息更新成功
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "用户名已存在，请选择其他用户名", "错误", JOptionPane.ERROR_MESSAGE);  // 弹出消息框提示用户名已存在
                }
            }
        });

        // 添加删除按钮的动作监听器
        deleteButton.addActionListener(e -> {
            String userIdToDelete = JOptionPane.showInputDialog(this, "输入要删除的用户ID:");  // 弹出输入对话框获取要删除的用户ID
            if (userIdToDelete != null) {  // 如果用户ID不为空
                try {
                    UserDao userDao = new UserDao(DatabaseConnectionManager.getConnection());  // 创建UserDao对象
                    userDao.deleteUser(Integer.parseInt(userIdToDelete));  // 调用UserDao的deleteUser方法删除用户
                    JOptionPane.showMessageDialog(this, "用户删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);  // 弹出消息框提示用户删除成功
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "操作失败，请重试", "错误", JOptionPane.ERROR_MESSAGE);  // 弹出消息框提示操作失败
                }
            }
        });

        setVisible(true);  // 设置窗口可见
    }
}

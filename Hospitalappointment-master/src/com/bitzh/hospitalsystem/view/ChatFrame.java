package com.bitzh.hospitalsystem.view;

import com.bitzh.hospitalsystem.dao.UserDao;
import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ChatFrame extends JFrame {
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private UserDao userDao;
    private String currentUser;
    private String chatPartner;
    private Timer timer;

    public ChatFrame(String currentUser, String chatPartner) {
        this.currentUser = currentUser;
        this.chatPartner = chatPartner;

        try {
            Connection conn = DatabaseConnectionManager.getConnection();
            userDao = new UserDao(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "数据库连接失败", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        setTitle("Chat with " + chatPartner);
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        messageField = new JTextField();
        messageField.setColumns(25);

        sendButton = new JButton("Send");

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(messageField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageField.getText();
                if (!message.isEmpty()) {
                    sendMessage(message);
                    messageField.setText("");
                }
            }
        });

        // 定期刷新聊天记录
        timer = new Timer(300, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshChatHistory();
            }
        });
        timer.start();

        loadChatHistory();
    }

    private void loadChatHistory() {
        chatArea.setText(""); // 清空聊天区域
        try {
            List<String> messages = userDao.getChatHistory(currentUser, chatPartner);
            for (String message : messages) {
                chatArea.append(message + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refreshChatHistory() {
        try {
            List<String> messages = userDao.getChatHistory(currentUser, chatPartner);
            chatArea.setText(""); // 清空聊天区域
            for (String message : messages) {
                chatArea.append(message + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        chatArea.append("You: " + message + "\n");
        try {
            userDao.saveMessage(currentUser, chatPartner, message);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dispose() {
        timer.stop();
        super.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChatFrame chatFrame = new ChatFrame("currentUser", "chatPartner");
            chatFrame.setVisible(true);
        });
    }
}

package com.bitzh.hospitalsystem.view;

import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;
import com.bitzh.hospitalsystem.dao.ReviewDao;
import com.bitzh.hospitalsystem.model.Review;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ManageReviewsFrame extends JFrame {
    public ManageReviewsFrame() {
        setTitle("评价管理");  // 设置窗口标题
        setSize(400, 300);  // 设置窗口大小
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // 设置窗口关闭操作为释放资源
        setLocationRelativeTo(null);  // 将窗口位置设置为屏幕中央

        JTextArea textArea = new JTextArea();  // 创建文本区域组件
        add(new JScrollPane(textArea), BorderLayout.CENTER);  // 添加带滚动条的文本区域到窗口中央位置

        try {
            ReviewDao reviewDao = new ReviewDao(DatabaseConnectionManager.getConnection());  // 创建ReviewDao对象
            List<Review> reviews = reviewDao.getAllReviews();  // 调用ReviewDao的getAllReviews方法获取所有评价信息
            StringBuilder info = new StringBuilder();  // 创建字符串构建器用于存储评价信息的字符串
            for (Review review : reviews) {  // 遍历评价列表
                // 构建每个评价信息的字符串，并添加到字符串构建器中
                info.append("评价ID: ").append(review.getId())
                        .append(", 用户ID: ").append(review.getUserId())
                        .append(", 医生ID: ").append(review.getDoctorId())
                        .append(", 评分: ").append(review.getRating())
                        .append(", 评价内容: ").append(review.getReviewContent())
                        .append("\n");
            }
            textArea.setText(info.toString());  // 在文本区域中显示评价信息字符串
        } catch (SQLException e) {
            e.printStackTrace();  // 打印SQL异常信息
        }

        JPanel panel = new JPanel();  // 创建面板
        add(panel, BorderLayout.SOUTH);  // 将面板添加到窗口南侧位置

        JButton deleteButton = new JButton("删除");  // 创建删除按钮

        panel.add(deleteButton);  // 将删除按钮添加到面板中

        // 添加删除按钮的动作监听器
        deleteButton.addActionListener(e -> {
            String reviewIdToDelete = JOptionPane.showInputDialog(this, "输入要删除的评价ID:");  // 弹出输入对话框获取要删除的评价ID
            if (reviewIdToDelete != null) {  // 如果评价ID不为空
                try {
                    ReviewDao reviewDao = new ReviewDao(DatabaseConnectionManager.getConnection());  // 创建ReviewDao对象
                    reviewDao.deleteReview(Integer.parseInt(reviewIdToDelete));  // 调用ReviewDao的deleteReview方法删除评价
                    JOptionPane.showMessageDialog(this, "评价删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);  // 弹出消息框提示评价删除成功
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "操作失败，请重试", "错误", JOptionPane.ERROR_MESSAGE);  // 弹出消息框提示操作失败
                }
            }
        });

        setVisible(true);  // 设置窗口可见
    }
}

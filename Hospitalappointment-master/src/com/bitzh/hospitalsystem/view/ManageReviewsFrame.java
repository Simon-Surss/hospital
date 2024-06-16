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
        setTitle("评价管理");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextArea textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        try {
            ReviewDao reviewDao = new ReviewDao(DatabaseConnectionManager.getConnection());
            List<Review> reviews = reviewDao.getAllReviews();
            StringBuilder info = new StringBuilder();
            for (Review review : reviews) {
                info.append("评价ID: ").append(review.getId())
                        .append(", 用户ID: ").append(review.getUserId())
                        .append(", 医生ID: ").append(review.getDoctorId())
                        .append(", 评分: ").append(review.getRating())
                        .append(", 评价内容: ").append(review.getReviewContent())
                        .append("\n");
            }
            textArea.setText(info.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JPanel panel = new JPanel();
        add(panel, BorderLayout.SOUTH);

        JButton deleteButton = new JButton("删除");

        panel.add(deleteButton);

        deleteButton.addActionListener(e -> {
            String reviewIdToDelete = JOptionPane.showInputDialog(this, "输入要删除的评价ID:");
            if (reviewIdToDelete != null) {
                try {
                    ReviewDao reviewDao = new ReviewDao(DatabaseConnectionManager.getConnection());
                    reviewDao.deleteReview(Integer.parseInt(reviewIdToDelete));
                    JOptionPane.showMessageDialog(this, "评价删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "操作失败，请重试", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }
}

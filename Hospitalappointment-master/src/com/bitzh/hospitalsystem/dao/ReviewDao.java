package com.bitzh.hospitalsystem.dao;

import com.bitzh.hospitalsystem.model.Review;
import com.bitzh.hospitalsystem.Utils.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao {
    private Connection connection;

    public ReviewDao(Connection connection) throws SQLException {
        this.connection = connection;
    }

    public List<Review> getAllReviews() throws SQLException {
        String sql = "SELECT * FROM reviews";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        List<Review> reviews = new ArrayList<>();
        while (resultSet.next()) {
            Review review = new Review(
                    resultSet.getInt("id"),
                    resultSet.getInt("user_id"),
                    resultSet.getInt("doctor_id"),
                    resultSet.getInt("rating"),
                    resultSet.getString("review_content")
            );
            reviews.add(review);
        }
        return reviews;
    }

    public List<Review> getDoctorReviews(int doctorId) throws SQLException {
        String sql = "SELECT * FROM reviews WHERE doctor_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, doctorId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Review> reviews = new ArrayList<>();
        while (resultSet.next()) {
            Review review = new Review(
                    resultSet.getInt("id"),
                    resultSet.getInt("user_id"),
                    resultSet.getInt("doctor_id"),
                    resultSet.getInt("rating"),
                    resultSet.getString("review_content")
            );
            reviews.add(review);
        }
        return reviews;
    }

    public void addReview(int userId, int doctorId, int rating, String reviewContent) throws SQLException {
        String sql = "INSERT INTO reviews (user_id, doctor_id, rating, review_content) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, doctorId);
        preparedStatement.setInt(3, rating);
        preparedStatement.setString(4, reviewContent);
        preparedStatement.executeUpdate();
    }

    public void deleteReview(int id) throws SQLException {
        String sql = "DELETE FROM reviews WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }
}

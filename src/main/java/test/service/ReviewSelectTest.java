package test.service;

import core.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReviewSelectTest {
    public static void main(String[] args) {
        ConnectionPool cp = null;
        try {
            cp = ConnectionPool.create();  // ConnectionPool 객체 생성
        } catch (Exception e) {
            System.out.println("Error initializing ConnectionPool: " + e.getMessage());
            return;
        }

        String sql = """
            SELECT r.review_id, r.product_id, p.name AS product_name, r.customer_id, r.rating, r.comment, r.created_at
            FROM review r
            JOIN product p ON r.product_id = p.product_id
            ORDER BY r.created_at DESC
            """;

        try (Connection conn = cp.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // 결과 출력
            while (rs.next()) {
                int reviewId = rs.getInt("review_id");
                int productId = rs.getInt("product_id");
                String productName = rs.getString("product_name");
                String customerId = rs.getString("customer_id");
                double rating = rs.getDouble("rating");
                String comment = rs.getString("comment");
                String createdAt = rs.getString("created_at");

                System.out.printf("Review ID: %d, Product ID: %d, Product Name: %s, Customer ID: %s, Rating: %.2f, Comment: %s, Created At: %s%n",
                        reviewId, productId, productName, customerId, rating, comment, createdAt);
            }
        } catch (Exception e) {
            System.out.println("SQL execution error: " + e.getMessage());
        }
    }
}

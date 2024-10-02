package test.service;

import core.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerOrderSelectTest {
    public static void main(String[] args) {
        ConnectionPool cp = null;
        try {
            cp = ConnectionPool.create();  // ConnectionPool 객체 생성
        } catch (Exception e) {
            System.out.println("Error initializing ConnectionPool: " + e.getMessage());
            return;
        }

        String sql = """
            SELECT order_id, total_price, status, created_at
            FROM orders
            WHERE customer_id = ?
            ORDER BY created_at DESC
            """;

        try (Connection conn = cp.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 파라미터 설정
            pstmt.setString(1, "customer023");  // 고객 ID

            // 쿼리 실행
            ResultSet rs = pstmt.executeQuery();

            // 결과 처리
            while (rs.next()) {
                String orderId = rs.getString("order_id");
                double totalPrice = rs.getDouble("total_price");
                String status = rs.getString("status");
                String createdAt = rs.getString("created_at");

                System.out.printf("Order ID: %s, Total Price: %.2f, Status: %s, Created At: %s%n",
                        orderId, totalPrice, status, createdAt);
            }
        } catch (Exception e) {
            System.out.println("SQL execution error: " + e.getMessage());
        }
    }
}

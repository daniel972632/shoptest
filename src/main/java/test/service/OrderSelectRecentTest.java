package test.service;

import core.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OrderSelectRecentTest {
    public static void main(String[] args) {
        ConnectionPool cp = null;
        try {
            cp = ConnectionPool.create();  // ConnectionPool 객체 생성
        } catch (Exception e) {
            System.out.println("Error initializing ConnectionPool: " + e.getMessage());
            return;
        }

        try (Connection conn = cp.getConnection()) {
            String sql = "SELECT o.order_id, o.customer_id, c.name AS customer_name, o.total_price, o.status, o.created_at " +
                    "FROM orders o " +
                    "JOIN customer c ON o.customer_id = c.customer_id " +
                    "ORDER BY o.created_at DESC " +
                    "LIMIT 10";

            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                System.out.println("최근 주문 목록:");
                while (rs.next()) {
                    System.out.println("주문 ID: " + rs.getInt("order_id"));
                    System.out.println("고객 ID: " + rs.getString("customer_id"));
                    System.out.println("고객 이름: " + rs.getString("customer_name"));
                    System.out.println("총 가격: " + rs.getDouble("total_price"));
                    System.out.println("상태: " + rs.getString("status"));
                    System.out.println("주문 날짜: " + rs.getTimestamp("created_at"));
                    System.out.println("------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("SQL execution error: " + e.getMessage());
        }
    }
}

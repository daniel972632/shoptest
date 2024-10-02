package test;

import core.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderSalesTest {
    public static void main(String[] args) {
        ConnectionPool cp = null;

        try {
            // ConnectionPool 객체 생성
            cp = ConnectionPool.create();
        } catch (Exception e) {
            System.out.println("Error initializing connection pool: " + e.getMessage());
            return;
        }

        String sql = """
            SELECT DATE_FORMAT(o.created_at, '%Y-%m-%d') AS order_date,
                   SUM(od.product_price * od.quantity) AS total_sales
            FROM orders o
            JOIN order_detail od ON o.order_id = od.order_id
            GROUP BY order_date
            ORDER BY order_date DESC;
            """;

        try (Connection conn = cp.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // 결과 출력
            while (rs.next()) {
                String orderDate = rs.getString("order_date");
                double totalSales = rs.getDouble("total_sales");
                System.out.println("주문 날짜: " + orderDate + " | 총 매출: " + totalSales);
            }

        } catch (SQLException e) {
            System.out.println("SQL execution error: " + e.getMessage());
        }
    }
}

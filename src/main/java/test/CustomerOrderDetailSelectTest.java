package test;

import core.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerOrderDetailSelectTest {
    public static void main(String[] args) {
        ConnectionPool cp = null;
        try {
            cp = ConnectionPool.create();  // ConnectionPool 객체 생성
        } catch (Exception e) {
            System.out.println("Error initializing ConnectionPool: " + e.getMessage());
            return;
        }

        String sql = """
            SELECT o.order_id, o.total_price, o.status, od.product_id, p.name AS product_name, od.quantity
            FROM orders o
            JOIN order_detail od ON o.order_id = od.order_id
            JOIN product p ON od.product_id = p.product_id
            WHERE o.customer_id = ? AND o.order_id = ?
            """;

        try (Connection conn = cp.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 파라미터 설정
            pstmt.setString(1, "customer023");  // 고객 ID
            pstmt.setInt(2, 8);                  // 주문 ID

            // 쿼리 실행
            ResultSet rs = pstmt.executeQuery();

            // 결과 처리
            while (rs.next()) {
                String orderId = rs.getString("order_id");
                double totalPrice = rs.getDouble("total_price");
                String status = rs.getString("status");
                String productId = rs.getString("product_id");
                String productName = rs.getString("product_name");
                int quantity = rs.getInt("quantity");

                System.out.printf("Order ID: %s, Total Price: %.2f, Status: %s, Product ID: %s, Product Name: %s, Quantity: %d%n",
                        orderId, totalPrice, status, productId, productName, quantity);
            }
        } catch (Exception e) {
            System.out.println("SQL execution error: " + e.getMessage());
        }
    }
}

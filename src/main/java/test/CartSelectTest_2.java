package test;

import core.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CartSelectTest_2 {
    public static void main(String[] args) {
        ConnectionPool cp = null;
        try {
            cp = ConnectionPool.create();  // ConnectionPool 객체 생성
        } catch (Exception e) {
            System.out.println("Error initializing ConnectionPool: " + e.getMessage());
            return;
        }

        String sql = """
            SELECT c.cart_id, p.name AS product_name, c.quantity, c.total_price, c.created_at
            FROM cart c
            JOIN product p ON c.product_id = p.product_id
            WHERE c.customer_id = ?
            """;

        try (Connection conn = cp.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 파라미터 설정
            pstmt.setString(1, "customer999");

            // 쿼리 실행
            try (ResultSet rs = pstmt.executeQuery()) {
                // 결과 처리
                if (!rs.isBeforeFirst()) { // 결과가 없는 경우
                    System.out.println("장바구니가 비어 있습니다.");
                } else {
                    while (rs.next()) {
                        int cartId = rs.getInt("cart_id");
                        String productName = rs.getString("product_name");
                        int quantity = rs.getInt("quantity");
                        int totalPrice = rs.getInt("total_price");
                        String createdAt = rs.getString("created_at");

                        // 결과 출력
                        System.out.printf("Cart ID: %d, Product: %s, Quantity: %d, Total Price: %d, Created At: %s%n",
                                cartId, productName, quantity, totalPrice, createdAt);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("SQL execution error: " + e.getMessage());
        }
    }
}

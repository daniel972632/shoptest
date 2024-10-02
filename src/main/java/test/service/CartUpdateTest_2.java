package test.service;

import core.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CartUpdateTest_2 {
    public static void main(String[] args) {
        ConnectionPool cp = null;
        try {
            cp = ConnectionPool.create();  // ConnectionPool 객체 생성
        } catch (Exception e) {
            System.out.println("Error initializing ConnectionPool: " + e.getMessage());
            return;
        }

        String sql = """
            UPDATE cart
            SET quantity = ?,
                total_price = quantity * (SELECT price FROM product WHERE product_id = cart.product_id),
                created_at = NOW()
            WHERE cart_id = ? AND customer_id = ?
            """;

        try (Connection conn = cp.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 파라미터 설정
            pstmt.setInt(1, 5);  // 수량을 5로 설정
            pstmt.setInt(2, 64);  // cart_id를 64로 설정
            pstmt.setString(3, "customer999");  // customer_id 설정

            // 쿼리 실행
            int rowsAffected = pstmt.executeUpdate();

            // 결과 처리
            if (rowsAffected > 0) {
                System.out.println("장바구니 항목이 성공적으로 업데이트되었습니다.");
            } else {
                System.out.println("장바구니 항목 업데이트에 실패했습니다. 항목을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            System.out.println("SQL execution error: " + e.getMessage());
        }
    }
}

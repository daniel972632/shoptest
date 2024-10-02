package test;

import core.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class OrderUpdateStatusTest {
    public static void main(String[] args) {
        ConnectionPool cp = null;
        try {
            cp = ConnectionPool.create();  // ConnectionPool 객체 생성
        } catch (Exception e) {
            System.out.println("Error initializing ConnectionPool: " + e.getMessage());
            return;
        }

        try (Connection conn = cp.getConnection()) {
            String sql = "UPDATE orders SET status = 'shipped', updated_at = NOW() WHERE order_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, 9); // 주문 ID를 9로 설정
                int affectedRows = pstmt.executeUpdate(); // 쿼리 실행

                if (affectedRows > 0) {
                    System.out.println("주문 상태가 성공적으로 업데이트되었습니다.");
                } else {
                    System.out.println("주문 ID가 9인 주문이 존재하지 않습니다.");
                }
            }
        } catch (Exception e) {
            System.out.println("SQL execution error: " + e.getMessage());
        }
    }
}

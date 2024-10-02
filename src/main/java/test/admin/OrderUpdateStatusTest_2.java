package test.admin;

import core.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class OrderUpdateStatusTest_2 {
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
                pstmt.setInt(1, 16);  // order_id를 1로 설정

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("주문 상태 업데이트 성공: 주문 ID 1의 상태가 'shipped'로 변경되었습니다.");
                } else {
                    System.out.println("주문 상태 업데이트 실패: 주문 ID 1이 존재하지 않습니다.");
                }
            }
        } catch (Exception e) {
            System.out.println("SQL execution error: " + e.getMessage());
        }
    }
}

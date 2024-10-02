package test.service;

import core.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CartDeleteTest_2 {
    public static void main(String[] args) {
        ConnectionPool cp = null;
        try {
            cp = ConnectionPool.create();  // ConnectionPool 객체 생성
        } catch (Exception e) {
            System.out.println("Error initializing ConnectionPool: " + e.getMessage());
            return;
        }

        String sql = "DELETE FROM cart WHERE cart_id = ? AND customer_id = ?";

        try (Connection conn = cp.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 파라미터 설정
            pstmt.setInt(1, 64);  // cart_id를 64로 설정
            pstmt.setString(2, "customer999");  // customer_id 설정

            // 쿼리 실행
            int rowsAffected = pstmt.executeUpdate();

            // 결과 처리
            if (rowsAffected > 0) {
                System.out.println("장바구니 항목이 성공적으로 삭제되었습니다.");
            } else {
                System.out.println("장바구니 항목 삭제에 실패했습니다. 항목을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            System.out.println("SQL execution error: " + e.getMessage());
        }
    }
}

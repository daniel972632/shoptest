package test;

import core.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CouponDeleteTest {
    public static void main(String[] args) {
        ConnectionPool cp = null;
        try {
            cp = ConnectionPool.create();  // ConnectionPool 객체 생성
        } catch (Exception e) {
            System.out.println("Error initializing ConnectionPool: " + e.getMessage());
            return;
        }

        String sql = "DELETE FROM coupon WHERE coupon_id = ?";

        try (Connection conn = cp.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, 34);  // 삭제할 쿠폰의 ID 설정

            // 쿼리 실행
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("쿠폰이 성공적으로 삭제되었습니다. 삭제된 행 수: " + rowsAffected);
            } else {
                System.out.println("삭제할 쿠폰이 존재하지 않습니다.");
            }
        } catch (Exception e) {
            System.out.println("SQL execution error: " + e.getMessage());
        }
    }
}

package test.admin;

import core.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CouponInsertTest {
    public static void main(String[] args) {
        ConnectionPool cp = null;
        try {
            cp = ConnectionPool.create();  // ConnectionPool 객체 생성
        } catch (Exception e) {
            System.out.println("Error initializing ConnectionPool: " + e.getMessage());
            return;
        }

        String sql = "INSERT INTO coupon (name, code, type, discount, count, min_order_price, max_discount_price, start_date, end_date, is_active) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = cp.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 값 설정
            pstmt.setString(1, "신규 쿠폰");
            pstmt.setString(2, "NEW123");
            pstmt.setString(3, "percentage");
            pstmt.setDouble(4, 10.00);
            pstmt.setInt(5, 100);
            pstmt.setInt(6, 5000);
            pstmt.setInt(7, 2000);
            pstmt.setString(8, "2024-10-01");
            pstmt.setString(9, "2024-12-31");
            pstmt.setBoolean(10, true);

            // 쿼리 실행
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("쿠폰이 성공적으로 추가되었습니다. 추가된 행 수: " + rowsAffected);
        } catch (Exception e) {
            System.out.println("SQL execution error: " + e.getMessage());
        }
    }
}

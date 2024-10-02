package test;

import core.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CouponUpdateTest {
    public static void main(String[] args) {
        ConnectionPool cp = null;
        try {
            cp = ConnectionPool.create();  // ConnectionPool 객체 생성
        } catch (Exception e) {
            System.out.println("Error initializing ConnectionPool: " + e.getMessage());
            return;
        }

        String sql = "UPDATE coupon SET name = ?, discount = ?, is_active = ? WHERE coupon_id = ?";

        try (Connection conn = cp.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 값 설정
            pstmt.setString(1, "변경된 쿠폰명");
            pstmt.setDouble(2, 15.00);
            pstmt.setBoolean(3, false);
            pstmt.setInt(4, 34);  // coupon_id를 int로 설정

            // 쿼리 실행
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("쿠폰이 성공적으로 업데이트되었습니다. 업데이트된 행 수: " + rowsAffected);
        } catch (Exception e) {
            System.out.println("SQL execution error: " + e.getMessage());
        }
    }
}

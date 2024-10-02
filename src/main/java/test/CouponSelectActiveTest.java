package test;

import core.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CouponSelectActiveTest {
    public static void main(String[] args) {
        ConnectionPool cp = null;
        try {
            cp = ConnectionPool.create();  // ConnectionPool 객체 생성
        } catch (Exception e) {
            System.out.println("Error initializing ConnectionPool: " + e.getMessage());
            return;
        }

        try (Connection conn = cp.getConnection()) {
            String sql = "SELECT * FROM coupon WHERE is_active = TRUE AND NOW() BETWEEN start_date AND end_date";

            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                System.out.println("활성화된 쿠폰 목록:");
                boolean hasCoupons = false;
                while (rs.next()) {
                    hasCoupons = true;
                    System.out.println("쿠폰 ID: " + rs.getInt("coupon_id"));
                    System.out.println("쿠폰 이름: " + rs.getString("name"));
                    System.out.println("할인율: " + rs.getString("discount"));  // 'discount'로 수정
                    System.out.println("할인 종류: " + rs.getString("type")); // 할인 종류 출력
                    System.out.println("시작 날짜: " + rs.getTimestamp("start_date"));
                    System.out.println("종료 날짜: " + rs.getTimestamp("end_date"));
                    System.out.println("상태: " + (rs.getBoolean("is_active") ? "활성" : "비활성"));
                    System.out.println("------------------------");
                }
                if (!hasCoupons) {
                    System.out.println("활성화된 쿠폰이 없습니다.");
                }
            }
        } catch (Exception e) {
            System.out.println("SQL execution error: " + e.getMessage());
        }
    }
}

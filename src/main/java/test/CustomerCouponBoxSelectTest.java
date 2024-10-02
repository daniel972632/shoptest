package test;

import core.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerCouponBoxSelectTest {
    public static void main(String[] args) {
        ConnectionPool cp = null;
        try {
            cp = ConnectionPool.create();  // ConnectionPool 객체 생성
        } catch (Exception e) {
            System.out.println("Error initializing ConnectionPool: " + e.getMessage());
            return;
        }

        String sql = """
            SELECT cb.coupon_box_id, c.name AS coupon_name, c.discount, c.start_date, c.end_date, cb.is_used
            FROM coupon_box cb
            JOIN coupon c ON cb.coupon_id = c.coupon_id
            WHERE cb.customer_id = ?
            """;

        try (Connection conn = cp.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 파라미터 설정
            pstmt.setString(1, "customer034");  // 고객 ID

            // 쿼리 실행
            ResultSet rs = pstmt.executeQuery();

            // 결과 처리
            while (rs.next()) {
                int couponBoxId = rs.getInt("coupon_box_id");
                String couponName = rs.getString("coupon_name");
                double discount = rs.getDouble("discount");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                boolean isUsed = rs.getBoolean("is_used");

                System.out.printf("Coupon Box ID: %d, Coupon Name: %s, Discount: %.2f, Start Date: %s, End Date: %s, Is Used: %b%n",
                        couponBoxId, couponName, discount, startDate, endDate, isUsed);
            }
        } catch (Exception e) {
            System.out.println("SQL execution error: " + e.getMessage());
        }
    }
}

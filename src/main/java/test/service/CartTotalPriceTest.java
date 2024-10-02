package test.service;

import core.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CartTotalPriceTest {
    public static void main(String[] args) {
        ConnectionPool cp = null;
        try {
            cp = ConnectionPool.create();  // ConnectionPool 객체 생성
        } catch (Exception e) {
            System.out.println("Error initializing ConnectionPool: " + e.getMessage());
            return;
        }

        String sql = "SELECT SUM(c.total_price) AS total_cart_price FROM cart c WHERE c.customer_id = ?";

        try (Connection conn = cp.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 파라미터 설정
            pstmt.setString(1, "customer150");  // customer_id 설정

            // 쿼리 실행
            ResultSet rs = pstmt.executeQuery();

            // 결과 처리
            if (rs.next()) {
                double totalCartPrice = rs.getDouble("total_cart_price");
                System.out.println("고객 'customer150'의 총 장바구니 가격: " + totalCartPrice);
            } else {
                System.out.println("고객 'customer150'의 장바구니에 항목이 없습니다.");
            }
        } catch (Exception e) {
            System.out.println("SQL execution error: " + e.getMessage());
        }
    }
}

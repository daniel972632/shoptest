package test.service;

import core.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerSelectByIdTest {
    public static void main(String[] args) {
        ConnectionPool cp = null;
        try {
            cp = ConnectionPool.create();  // ConnectionPool 객체 생성
        } catch (Exception e) {
            System.out.println("Error initializing ConnectionPool: " + e.getMessage());
            return;
        }

        try (Connection conn = cp.getConnection()) {
            String sql = "SELECT customer_id, name, email, phone, level, created_at " +
                    "FROM customer WHERE customer_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, "customer021");  // customer_id를 'customer021'로 설정

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("고객 ID: " + rs.getString("customer_id"));
                        System.out.println("이름: " + rs.getString("name"));
                        System.out.println("이메일: " + rs.getString("email"));
                        System.out.println("전화: " + rs.getString("phone"));
                        System.out.println("레벨: " + rs.getString("level"));
                        System.out.println("가입 날짜: " + rs.getTimestamp("created_at"));
                    } else {
                        System.out.println("고객 ID 'customer021'에 대한 정보를 찾을 수 없습니다.");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("SQL execution error: " + e.getMessage());
        }
    }
}

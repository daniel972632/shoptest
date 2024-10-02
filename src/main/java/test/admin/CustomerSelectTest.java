package test.admin;

import core.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerSelectTest {
    public static void main(String[] args) {
        ConnectionPool cp = null;

        try {
            // ConnectionPool 객체 생성
            cp = ConnectionPool.create();
        } catch (Exception e) {
            System.out.println("Error initializing connection pool: " + e.getMessage());
            return;
        }

        String sql = """
            SELECT customer_id, name, email, phone, level, created_at
            FROM customer
            ORDER BY created_at DESC;
            """;

        try (Connection conn = cp.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // 결과 출력
            System.out.println("고객 목록:");
            while (rs.next()) {
                String customerId = rs.getString("customer_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String level = rs.getString("level");  // level을 String으로 가져옵니다.
                String createdAt = rs.getString("created_at");

                System.out.printf("ID: %s | 이름: %s | 이메일: %s | 전화: %s | 레벨: %s | 가입일: %s%n",
                        customerId, name, email, phone, level, createdAt);
            }

        } catch (SQLException e) {
            System.out.println("SQL execution error: " + e.getMessage());
        }
    }
}

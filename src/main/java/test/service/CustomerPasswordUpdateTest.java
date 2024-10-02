package test.service;

import core.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CustomerPasswordUpdateTest {
    public static void main(String[] args) {
        ConnectionPool cp = null;
        try {
            cp = ConnectionPool.create();  // ConnectionPool 객체 생성
        } catch (Exception e) {
            System.out.println("Error initializing ConnectionPool: " + e.getMessage());
            return;
        }

        String sql = """
            UPDATE customer
            SET password = ?, updated_at = NOW()
            WHERE customer_id = ?
            """;

        try (Connection conn = cp.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 파라미터 설정
            pstmt.setString(1, "newpassword");  // 새로운 비밀번호
            pstmt.setString(2, "customer023");   // 고객 ID

            // 쿼리 실행
            int rowsUpdated = pstmt.executeUpdate();
            System.out.println("Updated rows: " + rowsUpdated);
        } catch (Exception e) {
            System.out.println("SQL execution error: " + e.getMessage());
        }
    }
}

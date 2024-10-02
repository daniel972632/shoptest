package test.service;

import core.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddressUpdateTest {
    public static void main(String[] args) {
        ConnectionPool cp = null;
        try {
            cp = ConnectionPool.create();  // ConnectionPool 객체 생성
        } catch (Exception e) {
            System.out.println("Error initializing ConnectionPool: " + e.getMessage());
            return;
        }

        String sql = """
            UPDATE address
            SET name = ?, phone = ?, address1 = ?, is_default = ?
            WHERE address_id = ? AND customer_id = ?
            """;

        try (Connection conn = cp.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 파라미터 설정
            pstmt.setString(1, "김철수");             // name
            pstmt.setString(2, "010-9876-5432");      // phone
            pstmt.setString(3, "서울시 강북구");       // address1
            pstmt.setBoolean(4, false);                // is_default
            pstmt.setInt(5, 293);                        // address_id
            pstmt.setString(6, "user123");             // customer_id

            // 쿼리 실행
            int rowsAffected = pstmt.executeUpdate();

            // 결과 출력
            if (rowsAffected > 0) {
                System.out.println("주소가 성공적으로 업데이트되었습니다.");
            } else {
                System.out.println("주소 업데이트에 실패했습니다.");
            }
        } catch (Exception e) {
            System.out.println("SQL execution error: " + e.getMessage());
        }
    }
}

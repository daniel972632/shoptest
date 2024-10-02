package test;

import core.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddressInsertTest {
    public static void main(String[] args) {
        ConnectionPool cp = null;
        try {
            cp = ConnectionPool.create();  // ConnectionPool 객체 생성
        } catch (Exception e) {
            System.out.println("Error initializing ConnectionPool: " + e.getMessage());
            return;
        }

        String sql = """
            INSERT INTO address (customer_id, type, name, phone, address1, address2, zip_code, is_default)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = cp.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 파라미터 설정
            pstmt.setString(1, "user123");            // customer_id
            pstmt.setString(2, "home");                // type
            pstmt.setString(3, "홍길동");               // name
            pstmt.setString(4, "010-1234-5678");      // phone
            pstmt.setString(5, "서울시 강남구");       // address1
            pstmt.setString(6, "아파트 101호");        // address2
            pstmt.setString(7, "123456");              // zip_code
            pstmt.setBoolean(8, true);                 // is_default

            // 쿼리 실행
            int rowsAffected = pstmt.executeUpdate();

            // 결과 출력
            if (rowsAffected > 0) {
                System.out.println("주소가 성공적으로 추가되었습니다.");
            } else {
                System.out.println("주소 추가에 실패했습니다.");
            }
        } catch (Exception e) {
            System.out.println("SQL execution error: " + e.getMessage());
        }
    }
}

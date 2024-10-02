package test.admin;

import core.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ProductDeleteTest {
    public static void main(String[] args) {
        ConnectionPool cp = null;
        try {
            cp = ConnectionPool.create();  // ConnectionPool 객체 생성
        } catch (Exception e) {
            System.out.println("Error initializing ConnectionPool: " + e.getMessage());
            return;
        }

        try (Connection conn = cp.getConnection()) {
            String sql = "DELETE FROM product WHERE product_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, 11);  // 삭제할 product_id

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("상품 삭제 성공: " + affectedRows + "개의 행이 삭제되었습니다.");
                } else {
                    System.out.println("상품 삭제 실패: 해당 상품이 존재하지 않습니다.");
                }
            }
        } catch (Exception e) {
            System.out.println("SQL execution error: " + e.getMessage());
        }
    }
}

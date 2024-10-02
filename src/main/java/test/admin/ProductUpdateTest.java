package test.admin;

import core.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ProductUpdateTest {
    public static void main(String[] args) {
        ConnectionPool cp = null;
        try {
            cp = ConnectionPool.create();  // ConnectionPool 객체 생성
        } catch (Exception e) {
            System.out.println("Error initializing ConnectionPool: " + e.getMessage());
            return;
        }

        try (Connection conn = cp.getConnection()) {
            String sql = "UPDATE product " +
                    "SET name = ?, price = ?, stock_quantity = ?, is_selling = ?, updated_at = NOW() " +
                    "WHERE product_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, "변경된 상품명");
                pstmt.setInt(2, 12000);
                pstmt.setInt(3, 40);
                pstmt.setBoolean(4, true);
                pstmt.setInt(5, 11);  // product_id가 11인 상품 업데이트

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("상품 업데이트 성공: " + affectedRows + "개의 행이 업데이트되었습니다.");
                } else {
                    System.out.println("상품 업데이트 실패: 해당 상품이 존재하지 않습니다.");
                }
            }
        } catch (Exception e) {
            System.out.println("SQL execution error: " + e.getMessage());
        }
    }
}

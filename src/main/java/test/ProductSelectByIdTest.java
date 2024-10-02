package test;

import core.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductSelectByIdTest {
    public static void main(String[] args) {
        ConnectionPool cp = null;
        try {
            cp = ConnectionPool.create();  // ConnectionPool 객체 생성
        } catch (Exception e) {
            System.out.println("Error initializing ConnectionPool: " + e.getMessage());
            return;
        }

        try (Connection conn = cp.getConnection()) {
            String sql = "SELECT product_id, name, stock_quantity FROM product WHERE product_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, 10);  // 조회할 product_id

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("상품 ID: " + rs.getInt("product_id"));
                        System.out.println("상품 이름: " + rs.getString("name"));
                        System.out.println("재고 수량: " + rs.getInt("stock_quantity"));
                    } else {
                        System.out.println("해당 상품이 존재하지 않습니다.");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("SQL execution error: " + e.getMessage());
        }
    }
}

package test;

import core.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductSelectTest_1 {
    public static void main(String[] args) {
        ConnectionPool cp = null;
        try {
            cp = ConnectionPool.create();  // ConnectionPool 객체 생성
        } catch (Exception e) {
            System.out.println("Error initializing ConnectionPool: " + e.getMessage());
            return;
        }

        try (Connection conn = cp.getConnection()) {
            String sql = "SELECT p.*, c.name AS category_name " +
                    "FROM product p " +
                    "JOIN category c ON p.category_id = c.category_id " +
                    "WHERE p.product_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, 10);  // product_id에 대한 값을 설정
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("상품 ID: " + rs.getInt("product_id"));
                        System.out.println("상품 이름: " + rs.getString("name"));
                        System.out.println("가격: " + rs.getInt("price"));
                        System.out.println("재고 수량: " + rs.getInt("stock_quantity"));
                        System.out.println("판매 여부: " + rs.getBoolean("is_selling"));
                        System.out.println("카테고리 이름: " + rs.getString("category_name"));
                    } else {
                        System.out.println("해당 ID의 상품이 존재하지 않습니다.");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("SQL execution error: " + e.getMessage());
        }
    }
}

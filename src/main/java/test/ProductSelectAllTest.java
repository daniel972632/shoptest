package test;

import core.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductSelectAllTest {
    public static void main(String[] args) {
        ConnectionPool cp = null;
        try {
            cp = ConnectionPool.create();  // ConnectionPool 객체 생성
        } catch (Exception e) {
            System.out.println("Error initializing ConnectionPool: " + e.getMessage());
            return;
        }

        try (Connection conn = cp.getConnection()) {
            String sql = "SELECT p.product_id, p.name, p.price, p.stock_quantity, p.is_selling, c.name AS category_name " +
                    "FROM product p " +
                    "JOIN category c ON p.category_id = c.category_id " +
                    "ORDER BY p.created_at DESC " +  // ORDER BY 절 뒤에 공백 추가
                    "LIMIT 10";  // LIMIT 절 추가

            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                System.out.println("상품 목록:");
                while (rs.next()) {
                    System.out.println("상품 ID: " + rs.getInt("product_id"));
                    System.out.println("상품 이름: " + rs.getString("name"));
                    System.out.println("가격: " + rs.getInt("price"));
                    System.out.println("재고 수량: " + rs.getInt("stock_quantity"));
                    System.out.println("판매 여부: " + rs.getBoolean("is_selling"));
                    System.out.println("카테고리 이름: " + rs.getString("category_name"));
                    System.out.println("------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("SQL execution error: " + e.getMessage());
        }
    }
}

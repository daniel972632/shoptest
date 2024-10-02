package test;


import core.ConnectionPool;
import product.dto.Product;
import product.dao.ProductDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductSelectTest {
    public static void main(String[] args) {
        ConnectionPool cp = null;

        try {
            // ConnectionPool 객체 생성
            cp = ConnectionPool.create();
        } catch (Exception e) {
            System.out.println("Error initializing connection pool: " + e.getMessage());
            return;
        }

        try (Connection conn = cp.getConnection()) {
            // SQL 쿼리 작성
            String sql = "SELECT p.product_id, p.name, p.price, p.stock_quantity, p.is_selling, c.name AS category_name " +
                    "FROM product p " +
                    "JOIN category c ON p.category_id = c.category_id " +
                    "ORDER BY p.created_at DESC;";

            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                List<Product> products = new ArrayList<>();
                System.out.println("제품 목록:");
                while (rs.next()) {
                    // 제품 정보 가져오기
                    int productId = rs.getInt("product_id");
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    int stockQuantity = rs.getInt("stock_quantity");
                    boolean isSelling = rs.getBoolean("is_selling");
                    String categoryName = rs.getString("category_name");

                    // 제품 객체 생성
                    Product product = new Product(productId, 0, name, "", price, stockQuantity, isSelling, null, null);
                    products.add(product);

                    // 결과 출력
                    System.out.println("제품 ID: " + productId + ", 이름: " + name + ", 가격: " + price +
                            ", 재고 수량: " + stockQuantity + ", 판매 중: " + isSelling +
                            ", 카테고리 이름: " + categoryName);
                }

                System.out.println("총 " + products.size() + " 개의 제품이 조회되었습니다.");
            }
        } catch (Exception e) {
            System.out.println("SQL execution error: " + e.getMessage());
        }
    }
}
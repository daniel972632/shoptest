package test.admin;
import core.ConnectionPool;
import product.dto.Product;
import product.dao.ProductDao;

import java.sql.Connection;

public class ProductInsertTest {
    public static void main(String[] args) {
        ProductDao productDao = new ProductDao();
        ConnectionPool cp = null;

        try {
            // ConnectionPool 객체 생성
            cp = ConnectionPool.create();
        } catch (Exception e) {
            System.out.println("Error initializing connection pool: " + e.getMessage());
            return;
        }

        try (Connection conn = cp.getConnection()) {
            // 새로운 제품 정보 생성
            Product newProduct = Product.builder()
                    .category_id(1)
                    .name("상품명")
                    .description("상품 설명")
                    .price(10000)
                    .stock_quantity(50)
                    .is_selling(true)
                    .created_at(new java.sql.Timestamp(System.currentTimeMillis())) // 현재 시간
                    .build();

            // 제품 삽입
            Product insertedProduct = productDao.insert(newProduct, conn);
            System.out.println("제품 삽입 성공: " + insertedProduct);
        } catch (Exception e) {
            System.out.println("Insert error: " + e.getMessage());
        }
    }
}
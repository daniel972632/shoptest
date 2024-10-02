package product.test;

import product.service.ProductService;
import product.dao.ProductDao;
import product.dto.Product;
import core.ConnectionPool;

import java.sql.Connection;

public class ProductInsert {
    public static void main(String[] args) {
        ProductService productService = null;
        ConnectionPool cp = null;

        try {
            productService = new ProductService(new ProductDao()); // ProductDao 구현체 생성
            cp = ConnectionPool.create();  // ConnectionPool 객체 생성
        } catch (Exception e) {
            System.out.println("Error initializing service: " + e.getMessage());
            return;
        }

        try (Connection conn = cp.getConnection()) {
            Product newProduct = Product.builder()
                    .product_id(0) // 제품 ID는 자동 생성이므로 0으로 설정
                    .name("New Product")
                    .description("This is a new product.")
                    .price(100)
                    .stock_quantity(50)
                    .is_selling(true)
                    .build();

            Product insertedProduct = productService.register(newProduct, conn);
            System.out.println("제품 삽입 성공: " + insertedProduct);
        } catch (Exception e) {
            System.out.println("Insert error: " + e.getMessage());
        }
    }
}

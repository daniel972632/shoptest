package product.test;

import product.service.ProductService;
import product.dao.ProductDao;
import product.dto.Product;
import core.ConnectionPool;

import java.sql.Connection;

public class ProductUpdate {
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
            int productIdToUpdate = 1; // 업데이트할 제품 ID (예: 1)

            // 제품 정보를 업데이트할 객체 생성
            Product updatedProduct = Product.builder()
                    .product_id(productIdToUpdate)
                    .category_id(2) // 새로운 카테고리 ID
                    .name("Updated Product Name")
                    .description("Updated Description")
                    .price(15000) // 새로운 가격
                    .stock_quantity(100) // 새로운 재고 수량
                    .is_selling(true) // 판매 여부
                    .build();

            Product resultProduct = productService.modify(updatedProduct, conn);
            if (resultProduct != null) {
                System.out.println("제품 업데이트 성공: " + resultProduct);
            } else {
                System.out.println("제품 업데이트 실패: ID " + productIdToUpdate);
            }
        } catch (Exception e) {
            System.out.println("Update error: " + e.getMessage());
        }
    }
}

package product.test;

import product.service.ProductService;
import product.dao.ProductDao;
import product.dto.Product;
import core.ConnectionPool;

import java.sql.Connection;

public class ProductSelectOne {
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
            int productIdToSelect = 154; // 조회할 제품 ID (예: 1)
            Product product = productService.get(productIdToSelect, conn);
            if (product != null) {
                System.out.println("제품 조회 성공: " + product);
            } else {
                System.out.println("제품을 찾을 수 없습니다: ID " + productIdToSelect);
            }
        } catch (Exception e) {
            System.out.println("Select one error: " + e.getMessage());
        }
    }
}

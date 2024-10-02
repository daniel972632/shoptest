package product.test;

import product.service.ProductService;
import product.dao.ProductDao;
import core.ConnectionPool;

import java.sql.Connection;

public class ProductDelete {
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
            int productIdToDelete = 1; // 삭제할 제품 ID (예: 1)
            boolean isDeleted = productService.remove(productIdToDelete, conn);
            if (isDeleted) {
                System.out.println("제품 삭제 성공: ID " + productIdToDelete);
            } else {
                System.out.println("제품 삭제 실패: ID " + productIdToDelete);
            }
        } catch (Exception e) {
            System.out.println("Delete error: " + e.getMessage());
        }
    }
}

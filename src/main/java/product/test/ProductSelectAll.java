package product.test;

import product.service.ProductService;
import product.dao.ProductDao;
import product.dto.Product;
import core.ConnectionPool;

import java.sql.Connection;
import java.util.List;

public class ProductSelectAll {
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
            List<Product> productList = productService.getAll(conn);
            System.out.println("모든 제품 조회 성공: ");
            for (Product product : productList) {
                System.out.println(product);
            }
        } catch (Exception e) {
            System.out.println("Select all error: " + e.getMessage());
        }
    }
}

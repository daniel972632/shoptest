package product.service;

import core.ConnectionPool;
import core.MService;
import product.dao.ProductDao;
import product.dto.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductService implements MService<Integer, Product> {

    private final ProductDao productDao;
    private final ConnectionPool cp;

    // 서비스 생성자에서 ConnectionPool과 DAO 객체 초기화
    public ProductService(ProductDao productDao) throws SQLException {
        this.productDao = productDao;
        this.cp = ConnectionPool.create();  // 커넥션 풀 초기화
    }

    // 트랜잭션을 위한 메서드
    private void executeTransaction(TransactionBlock block) throws SQLException {
        Connection conn = cp.getConnection();
        try {
            conn.setAutoCommit(false);  // 트랜잭션 시작
            block.execute(conn);  // 트랜잭션 로직 실행
            conn.commit();  // 성공하면 커밋
        } catch (Exception e) {
            conn.rollback();  // 예외 발생 시 롤백
            throw new SQLException("Transaction failed: " + e.getMessage(), e);
        } finally {
            cp.releaseConnection(conn);  // 커넥션 반환
        }
    }

    // Connection을 인자로 받는 register 구현
    @Override
    public Product register(Product product, Connection conn) throws Exception {
        executeTransaction(c -> {
            productDao.insert(product, c);  // DAO를 사용하여 상품 등록
            System.out.println("상품 등록 성공: " + product.getName());
        });
        return product;
    }

    // Connection을 인자로 받는 modify 구현
    @Override
    public Product modify(Product product, Connection conn) throws Exception {
        executeTransaction(c -> {
            productDao.update(product, c);  // DAO를 사용하여 상품 수정
            System.out.println("상품 업데이트 성공: " + product.getProduct_id());
        });
        return product;
    }

    // Connection을 인자로 받는 remove 구현
    @Override
    public boolean remove(Integer productId, Connection conn) throws Exception {
        // executeTransaction 메서드를 사용하여 삭제 작업을 수행하고 결과를 반환
        boolean[] result = {false};  // 배열을 사용하여 내부에서 값을 변경

        executeTransaction(c -> {
            result[0] = productDao.delete(productId, c);  // DAO를 사용하여 상품 삭제
            if (result[0]) {
                System.out.println("상품 삭제 성공: " + productId);
            } else {
                System.out.println("상품 삭제 실패: " + productId);
            }
        });
        return result[0];
    }


    // Connection을 인자로 받는 get 구현
    @Override
    public Product get(Integer productId, Connection conn) throws Exception {
        return productDao.select(productId, conn);  // DAO를 사용하여 상품 조회
    }

    // Connection을 인자로 받는 getAll 구현
    @Override
    public List<Product> getAll(Connection conn) throws Exception {
        return productDao.selectAll(conn);  // DAO를 사용하여 모든 상품 조회
    }

    // 트랜잭션 블록을 위한 인터페이스
    @FunctionalInterface
    private interface TransactionBlock {
        void execute(Connection conn) throws Exception;
    }
}

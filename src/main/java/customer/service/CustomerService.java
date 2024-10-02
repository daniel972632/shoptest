package customer.service;

import customer.dao.CustomerDaoImpl;
import customer.dto.Customer;
import core.ConnectionPool;
import core.MService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CustomerService implements MService<String, Customer> {

    private final CustomerDaoImpl customerDaoImpl;
    private final ConnectionPool cp;

    // 서비스 생성자에서 ConnectionPool과 DAO 객체 초기화
    public CustomerService(CustomerDaoImpl customerDaoImpl) throws SQLException {
        this.customerDaoImpl = customerDaoImpl;
        this.cp = ConnectionPool.create();  // 커넥션 풀 초기화
    }

    // 트랜잭션을 위한 메서드 예시
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
    public Customer register(Customer customer, Connection conn) throws Exception {
        customerDaoImpl.insert(customer, conn);
        System.out.println("회원가입 성공: " + customer.getName());
        return customer;
    }

    // Connection을 인자로 받는 modify 구현
    @Override
    public Customer modify(Customer customer, Connection conn) throws Exception {
        customerDaoImpl.update(customer, conn);
        System.out.println("업데이트 성공: " + customer.getCustomerId());
        return customer;
    }

    // Connection을 인자로 받는 remove 구현
    @Override
    public boolean remove(String customerId, Connection conn) throws Exception {
        boolean result = customerDaoImpl.delete(customerId, conn);
        if (result) {
            System.out.println("삭제 성공: " + customerId);
        } else {
            System.out.println("삭제 실패: " + customerId);
        }
        return result;
    }

    // Connection을 인자로 받는 get 구현
    @Override
    public Customer get(String customerId, Connection conn) throws Exception {
        return customerDaoImpl.select(customerId, conn);
    }

    // Connection을 인자로 받는 getAll 구현
    @Override
    public List<Customer> getAll(Connection conn) throws Exception {
        return customerDaoImpl.selectAll(conn);
    }

    // 트랜잭션 블록을 위한 인터페이스
    @FunctionalInterface
    private interface TransactionBlock {
        void execute(Connection conn) throws Exception;
    }
}

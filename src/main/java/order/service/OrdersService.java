package order.service;

import core.ConnectionPool;
import core.MService;
import order.dao.OrdersDao;
import order.dto.Orders;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrdersService implements MService<Integer, Orders> {

    private final OrdersDao ordersDao;
    private final ConnectionPool cp;

    // 서비스 생성자에서 ConnectionPool과 DAO 객체 초기화
    public OrdersService(OrdersDao ordersDao) throws SQLException {
        this.ordersDao = ordersDao;
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
    public Orders register(Orders order, Connection conn) throws Exception {
        Orders insertedOrder = ordersDao.insert(order, conn);
        System.out.println("주문 등록 성공: " + insertedOrder.getOrder_id());
        return insertedOrder;
    }

    // Connection을 인자로 받는 modify 구현
    @Override
    public Orders modify(Orders order, Connection conn) throws Exception {
        Orders updatedOrder = ordersDao.update(order, conn);
        System.out.println("주문 수정 성공: " + updatedOrder.getOrder_id());
        return updatedOrder;
    }

    // Connection을 인자로 받는 remove 구현
    @Override
    public boolean remove(Integer orderId, Connection conn) throws Exception {
        boolean result = ordersDao.delete(orderId, conn);
        if (result) {
            System.out.println("주문 삭제 성공: " + orderId);
        } else {
            System.out.println("주문 삭제 실패: " + orderId);
        }
        return result;
    }

    // Connection을 인자로 받는 get 구현
    @Override
    public Orders get(Integer orderId, Connection conn) throws Exception {
        return ordersDao.select(orderId, conn);
    }

    // Connection을 인자로 받는 getAll 구현
    @Override
    public List<Orders> getAll(Connection conn) throws Exception {
        return ordersDao.selectAll(conn);
    }

    // 트랜잭션 블록을 위한 인터페이스
    @FunctionalInterface
    private interface TransactionBlock {
        void execute(Connection conn) throws Exception;
    }
}

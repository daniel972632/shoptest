package order.service;

import order.dao.OrderDetailDao;
import order.dto.OrderDetail;
import core.ConnectionPool;
import core.MService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailService implements MService<Integer, OrderDetail> {

    private final OrderDetailDao orderDetailDao;
    private final ConnectionPool cp;

    // 서비스 생성자에서 ConnectionPool과 DAO 객체 초기화
    public OrderDetailService(OrderDetailDao orderDetailDao) throws SQLException {
        this.orderDetailDao = orderDetailDao;
        this.cp = ConnectionPool.create();  // 커넥션 풀 초기화
    }

    // Connection을 인자로 받는 register 구현
    @Override
    public OrderDetail register(OrderDetail orderDetail, Connection conn) throws Exception {
        orderDetailDao.insert(orderDetail, conn);
        System.out.println("주문 상세 등록 성공: " + orderDetail.getOrder_detail_id());
        return orderDetail;
    }

    // Connection을 인자로 받는 modify 구현
    @Override
    public OrderDetail modify(OrderDetail orderDetail, Connection conn) throws Exception {
        orderDetailDao.update(orderDetail, conn);
        System.out.println("주문 상세 수정 성공: " + orderDetail.getOrder_detail_id());
        return orderDetail;
    }

    // Connection을 인자로 받는 remove 구현
    @Override
    public boolean remove(Integer orderDetailId, Connection conn) throws Exception {
        boolean result = orderDetailDao.delete(orderDetailId, conn);
        if (result) {
            System.out.println("주문 상세 삭제 성공: " + orderDetailId);
        } else {
            System.out.println("주문 상세 삭제 실패: " + orderDetailId);
        }
        return result;
    }

    // Connection을 인자로 받는 get 구현
    @Override
    public OrderDetail get(Integer orderDetailId, Connection conn) throws Exception {
        return orderDetailDao.select(orderDetailId, conn);
    }

    // Connection을 인자로 받는 getAll 구현
    @Override
    public List<OrderDetail> getAll(Connection conn) throws Exception {
        return orderDetailDao.selectAll(conn);
    }
}

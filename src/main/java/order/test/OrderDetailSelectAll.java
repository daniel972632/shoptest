package order.test;

import core.ConnectionPool;
import order.dto.OrderDetail;
import order.service.OrderDetailService;
import order.dao.OrderDetailDao;

import java.sql.Connection;
import java.util.List;

public class OrderDetailSelectAll {
    public static void main(String[] args) {
        OrderDetailService orderDetailService = null;
        ConnectionPool cp = null;

        // ConnectionPool 초기화
        try {
            cp = ConnectionPool.create(); // ConnectionPool을 생성
            orderDetailService = new OrderDetailService(new OrderDetailDao());
        } catch (Exception e) {
            System.out.println("Error initializing service: " + e.getMessage());
            return;
        }

        // 데이터베이스 연결 및 주문 상세 목록 조회
        try (Connection conn = cp.getConnection()) {
            List<OrderDetail> orderDetails = orderDetailService.getAll(conn);
            System.out.println("주문 상세 목록 조회 성공: " + orderDetails);
        } catch (Exception e) {
            System.out.println("System error: " + e.getMessage());
        }
    }
}

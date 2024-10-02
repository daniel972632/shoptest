package order.test;

import core.ConnectionPool;
import order.dto.OrderDetail;
import order.service.OrderDetailService;
import order.dao.OrderDetailDao;

import java.sql.Connection;

public class OrderDetailSelectOne {
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

        int orderDetailId = 1; // 조회할 주문 상세 ID

        // 데이터베이스 연결 및 주문 상세 조회
        try (Connection conn = cp.getConnection()) {
            OrderDetail orderDetail = orderDetailService.get(orderDetailId, conn);
            if (orderDetail != null) {
                System.out.println("주문 상세 조회 성공: " + orderDetail);
            } else {
                System.out.println("주문 상세 조회 실패: " + orderDetailId);
            }
        } catch (Exception e) {
            System.out.println("System error: " + e.getMessage());
        }
    }
}

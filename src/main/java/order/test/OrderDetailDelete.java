package order.test;

import core.ConnectionPool;
import order.service.OrderDetailService;
import order.dao.OrderDetailDao;

import java.sql.Connection;

public class OrderDetailDelete {
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

        int orderDetailId = 1; // 삭제할 주문 상세 ID

        // 데이터베이스 연결 및 주문 상세 삭제
        try (Connection conn = cp.getConnection()) {
            boolean result = orderDetailService.remove(orderDetailId, conn);
            System.out.println(result ? "주문 상세 삭제 성공: " + orderDetailId : "주문 상세 삭제 실패: " + orderDetailId);
        } catch (Exception e) {
            System.out.println("System error: " + e.getMessage());
        }
    }
}

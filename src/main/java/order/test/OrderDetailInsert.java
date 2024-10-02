package order.test;

import core.ConnectionPool;
import order.dto.OrderDetail;
import order.service.OrderDetailService;
import order.dao.OrderDetailDao;

import java.sql.Connection;
import java.sql.SQLException;

public class OrderDetailInsert {
    public static void main(String[] args) {
        OrderDetailService orderDetailService = null;
        ConnectionPool cp = null;  // ConnectionPool 초기화
        try {
            cp = ConnectionPool.create();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating connection pool: " + e.getMessage(), e);
        }

        try {
            orderDetailService = new OrderDetailService(new OrderDetailDao());
        } catch (Exception e) {
            System.out.println("Error initializing service: " + e.getMessage());
            return;
        }

        // OrderDetail 객체 생성
        OrderDetail orderDetail = OrderDetail.builder()
                .order_detail_id(1)        // 주문 상세 ID (자동 생성될 수 있으므로 적절히 설정)
                .order_id(1001)           // 예시 주문 ID
                .product_id(2001)         // 예시 상품 ID
                .quantity(2)              // 수량
                .product_price(5000)      // 가격
                .total_discount_price(0)   // 할인 가격 (예시로 0으로 설정)
                .build();

        try (Connection conn = cp.getConnection()) {
            orderDetailService.register(orderDetail, conn); // 등록 메서드 호출
            System.out.println("주문 상세 등록 성공: " + orderDetail.getOrder_detail_id());
        } catch (Exception e) {
            System.out.println("System error: " + e.getMessage());
        }
    }
}

package order.test;

import core.ConnectionPool;
import order.dto.OrderDetail;
import order.service.OrderDetailService;
import order.dao.OrderDetailDao;

import java.sql.Connection;

public class OrderDetailUpdate {
    public static void main(String[] args) {
        OrderDetailService orderDetailService = null;
        ConnectionPool cp = null;

        try {
            cp = ConnectionPool.create(); // ConnectionPool 초기화
            orderDetailService = new OrderDetailService(new OrderDetailDao());
        } catch (Exception e) {
            System.out.println("Error initializing service: " + e.getMessage());
            return;
        }

        // 수정할 주문 상세 객체 생성
        OrderDetail orderDetail = OrderDetail.builder()
                .order_detail_id(1)       // 수정할 주문 상세 ID
                .order_id(1001)          // 주문 ID
                .product_id(2001)        // 상품 ID
                .quantity(3)             // 수정된 수량
                .product_price(5000)     // 가격
                .total_discount_price(0)  // 할인 가격 (예시로 0으로 설정)
                .build();

        try (Connection conn = cp.getConnection()) {
            orderDetailService.modify(orderDetail, conn); // 수정 메서드 호출
            System.out.println("주문 상세 수정 성공: " + orderDetail.getOrder_detail_id());
        } catch (Exception e) {
            System.out.println("System error: " + e.getMessage());
        }
    }
}

package order.test;

import core.ConnectionPool;
import order.dto.Orders;
import order.service.OrdersService;
import order.dao.OrdersDao;
import java.sql.Connection;
import java.util.List;

public class OrderSelectAll {
    public static void main(String[] args) {
        OrdersService ordersService = null;
        ConnectionPool cp = null;

        try {
            ordersService = new OrdersService(new OrdersDao());
            cp = ConnectionPool.create();  // ConnectionPool 객체 생성 및 초기화
        } catch (Exception e) {
            System.out.println("Error initializing service: " + e.getMessage());
            return;
        }

        try (Connection conn = cp.getConnection()) {
            List<Orders> ordersList = ordersService.getAll(conn);
            System.out.println("주문 목록 조회 성공: " + ordersList.size() + " 개의 주문이 있습니다.");
            for (Orders order : ordersList) {
                System.out.println("주문 ID: " + order.getOrder_id() + ", 고객 이름: " + order.getName());
            }
        } catch (Exception e) {
            System.out.println("System error: " + e.getMessage());
        }
    }
}

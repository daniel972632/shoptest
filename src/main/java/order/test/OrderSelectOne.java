package order.test;

import core.ConnectionPool;
import order.dto.Orders;
import order.service.OrdersService;
import order.dao.OrdersDao;
import java.sql.Connection;

public class OrderSelectOne {
    public static void main(String[] args) {
        OrdersService ordersService = null;
        ConnectionPool cp = null;

        try {
            ordersService = new OrdersService(new OrdersDao());
        } catch (Exception e) {
            System.out.println("Error initializing service: " + e.getMessage());
            return;
        }

        int orderId = 1; // ID of the order to select

        try (Connection conn = cp.getConnection()) {
            Orders order = ordersService.get(orderId, conn);
            if (order != null) {
                System.out.println("주문 조회 성공: " + order.getName());
            } else {
                System.out.println("주문이 존재하지 않습니다: " + orderId);
            }
        } catch (Exception e) {
            System.out.println("System error: " + e.getMessage());
        }
    }
}

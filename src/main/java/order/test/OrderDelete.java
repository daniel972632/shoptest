package order.test;

import core.ConnectionPool;
import order.service.OrdersService;
import order.dao.OrdersDao;
import java.sql.Connection;

public class OrderDelete {
    public static void main(String[] args) {
        OrdersService ordersService = null;
        ConnectionPool cp = null;

        try {
            ordersService = new OrdersService(new OrdersDao());
        } catch (Exception e) {
            System.out.println("Error initializing service: " + e.getMessage());
            return;
        }

        int orderId = 1; // ID of the order to delete

        try (Connection conn = cp.getConnection()) {
            boolean result = ordersService.remove(orderId, conn);
            if (result) {
                System.out.println("주문 삭제 성공: " + orderId);
            } else {
                System.out.println("주문 삭제 실패: " + orderId);
            }
        } catch (Exception e) {
            System.out.println("System error: " + e.getMessage());
        }
    }
}

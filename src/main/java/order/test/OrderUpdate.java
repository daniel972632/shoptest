package order.test;

import core.ConnectionPool;
import order.dto.Orders;
import order.service.OrdersService;
import order.dao.OrdersDao;
import java.sql.Connection;

public class OrderUpdate {
    public static void main(String[] args) {
        OrdersService ordersService = null;
        ConnectionPool cp = null;

        try {
            ordersService = new OrdersService(new OrdersDao());
        } catch (Exception e) {
            System.out.println("Error initializing service: " + e.getMessage());
            return;
        }

        Orders order = new Orders();
        order.setOrder_id(1); // Update an existing order
        order.setStatus("Shipped");

        try (Connection conn = cp.getConnection()) {
            ordersService.modify(order, conn);
            System.out.println("주문 수정 성공: " + order.getOrder_id());
        } catch (Exception e) {
            System.out.println("System error: " + e.getMessage());
        }
    }
}

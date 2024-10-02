package order.test;

import core.ConnectionPool;
import order.dto.Orders;
import order.service.OrdersService;
import order.dao.OrdersDao;
import java.sql.Connection;
import java.sql.Date;

public class OrderInsert {
    public static void main(String[] args) {
        OrdersService ordersService = null;
        ConnectionPool cp = null;

        try {
            ordersService = new OrdersService(new OrdersDao());
        } catch (Exception e) {
            System.out.println("Error initializing service: " + e.getMessage());
            return;
        }

        Orders order = Orders.builder()
                .customer_id("cust08")
                .name("John Doe")
                .phone("010-1234-5678")
                .address1("123 Main St")
                .address2("Apt 4B")
                .zip_code("123456")
                .total_price(100)
                .status("Pending")
                .created_at(new Date(System.currentTimeMillis())) // 변경
                .build();

        try (Connection conn = cp.getConnection()) {
            ordersService.register(order, conn);
            System.out.println("주문 등록 성공: " + order.getName());
        } catch (Exception e) {
            System.out.println("System error: " + e.getMessage());
        }
    }
}

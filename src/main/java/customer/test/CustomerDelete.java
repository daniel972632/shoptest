package customer.test;

import customer.service.CustomerService;
import customer.dao.CustomerDaoImpl;
import core.ConnectionPool;

import java.sql.Connection;

public class CustomerDelete {
    public static void main(String[] args) {
        CustomerService customerService = null;
        ConnectionPool cp = null;

        try {
            customerService = new CustomerService(new CustomerDaoImpl());
            cp = ConnectionPool.create();  // ConnectionPool 객체를 생성하는 부분 추가
        } catch (Exception e) {
            System.out.println("Error initializing service: " + e.getMessage());
            return;
        }

        String customerId = "cust08"; // 삭제할 고객 ID

        try (Connection conn = cp.getConnection()) {  // cp가 null이 아니도록 수정
            boolean isDeleted = customerService.remove(customerId, conn);  // Connection과 함께 호출
            if (isDeleted) {
                System.out.println("삭제 성공: " + customerId);
            } else {
                System.out.println("삭제 실패: " + customerId);
            }
        } catch (Exception e) {
            System.out.println("System error: " + e.getMessage());
        }
    }
}

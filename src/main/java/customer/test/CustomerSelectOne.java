// package 경로는 필요에 맞게 설정해주세요.
package customer.test;

import customer.dto.Customer;
import customer.dao.CustomerDaoImpl;
import customer.service.CustomerService;
import core.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class CustomerSelectOne {
    public static void main(String[] args) {
        // CustomerDaoImpl 인스턴스 생성
        CustomerDaoImpl customerDaoImpl = new CustomerDaoImpl();

        // CustomerService 인스턴스 생성
        CustomerService customerService = null;
        ConnectionPool cp = null;
        try {
            cp = ConnectionPool.create(); // ConnectionPool 초기화
            customerService = new CustomerService(customerDaoImpl);
        } catch (SQLException e) {
            System.out.println("Error initializing CustomerService: " + e.getMessage());
            return;
        }

        // Connection 객체를 가져와서 사용
        Connection conn = null;
        try {
            conn = cp.getConnection();

            // 조회할 고객 ID 설정
            String customerId = "customer099"; // 원하는 고객 ID로 변경하세요.

            // 고객 정보 조회
            Customer customer = customerService.get(customerId, conn);

            // 조회된 고객 정보 출력
            if (customer != null) {
                System.out.println("고객 정보: " + customer);
            } else {
                System.out.println("해당 ID의 고객을 찾을 수 없습니다: " + customerId);
            }
        } catch (Exception e) {
            System.out.println("System error: " + e.getMessage());
        } finally {
            // Connection 반환
            if (conn != null) {
                cp.releaseConnection(conn);
            }
        }
    }
}

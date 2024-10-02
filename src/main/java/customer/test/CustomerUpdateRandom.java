package customer.test;

import customer.dao.CustomerDaoImpl;
import customer.dto.Customer;
import customer.service.CustomerService;
import core.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class CustomerUpdateRandom {
    public static void main(String[] args) {
        // CustomerDaoImpl 및 CustomerService 인스턴스 생성
        CustomerDaoImpl customerDaoImpl = new CustomerDaoImpl();
        CustomerService customerService = null;
        ConnectionPool cp = null;

        try {
            // 커넥션 풀 초기화 및 서비스 생성
            cp = ConnectionPool.create();
            customerService = new CustomerService(customerDaoImpl);
        } catch (SQLException e) {
            System.out.println("Error initializing CustomerService: " + e.getMessage());
            return;
        }

        // 커넥션 가져오기
        Connection conn = null;
        try {
            conn = cp.getConnection();

            // 전체 고객 리스트 조회
            List<Customer> allCustomers = customerService.getAll(conn);

            // 총 고객 수
            int totalCustomers = allCustomers.size();

            // 랜덤 인스턴스 생성
            Random random = new Random();

            // 실버 등급으로 업데이트할 고객 40명 랜덤 선택
            for (int i = 0; i < 40; i++) {
                int index = random.nextInt(totalCustomers); // 랜덤 인덱스 선택
                Customer customer = allCustomers.get(index);
                customer.setLevel("silver"); // 등급 변경
                customerService.modify(customer, conn); // 수정
                System.out.println("Updated to Silver: " + customer.getCustomerId());
            }

            // 골드 등급으로 업데이트할 고객 20명 랜덤 선택
            for (int i = 0; i < 20; i++) {
                int index = random.nextInt(totalCustomers); // 랜덤 인덱스 선택
                Customer customer = allCustomers.get(index);
                customer.setLevel("gold"); // 등급 변경
                customerService.modify(customer, conn); // 수정
                System.out.println("Updated to Gold: " + customer.getCustomerId());
            }

        } catch (Exception e) {
            System.out.println("System error: " + e.getMessage());
        } finally {
            // 커넥션 반환
            if (conn != null) {
                cp.releaseConnection(conn);
            }
        }
    }
}

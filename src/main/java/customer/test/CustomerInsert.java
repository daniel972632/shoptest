package customer.test;

import core.ConnectionPool;
import customer.dto.Customer;
import customer.service.CustomerService;
import customer.dao.CustomerDaoImpl;

import java.sql.Connection;

public class CustomerInsert {
    public static void main(String[] args) {
        CustomerService customerService = null;
        ConnectionPool cp = null;

        try {
            customerService = new CustomerService(new CustomerDaoImpl());
        } catch (Exception e) {
            System.out.println("Error initializing service: " + e.getMessage());
            return;
        }

        Customer customer = Customer.builder()
                .customerId("cust08")
                .name("John Doe")
                .email("john08@example.com")
                .phone("010-1234-5678")
                .password("password08")
                .level("bronze")
                .build();

        try (Connection conn = cp.getConnection()) {
            customerService.register(customer,conn);
            System.out.println("회원가입 성공: " + customer.getName());
        } catch (Exception e) {
            System.out.println("System error: " + e.getMessage());
        }
    }
}

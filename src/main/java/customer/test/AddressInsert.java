package customer.test;

import customer.service.AddressService;
import customer.dao.AddressDaoImpl;
import customer.dto.Address;
import core.ConnectionPool;

import java.sql.Connection;

public class AddressInsert {
    public static void main(String[] args) {
        AddressService addressService = null;
        ConnectionPool cp = null;

        try {
            addressService = new AddressService(new AddressDaoImpl());
            cp = ConnectionPool.create();  // ConnectionPool 객체 생성
        } catch (Exception e) {
            System.out.println("Error initializing service: " + e.getMessage());
            return;
        }

        try (Connection conn = cp.getConnection()) {
            Address newAddress = Address.builder()
                    .customer_id("customer001")
                    .type("home")
                    .name("John Doe")
                    .phone("123-456-7890")
                    .address1("123 Main St")
                    .address2("Apt 4B")
                    .zip_code("12345")
                    .is_default(true)
                    .build();

            Address insertedAddress = addressService.register(newAddress, conn);
            System.out.println("주소 삽입 성공: " + insertedAddress);
        } catch (Exception e) {
            System.out.println("Insert error: " + e.getMessage());
        }
    }
}
package customer.test;

import customer.service.AddressService;
import customer.dao.AddressDaoImpl;
import customer.dto.Address;
import core.ConnectionPool;

import java.sql.Connection;

public class AddressUpdate {
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
            Address updatedAddress = Address.builder()
                    .address_id(1)  // 업데이트할 주소 ID
                    .customer_id("cust01")
                    .type("work")
                    .name("John Doe")
                    .phone("987-654-3210")
                    .address1("456 Another St")
                    .address2("Suite 200")
                    .zip_code("67890")
                    .is_default(false)
                    .build();

            Address resultAddress = addressService.modify(updatedAddress, conn);
            System.out.println("주소 업데이트 성공: " + resultAddress);
        } catch (Exception e) {
            System.out.println("Update error: " + e.getMessage());
        }
    }
}

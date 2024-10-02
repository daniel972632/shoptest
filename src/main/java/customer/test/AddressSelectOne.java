package customer.test;

import customer.service.AddressService;
import customer.dao.AddressDaoImpl;
import customer.dto.Address;
import core.ConnectionPool;

import java.sql.Connection;

public class AddressSelectOne {
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

        int addressId = 1;  // 조회할 주소 ID

        try (Connection conn = cp.getConnection()) {
            Address selectedAddress = addressService.get(addressId, conn);
            if (selectedAddress != null) {
                System.out.println("주소 조회 성공: " + selectedAddress);
            } else {
                System.out.println("해당 주소를 찾을 수 없습니다: " + addressId);
            }
        } catch (Exception e) {
            System.out.println("Select error: " + e.getMessage());
        }
    }
}

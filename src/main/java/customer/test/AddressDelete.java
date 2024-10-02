package customer.test;

import customer.service.AddressService;
import customer.dao.AddressDaoImpl;
import core.ConnectionPool;

import java.sql.Connection;

public class AddressDelete {
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

        int addressId = 1;  // 삭제할 주소 ID

        try (Connection conn = cp.getConnection()) {
            boolean isDeleted = addressService.remove(addressId, conn);
            if (isDeleted) {
                System.out.println("주소 삭제 성공: " + addressId);
            } else {
                System.out.println("주소 삭제 실패: " + addressId);
            }
        } catch (Exception e) {
            System.out.println("Delete error: " + e.getMessage());
        }
    }
}

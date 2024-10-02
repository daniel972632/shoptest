package customer.test;

import customer.service.AddressService;
import customer.dao.AddressDaoImpl;
import customer.dto.Address;
import core.ConnectionPool;

import java.sql.Connection;
import java.util.List;

public class AddressSelectAll {
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
            List<Address> addressList = addressService.getAll(conn);
            if (!addressList.isEmpty()) {
                System.out.println("모든 주소 조회 성공:");
                for (Address address : addressList) {
                    System.out.println(address);
                }
            } else {
                System.out.println("주소 목록이 비어 있습니다.");
            }
        } catch (Exception e) {
            System.out.println("Select all error: " + e.getMessage());
        }
    }
}

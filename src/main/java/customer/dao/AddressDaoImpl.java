package customer.dao;

import customer.dto.Address;
import customer.sql.SQL_Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDaoImpl implements core.Dao<Integer, Address> {

    @Override
    public Address insert(Address address, Connection conn) throws SQLException {
        String query = SQL_Address.me_ad_ma; // 주소 추가 쿼리 호출
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, address.getCustomer_id());
            pstmt.setString(2, address.getName());
            pstmt.setString(3, address.getPhone());
            pstmt.setString(4, address.getAddress1());
            pstmt.setString(5, address.getAddress2());
            pstmt.setString(6, address.getZip_code());
            pstmt.setBoolean(7, address.is_default());
            pstmt.executeUpdate();
        }
        return address;
    }

    @Override
    public Address update(Address address, Connection conn) throws SQLException {
        String query = SQL_Address.me_up_ad; // 주소 업데이트 쿼리 호출
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, address.getName());
            pstmt.setString(2, address.getPhone());
            pstmt.setString(3, address.getAddress1());
            pstmt.setString(4, address.getAddress2());
            pstmt.setString(5, address.getZip_code());
            pstmt.setBoolean(6, address.is_default());
            pstmt.setInt(7, address.getAddress_id());
            pstmt.executeUpdate();
        }
        return address;
    }

    @Override
    public boolean delete(Integer addressId, Connection conn) throws SQLException {  // Integer로 수정
        String query = SQL_Address.me_li_de; // 주소 삭제 쿼리 호출
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, addressId);  // addressId는 Integer 타입
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    @Override
    public Address select(Integer addressId, Connection conn) throws SQLException {  // Integer로 수정
        Address address = null; // 초기 값을 null로 설정하여 주소가 없을 경우 처리
        String query = SQL_Address.me_or_vi; // 주소 조회 쿼리 호출

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, addressId);  // addressId는 Integer 타입
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    address = Address.builder()
                            .address_id(rs.getInt("address_id"))        // address_id는 int 타입
                            .customer_id(rs.getString("customer_id"))
                            .type(rs.getString("type"))
                            .name(rs.getString("name"))
                            .phone(rs.getString("phone"))
                            .address1(rs.getString("address1"))
                            .address2(rs.getString("address2"))
                            .zip_code(rs.getString("zip_code"))
                            .is_default(rs.getBoolean("is_default"))    // boolean 타입
                            .build();  // 빌더 패턴으로 Address 객체 생성
                }
            }
        }
        return address;
    }

    @Override
    public List<Address> selectAll(Connection conn) throws SQLException {
        String query = SQL_Address.me_or_de; // 모든 주소 조회 쿼리 호출
        List<Address> addresses = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                addresses.add(Address.builder()  // 빌더 패턴으로 객체 생성
                        .address_id(rs.getInt("address_id"))       // int형 필드
                        .customer_id(rs.getString("customer_id"))
                        .type(rs.getString("type"))                // type 필드 추가
                        .name(rs.getString("name"))
                        .phone(rs.getString("phone"))
                        .address1(rs.getString("address1"))
                        .address2(rs.getString("address2"))
                        .zip_code(rs.getString("zip_code"))
                        .is_default(rs.getBoolean("is_default"))   // boolean 필드
                        .build()
                );
            }
        }
        return addresses;
    }
}

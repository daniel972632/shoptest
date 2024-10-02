package customer.service;

import customer.dao.AddressDaoImpl;
import customer.dto.Address;
import core.ConnectionPool;
import core.MService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AddressService implements MService<Integer, Address> {

    private final AddressDaoImpl addressDaoImpl;
    private final ConnectionPool cp;

    // 서비스 생성자에서 ConnectionPool과 DAO 객체 초기화
    public AddressService(AddressDaoImpl addressDaoImpl) throws SQLException {
        this.addressDaoImpl = addressDaoImpl;
        this.cp = ConnectionPool.create();  // 커넥션 풀 초기화
    }

    // 트랜잭션을 위한 메서드 예시
    private void executeTransaction(TransactionBlock block) throws SQLException {
        Connection conn = cp.getConnection();
        try {
            conn.setAutoCommit(false);  // 트랜잭션 시작
            block.execute(conn);  // 트랜잭션 로직 실행
            conn.commit();  // 성공하면 커밋
        } catch (Exception e) {
            conn.rollback();  // 예외 발생 시 롤백
            throw new SQLException("Transaction failed: " + e.getMessage(), e);
        } finally {
            cp.releaseConnection(conn);  // 커넥션 반환
        }
    }

    // Connection을 인자로 받는 register 구현
    @Override
    public Address register(Address address, Connection conn) throws Exception {
        addressDaoImpl.insert(address, conn);
        System.out.println("주소 등록 성공: " + address.getAddress_id());
        return address;
    }

    // Connection을 인자로 받는 modify 구현
    @Override
    public Address modify(Address address, Connection conn) throws Exception {
        addressDaoImpl.update(address, conn);
        System.out.println("주소 수정 성공: " + address.getAddress_id());
        return address;
    }

    // Connection을 인자로 받는 remove 구현
    @Override
    public boolean remove(Integer addressId, Connection conn) throws Exception {
        boolean result = addressDaoImpl.delete(addressId, conn);
        if (result) {
            System.out.println("주소 삭제 성공: " + addressId);
        } else {
            System.out.println("주소 삭제 실패: " + addressId);
        }
        return result;
    }

    // Connection을 인자로 받는 get 구현
    @Override
    public Address get(Integer addressId, Connection conn) throws Exception {
        return addressDaoImpl.select(addressId, conn);
    }

    // Connection을 인자로 받는 getAll 구현
    @Override
    public List<Address> getAll(Connection conn) throws Exception {
        return addressDaoImpl.selectAll(conn);
    }

    // 트랜잭션 블록을 위한 인터페이스
    @FunctionalInterface
    private interface TransactionBlock {
        void execute(Connection conn) throws Exception;
    }
}

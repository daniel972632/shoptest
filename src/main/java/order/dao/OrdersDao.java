package order.dao;

import core.Dao;
import order.dto.Orders;
import order.sql.SQL_Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrdersDao implements Dao<Integer, Orders> {
    @Override
    public Orders insert(Orders entity, Connection conn) throws Exception {
        String sql = SQL_Orders.me_or_cr; // 주문 생성 쿼리
        try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, entity.getCustomer_id());
            pstmt.setString(2, entity.getName());
            pstmt.setString(3, entity.getPhone());
            pstmt.setString(4, entity.getAddress1());
            pstmt.setString(5, entity.getAddress2());
            pstmt.setString(6, entity.getZip_code());
            pstmt.setInt(7, entity.getTotal_price());
            pstmt.setString(8, entity.getStatus());
            pstmt.setTimestamp(9, new java.sql.Timestamp(entity.getCreated_at().getTime()));
            pstmt.executeUpdate();

            // 생성된 주문 ID 가져오기
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                entity.setOrder_id(generatedKeys.getInt(1));
            }
            return entity;
        }
    }

    @Override
    public Orders update(Orders entity, Connection conn) throws Exception {
        String sql = SQL_Orders.me_or_up; // 주문 상태 업데이트 쿼리
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getStatus());
            pstmt.setInt(2, entity.getOrder_id());
            pstmt.executeUpdate();
            return entity;
        }
    }

    @Override
    public boolean delete(Integer key, Connection conn) throws Exception {
        String sql = "DELETE FROM orders WHERE order_id = ?;"; // 주문 삭제 쿼리
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, key);
            return pstmt.executeUpdate() > 0; // 삭제된 행 수가 0보다 크면 성공
        }
    }

    @Override
    public Orders select(Integer key, Connection conn) throws Exception {
        String sql = SQL_Orders.me_or_vi; // 주문 상세 조회 쿼리
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, key);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapToOrder(rs);
            }
            return null; // 주문이 없을 경우 null 반환
        }
    }

    @Override
    public List<Orders> selectAll(Connection conn) throws Exception {
        String sql = SQL_Orders.me_or_li; // 주문 목록 조회 쿼리
        List<Orders> ordersList = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "some_customer_id"); // 특정 고객 ID를 사용할 수 있도록 설정 필요
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ordersList.add(mapToOrder(rs));
            }
        }
        return ordersList;
    }

    private Orders mapToOrder(ResultSet rs) throws Exception {
        Orders order = new Orders();
        order.setOrder_id(rs.getInt("order_id"));
        order.setCustomer_id(rs.getString("customer_id"));
        order.setName(rs.getString("name"));
        order.setPhone(rs.getString("phone"));
        order.setAddress1(rs.getString("address1"));
        order.setAddress2(rs.getString("address2"));
        order.setZip_code(rs.getString("zip_code"));
        order.setTotal_price(rs.getInt("total_price"));
        order.setStatus(rs.getString("status"));
        order.setCreated_at(rs.getDate("created_at")); // 수정된 부분: Date로 가져오기
        return order;
    }

}

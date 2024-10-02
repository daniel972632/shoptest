package order.dao;

import core.Dao;
import order.dto.OrderDetail;
import order.sql.SQL_Order_detail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDao implements Dao<Integer, OrderDetail> {

    @Override
    public OrderDetail insert(OrderDetail entity, Connection conn) throws Exception {
        String sql = SQL_Order_detail.me_or_de_ma; // 주문 상세 추가 쿼리
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, entity.getOrder_id());
            pstmt.setInt(2, entity.getProduct_id());
            pstmt.setInt(3, entity.getQuantity());
            pstmt.setInt(4, entity.getProduct_price()); // 변경된 부분
            pstmt.setInt(5, entity.getTotal_discount_price()); // 추가된 부분
            pstmt.executeUpdate();
        }
        return entity; // 추가한 주문 상세를 반환
    }

    @Override
    public OrderDetail update(OrderDetail entity, Connection conn) throws Exception {
        String sql = SQL_Order_detail.me_or_de_up; // 주문 상세 업데이트 쿼리
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, entity.getQuantity());
            pstmt.setInt(2, entity.getProduct_price()); // 변경된 부분
            pstmt.setInt(3, entity.getTotal_discount_price()); // 추가된 부분
            pstmt.setInt(4, entity.getOrder_detail_id());
            pstmt.executeUpdate();
        }
        return entity; // 업데이트된 주문 상세를 반환
    }

    @Override
    public boolean delete(Integer key, Connection conn) throws Exception {
        String sql = SQL_Order_detail.me_or_de_de; // 주문 상세 삭제 쿼리
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, key);
            return pstmt.executeUpdate() > 0; // 성공적으로 삭제된 경우 true 반환
        }
    }

    @Override
    public OrderDetail select(Integer key, Connection conn) throws Exception {
        String sql = SQL_Order_detail.me_or_de_vi; // 주문 상세 조회 쿼리
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, key);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return OrderDetail.builder()
                        .order_detail_id(rs.getInt("order_detail_id"))
                        .order_id(rs.getInt("order_id"))
                        .product_id(rs.getInt("product_id"))
                        .quantity(rs.getInt("quantity"))
                        .product_price(rs.getInt("product_price")) // 변경된 부분
                        .total_discount_price(rs.getInt("total_discount_price")) // 추가된 부분
                        .build();
            }
        }
        return null; // 해당 주문 상세가 없으면 null 반환
    }

    @Override
    public List<OrderDetail> selectAll(Connection conn) throws Exception {
        String sql = SQL_Order_detail.me_or_de_all; // 모든 주문 상세 조회 쿼리
        List<OrderDetail> orderDetails = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                orderDetails.add(OrderDetail.builder()
                        .order_detail_id(rs.getInt("order_detail_id"))
                        .order_id(rs.getInt("order_id"))
                        .product_id(rs.getInt("product_id"))
                        .quantity(rs.getInt("quantity"))
                        .product_price(rs.getInt("product_price")) // 변경된 부분
                        .total_discount_price(rs.getInt("total_discount_price")) // 추가된 부분
                        .build());
            }
        }
        return orderDetails; // 모든 주문 상세 목록 반환
    }
}

package order.sql;

public class SQL_Order_detail {
    // SQL 쿼리 상수 정의
    public static final String me_or_de_ma = "INSERT INTO order_detail (order_id, product_id, quantity, product_price, total_discount_price) VALUES (?, ?, ?, ?, ?);";
    public static final String me_or_de_up = "UPDATE order_detail SET quantity = ?, product_price = ?, total_discount_price = ? WHERE order_detail_id = ?;";
    public static final String me_or_de_de = "DELETE FROM order_detail WHERE order_detail_id = ?;";
    public static final String me_or_de_vi = "SELECT * FROM order_detail WHERE order_detail_id = ?;";
    public static final String me_or_de_all = "SELECT * FROM order_detail;"; // 모든 주문 상세 조회
}

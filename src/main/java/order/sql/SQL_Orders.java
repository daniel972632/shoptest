package order.sql;

public class SQL_Orders {

    // SQL 쿼리 상수 정의
    public static final String me_or_cr = "INSERT INTO orders (customer_id, name, phone, address1, address2, zip_code, total_price, status, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);"; // 주문 생성
    public static final String me_or_li = "SELECT * FROM orders WHERE customer_id = ? ORDER BY created_at DESC;"; // 주문 목록 조회
    public static final String me_or_up = "UPDATE orders SET status = ? WHERE order_id = ?;"; // 주문 상태 업데이트
    public static final String me_or_vi = "SELECT * FROM orders WHERE order_id = ?;"; // 주문 상세 조회
}

package product.sql;

public class SQL_Product {
    // SQL 쿼리 상수 정의

    // 제품 삽입 쿼리 (product_id는 자동 증가가 아닌 경우 별도로 처리해야 함)
    public static final String me_ad_ma = "INSERT INTO product (category_id, name, description, price, stock_quantity, is_selling, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

    // 제품 삭제 쿼리
    public static final String me_li_de = "DELETE FROM product WHERE product_id = ?;";

    // 특정 제품 조회 쿼리
    public static final String me_or_vi = "SELECT * FROM product WHERE product_id = ?;";

    // 모든 제품 조회 쿼리
    public static final String me_or_de = "SELECT * FROM product;";

    // 제품 업데이트 쿼리
    public static final String me_up_ad = "UPDATE product SET category_id = ?, name = ?, description = ?, price = ?, stock_quantity = ?, is_selling = ?, updated_at = ? WHERE product_id = ?;";
}

package customer.sql;

public class SQL_Customer {

    public static final String me_in_ed = "INSERT INTO customer (customer_id, name, email, phone, password, level, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, NOW(), NOW())";
    public static final String me_in_pw = "UPDATE customer SET password = ? WHERE customer_id = ?";
    public static final String me_or_vi = "SELECT * FROM customer WHERE customer_id = ?";
    public static final String me_ad_ma = "DELETE FROM customer WHERE customer_id = ?";
    public static final String me_or_de = "SELECT * FROM customer LIMIT 50";

    // 추가적인 쿼리들도 여기에 저장
}

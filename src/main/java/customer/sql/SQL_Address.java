package customer.sql;

public class SQL_Address {

    // SQL 쿼리 상수 정의
    public static final String me_ad_ma = "INSERT INTO address (customer_id, name, phone, address1, address2, zip_code, is_default) VALUES (?, ?, ?, ?, ?, ?, ?);";
    public static final String me_li_de = "DELETE FROM address WHERE address_id = ?;";
    public static final String me_or_vi = "SELECT * FROM address WHERE address_id = ?;";
    public static final String me_or_de = "SELECT * FROM address;"; // 모든 주소 조회
    public static final String me_up_ad = "UPDATE address SET name = ?, phone = ?, address1 = ?, address2 = ?, zip_code = ?, is_default = ? WHERE address_id = ?;";
}


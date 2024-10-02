package order.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Orders {
    private int order_id;        // 주문 ID
    private String customer_id;  // 고객 ID
    private String name;         // 고객 이름
    private String phone;        // 고객 전화번호
    private String address1;     // 주소1
    private String address2;     // 주소2
    private String zip_code;     // 우편번호
    private int total_price;     // 총 가격
    private String status;       // 주문 상태
    private Date created_at;     // 주문 생성일

}
//Table: orders
//Columns:
//order_id int AI PK
//customer_id varchar(20)
//name varchar(20)
//phone varchar(14)
//address1 varchar(255)
//address2 varchar(255)
//zip_code varchar(6)
//total_price int
//status varchar(10)
//created_at datetime
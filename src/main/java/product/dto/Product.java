package product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private int product_id;        // 제품 ID (Primary Key)
    private int category_id;       // 카테고리 ID
    private String name;           // 제품명
    private String description;     // 제품 설명
    private int price;             // 가격
    private int stock_quantity;    // 재고 수량
    private boolean is_selling;     // 판매 여부 (tinyint(1)으로 저장)
    private Date created_at;       // 생성일 (datetime)
    private Date updated_at;       // 수정일 (datetime)
}

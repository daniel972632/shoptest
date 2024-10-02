package order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetail {
    private int order_detail_id;     // 주문 상세 ID
    private int order_id;            // 주문 ID
    private int product_id;          // 상품 ID
    private int quantity;            // 수량
    private int product_price;       // 상품 가격
    private int total_discount_price; // 총 할인 가격
}

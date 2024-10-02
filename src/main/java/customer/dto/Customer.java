package customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    private String customerId;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String level;  // 'bronze', 'silver', 'gold' 중 하나
    private Timestamp createdAt;
    private Timestamp updatedAt;
}

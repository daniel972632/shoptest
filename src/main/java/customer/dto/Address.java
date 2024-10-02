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
public class Address {
    private int address_id;
    private String customer_id;
    private String type;
    private String name;
    private String phone;
    private String address1;
    private String address2;
    private String zip_code;
    private boolean is_default;
}

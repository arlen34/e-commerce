package kg.dev_abe.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItemResponse {
    private Long orderItemId;
    private int quantity;
    private ProductResponse productResponse;
    private double totalPrice;
}

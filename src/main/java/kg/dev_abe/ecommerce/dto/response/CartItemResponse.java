package kg.dev_abe.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
    private long cartItemId;
    private int quantity;
    private ProductResponse productResponse;
    private double totalPrice;

}

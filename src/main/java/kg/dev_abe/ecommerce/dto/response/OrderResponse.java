package kg.dev_abe.ecommerce.dto.response;

import kg.dev_abe.ecommerce.models.enums.OrderStatus;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderResponse {
    private Long id;
    private LocalDate orderDate;
    private OrderStatus orderStatus;
    private double totalPrice;
    private UserResponse customer;
}

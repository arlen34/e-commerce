package kg.dev_abe.ecommerce.dto.response;


import kg.dev_abe.ecommerce.models.enums.OrderStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetailsResponse {
    private Long id;
    private LocalDate orderDate;
    private OrderStatus orderStatus;
    private double totalPrice;
    private List<OrderItemResponse> orderItems;
    private UserResponse customer;
}

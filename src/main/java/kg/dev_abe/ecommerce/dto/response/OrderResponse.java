package kg.dev_abe.ecommerce.dto.response;


import kg.dev_abe.ecommerce.models.OrderItem;
import kg.dev_abe.ecommerce.models.enums.OrderStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderResponse {
    private Long orderId;
    private LocalDate orderDate;
    private OrderStatus orderStatus;
    private double totalPrice;
    private List<OrderItemResponse> orderItems;
}

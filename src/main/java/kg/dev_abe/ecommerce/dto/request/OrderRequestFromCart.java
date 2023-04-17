package kg.dev_abe.ecommerce.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestFromCart {
    private List<Long> cartItemIds;
}

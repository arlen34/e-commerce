package kg.dev_abe.ecommerce.dto.request;

import kg.dev_abe.ecommerce.dto.response.CartItemResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private List<Long> cartItemIds;
}

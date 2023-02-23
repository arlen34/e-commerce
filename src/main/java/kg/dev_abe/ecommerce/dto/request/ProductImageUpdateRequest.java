package kg.dev_abe.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductImageUpdateRequest {
    private Long productId;
    private String imageUrl;
}

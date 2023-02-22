package kg.dev_abe.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProductCreateRequest {
    private Long categoryId;
    private List<ProductImageRequest> imageRequests;
    private String productName;
    private String description;
    private Double price;
    private Integer amount;
}

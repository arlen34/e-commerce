package kg.dev_abe.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductCreateRequest {
    private long categoryId;
    private String productName;
    private String description;
    private double price;
    private int amount;
}

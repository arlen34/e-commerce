package kg.dev_abe.ecommerce.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class ProductResponses {
    private Long id;
    private String productName;
    private String description;
    private Double price;
    private String category;
    private Integer reviewsCount;


}

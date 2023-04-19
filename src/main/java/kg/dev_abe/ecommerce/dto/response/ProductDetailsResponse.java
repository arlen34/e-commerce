package kg.dev_abe.ecommerce.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class ProductDetailsResponse {
    private Long id;
    private String productName;
    private String description;
    private Double price;
    private Integer amount;
    private String category;
    private List<ReviewResponse> reviews;
    private List<ImageDto> imageList;

}

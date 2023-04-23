package kg.dev_abe.ecommerce.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class ProductDetailsResponse {
    private long id;
    private int price;
    private int amount;
    private int sold;

    private String category;
    private String productName;
    private String description;

    private LocalDate receiptDate;

    private List<ImageDto> imageList;
    private List<ReviewResponse> reviews;

}

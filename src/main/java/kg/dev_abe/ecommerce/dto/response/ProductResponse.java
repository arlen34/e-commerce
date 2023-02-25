package kg.dev_abe.ecommerce.dto.response;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import kg.dev_abe.ecommerce.models.Category;
import kg.dev_abe.ecommerce.models.ProductImage;
import kg.dev_abe.ecommerce.models.Review;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class ProductResponse {
    private Long id;
    private String productName;
    private String description;
    private Double price;
    private Integer amount;
    private String category;
    private List<ReviewResponse> reviews;
    private List<byte[]> imageList;

}

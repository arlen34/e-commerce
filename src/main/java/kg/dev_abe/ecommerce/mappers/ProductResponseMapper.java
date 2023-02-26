package kg.dev_abe.ecommerce.mappers;

import kg.dev_abe.ecommerce.dto.response.ProductImageDto;
import kg.dev_abe.ecommerce.dto.response.ProductResponse;
import kg.dev_abe.ecommerce.models.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProductResponseMapper implements Function<Product,ProductResponse> {

    private final ReviewMapper reviewMapper;
    @Override
    public ProductResponse apply(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .category(product.getCategory().getCategoryName())
                .price(product.getPrice())
                .amount(product.getAmount())
                .reviews(product.getReviews()
                        .stream()
                        .map(reviewMapper)
                        .collect(Collectors.toList())
                )
                .description(product.getDescription())
                .imageList(product.getImageList()
                        .stream()
                        .map(productImage -> ProductImageDto.builder()
                                .id(productImage.getId())
                                .data(productImage.getImageData())
                                .fileType(productImage.getFileType())
                                .build()
                        )
                        .collect(Collectors.toList())
                )
                .build();
    }

}

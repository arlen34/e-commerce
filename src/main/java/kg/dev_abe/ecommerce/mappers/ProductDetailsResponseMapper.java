package kg.dev_abe.ecommerce.mappers;

import kg.dev_abe.ecommerce.dto.response.ProductDetailsResponse;
import kg.dev_abe.ecommerce.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ReviewMapper.class})
public interface ProductDetailsResponseMapper {
    @Mapping(target = "category", source = "category.categoryName")
    ProductDetailsResponse toProductResponse(Product product);


}


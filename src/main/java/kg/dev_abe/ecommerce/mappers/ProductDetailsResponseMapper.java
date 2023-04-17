package kg.dev_abe.ecommerce.mappers;

import kg.dev_abe.ecommerce.dto.response.ProductDetailsResponse;
import kg.dev_abe.ecommerce.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { ReviewMapper.class})
public interface ProductDetailsResponseMapper {
    ProductDetailsResponseMapper INSTANCE = Mappers.getMapper(ProductDetailsResponseMapper.class);
    @Mapping(target = "category", source = "category.categoryName")
    ProductDetailsResponse toProductResponse(Product product);


}


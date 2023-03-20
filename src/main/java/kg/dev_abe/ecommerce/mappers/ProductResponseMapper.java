package kg.dev_abe.ecommerce.mappers;

import kg.dev_abe.ecommerce.dto.response.ProductResponse;
import kg.dev_abe.ecommerce.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", uses = {ProductImageMapper.class, ReviewMapper.class})
public interface ProductResponseMapper {

    @Mapping(target = "reviews", source = "reviews")
    @Mapping(target = "imageList", source = "imageList")
    @Mapping(target = "category",source = "category.categoryName")
    ProductResponse toProductResponse(Product product);


}


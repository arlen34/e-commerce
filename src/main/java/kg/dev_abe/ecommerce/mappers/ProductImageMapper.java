package kg.dev_abe.ecommerce.mappers;

import kg.dev_abe.ecommerce.dto.response.ProductImageDto;
import kg.dev_abe.ecommerce.models.ProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {

    @Mapping(source = "imageData", target = "data")
    ProductImageDto toProductImageDto(ProductImage productImage);
}

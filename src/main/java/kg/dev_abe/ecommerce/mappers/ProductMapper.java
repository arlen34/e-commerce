package kg.dev_abe.ecommerce.mappers;

import kg.dev_abe.ecommerce.dto.response.ImageDto;
import kg.dev_abe.ecommerce.dto.response.ProductDetailsResponse;
import kg.dev_abe.ecommerce.dto.response.ProductResponse;
import kg.dev_abe.ecommerce.models.Image;
import kg.dev_abe.ecommerce.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { ReviewMapper.class, ImageMapper.class})
public interface ProductMapper {
    @Mapping(target = "category", source = "category.categoryName")
    ProductDetailsResponse toProductDetailsResponse(Product product);

    @Mapping(target = "imageDto", source = "imageList")
    ProductResponse toProductResponse(Product product);

    default ImageDto toImageDto(List<Image> imageList) {
        return imageList != null && !imageList.isEmpty() ? ImageMapper.INSTANCE.toImageDto(imageList.get(0)) : null;
    }

}


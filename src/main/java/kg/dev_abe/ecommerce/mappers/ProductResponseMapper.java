package kg.dev_abe.ecommerce.mappers;

import kg.dev_abe.ecommerce.dto.response.ImageDto;
import kg.dev_abe.ecommerce.dto.response.ProductResponse;
import kg.dev_abe.ecommerce.models.Image;
import kg.dev_abe.ecommerce.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ImageMapper.class})
public interface ProductResponseMapper {
    @Mapping(target = "imageDto", source = "imageList")
    ProductResponse getProductsFromCart(Product product);

    default ImageDto toImageDto(List<Image> imageList) {
        return imageList != null && !imageList.isEmpty() ? ImageMapper.INSTANCE.toImageDto(imageList.get(0)) : null;
    }
}

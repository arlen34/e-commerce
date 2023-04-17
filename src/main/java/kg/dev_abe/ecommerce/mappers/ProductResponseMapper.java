package kg.dev_abe.ecommerce.mappers;

import kg.dev_abe.ecommerce.dto.response.ProductImageDto;
import kg.dev_abe.ecommerce.dto.response.ProductResponse;
import kg.dev_abe.ecommerce.models.Product;
import kg.dev_abe.ecommerce.models.ProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductImageMapper.class})
public interface ProductResponseMapper {
    @Mapping(target = "productImageDto", source = "imageList")
    ProductResponse getProductsFromCart(Product product);

    default ProductImageDto toProductImageDto(List<ProductImage> imageList) {
        return imageList != null && !imageList.isEmpty() ? ProductImageMapper.INSTANCE.toProductImageDto(imageList.get(0)) : null;
    }
}

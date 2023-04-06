package kg.dev_abe.ecommerce.mappers;

import kg.dev_abe.ecommerce.dto.response.ProductImageDto;
import kg.dev_abe.ecommerce.dto.response.ProductResponse;
import kg.dev_abe.ecommerce.dto.response.ProductResponses;
import kg.dev_abe.ecommerce.models.CartItem;
import kg.dev_abe.ecommerce.models.Product;
import kg.dev_abe.ecommerce.models.ProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductImageMapper.class, ReviewMapper.class})
public interface ProductResponseMapper {
    @Mapping(target = "category", source = "category.categoryName")
    ProductResponse toProductResponse(Product product);
    @Mapping(target = "category", source = "product.category.categoryName")
    @Mapping(target = "id", source = "product.id")
    @Mapping(target = "productName", source = "product.productName")
    @Mapping(target = "description", source = "product.description")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "reviewsCount", expression = "java(cartItem.getProduct().getReviews().size())")
    @Mapping(target = "productImageDto", source = "product.imageList")
    ProductResponses getProductsFromCart(CartItem cartItem);

    default ProductImageDto toProductImageDto(List<ProductImage> imageList) {
        if (imageList != null && !imageList.isEmpty()) {
            return ProductImageMapper.INSTANCE.toProductImageDto(imageList.get(0));
        }
        return null;
    }

}


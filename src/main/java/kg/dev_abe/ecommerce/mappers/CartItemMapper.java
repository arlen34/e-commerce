package kg.dev_abe.ecommerce.mappers;

import kg.dev_abe.ecommerce.dto.response.CartItemResponse;
import kg.dev_abe.ecommerce.models.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = { ProductResponseMapper.class})
public interface CartItemMapper {
    @Mapping(target = "totalPrice", expression = "java(cartItem.evalTotalPrice())")
    @Mapping(target = "cartItemId", source = "id")
    @Mapping(target = "productResponse", source = "product")
    CartItemResponse toCartItemResponse(CartItem cartItem);


}

package kg.dev_abe.ecommerce.mappers;

import kg.dev_abe.ecommerce.dto.response.OrderItemResponse;
import kg.dev_abe.ecommerce.models.CartItem;
import kg.dev_abe.ecommerce.models.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {CartItemMapper.class, ProductResponseMapper.class})
public interface OrderItemMapper {
    @Mapping(target = "totalPrice", expression = "java(cartItem.evalTotalPrice())")
    @Mapping(target = "orderItemId", ignore = true)
    OrderItem toOrderItem(CartItem cartItem);

    @Mapping(target = "productResponse", source = "product")
    OrderItemResponse toOrderItemResponse(OrderItem orderItem);
}

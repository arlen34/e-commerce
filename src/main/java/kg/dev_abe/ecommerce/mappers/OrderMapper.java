package kg.dev_abe.ecommerce.mappers;


import kg.dev_abe.ecommerce.dto.response.OrderResponse;
import kg.dev_abe.ecommerce.models.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {OrderItemMapper.class, CartItemMapper.class, ProductResponseMapper.class})
public interface OrderMapper {

    @Mapping(target = "orderId", source = "orderId")
    OrderResponse toOrderResponse(Order order);
}

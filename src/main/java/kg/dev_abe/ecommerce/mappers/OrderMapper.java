package kg.dev_abe.ecommerce.mappers;


import kg.dev_abe.ecommerce.dto.response.OrderDetailsResponse;
import kg.dev_abe.ecommerce.dto.response.OrderResponse;
import kg.dev_abe.ecommerce.models.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {OrderItemMapper.class, CartItemMapper.class, ProductMapper.class, UserMapper.class})
public interface OrderMapper {

    @Mapping(target = "customer",source = "user")
    OrderDetailsResponse toOrderDetailsResponse(Order order);

    @Mapping(target = "customer",source = "user")
    OrderResponse toOrderResponse(Order order);
}

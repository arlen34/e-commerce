package kg.dev_abe.ecommerce.mappers;

import kg.dev_abe.ecommerce.dto.response.UserResponse;
import kg.dev_abe.ecommerce.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "fullName",expression = "java(user.getSurname() + \" \" + user.getName())")
    UserResponse toUserResponse(User user);
}

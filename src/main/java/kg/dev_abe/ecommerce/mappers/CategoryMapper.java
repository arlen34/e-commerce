package kg.dev_abe.ecommerce.mappers;

import kg.dev_abe.ecommerce.dto.response.CategoryResponse;
import kg.dev_abe.ecommerce.models.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {ImageMapper.class})
public interface CategoryMapper {
    CategoryResponse toCategoryResponse(Category category);
}

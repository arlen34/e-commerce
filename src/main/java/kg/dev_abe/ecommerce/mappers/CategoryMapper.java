package kg.dev_abe.ecommerce.mappers;

import kg.dev_abe.ecommerce.dto.response.AllCategoriesResponses;
import kg.dev_abe.ecommerce.dto.response.CategoryResponse;
import kg.dev_abe.ecommerce.models.Category;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring",uses = {ImageMapper.class})
public interface CategoryMapper {
    CategoryResponse toCategoryResponse(Category category);

    default List<AllCategoriesResponses> mapToAllCategoriesResponses(List<Category> categories) {
        Map<Long, AllCategoriesResponses> map = new HashMap<>();
        List<AllCategoriesResponses> rootNodes = new ArrayList<>();

        for (Category category : categories) {
            AllCategoriesResponses node = map.get(category.getId());

            if (node == null) {
                node = new AllCategoriesResponses();
                node.setId(category.getId());
                node.setCategoryName(category.getCategoryName());
                node.setSubCategoryResponses(new ArrayList<>());
                map.put(category.getId(), node);
            }

            if (category.getParentCategory() == null) {
                rootNodes.add(node);
            } else {
                AllCategoriesResponses parentNode = map.get(category.getParentCategory().getId());
                if (parentNode != null) {
                    parentNode.getSubCategoryResponses().add(node);
                }
            }
        }

        return rootNodes;
    }
}

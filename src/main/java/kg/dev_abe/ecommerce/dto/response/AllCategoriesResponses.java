package kg.dev_abe.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AllCategoriesResponses {
    private Long id;
    private String categoryName;
    private List<AllCategoriesResponses> subCategoryResponses;
}

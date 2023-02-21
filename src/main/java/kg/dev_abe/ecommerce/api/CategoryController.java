package kg.dev_abe.ecommerce.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.dev_abe.ecommerce.dto.response.CategoryResponse;
import kg.dev_abe.ecommerce.models.Category;
import kg.dev_abe.ecommerce.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Category API",description = "The categories API for all")
public class CategoryController {
    private final CategoryService categoryService;
    @Operation(summary = "Get all categories",
            description = "This endpoint returns all categories")
    @GetMapping
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @Operation(summary = "Get categories by parent category id",
            description = "This endpoint returns the categories  by parent category id")
    @GetMapping("/{categoryId}")
    public List<CategoryResponse> getSubCategories(@PathVariable Long categoryId) {
        return categoryService.getSubCategoriesByParentCatId(categoryId);
    }


//    @PostMapping("/add")
//    public void addCategory(@RequestBody Category category) {
//        categoryService.addCategory(category);
//    }
//    @PutMapping("/update/{id}")
//    public void updateCategory(@PathVariable Long id, @RequestBody Category category) {
//        categoryService.updateCategory(id, category);
//    }
//    @DeleteMapping("/delete/{id}")
//    public void deleteCategory(@PathVariable Long id) {
//        categoryService.deleteCategory(id);
//    }



}

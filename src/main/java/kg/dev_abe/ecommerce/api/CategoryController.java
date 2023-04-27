package kg.dev_abe.ecommerce.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.dev_abe.ecommerce.dto.request.CategoryUpdateRequest;
import kg.dev_abe.ecommerce.dto.response.CategoryResponse;
import kg.dev_abe.ecommerce.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        return categoryService.getCategories(null);
    }

    @Operation(summary = "Get categories by parent category id",
            description = "This endpoint returns the categories  by parent category id")
    @GetMapping("/{categoryId}")
    public List<CategoryResponse> getSubCategories(@PathVariable Long categoryId) {
        return categoryService.getSubCategories(categoryId);
    }

    @Operation(summary = "Create parent categories or sub categories",
            description = "This endpoint returns the created categories")
    @PostMapping(value = "/create")
    public void addCategory(
            @RequestParam("parentCategoryId") Long parentCategoryId,
            @RequestParam("categoryName") String categoryName,
            @RequestParam(value = "file",required = false) MultipartFile file) {
        categoryService.create(parentCategoryId,categoryName,file);
    }

    @Operation(summary = "Update categories",
            description = "This endpoint returns the updated categories")
    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER_ADMIN')")
    @PatchMapping
    public List<CategoryResponse> updateCategory(@RequestBody CategoryUpdateRequest request) {
        return categoryService.update(request);
    }

    @Operation(summary = "Delete categories",
            description = "This endpoint returns the delete category")
    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public List<CategoryResponse> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }

}

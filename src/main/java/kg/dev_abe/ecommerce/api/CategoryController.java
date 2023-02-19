package kg.dev_abe.ecommerce.api;

import kg.dev_abe.ecommerce.models.Category;
import kg.dev_abe.ecommerce.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping("/all")
    public List<Category> getAllCategories() {
        return categoryService.findAllCategories();
    }
    @PostMapping("/add")
    public void addCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
    }
    @PutMapping("/update/{id}")
    public void updateCategory(@PathVariable Long id, @RequestBody Category category) {
        categoryService.updateCategory(id, category);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }



}

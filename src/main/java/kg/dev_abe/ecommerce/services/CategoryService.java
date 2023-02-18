package kg.dev_abe.ecommerce.services;

import kg.dev_abe.ecommerce.db.models.Category;
import kg.dev_abe.ecommerce.db.models.Product;
import kg.dev_abe.ecommerce.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void addCategory(Category category) {
        categoryRepository.save(category);
    }
    public void updateCategory(Long id,Category category) {
        category.setId(id);
        categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Product> findCategoryProducts(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        return category.getProducts();
    }
}

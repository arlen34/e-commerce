package kg.dev_abe.ecommerce.services;

import kg.dev_abe.ecommerce.dto.response.CategoryResponse;
import kg.dev_abe.ecommerce.models.Category;
import kg.dev_abe.ecommerce.models.Product;
import kg.dev_abe.ecommerce.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> getAllCategories() {
//        return categoryRepository.findAll().stream().map(c -> new CategoryResponse(c.getId(),c.getCategoryName())).collect(Collectors.toList());
        return categoryRepository.findAllByParentCategoryIsNull();
    }

    public List<CategoryResponse> getSubCategoriesByParentCatId(Long categoryId){
        return categoryRepository.getCategoriesByParentCategoryId(categoryId);
    }
//
//    public void addCategory(Category category) {
//        categoryRepository.save(category);
//    }
//    public void updateCategory(Long id,Category category) {
//        category.setId(id);
//        categoryRepository.save(category);
//    }
//
//    public void deleteCategory(Long id) {
//        categoryRepository.deleteById(id);
//    }
//    public List<Category> findAllCategories() {
//        return categoryRepository.findAll();
//    }
//
//    public List<Product> findCategoryProducts(Long id) {
//        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
//        return category.getProducts();
//    }
}

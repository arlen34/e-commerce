package kg.dev_abe.ecommerce.services;

import jakarta.transaction.Transactional;
import kg.dev_abe.ecommerce.dto.request.CategoryRequest;
import kg.dev_abe.ecommerce.dto.request.CategoryUpdateRequest;
import kg.dev_abe.ecommerce.dto.response.CategoryResponse;
import kg.dev_abe.ecommerce.dto.response.SimpleResponse;
import kg.dev_abe.ecommerce.models.CartItem;
import kg.dev_abe.ecommerce.models.Category;
import kg.dev_abe.ecommerce.repositories.CartItemRepository;
import kg.dev_abe.ecommerce.repositories.CategoryRepository;
import kg.dev_abe.ecommerce.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CartItemRepository cartItemRepository;

    public List<CategoryResponse> getAllCategories() {
//        return categoryRepository.findAll().stream().map(c -> new CategoryResponse(c.getId(),c.getCategoryName())).collect(Collectors.toList());
        return categoryRepository.findAllByParentCategoryIsNull();
    }

    public List<CategoryResponse> getSubCategoriesByParentCatId(Long categoryId) {
        return categoryRepository.getCategoriesByParentCategoryId(categoryId);
    }

    public List<CategoryResponse> create(CategoryRequest request) {
        Category parentCategory = categoryRepository.findById(request.getParentCategoryId()).orElse(null);
        categoryRepository.save(Category.builder()
                .categoryName(request.getCategoryName())
                .parentCategory(parentCategory)
                .build());
        if (request.getParentCategoryId() == 0) {
            return getAllCategories();
        } else return getSubCategoriesByParentCatId(request.getParentCategoryId());
    }

    @Transactional
    public List<CategoryResponse> update(CategoryUpdateRequest request) {
        Category category = categoryRepository.findById(request.getId()).orElseThrow(() -> new NotFoundException("Not found"));
        category.setCategoryName(request.getCategoryName());
        if (category.getParentCategory() == null) {
            return getAllCategories();
        } else return getSubCategoriesByParentCatId(category.getParentCategory().getId());
    }

    public List<CategoryResponse> deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found"));

        categoryRepository.delete(category);
        if (category.getParentCategory() == null) {
            return getAllCategories();
        } else return getSubCategoriesByParentCatId(category.getParentCategory().getId());
    }

}

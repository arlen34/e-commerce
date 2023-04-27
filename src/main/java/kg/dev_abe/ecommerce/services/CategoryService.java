package kg.dev_abe.ecommerce.services;

import jakarta.transaction.Transactional;
import kg.dev_abe.ecommerce.dto.request.CategoryUpdateRequest;
import kg.dev_abe.ecommerce.dto.response.CategoryResponse;
import kg.dev_abe.ecommerce.mappers.CategoryMapper;
import kg.dev_abe.ecommerce.models.Category;
import kg.dev_abe.ecommerce.models.Image;
import kg.dev_abe.ecommerce.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductService productService;
    private CategoryMapper categoryMapper;




    @Transactional
    public List<CategoryResponse> getCategories(Category parenCategory) {
        if (parenCategory == null) {
            return categoryRepository.findAllByParentCategoryIsNull()
                    .stream()
                    .map(categoryMapper::toCategoryResponse)
                    .toList();
        }
        return categoryRepository.getCategoriesByParentCategoryId(parenCategory.getId())
                .stream()
                .map(categoryMapper::toCategoryResponse)
                .toList();

    }
    @Transactional
    public List<CategoryResponse> getSubCategories(Long parentCategoryId) {
        return categoryRepository.getCategoriesByParentCategoryId(parentCategoryId)
                .stream().map(categoryMapper::toCategoryResponse).toList();
    }

    @Transactional
    public void create(Long parentId, String name, MultipartFile file) {
        Category parentCategory = categoryRepository.findById(parentId).orElse(null);
        Image image = file != null ? Image.parseImage(file) : null;

        Category category = Category.builder()
                .categoryName(name)
                .image(image)
                .parentCategory(parentCategory)
                .build();
        if (image != null) image.setCategory(category);

        categoryRepository.save(category);

    }

    @Transactional
    public List<CategoryResponse> update(CategoryUpdateRequest request) {
        Category category = categoryRepository.findById(request.getId()).orElseThrow(() -> new NotFoundException("Not found"));
        category.setCategoryName(request.getCategoryName());
        return getCategories(category.getParentCategory());
    }

    @Transactional
    public List<CategoryResponse> deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found"));
        if (category.getParentCategory() != null) {
            category.getProducts().forEach(p -> productService.deleteById(p.getId()));
        } else {
            category.getCategories().forEach((c) -> {
                c.getProducts().forEach(p -> productService.deleteById(p.getId()));
            });
        }
        categoryRepository.delete(category);
        return getCategories(null);
    }


}

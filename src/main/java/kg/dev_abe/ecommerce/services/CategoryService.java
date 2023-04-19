package kg.dev_abe.ecommerce.services;

import jakarta.transaction.Transactional;
import kg.dev_abe.ecommerce.dto.request.CategoryRequest;
import kg.dev_abe.ecommerce.dto.request.CategoryUpdateRequest;
import kg.dev_abe.ecommerce.dto.request.ImageRequest;
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

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAllByParentCategoryIsNull()
                .stream()
                .map(categoryMapper::toCategoryResponse)
                .toList();
    }

    public List<CategoryResponse> getSubCategoriesByParentCatId(Long categoryId) {
        return categoryRepository.getCategoriesByParentCategoryId(categoryId)
                .stream()
                .map(categoryMapper::toCategoryResponse)
                .toList();
    }

    public List<CategoryResponse> create(CategoryRequest request) {
        Category parentCategory = categoryRepository.findById(request.getParentCategoryId()).orElse(null);
        ImageRequest requestImage = request.getImage();
        Image image = (requestImage != null) ? new Image(requestImage.getImageData(), requestImage.getFileType()) : null;

        categoryRepository.save(Category.builder()
                .categoryName(request.getCategoryName())
                .image(image)
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
        if (category.getParentCategory() == null) {
            return getAllCategories();
        } else return getSubCategoriesByParentCatId(category.getParentCategory().getId());
    }


}

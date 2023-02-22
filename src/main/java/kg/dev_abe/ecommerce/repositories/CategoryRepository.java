package kg.dev_abe.ecommerce.repositories;

import jakarta.transaction.Transactional;
import kg.dev_abe.ecommerce.dto.response.CategoryResponse;
import kg.dev_abe.ecommerce.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByCategoryName(String categoryName);

    List<CategoryResponse> getCategoriesByParentCategoryId(Long parentId);

//    @Query("select new kg.dev_abe.ecommerce.dto.response.CategoryResponse(" +
//            "c.id," +
//            "c.categoryName" +
//            ") from Category c where c.parentCategory is null ")
//    List<CategoryResponse> getAllCategories();

    List<CategoryResponse> findAllByParentCategoryIsNull();

    @Modifying
    @Transactional
    @Query("update CartItem " +
            "set product = null where id = ?1")
    void updateByIdForDeleteInCartItem(Long id);
//    @Modifying
//    @Transactional
//    @Query("delete from Category c where c.id = ?1")
//    void deleteById(Long id);
}

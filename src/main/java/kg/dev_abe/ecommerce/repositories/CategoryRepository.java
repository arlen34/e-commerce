package kg.dev_abe.ecommerce.repositories;

import kg.dev_abe.ecommerce.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByCategoryName(String categoryName);
}

package kg.dev_abe.ecommerce.repositories;

import kg.dev_abe.ecommerce.db.models.Category;
import kg.dev_abe.ecommerce.db.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByCategoryName(String categoryName);
}

package kg.dev_abe.ecommerce.repositories;

import kg.dev_abe.ecommerce.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("select p from Product p where p.category.id = ?1")
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String name, String description, Pageable pageable);
    @Override
    Page<Product> findAll(Pageable pageable);


}

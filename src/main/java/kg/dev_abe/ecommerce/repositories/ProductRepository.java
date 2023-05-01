package kg.dev_abe.ecommerce.repositories;

import kg.dev_abe.ecommerce.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @EntityGraph(value = "product-entity-graph",type = EntityGraph.EntityGraphType.FETCH)

    Page<Product> findAllByCategoryIdOrderByReceiptDateDesc(Long categoryId, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndCategoryId(String name, String description, Long categoryId, Pageable pageable);


    @Query(value = "SELECT p FROM Product p ORDER BY p.receiptDate DESC LIMIT 20")
    List<Product> findLatestProducts();


    @Query(value = "SELECT p FROM Product p ORDER BY p.sold DESC LIMIT 20")
    List<Product> findTopBySoldOrderBySoldDesc();

    @EntityGraph(value = "product-entity-graph",type = EntityGraph.EntityGraphType.FETCH)
    Optional<Product> findById(Long id);

}

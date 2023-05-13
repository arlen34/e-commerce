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

    @EntityGraph(value = "product-entity-graph",type = EntityGraph.EntityGraphType.FETCH)
    Page<Product> findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description, Pageable pageable);

    @EntityGraph(value = "product-entity-graph",type = EntityGraph.EntityGraphType.FETCH)
    Page<Product> findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndCategoryId(String name, String description, Long categoryId, Pageable pageable);

    @EntityGraph(value = "product-entity-graph",type = EntityGraph.EntityGraphType.FETCH)
    @Query(value = "SELECT p FROM Product p ORDER BY p.receiptDate DESC LIMIT 20")
    List<Product> findLatestProducts();
    @EntityGraph(value = "product-entity-graph",type = EntityGraph.EntityGraphType.FETCH)
    @Query(value = "SELECT p FROM Product p ORDER BY p.sold DESC LIMIT 20")
    List<Product> findTopBySoldOrderBySoldDesc();

    /* This is jpql query
    @Query("SELECT p FROM Product p "
            + "JOIN FETCH p.category "
            + "LEFT JOIN FETCH p.imageList "
            + "LEFT JOIN FETCH p.reviews r "
            + "LEFT JOIN FETCH r.user "
            + "WHERE p.id = :id")
     **/
    @EntityGraph(value = "product-entity-graph-with-reviews",type = EntityGraph.EntityGraphType.FETCH)
    Optional<Product> findById(Long id);

}

package kg.dev_abe.ecommerce.repositories;

import kg.dev_abe.ecommerce.models.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    @EntityGraph(value = "category-entity-graph",type = EntityGraph.EntityGraphType.FETCH)
    List<Category> getCategoriesByParentCategoryId(Long parentId);

    @EntityGraph(value = "category-entity-graph",type = EntityGraph.EntityGraphType.FETCH)
    List<Category> findAllByParentCategoryIsNull();

    /*
        @Query(value = """
            WITH RECURSIVE category_tree AS (
              SELECT id, category_name, parent_category_id, image_id
              FROM categories
              WHERE parent_category_id IS NULL
              UNION ALL
              SELECT c.id, c.category_name, c.parent_category_id, c.image_id
              FROM categories c
              INNER JOIN category_tree ct ON c.parent_category_id = ct.id
            )
            SELECT ct.id, ct.category_name, ct.parent_category_id, ct.image_id
            FROM category_tree ct
            ORDER BY ct.id;
        """, nativeQuery = true
    )
     */
    @EntityGraph(value = "category-with-subcategories",type = EntityGraph.EntityGraphType.FETCH)
    List<Category> findAll();
}

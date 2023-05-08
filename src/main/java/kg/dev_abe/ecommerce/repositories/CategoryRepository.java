package kg.dev_abe.ecommerce.repositories;

import kg.dev_abe.ecommerce.models.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> getCategoriesByParentCategoryId(Long parentId);



    @EntityGraph(value = "category-entity-graph",type = EntityGraph.EntityGraphType.FETCH)
    List<Category> findAllByParentCategoryIsNull();

}

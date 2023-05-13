package kg.dev_abe.ecommerce.repositories;

import kg.dev_abe.ecommerce.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    @EntityGraph(value = "order-entity-graph",type = EntityGraph.EntityGraphType.FETCH)
    List<Order> findAllByUserEmail(String email);
    @EntityGraph(value = "order-entity-graph",type = EntityGraph.EntityGraphType.FETCH)
    Page<Order> findAll(Pageable pageable);

    @EntityGraph(value = "order-entity-graph",type = EntityGraph.EntityGraphType.FETCH)
    Optional<Order> findById(Long orderId);
}



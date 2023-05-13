package kg.dev_abe.ecommerce.repositories;

import kg.dev_abe.ecommerce.models.Order;
import kg.dev_abe.ecommerce.models.OrderItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    @Modifying
    @Query("DELETE FROM OrderItem o where o.product.id = :productId")
    void deleteByProductId(Long productId);


    @EntityGraph(value = "order-item-entity-graph")
    List<OrderItem> findByOrder(Order order);
}

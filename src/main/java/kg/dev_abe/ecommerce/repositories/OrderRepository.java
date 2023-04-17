package kg.dev_abe.ecommerce.repositories;

import kg.dev_abe.ecommerce.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByUserEmail(String email);
    Page<Order> findAll(Pageable pageable);
}

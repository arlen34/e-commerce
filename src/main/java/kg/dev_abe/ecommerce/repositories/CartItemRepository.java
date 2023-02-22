package kg.dev_abe.ecommerce.repositories;

import kg.dev_abe.ecommerce.models.CartItem;
import kg.dev_abe.ecommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
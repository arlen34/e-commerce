package kg.dev_abe.ecommerce.repositories;

import kg.dev_abe.ecommerce.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>{
    Cart findByUserEmail(String email);
}
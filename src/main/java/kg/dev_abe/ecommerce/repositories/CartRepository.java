package kg.dev_abe.ecommerce.repositories;

import kg.dev_abe.ecommerce.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long>{
    Cart findByUserEmail(String email);
}
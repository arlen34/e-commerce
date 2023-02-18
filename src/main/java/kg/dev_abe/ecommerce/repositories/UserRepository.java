package kg.dev_abe.ecommerce.repositories;

import kg.dev_abe.ecommerce.db.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByName(String name);
}

package kg.dev_abe.ecommerce.repositories;

import kg.dev_abe.ecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    boolean existsUserByEmail(String email);

}

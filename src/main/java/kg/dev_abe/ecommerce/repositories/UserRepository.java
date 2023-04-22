package kg.dev_abe.ecommerce.repositories;

import kg.dev_abe.ecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    User findUserByEmail(String email);

    boolean existsUserByEmail(String email);

    @Query("select u from User u where u.role = 'USER' or u.role = 'ADMIN'")
    List<User> findUsers();
}

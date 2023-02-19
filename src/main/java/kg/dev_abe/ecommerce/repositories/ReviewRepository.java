package kg.dev_abe.ecommerce.repositories;

import kg.dev_abe.ecommerce.models.Product;
import kg.dev_abe.ecommerce.models.Review;
import kg.dev_abe.ecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findAllByProductOrderByDateDesc(Product product);
    List<Review> findByUser(User user);
}

package kg.dev_abe.ecommerce.repositories;

import kg.dev_abe.ecommerce.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<Image,Long> {
}

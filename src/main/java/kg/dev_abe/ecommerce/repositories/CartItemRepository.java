package kg.dev_abe.ecommerce.repositories;

import kg.dev_abe.ecommerce.models.Cart;
import kg.dev_abe.ecommerce.models.CartItem;
import kg.dev_abe.ecommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Modifying
    @Transactional
    @Query("delete from CartItem where id = ?1")
    void updateForDelete(Long id);

    CartItem findByProductIdAndAndCart_Id(Long productId, Long cartId);

    List<CartItem> findByCart(Cart cart);

    @Query("delete from CartItem where id = ?1")
    void deleteCartItemById(Long id);
}
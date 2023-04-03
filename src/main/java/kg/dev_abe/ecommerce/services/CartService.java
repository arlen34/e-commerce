package kg.dev_abe.ecommerce.services;

import kg.dev_abe.ecommerce.dto.response.SimpleResponse;
import kg.dev_abe.ecommerce.exceptions.ECommerceException;
import kg.dev_abe.ecommerce.models.Cart;
import kg.dev_abe.ecommerce.models.CartItem;
import kg.dev_abe.ecommerce.models.User;
import kg.dev_abe.ecommerce.repositories.CartItemRepository;
import kg.dev_abe.ecommerce.repositories.CartRepository;
import kg.dev_abe.ecommerce.repositories.ProductRepository;
import kg.dev_abe.ecommerce.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CartService {

    private final UserRepository userRepository;
    private final CartItemRepository itemRepository;
    private final ProductRepository productRepo;
    private final CartRepository cartRepository;

    public SimpleResponse addToCart(Long productId, Principal principal) {
        Cart cart = cartRepository.findByUserEmail(principal.getName());
        if (!cartRepository.existsById(cart.getId())) {
            cart = Cart.builder()
                    .user(userRepository.findByEmail(principal.getName()).get())
                    .build();
        }
        CartItem cartItem = itemRepository.findByProductIdAndAndCart_Id(productId, cart.getId());
        if (cartItem != null && cartItem.getCart() == cart) {
            throw new ECommerceException("This product already add!");
        }
        itemRepository.save(CartItem.builder()
                .cart(cart)
                .product(productRepo.findById(productId).orElseThrow())
                .quantity(1)
                .build());
        return new SimpleResponse("Successfully added to cart", "SAVE");
    }

    public SimpleResponse changeQuantity(Long cartItemId, Integer quantity) {
        CartItem cartItem = itemRepository.findById(cartItemId).get();
        cartItem.setQuantity(quantity);
        itemRepository.saveAndFlush(cartItem);
        return new SimpleResponse("Successfully changed", "CHANGE");
    }

}

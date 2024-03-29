package kg.dev_abe.ecommerce.services;

import jakarta.transaction.Transactional;
import kg.dev_abe.ecommerce.dto.response.CartItemResponse;
import kg.dev_abe.ecommerce.dto.response.SimpleResponse;
import kg.dev_abe.ecommerce.exceptions.ECommerceException;
import kg.dev_abe.ecommerce.exceptions.NotFoundException;
import kg.dev_abe.ecommerce.mappers.CartItemMapper;
import kg.dev_abe.ecommerce.mappers.ProductMapper;
import kg.dev_abe.ecommerce.models.Cart;
import kg.dev_abe.ecommerce.models.CartItem;
import kg.dev_abe.ecommerce.repositories.CartItemRepository;
import kg.dev_abe.ecommerce.repositories.CartRepository;
import kg.dev_abe.ecommerce.repositories.ProductRepository;
import kg.dev_abe.ecommerce.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class CartService {

    private final UserRepository userRepository;
    private final CartItemRepository itemRepository;
    private final ProductRepository productRepo;
    private final CartRepository cartRepository;

    private final ProductMapper responseMapper;
    private final CartItemMapper cartItemMapper;

    public CartItem getById(Long id) {
        return itemRepository.findById(id).orElseThrow(NoClassDefFoundError::new);
    }

    public CartItemResponse addToCart(Long productId, Principal principal) {
        Cart cart = cartRepository.findByUserEmail(principal.getName());

        if (cart ==null || !cartRepository.existsById(cart.getId())) {
            cart = Cart.builder().user(userRepository.findByEmail(principal.getName()).get()).build();
            cartRepository.save(cart);
        }

        CartItem cartItem = itemRepository.findByProductIdAndAndCart_Id(productId, cart.getId());
        if (cartItem != null && cartItem.getCart() == cart) {
            throw new ECommerceException("This product already add!");
        }
        cartItem = CartItem.builder()
                .cart(cart)
                .product(productRepo.findById(productId).orElseThrow())
                .quantity(1)
                .build();
        itemRepository.save(cartItem);
        return cartItemMapper.toCartItemResponse(cartItem);
    }

    public SimpleResponse changeQuantity(Long cartItemId, Integer quantity) {
        CartItem cartItem = itemRepository.findById(cartItemId).orElseThrow(NotFoundException::new);
        if (cartItem.getProduct().getAmount() < quantity) {
            throw new ECommerceException("The quantity should not be more than product amount!");
        }
        cartItem.setQuantity(quantity);
        itemRepository.saveAndFlush(cartItem);
        return new SimpleResponse("Successfully changed", "CHANGE");
    }


    public List<CartItemResponse> getCartItems(Principal principal) {
        List<CartItem> cartItems = itemRepository.findByCartOrderById(cartRepository.findByUserEmail(principal.getName()));
        return cartItems.stream()
                .map(cartItemMapper::toCartItemResponse)
                .toList();
    }

    public SimpleResponse deleteItemFromCart(Long cartItemId){
        itemRepository.deleteById(cartItemId);
        return new SimpleResponse("The cart successfully deleted", "DELETE");
    }
    public void clearCart(Principal principal) {
        Cart cart = cartRepository.findByUserEmail(principal.getName());
        cart.getCartItems().clear();

    }
}

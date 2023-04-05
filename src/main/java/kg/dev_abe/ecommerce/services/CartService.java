package kg.dev_abe.ecommerce.services;

import kg.dev_abe.ecommerce.dto.response.CartItemResponse;
import kg.dev_abe.ecommerce.dto.response.SimpleResponse;
import kg.dev_abe.ecommerce.exceptions.ECommerceException;
import kg.dev_abe.ecommerce.mappers.ProductResponseMapper;
import kg.dev_abe.ecommerce.models.Cart;
import kg.dev_abe.ecommerce.models.CartItem;
import kg.dev_abe.ecommerce.repositories.CartItemRepository;
import kg.dev_abe.ecommerce.repositories.CartRepository;
import kg.dev_abe.ecommerce.repositories.ProductRepository;
import kg.dev_abe.ecommerce.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@AllArgsConstructor
public class CartService {

    private final UserRepository userRepository;
    private final CartItemRepository itemRepository;
    private final ProductRepository productRepo;
    private final CartRepository cartRepository;

    private final ProductResponseMapper responseMapper;

    public SimpleResponse addToCart(Long productId, Principal principal) {
        Cart cart = cartRepository.findByUserEmail(principal.getName());

        if (cart ==null || !cartRepository.existsById(cart.getId())) {
            cart = Cart.builder().user(userRepository.findByEmail(principal.getName()).get()).build();
            cartRepository.save(cart);
        }
        CartItem cartItem = itemRepository.findByProductIdAndAndCart_Id(productId, cart.getId());
        if (cartItem != null && cartItem.getCart() == cart) {
            throw new ECommerceException("This product already add!");
        }
        itemRepository.save(CartItem.builder().cart(cart).product(productRepo.findById(productId).orElseThrow()).quantity(1).build());
        return new SimpleResponse("Successfully added to cart", "SAVE");
    }

    public SimpleResponse changeQuantity(Long cartItemId, Integer quantity) {
        CartItem cartItem = itemRepository.findById(cartItemId).get();
        if (cartItem.getProduct().getAmount() < quantity) {
            throw new ECommerceException("The quantity must not be more product amount!");
        }
        cartItem.setQuantity(quantity);
        itemRepository.saveAndFlush(cartItem);
        return new SimpleResponse("Successfully changed", "CHANGE");
    }

    public List<CartItemResponse> getCartItems(Principal principal) {
        List<CartItem> cartItems = itemRepository.findByCart(cartRepository.findByUserEmail(principal.getName()));
        return cartItems.stream().map(cartItem ->
                new CartItemResponse(
                        cartItem.getId(),
                        cartItem.getQuantity(),
                        responseMapper.getProductsFromCart(cartItem))).toList();
    }
}

package kg.dev_abe.ecommerce.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.dev_abe.ecommerce.dto.response.CartItemResponse;
import kg.dev_abe.ecommerce.dto.response.SimpleResponse;
import kg.dev_abe.ecommerce.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api/carts")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Cart API",description = "The carts API for users")
public class CartController {

    private final CartService cartService;

    @Operation(summary = "Add product to cart",
            description = "This endpoint returns message about adding product to cart")
    @PostMapping("/{productId}")
    public CartItemResponse addToCart(@PathVariable Long productId, Principal principal) {
        return cartService.addToCart(productId, principal);
    }

    @Operation(summary = "Change quantity of product",
            description = "This endpoint returns the changed product quantity")
    @PatchMapping("/{cartItemId}")
    public SimpleResponse changeQuantity(@PathVariable Long cartItemId, Integer quantity) {
        return cartService.changeQuantity(cartItemId, quantity);
    }
    @Operation(summary = "Get your carts",
            description = "This endpoint returns the chose products in their cart")
    @GetMapping
    public List<CartItemResponse> getCartItems(Principal principal) {
        return cartService.getCartItems(principal);
    }
    @Operation(summary = "Delete cartItem from cart",
            description = "This endpoint delete item from shopping cart"
    )
    @DeleteMapping("/{cartItemId}")
    public SimpleResponse delete(@PathVariable Long cartItemId){
        return cartService.deleteItemFromCart(cartItemId);
    }


}

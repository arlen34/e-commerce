package kg.dev_abe.ecommerce.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.dev_abe.ecommerce.dto.response.SimpleResponse;
import kg.dev_abe.ecommerce.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/api/carts")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Cart API",description = "The carts API for users")
public class CartController {

    private final CartService cartService;

    @Operation(summary = "Create parent categories or sub categories",
            description = "This endpoint returns the created categories")
    @PostMapping("/{productId}")
    public SimpleResponse addToCart(@PathVariable Long productId, Principal principal) {
        return cartService.addToCart(productId, principal);
    }

    @Operation(summary = "Change quantity of product",
            description = "This endpoint returns the changed product quantity")
    @PatchMapping("/{cartItemId}")
    public SimpleResponse changeQuantity(@PathVariable Long cartItemId, Integer quantity) {
        return cartService.changeQuantity(cartItemId, quantity);
    }
}

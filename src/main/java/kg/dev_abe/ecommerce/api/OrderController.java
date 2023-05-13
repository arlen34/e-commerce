package kg.dev_abe.ecommerce.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.dev_abe.ecommerce.dto.request.OrderRequest;
import kg.dev_abe.ecommerce.dto.request.OrderRequestFromCart;
import kg.dev_abe.ecommerce.dto.response.OrderDetailsResponse;
import kg.dev_abe.ecommerce.dto.response.OrderResponse;
import kg.dev_abe.ecommerce.services.OrderService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Order API", description = "The order API for customers")
public class OrderController {
    private OrderService orderService;

    @Operation(summary = "Get all user orders", description = "This endpoint returns all user orders")
    @GetMapping("/my-orders")
    public List<OrderResponse> getAllUserOrders(Principal principal) {
        return orderService.getUserOrders(principal);
    }

    @Operation(summary = "Get all orders", description = "This endpoint returns all orders")
    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER_ADMIN')")
    @GetMapping("/all-orders")
    public Page<OrderResponse> getAllOrders(@PageableDefault(sort = "orderId", direction = Sort.Direction.DESC) Pageable pageable) {
        return orderService.getAllOrders(pageable);
    }

    @Operation(summary = "Get order by id", description = "This endpoint returns order by id")
    @GetMapping("/{orderId}")
    public OrderDetailsResponse getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @SneakyThrows
    @GetMapping("/{orderId}/generate-invoice")
    @Operation(summary = "Generate invoice file", description = "This endpoint return invoice file for order")
    public ResponseEntity<byte[]> generateInvoice(@PathVariable Long orderId) {
        return orderService.generateInvoice(orderId);
    }
    @Operation(summary = "Place order", description = "This endpoint place order from cart")
    @PostMapping("/place-order")
    public void placeOrder(@RequestBody OrderRequestFromCart orderRequest, Principal principal) {
         orderService.placeOrder(principal, orderRequest);
    }
    @Operation(summary = "Create order from admin panel", description = "This endpoint create order")
    @PostMapping("/create-order")
    public void createOrder(@RequestBody OrderRequest orderRequest, Principal principal) {
         orderService.createOrder(orderRequest, principal);
    }

    @Operation(summary = "Delete order", description = "This endpoint delete order by id")
    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER_ADMIN')")
    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
         orderService.deleteOrder(orderId);
    }


    @PatchMapping("/{orderId}/cancel")
    @Operation(summary = "Cancel order", description = "This endpoint cancel order by id")
    public void cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
    }

    @PatchMapping("/{orderId}/confirm")
    @Operation(summary = "Confirm order", description = "This endpoint confirm order by id")
    public void confirmOrder(@PathVariable Long orderId) {
        orderService.confirmOrder(orderId);
    }

    @PatchMapping("/{orderId}/complete")
    @Operation(summary = "Complete order", description = "This endpoint complete order by id")
    public void completeOrder(@PathVariable Long orderId) {
          orderService.completeOrder(orderId);
    }

}

package kg.dev_abe.ecommerce.services;

import kg.dev_abe.ecommerce.dto.request.OrderRequest;
import kg.dev_abe.ecommerce.dto.request.OrderRequestFromCart;
import kg.dev_abe.ecommerce.dto.response.OrderResponse;
import kg.dev_abe.ecommerce.dto.response.SimpleResponse;
import kg.dev_abe.ecommerce.exceptions.ECommerceException;
import kg.dev_abe.ecommerce.exceptions.NotFoundException;
import kg.dev_abe.ecommerce.mappers.OrderItemMapper;
import kg.dev_abe.ecommerce.mappers.OrderMapper;
import kg.dev_abe.ecommerce.models.Order;
import kg.dev_abe.ecommerce.models.OrderItem;
import kg.dev_abe.ecommerce.models.Product;
import kg.dev_abe.ecommerce.models.User;
import kg.dev_abe.ecommerce.models.enums.OrderStatus;
import kg.dev_abe.ecommerce.repositories.CartItemRepository;
import kg.dev_abe.ecommerce.repositories.OrderRepository;
import kg.dev_abe.ecommerce.repositories.ProductRepository;
import kg.dev_abe.ecommerce.util.InvoiceGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;


@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    private UserService userService;
    private ProductRepository productRepository;
    private CartItemRepository cartItemRepository;
    private OrderMapper orderMapper;
    private OrderItemMapper orderItemMapper;
    private EmailService emailService;
    private InvoiceGenerator invoiceGenerator;

    public List<OrderResponse> getUserOrders(Principal principal) {
        return orderRepository.findAllByUserEmail(principal.getName())
                .stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    public OrderResponse getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::toOrderResponse)
                .orElseThrow();
    }


    private void saveOrder(User user, List<OrderItem> orderItems) {
        Order order = Order.builder()
                .user(user)
                .orderStatus(OrderStatus.AWAITING)
                .orderDate(LocalDate.now())
                .orderItems(orderItems)
                .totalPrice(orderItems.stream()
                        .mapToDouble(OrderItem::getTotalPrice)
                        .sum())
                .build();

        orderItems.forEach((orderItem) -> {
            orderItem.setOrder(order);
            orderItem.getProduct().setAmount(orderItem.getProduct().getAmount() - orderItem.getQuantity());
        });

        orderRepository.save(order);
    }

    public SimpleResponse placeOrder(Principal principal, OrderRequestFromCart orderRequest) {
        User user = userService.findUserByEmail(principal.getName());

        List<OrderItem> orderItems = orderRequest.getCartItemIds().stream()
                .map(id -> cartItemRepository.findById(id).get())
                .map(orderItemMapper::toOrderItem)
                .toList();

        saveOrder(user, orderItems);

        emailService.sendEmail(user.getEmail(), "Order placing", "Your order placed successfully");

        return new SimpleResponse("Order placed successfully", "Saved");
    }


    //write create order from OrderRequest
    public SimpleResponse createOrder(OrderRequest orderRequest, Principal principal) {
        User user = userService.findUserByEmail(principal.getName());

        List<OrderItem> orderItems = orderRequest.getOrderItems().stream()
                .map((orderItem) -> {
                    Product product = productRepository.findById(orderItem.getProductId()).orElseThrow(() -> new NotFoundException("Product not found"));
                    if (product.getAmount() < orderItem.getQuantity()) {
                        throw new ECommerceException("Not enough product in stock");
                    }
                    return OrderItem.builder()
                            .product(product)
                            .quantity(orderItem.getQuantity())
                            .totalPrice(product.getPrice() * orderItem.getQuantity())
                            .build();
                }).toList();


        saveOrder(user, orderItems);

        emailService.sendEmail(user.getEmail(), "Order placing", "Your order placed successfully");

        return new SimpleResponse("Order placed successfully", "Saved");
    }

    public SimpleResponse deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
        return new SimpleResponse("Order deleted successfully", "Deleted");
    }

    public Page<OrderResponse> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(orderMapper::toOrderResponse);
    }

    @Async
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(NotFoundException::new);

        if (order.getOrderStatus() == OrderStatus.COMPLETED || order.getOrderStatus() == OrderStatus.CANCELED)
            throw new ECommerceException("Order already completed or canceled");
        order.getOrderItems().forEach((orderItem) -> orderItem.getProduct().setAmount(orderItem.getProduct().getAmount() + orderItem.getQuantity()));
        order.setOrderStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
        emailService.sendEmail(order.getUser().getEmail(), "Order cancellation", "Your order canceled successfully");
    }

    //write confirm order method
    @Async
    public void confirmOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(NotFoundException::new);
        if (order.getOrderStatus() == OrderStatus.COMPLETED || order.getOrderStatus() == OrderStatus.CANCELED)
            throw new ECommerceException("Order already completed or canceled");

        order.getOrderItems().forEach((orderItem) -> orderItem.getProduct().setAmount(orderItem.getProduct().getAmount() - orderItem.getQuantity()));
        order.setOrderStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);

        emailService.sendEmail(order.getUser().getEmail(), "Order confirmation", "Your order confirmed successfully");

    }

    public SimpleResponse completeOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(NotFoundException::new);
        if (order.getOrderStatus() == OrderStatus.COMPLETED || order.getOrderStatus() == OrderStatus.CANCELED)
            throw new ECommerceException("Order already completed or canceled");

        order.setOrderStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);

        return new SimpleResponse("Order completed successfully", "Completed");
    }



    public  ResponseEntity<byte[]> generateInvoice(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(NotFoundException::new);
        byte[] pdfBytes  = invoiceGenerator.generateInvoice(order);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "order-invoice.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}


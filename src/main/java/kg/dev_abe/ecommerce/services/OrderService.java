package kg.dev_abe.ecommerce.services;

import kg.dev_abe.ecommerce.dto.request.OrderRequest;
import kg.dev_abe.ecommerce.dto.response.OrderResponse;
import kg.dev_abe.ecommerce.dto.response.SimpleResponse;
import kg.dev_abe.ecommerce.exceptions.ECommerceException;
import kg.dev_abe.ecommerce.exceptions.NotFoundException;
import kg.dev_abe.ecommerce.mappers.OrderItemMapper;
import kg.dev_abe.ecommerce.mappers.OrderMapper;
import kg.dev_abe.ecommerce.models.Order;
import kg.dev_abe.ecommerce.models.OrderItem;
import kg.dev_abe.ecommerce.models.User;
import kg.dev_abe.ecommerce.models.enums.OrderStatus;
import kg.dev_abe.ecommerce.repositories.CartItemRepository;
import kg.dev_abe.ecommerce.repositories.OrderRepository;
import kg.dev_abe.ecommerce.repositories.UserRepository;
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

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;


@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    private UserRepository userRepository;
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


    public SimpleResponse placeOrder(Principal principal, OrderRequest orderRequest) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();

        List<OrderItem> orderItems = orderRequest.getCartItemIds().stream()
                .map(id -> cartItemRepository.findById(id).get())
                .map(orderItemMapper::toOrderItem)
                .toList();

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
        userRepository.save(user);

        user.getCart().getCartItems().clear();

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

    @Async
    public void completeOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(NotFoundException::new);
        if (order.getOrderStatus() == OrderStatus.COMPLETED || order.getOrderStatus() == OrderStatus.CANCELED)
            order.setOrderStatus(OrderStatus.COMPLETED);

        orderRepository.save(order);
        emailService.sendEmail(order.getUser().getEmail(), "Order completed successfully", "Your order completed successfully");
    }


    public ResponseEntity<byte[]> generateInvoice(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(NotFoundException::new);
        byte[] pdfBytes;
        try {
            pdfBytes = invoiceGenerator.generateInvoice(order);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "order-invoice.pdf");

        return new ResponseEntity<>(pdfBytes,headers,HttpStatus.OK);
    }
}

